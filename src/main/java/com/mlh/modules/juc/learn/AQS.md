Node addWaiter(Node node)  //加入等待队列

```
pred = tail 

if(pred!=null)
      node.prev = pred;
      if(casTail(pred,node))
             pred.next  = node;     
enq(node)
return node;  

```           
 
####Node enq(Node node)   
- 构造一个Node返回 
- 当队列为空时，调用这个构造一个waiter
- 去队列操作实现了CHL队列的算法，如果为空就创建头结点，
- 然后同时比较节点尾部是否是改变来决定CAS操作是否成功，
- 当且仅当成功后才将为不节点的下一个节点指向为新节点。可以看到这里仍然是CAS操作。
       
```
 for(;;)
     Node t =tail;
     if(t == null)
         h = new Node
         h.next = node;
         node.prev = h;
         if(casHead(h)){
             tail = node;
             return h;
         }
     else 
        node.prev = t ;
         if(casTail(t, node))
             t.next = node;
             return t;
```       
         