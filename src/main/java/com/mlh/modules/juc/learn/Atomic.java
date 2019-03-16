package com.mlh.modules.juc.learn;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import  java.util.concurrent.atomic.*;
import  java.util.concurrent.locks.*;


public class Atomic {
    Lock lock = new Lock() {
        //获取锁，如果获取不到，当前线程将一直休眠，直到可用为止
        @Override
        public void lock() {
            //
        }

        /**
         * 如果当前线程未被中断，则获取锁
         * 如果获取到，返回之
         * 如果未获取到，当前显示等待，休眠
         *    1. 锁释放了，当前线程获取到了，停止休眠
         *    2. 其他线程中断了当前线程，停止休眠
         * 如果当前线程
         *    1. 进入此方法时已设置了中断状态
         *    2. 获取锁时被中断
         *  则抛出InterruptedException,并清除当前线程的已中断状态
         * @throws InterruptedException
         */
        @Override
        public void lockInterruptibly() throws InterruptedException {
            //
        }

        /**
         * 尝试获取锁
         * 若可用 返回 true
         * 若不可用  返回 false
         * @return
         */
        @Override
        public boolean tryLock() {
            //
            return false;
        }

        /**
         * 在给定时间内尝试获取锁
         * 若可用 返回 true
         * 若不可用  线程休眠 唤醒条件
         * 1. 锁由当前线程获取
         * 2. 其他线程中断了当前线程
         * 3. 超过等待时间
         * curtime > time 超过等待时间 返回 false
         * time<=0 不等待
         * @return
         */
        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            //
            return false;
        }
        //
        @Override
        public void unlock() {
            //
        }

        /**
         * 等待队列
         * @return
         */
        @Override
        public Condition newCondition() {
            //
            return null;
        }
    };


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = new Condition() {
            @Override
            public void await() throws InterruptedException {
                //
            }

            @Override
            public void awaitUninterruptibly() {
                   //
            }

            @Override
            public long awaitNanos(long nanosTimeout) throws InterruptedException {
                return 0;
            }

            @Override
            public boolean await(long time, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public boolean awaitUntil(Date deadline) throws InterruptedException {
                return false;
            }

            @Override
            public void signal() {

            }

            @Override
            public void signalAll() {

            }
        };

        Thread thread =new Thread();

        lock.lock();

        lock.unlock();

        lock.tryLock();

        lock.getHoldCount();

        lock.getWaitQueueLength(condition);

        lock.getQueueLength();

        lock.hasQueuedThread(thread);

        lock.isFair();

        lock.isHeldByCurrentThread();

        lock.hasWaiters(condition);


        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        AbstractQueuedSynchronizer aqs =new AbstractQueuedSynchronizer() {
            //试图在独占模式下获取对象状态。此方法应该查询是否允许它在独占模式下获取对象状态，如果允许，则获取它。

            /**
             * 1. 如果tryAcquire(arg)成功，那就没有问题，已经拿到锁，整个lock()过程就结束了。如果失败进行操作2。
             * 2. 创建一个独占节点（Node）并且此节点加入CHL队列末尾。进行操作3。
               3. 自旋尝试获取锁，失败根据前一个节点来决定是否挂起（park()），直到成功获取到锁。进行操作4。
               4. 如果当前线程已经中断过，那么就中断当前线程（清除中断位）。

             判断AQS是否为空或者当前线程是否在队列头:
             1. 如果当前锁有其它线程持有，c!=0，进行操作2。否则，如果当前线程在AQS队列头部，则尝试将AQS状态state设为acquires（等于1），成功后将AQS独占线程设为当前线程返回true，否则进行2。这里可以看到compareAndSetState就是使用了CAS操作
             2. 判断当前线程与AQS的独占线程是否相同，如果相同，那么就将当前状态位加1（这里+1后结果为负数后面会讲，这里暂且不理它），修改状态位，返回true，否则进行3。这里之所以不是将当前状态位设置为1，而是修改为旧值+1呢？这是因为ReentrantLock是可重入锁，同一个线程每持有一次就+1

             3. 返回false。

             公平锁多了一个判断当前节点是否在队列头
             * @param arg
             * @return
             */
            @Override
            protected boolean tryAcquire(int arg) {
//                if (!tryAcquire(arg) &&
//                        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
//                    selfInterrupt();

//                final Thread current = Thread.currentThread();
//                int c = getState();
//                if (c == 0) {
//                    if (isFirst(current) &&
//                            compareAndSetState(0, acquires)) {
//                        setExclusiveOwnerThread(current);
//                        return true;
//                    }
//                }
//                else if (current == getExclusiveOwnerThread()) {
//                    int nextc = c + acquires;
//                    if (nextc < 0)
//                        throw new Error("Maximum lock count exceeded");
//                    setState(nextc);
//                    return true;
//                }
//                return false;
//            }
                return super.tryAcquire(arg);
            }

            //试图设置状态来反映独占模式下的一个释放。 此方法总是由正在执行释放的线程调用。释放锁可能失败或者抛出异常
            @Override
            protected boolean tryRelease(int arg) {
                return super.tryRelease(arg);
            }

            // 试图在共享模式下获取对象状态
            @Override
            protected int tryAcquireShared(int arg) {
                return super.tryAcquireShared(arg);
            }

             // 试图设置状态来反映共享模式下的一个释放。
            @Override
            protected boolean tryReleaseShared(int arg) {
                return super.tryReleaseShared(arg);
            }

           //如果对于当前（正调用的）线程，同步是以独占方式进行的，则返回 true。
            @Override
            protected boolean isHeldExclusively() {
                return super.isHeldExclusively();
            }


           // addWaiter(mode)




            //Object method

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }


        };

        try {
            aqs.acquireInterruptibly(10);
            aqs.acquire(10);
            aqs.hasQueuedThreads();
            aqs.tryAcquireNanos(11,TimeUnit.NANOSECONDS.toMillis(10));
            aqs.getFirstQueuedThread();
            aqs.hasContended();
//            aqs.hasWaiters(condition);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

//    HashMap
    //TreeSet
//    Queue
//        Deque
//    Stack

//    ReentrantLock lock =new ReentrantLock();

//    AbstractQueuedSynchronizer
// Condition
   ReadWriteLock readWriteLock = new ReadWriteLock() {
    @Override
    public Lock readLock() {
        return null;
    }

    @Override
    public Lock writeLock() {
        return null;
    }
};


}