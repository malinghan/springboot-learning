CyclicBarrier 

- CyclicBarrier 如何理解？

- 举例说明如何使用 CyclicBarrier

- CyclicBarrier有以下几个特点

- CyclicBarrier的API？

  * public CyclicBarrier(int parties) 创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，但它不会在启动 barrier 时执行预定义的操作。
  * public CyclicBarrier(int parties, Runnable barrierAction) 创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，并在启动 barrier 时执行给定的屏障操作，该操作由最后一个进入 barrier 的线程执行。
  * public int await() throws InterruptedException, BrokenBarrierException 在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
  * public int await(long timeout,TimeUnit unit) throws InterruptedException, BrokenBarrierException,TimeoutException 在所有参与者都已经在此屏障上调用 await 方法之前将一直等待,或者超出了指定的等待时间。
  * public int getNumberWaiting() 返回当前在屏障处等待的参与者数目。此方法主要用于调试和断言。
  * public int getParties() 返回要求启动此 barrier 的参与者数目。
  * public boolean isBroken() 查询此屏障是否处于损坏状态。
  * public void reset() 将屏障重置为其初始状态。