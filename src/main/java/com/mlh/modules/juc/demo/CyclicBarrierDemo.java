package com.mlh.modules.juc.demo;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
/**i
 * @author: linghan.ma
 * @DATE: 2018/8/8
 * @description:
 */

/**
 * - public CyclicBarrier(int parties)
 *           创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，
 *           但它不会在启动 barrier 时执行预定义的操作。
 * - public CyclicBarrier(int parties, Runnable barrierAction)
 *           创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，
 *           并在启动 barrier 时执行给定的屏障操作，该操作由最后一个进入 barrier 的线程执行。
 * - public int await() throws InterruptedException, BrokenBarrierException
 *           在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
 * - public int await(long timeout,TimeUnit unit) throws InterruptedException, BrokenBarrierException,TimeoutException
 *           在所有参与者都已经在此屏障上调用 await 方法之前将一直等待,或者超出了指定的等待时间。
 * - public int getNumberWaiting()
 *           返回当前在屏障处等待的参与者数目。此方法主要用于调试和断言。
 * - public int getParties()
 *            返回要求启动此 barrier 的参与者数目。
 * - public boolean isBroken()
 *            查询此屏障是否处于损坏状态。
 * - public void reset()
 *             将屏障重置为其初始状态
 */


public class CyclicBarrierDemo {

    final CyclicBarrier barrier;  //

    final int MAX_TASK; //

    public CyclicBarrierDemo(int cnt) {
        barrier = new CyclicBarrier(cnt + 1);
        MAX_TASK = cnt;
    }

    //执行
    public void doWork(final Runnable work) {
        new Thread() {

            public void run() {
                work.run();
                try {
                    /**
                     * - await()方法将挂起线程，直到同组的其它线程执行完毕才能继续
                     * - await()方法返回线程执行完毕的索引，注意，索引时从任务数-1开始的，也就是第一个执行完成的任务索引为parties-1,最后一个为0，这个parties为总任务数，清单中是cnt+1
                     * - CyclicBarrier 是可循环的，显然名称说明了这点。在清单1中，每一组任务执行完毕就能够执行下一组任务。
                     * -
                     */
                    int index = barrier.await();
                    /**
                     * 如果屏障操作不依赖于挂起的线程，那么任何线程都可以执行屏障操作。在清单1中可以看到并没有指定那个线程执行50%、30%、0%的操作，而是一组线程（cnt+1）个中任何一个线程只要到达了屏障点都可以执行相应的操作
                     * CyclicBarrier 的构造函数允许携带一个任务，这个任务将在0%屏障点执行，它将在await()==0后执行。
                     * CyclicBarrier 如果在await时因为中断、失败、超时等原因提前离开了屏障点，那么任务组中的其他任务将立即被中断，以InterruptedException异常离开线程。
                     * 所有await()之前的操作都将在屏障点之前运行，也就是CyclicBarrier 的内存一致性效果
                     */
                    doWithIndex(index); // 当index达到某种值的时候，执行某种操作
                } catch (InterruptedException e) {
                    return;
                } catch (BrokenBarrierException e) {
                    return;
                }
            }
        }.start();
    }

    private void doWithIndex(int index) {
        if (index == MAX_TASK / 3) {
            System.out.println("Left 30%.");
        } else if (index == MAX_TASK / 2) {
            System.out.println("Left 50%");
        } else if (index == 0) {
            System.out.println("run over");
        }
    }

    public void waitForNext() {
        try {
            doWithIndex(barrier.await());
        } catch (InterruptedException e) {
            return;
        } catch (BrokenBarrierException e) {
            return;
        }
    }

    public static void main(String[] args) {
        final int count = 10;
        CyclicBarrierDemo demo = new CyclicBarrierDemo(count);
        for (int i = 0; i < 100; i++) {
            demo.doWork(new Runnable() {

                public void run() {
                    //do something
                    try {
                        Thread.sleep(1000L);
                    } catch (Exception e) {
                        return;
                    }
                }
            });

            if ((i + 1) % count == 0) {
                demo.waitForNext();
            }
        }
    }

}