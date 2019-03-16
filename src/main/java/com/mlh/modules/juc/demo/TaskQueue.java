package com.mlh.modules.juc.demo;

import org.springframework.scheduling.config.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/2
 * @description:
 */
public class TaskQueue {
    private static final int MAX_TASK = 1000 ;

    private BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>(MAX_TASK);

    public void putTask(Task r) throws InterruptedException {
        queue.put(r);
    }

    public  Task getTask() throws InterruptedException {
        return queue.take();
    }

}
