package com.mlh.modules.juc.demo;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description:
 * 在这个例子中one/two两个线程修改区x,y,a,b四个变量，在执行100次的情况下，可能得到(0 1)或者（1 0）或者（1 1）。事实上按照JVM的规范以及CPU的特性有很可能得到（0 0）。当然上面的代码大家不一定能得到（0 0），因为run()里面的操作过于简单，可能比启动一个线程花费的时间还少，因此上面的例子难以出现（0,0）。但是在现代CPU和JVM上确实是存在的。由于run()里面的动作对于结果是无关的，因此里面的指令可能发生指令重排序，即使是按照程序的顺序执行，数据变化刷新到主存也是需要时间的。假定是按照a=1;x=b;b=1;y=a;执行的，x=0是比较正常的，虽然a=1在y=a之前执行的，但是由于线程one执行a=1完成后还没有来得及将数据1写回主存（这时候数据是在线程one的堆栈里面的），线程two从主存中拿到的数据a可能仍然是0（显然是一个过期数据，但是是有可能的），这样就发生了数据错误。
 */
public class ReorderingDemo {
    private  static int x = 0, y = 0 , a = 0 ,b = 0;

    public static void main(String[] args)  throws  InterruptedException{
        for(int i=0; i<100; i++){
            //
            x = y = a = b =0;

            Thread one = new Thread(){
                public  void  run(){
                    a = 1;
                    x =b;
                }
            };

            Thread two = new Thread(){
                public  void run(){
                    b =1;
                    y =a;
                }
            };

            one.start();
            two.start();
            one.join();
            two.join();

            System.out.println(x + "" + y );
        }
    }
}
