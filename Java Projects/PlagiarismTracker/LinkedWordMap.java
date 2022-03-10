import java.util.Comparator;
public class LinkedWordMap implements WordMap{
  /**
  * Returns the logical size of this WordMap. That is the number of
  * associations currently stored in it.
  *
  * @return the logical size of this WordMap
  */

    private static class Node {

        private String value;
        private int occurence;
        private Node previous, next;

        public Node(String value, Node previous, Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
            occurence=1;
        }
    }

    //helper class
    private final Node head;
    private int size;
    public LinkedWordMap(){
      head = new Node(null,null,null);
      head.next = head;
      head.previous = head;
      size=0;
    }

    public int size(){
      return size;
    }

    /**
     * Returns true if and only if this WordMap contains the specified
     * word.
     *
     * @param key the specified word
     * @return true if and only if this WordMap contains the specified word
     * @throws NullPointerException if the value of the parameter is null
     */


    public boolean contains(String key){
      if(key==null){
        throw new NullPointerException();
      }
      Node temp = head;
      for(int i=0; i<size();i++){
        temp = temp.next;
        if(temp.value.equals(key)){
          return true;
        }
      }
      return false;
    }

    /**
     * Returns the count associated with the specified word or 0 if
     * the word is absent.
     *
     * @param key the specified word
     * @return the count associated with the specified word or 0 if absent
     * @throws NullPointerException if the value of the parameter is null
     */


    public int get(String key){
      if(key==null){
        throw new NullPointerException();
      }
      int i=0;
      Node temp = head;
      while(i<size()){
        temp = temp.next;
        if(temp.value.equals(key)){
          return temp.occurence;
        }
        i++;
      }
      return 0;
    }

    /**
     * Increments by 1 the counter associated with the specified
     * word. If the specified word is absent from the data structure,
     * a new association is created.
     *
     * @param key the specified word
     * @throws NullPointerException if the value of the parameter is null
     */

    public void update(String key){
      if(key==null){
        throw new NullPointerException();
      }
      generalAdd(key,head.next);
    }

    public void generalAdd(String key, Node elem){
      boolean added = false;
      while(!added){
      if(size==0){
        add(key,head);
        added=true;
      }else if(key.equals(elem.value)){
        elem.occurence++;
        added = true;
      }else if (elem.value.compareTo(key)>0) {
        add(key, elem.previous);
        added=true;
      }else if(elem.next==head){
        add(key,elem);
        added=true;
      }else {
        elem = elem.next;
      }
      }

    }

    //helper function that adds in alphabetical order
    private void add(String key, Node previous){
      Node q=previous.next;
      previous.next = new Node(key,previous,previous.next);
      q.previous = previous.next;
      size++;
    }

    /**
     * Returns all the keys (words) of this WordMap using their
     * natural order.
     *
     * @return all the keys (words)
     */

    public String[] keys() {

        String[] words = new String[size()];
        Node temp= head;
        for(int i=0;i<size();i++){
          temp=temp.next;
          words[i]= temp.value;
        }
        return words;
    }

    /**
     * Returns all the counts associated with keys in this
     * WordMap. The counts are in the same order as that of the keys
     * returned by the method keys().
     *
     * @return all the counts
     */
    public Integer[] counts() {

        Integer[] numbers = new Integer[size()];
        Node temp=head;
        for(int i=0;i<size();i++){
          temp=temp.next;
          numbers[i]= temp.occurence;
        }
        return numbers;
    }

}
