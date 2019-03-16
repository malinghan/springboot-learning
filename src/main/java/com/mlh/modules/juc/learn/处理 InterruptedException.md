####  InterruptedException

每个线程都有一个与之相关联的 Boolean 属性，用于表示线程的中断状态（interrupted status）。中断状态初始时为 false；

当另一个线程通过调用 Thread.interrupt() 中断一个线程时，会出现以下两种情况之一。

如果那个线程在执行一个低级可中断阻塞方法，例如 Thread.sleep()、 Thread.join() 或 Object.wait()，那么它将取消阻塞并抛出 InterruptedException。

否则， interrupt() 只是设置线程的中断状态。aqs


