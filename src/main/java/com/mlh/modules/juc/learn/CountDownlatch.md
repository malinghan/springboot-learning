- 如何形象的描述Latch？他有什么特征？

- CountDownLatch的API方法有哪些？
   * public void await() throws InterruptedException
   * public boolean await(long timeout, TimeUnit unit) throws InterruptedException
   * public void countDown()
   * public long getCount()
   
- [ ] 闭锁API使用的例子？
   
- 闭锁`内存一致性效果`特性?

- CountDownLatch与FutureTask

- CountDownLatch底层到底是如何使用共享锁实现await*和countDown的？

- 什么是共享锁？

共享锁是说所有共享锁的线程共享同一个资源，一旦任意一个线程拿到共享资源，那么所有线程就都拥有的同一份资源。

- 