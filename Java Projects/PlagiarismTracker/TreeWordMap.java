/**
 * A Binary Search Tree implementation of the interface WordMap.
 *
 * @author Marcel Turcotte (marcel.turcotte@uottawa.ca)
 */

public class TreeWordMap implements WordMap {

    private static class Elem {

        private String key;
        private int count;
        private Elem left, right;

        private Elem(String key) {
            this.key = key;
            count = 1;
        }

    }

    private Elem root;
    private int size;

    public TreeWordMap(){
      size=0;
    }

    /**
     * Returns true if and only if this WordMap contains the specified
     * word.
     *
     * @param key the specified word
     * @return true if and only if this WordMap contains the specified key
     * @throws NullPointerException if the value of the parameter is null
     */

    public boolean contains(String key) {

        if (key == null) {
            throw new NullPointerException();
        }

        boolean found = false;
        Elem current = root;
        while (! found && current != null) {
            int test = key.compareTo(current.key);
            if (test == 0) {
                found = true;
            } else if (test < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return found;
    }

    /**
     * Increments by 1 the counter associated with the specified
     * word. If the specified word is absent from the data structure,
     * a new association is created.
     *
     * @param key the specified word
     * @throws NullPointerException if the value of the parameter is null
     */

    public void update(String key) {
      if(key==null){
        throw new NullPointerException();
      }
      add(key);
    }


    //helper function for the update
    private void add(String text){
      boolean added = false;
      if(size==0){
        root = new Elem(text);
        size++;
      } else {
        Elem current = root;
        while(!added){
          int test = text.compareTo(current.key);
          if(test==0){
            current.count++;
            added=true;
          }
          if(test<0 && current.left==null){
            current.left = new Elem(text);
            size++;
            added=true;
          } else if(test>0 && current.right==null){
            current.right = new Elem(text);
            size++;
            added=true;
          } else if (test>0){
            current = current.right;
          } else {
            current = current.left;
          }
        }
      }
    }

    /**
     * Returns the count associated with the specified word or 0 if
     * the word is absent.
     *
     * @param key the specified word
     * @return the count associated with the specified word or 0 if absent
     * @throws NullPointerException if the value of the parameter is null
     */

    public int get(String key) {

        if (key == null) {
            throw new NullPointerException();
        }

        boolean found = false;
        Elem current = root;
        while (current != null) {
            int test = key.compareTo(current.key);
            if (test == 0) {
                return current.count;
            } else if (test < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return 0;
    }

    /**
     * Returns the logical size of this WordMap. That is the number of
     * associations currently stored in it.
     *
     * @return the logical size of this WordMap
     */

    public int size() {
        return size;
    }

    /**
     * Returns all the keys (words) of this WordMap using their
     * natural order.
     *
     * @return all the keys (words)
     */

    public String[] keys() {
      LinkedList<String> table = new LinkedList<String>();
      keys(root, table);
      String[] array= new String[size];
      return table.toArray(array);
    }

    //helper function
    private void keys(Elem value, LinkedList<String> table){
      if(value!=null){
        keys(value.left,table);
        table.addLast(value.key);
        keys(value.right,table);
      }
    }


    /**
     * Returns all the counts associated with keys in this
     * WordMap. The counts are in the same order as that of the keys
     * returned by the method keys().
     *
     * @return all the counts
     */


    public Integer[] counts() {
      LinkedList<Integer> tableInt = new LinkedList<Integer>();
      keysInt(root, tableInt);
      Integer[] array= new Integer[size];
      return tableInt.toArray(array);
    }

    //helper function
    private void keysInt(Elem value, LinkedList<Integer> tableInt){
      if(value!=null){
        keysInt(value.left,tableInt);
        tableInt.addLast(value.count);
        keysInt(value.right,tableInt);
      }
    }

}
