package com.mlh.modules.juc.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/9
 * @description:
 */
public class BirthdayService {

    final int workerNumber;

    final Worker[] workers;

    final ExecutorService threadPool;

    static volatile boolean running = true;
   //init works
    public BirthdayService(int workerNumber, int capacity) {
        //
        if (workerNumber <= 0) throw new IllegalArgumentException();
        //
        this.workerNumber = workerNumber;
        //
        workers = new Worker[workerNumber];
        for (int i = 0; i < workerNumber; i++) {
            workers[i] = new Worker(capacity);
        }

        boolean b = running;// kill the resorting
        //LinkedBlockingQueue
        threadPool = Executors.newFixedThreadPool(workerNumber);
        for (Worker w : workers) {
            threadPool.submit(w);
        }
    }

    Worker getWorker(int id) {
        return workers[id % workerNumber];

    }

    // Worker
    class Worker implements Runnable {
          //
        final BlockingQueue<Integer> queue;

        public Worker(int capacity) {
            //
            queue = new LinkedBlockingQueue<Integer>(capacity);
        }

        public void run() {
            while (true) {
                try {
                    consume(queue.take());
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

        void put(int id) {
            try {
                queue.put(id);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
    // add into worker
    public void accept(int id) {
        //accept client request
        getWorker(id).put(id);
    }
    // what work doing
    protected void consume(int id) {
        //do the work
        //get the list of friends and save the birthday to cache
    }

}
