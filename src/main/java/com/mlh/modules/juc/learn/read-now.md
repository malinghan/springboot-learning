- ReentrantLock.lock

```
if(lock 没有被别的thread抢) 
      获取这个lock 返回 1
if(lock 被自己抢了)
       获取这个lock 返回i++
if(lock 被其他thread抢了)
       加入等待队列，阻塞自旋, 保存1
```

- <b>AQS中acquire的实现</b>
```
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}
```
- 如果tryAcquire(arg)成功，那就没有问题，已经拿到锁，整个lock()过程就结束了。如果失败进行操作2。 `tryAcquire`
- 创建一个独占节点（Node）并且此节点加入CHL队列末尾。进行操作3。 `addWaiter`
- 自旋尝试获取锁，失败根据前一个节点来决定是否挂起（park()），直到成功获取到锁。进行操作4。 `acquireQueued`
- 如果当前线程已经中断过，那么就中断当前线程（清除中断位）。`selfInterrupt`

- tryAcquire(acquires)
```
protected final boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {//c == 0 锁未被其他现在占用
            if (
            isFirst(current)// 判断AQS是否为空或者当前线程是否在队列头
             &&
                compareAndSetState(0, acquires) 
                ) {
                setExclusiveOwnerThread(current)
                ;
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {//可重入锁
            int nextc = c + acquires;//
            if (nextc < 0)
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }
}
```

- acquireQueued(node,arg)
```
// 自旋请求锁，如果可能的话挂起线程，直到得到锁，
// 返回当前线程是否中断过（如果park()过并且中断过的话有一个interrupted中断位）
final boolean acquireQueued(final Node node, int arg) {
    try {
        boolean interrupted = false;//
        for (;;) {
            final Node p = node.predecessor();//前驱
            if (p == head && tryAcquire(arg)) { //判断是否是头结点
                setHead(node); // 拿到了锁,会将当前持有锁的节点设为头结点
                p.next = null; // help GC 
                return interrupted;
            }
            if (shouldParkAfterFailedAcquire(p, node) //检测当前节点是否应该park()
                &&
                parkAndCheckInterrupt()) // 
                interrupted = true;
        }
    } catch (RuntimeException ex) {
        cancelAcquire(node);
        throw ex;
    }
}
```

- shouldParkAfterFailedAcquire
```
 /**
     * Checks and updates status for a node that failed to acquire.
     * Returns true if thread should block. This is the main signal
     * control in all acquire loops.  Requires that pred == node.prev.
     *  afterFailedAcquire 检查一个node是否需要park
     * @param pred node's predecessor holding status 前驱
     * @param node the node
     * @return {@code true} if thread should block
     */
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus; 
        // 前面的都没有获取到锁，轮不到你获取
        if (ws == Node.SIGNAL) //waitStatus<0 前面的节点没有获取到锁，那么返回true，表示当前节点（线程）就应该park()
            /*
             * This node has already set status asking a release
             * to signal it, so it can safely park.
             */
            return true;
         // CANCELLED 取消获取锁，我的前驱不想获取锁，就占个坑
        if (ws > 0) { // 如果前一个节点的等待状态waitStatus>0.也就是前一个节点被CANCELLED了
            /*
             * Predecessor was cancelled. Skip over predecessors and
             * indicate retry.
              前驱被取消了，跳过他们
             */
            do {
                node.prev = pred = pred.prev; //递归
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else { 
            /*
             * waitStatus must be 0 or PROPAGATE.  Indicate that we
             * need a signal, but don't park yet.  Caller will need to
             * retry to make sure it cannot acquire before parking.
              告诉前面的伙计，我在等他呢，然后park自己
              修改前一个节点状态位为SINGAL,表示后面有节点等待你处理,需要根据它的等待状态来决定是否该park()
             */
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }
```

- unlock
>  释放响应的资源，并且唤醒AQS队列中有效的继任节点
  

```
public final boolean release(int arg) {
    if (tryRelease(arg)) {
        Node h = head;
        if (h != null && h.waitStatus != 0)//头结点
            unparkSuccessor(h);// 唤醒继任节点
        return true;
    }
    return false;
}


```

- java.util.concurrent.locks.ReentrantLock.Sync.tryRelease(int)
```
protected final boolean tryRelease(int releases) {
    int c = getState() - releases;//将AQS状态位减少要释放的次数（对于独占锁而言总是1）
    if (Thread.currentThread() != getExclusiveOwnerThread())//判断当前线程是否持有锁
        throw new IllegalMonitorStateException();
    boolean free = false;
    if (c == 0) {//如果剩余的状态位0（也就是没有线程持有锁）
        free = true;////如果没有线程持有锁就返回true
        setExclusiveOwnerThread(null);//将持有锁释放
    }
    setState(c); //将剩余的状态位写回AQS   
    return free;
}
```

- unparkSuccessor(Node node)
```
private void unparkSuccessor(Node node) {
        //此时node是需要是需要释放锁的头结点

        //清空头结点的waitStatus，也就是不再需要锁了
        compareAndSetWaitStatus(node, Node.SIGNAL, 0);

        //从头结点的下一个节点开始寻找继任节点，当且仅当继任节点的waitStatus<=0才是有效继任节点，否则将这些waitStatus>0（也就是CANCELLED的节点）从AQS队列中剔除  
       //这里并没有从head->tail开始寻找，而是从tail->head寻找最后一个有效节点。
       //解释在这里 http://www.blogjava.net/xylz/archive/2010/07/08/325540.html#377512

        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }

        //如果找到一个有效的继任节点，就唤醒此节点线程
        if (s != null)
            LockSupport.unpark(s.thread);
    }
```

- 公平锁和非公平锁只是在入AQS的CLH队列之前有所差别，一旦进入了队列，所有线程都是按照队列中先来后到的顺序请求锁

- Condition

```
void await() throws InterruptedException; // 等待
void awaitUninterruptibly();
long awaitNanos(long nanosTimeout) throws InterruptedException;
boolean await(long time, TimeUnit unit) throws InterruptedException;
boolean awaitUntil(Date deadline) throws InterruptedException;
void signal();
void signalAll();
```

- Condition.await
```
/**
         * Implements interruptible condition wait.
         * <ol>
         * <li>1. If current thread is interrupted, throw InterruptedException.
         * <li>2. Save lock state returned by {@link #getState}.
         * <li>3. Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li>4. Block until signalled or interrupted.
         * <li>5. Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * <li>6. If interrupted while blocked in step 4, throw InterruptedException.
         * </ol>
         */
        public final void await() throws InterruptedException {
            if (Thread.interrupted()) 
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);//保存锁状态
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }
```

- signal/signalAll
 signal就是唤醒Condition队列中的第一个非CANCELLED节点线程
 signalAll就是唤醒所有非CANCELLED节点线程,当然了遇到CANCELLED线程就需要将其从FIFO队列中剔除
 
 