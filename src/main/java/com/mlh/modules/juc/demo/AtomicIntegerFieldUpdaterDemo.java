package com.mlh.modules.juc.demo;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description:
 */
public class AtomicIntegerFieldUpdaterDemo {
    //内部类初始化
    class DemoDate {
        public volatile  int value1 = 1;
        volatile  int value2 = 2;
        protected  volatile int value3 = 3;
        private  volatile  int value4 = 4;
    }
   //<Object,Boolean> MarkableReference
//    AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(new DemoDate(),true);

    //获取AtomicIntegerFieldUpdater fieldName 指的是更新哪个变量
    protected  AtomicIntegerFieldUpdater<DemoDate>  getUpdater(String fieldName){
        return AtomicIntegerFieldUpdater.newUpdater(DemoDate.class,fieldName);
    }

    void done(){
        DemoDate data = new DemoDate();
        //update value1
        System.out.println("1 =>" + getUpdater("value1").getAndSet(data,10));
        //
        System.out.println("2 => "+ getUpdater("value2").incrementAndGet(data));
        //protected getUpdater为default 方法
        System.out.println("3 =>" + getUpdater("value3").decrementAndGet(data));//
       // private
        System.out.println("true =>" + getUpdater("value4").compareAndSet(data,4,5));
    }

    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterDemo demo =  new AtomicIntegerFieldUpdaterDemo();
        demo.done();
    }

}
