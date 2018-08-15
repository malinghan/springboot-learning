package com.mlh.modules.juc.demo;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/14
 * @description:
 */
public class SimpleExchanger {

    static class Slot {
        final Object offer; //offer
        final Thread waiter; // waiter
        volatile Object other; // other

        public Slot(Thread waiter,Object offer){
            this.offer = offer;
            this.waiter = waiter;
        }
    }

    private  volatile AtomicReference<Slot> refer =  new AtomicReference<>();

    //把me的offer exchange 给 you
    public  Object exchange(Object offer){

        Slot me = new Slot(Thread.currentThread(),offer); //offer

        Slot you;

        for(;;){
            if( (you = refer.get()) == null){
                //
                if(refer.compareAndSet(null,me)){
                    LockSupport.park();
                    return me.offer;
                }
            }else  if(refer.compareAndSet(you,null)){
                you.other = offer;
                LockSupport.unpark(you.waiter);
                return you.offer;
            }
        }
    }
}
