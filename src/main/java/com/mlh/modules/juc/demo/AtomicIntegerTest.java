package com.mlh.modules.juc.demo;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description:
 */
public class AtomicIntegerTest {
    @Test
    public void testAll() throws InterruptedException{
        final AtomicInteger value = new AtomicInteger(10);
        //compareAndSet左边必须和value相等，否则无法set进去
        assertEquals(value.compareAndSet(1, 2), false);
        assertEquals(value.get(), 10);
        assertTrue(value.compareAndSet(10, 3));
        assertEquals(value.get(), 3);
        value.set(0);
        //
        assertEquals(value.incrementAndGet(), 1);
        //getAndAdd value加 返回 value
        assertEquals(value.getAndAdd(2),1);
        //
        assertEquals(value.getAndSet(5),3);
        assertEquals(value.get(),5);
//        //
        final int threadSize = 10;
        Thread[] ts = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            ts[i] = new Thread() {
                public void run() {
                    value.incrementAndGet();
                }
            };
        }
        //
        for(Thread t:ts) {
            t.start();
        }
        for(Thread t:ts) {
            t.join();
        }
        //incrementAndGet AddAndGet
        assertEquals(value.get(), 5+threadSize);

    }
}
