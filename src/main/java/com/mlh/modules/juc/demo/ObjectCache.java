package com.mlh.modules.juc.demo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 对象池：
 *   ObjectCache(capacity,ObjectFactory)
 *   ObjectCache.getObject
 *   ObjectCache.
 *
 * 实际上对象池、线程池的原理大致上就是这样的，只不过真正的对象池、线程池要处理比较复杂的逻辑，
 * 所以实现起来还需要做很多的工作，例如超时机制，自动回收机制，对象的有效期等等问题。
 *
 * 将信号量初始化为 1，使得它在使用时最多只有一个可用的许可，从而可用作一个相互排斥的锁。
 * 这通常也称为二进制信号量，因为它只能有两种状态：一个可用的许可，或零个可用的许可。
 * 按此方式使用时，二进制信号量具有某种属性（与很多 Lock 实现不同），
 * 即可以由线程释放“锁”，而不是由所有者（因为信号量没有所有权的概念）。
 * 在某些专门的上下文（如死锁恢复）中这会很有用。
 * @param <T>
 */
public class ObjectCache<T> {

    public interface ObjectFactory<T> {

        T makeObject();
    }

    class Node {

        T obj;

        Node next;
    }

    final int capacity; //容量

    final ObjectFactory<T> factory;

    final Lock lock = new ReentrantLock(); // lock

    final Semaphore semaphore;  //

    private Node head;

    private Node tail;

    /**
     * 此对象池最多支持capacity个对象，这在构造函数中传入
     * @param capacity
     * @param factory
     */
    public ObjectCache(int capacity, ObjectFactory<T> factory) {
        this.capacity = capacity;
        this.factory = factory;
        this.semaphore = new Semaphore(this.capacity);
        this.head = null;
        this.tail = null;
    }

    /**
     * 特别要说明的如果对象的个数用完了，那么新的线程将被阻塞，直到有对象被返回回来。
     * 返还对象时将对象加入FIFO的尾节点并且释放一个空闲的信号量，表示对象池中增加一个可用对象。
     * @return
     * @throws InterruptedException
     */
    public T getObject() throws InterruptedException {
        semaphore.acquire();  // 获取
        return getNextObject();
    }

    /**
     * 对象池有一个基于FIFO的队列，每次从对象池的头结点开始取对象，
     * 如果头结点为空就直接构造一个新的对象返回。
     * 否则将头结点对象取出，并且头结点往后移动。
     * @return
     */
    private T getNextObject() {
        /**
         * 这里特别说明的是信号量只是在信号不够的时候挂起线程，
         * 但是并不能保证信号量足够的时候获取对象和返还对象是线程安全的，
         * 所以在清单1中仍然需要锁Lock来保证并发的正确性。
         */
        lock.lock();
        try {
            if (head == null) {
                return factory.makeObject();
            } else {
                Node ret = head;//
                head = head.next; //指针后移
                if (head == null){
                    tail = null;
                }
                ret.next = null;//help GC
                return ret.obj;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 返还对象时将对象加入FIFO的尾节点并且释放一个空闲的信号量，表示对象池中增加一个可用对象。
     * @param t
     */
    private void returnObjectToPool(T t) {
        lock.lock();
        try {
            Node node = new Node();
            node.obj = t;
            if (tail == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }

        } finally {
            lock.unlock();
        }
    }

    /**
     * 特别要说明的如果对象的个数用完了，那么新的线程将被阻塞，直到有对象被返回回来。
     * 返还对象时将对象加入FIFO的尾节点并且释放一个空闲的信号量，表示对象池中增加一个可用对象。
     * @param t
     */
    public void returnObject(T t) {
        returnObjectToPool(t);
        semaphore.release(); //释放一个
    }

}