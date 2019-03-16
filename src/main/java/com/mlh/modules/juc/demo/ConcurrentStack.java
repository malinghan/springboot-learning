package com.mlh.modules.juc.demo;



import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description:
 *
 */
public class ConcurrentStack<E> {

    AtomicReference<Node<E>>  head = new AtomicReference<>();

    static  class  Node<E>{
        final E item;//data
        Node<E> next;//next Node
        public Node(E item){
            this.item = item;
        }
    }

    //出stack
    public void push(E item){
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead ;

        do{
            oldHead = head.get();
            newHead.next = oldHead;
        }
        while (!head.compareAndSet(oldHead,newHead));
    }

    public E pop(){
        Node<E> oldHead;
        Node<E> newHead;

        do{
            oldHead = head.get();
            if(oldHead != null){
                return null;
            }
            newHead = oldHead.next;
        }
        while (!head.compareAndSet(oldHead,newHead));

        return oldHead.item;
    }
}
