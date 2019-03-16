package com.mlh.modules.juc.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description: 使用CAS非阻塞算法
 */
public class NonbloackingCounter {
    private AtomicInteger value;

    public  int getValue(){
        return value.get();
    }

    public int increment(){
        int v;
        do {
            v = value.get();
        }
        while (!value.compareAndSet(v,v+1));
        return v+1;
    }
}
