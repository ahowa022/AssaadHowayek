import java.util.ArrayList;
public class QueueImplementation<E> implements Queue<E> {

 // YOUR CODE HERE
 private ArrayList<E> queue;
 public QueueImplementation(){
   queue = new ArrayList<E>();
 }

 public void enqueue(E value){
   if (value==null){
     throw new NullPointerException("value to enqueue cannot be null");
   }
   queue.add(value);
 }

 public E dequeue(){
   E tmp = queue.remove(0);
   return tmp;
 }

 public boolean isEmpty(){
   if(queue.size()==0){
     return true;
   } else {
     return false;
   }
 }

}
