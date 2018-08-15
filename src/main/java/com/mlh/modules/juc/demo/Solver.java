package com.mlh.modules.juc.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/8
 * @description:
 */
public class Solver {
    final int N;
    final float[][] data;
    final CyclicBarrier barrier;

    class Worker implements Runnable {
      int myRow;
      Worker(int row) { myRow = row; }
      public void run() {
        while (true
               // !done()
                ) {
         // processRow(myRow);

          try {
            barrier.await();
         } catch (InterruptedException ex) {
           return;
         } catch (BrokenBarrierException ex) {
            return;
          }
       }
      }
    }

    public Solver(float[][] matrix) {
     data = matrix;
      N = matrix.length;
     Runnable barrierAction =
       new Runnable() { public void run() {
           //mergeRows(...);
       }
     };

       barrier = new CyclicBarrier(N, barrierAction);

      List<Thread> threads = new ArrayList<>(N);

      for (int i = 0; i < N; i++) {
        Thread thread = new Thread(new Worker(i));
        threads.add(thread);
        thread.start();
      }

      // wait until done
      for (Thread thread : threads){
          //        thread.join();
      }
    }


  }