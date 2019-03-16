package com.mlh.modules.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: linghan.ma
 * @DATE: 2019/1/29
 * @description:
 */
public class MyObservable extends Observable {
    public static void main(String[] args){
        MyObservable myObservable = new MyObservable();
        myObservable.addObserver((Observable o, Object arg) ->
                System.out.println(arg));
        myObservable.setChanged();
        myObservable.notifyObservers("hello");
    }
}
