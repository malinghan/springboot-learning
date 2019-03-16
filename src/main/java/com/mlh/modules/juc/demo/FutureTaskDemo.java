package com.mlh.modules.juc.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: linghan.ma
 * @DATE: 2018/10/11
 * @description:
 */
public class FutureTaskDemo {
    public static void main(String[] args) {
        //
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //
        Future result = executorService.submit(
                () -> {
                    for (int i = 0; i < 200; i++) {
                        System.out.println("task 1 running： "+i);
                    }
                    return "task 1";
                }
        );

        Future result2 = executorService.submit(
                () -> {
                    for (int i = 0; i < 200; i++) {
                        System.out.println("task 2 running： "+i);
                    }
                    return "task2";
                }
        );


        try {
            System.out.println(result.get()+" executor complete");

            System.out.println(result2.get()+" executor complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //线程池关闭
            executorService.shutdown();
        }

    }
}
