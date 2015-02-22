/*************************************************************************
 * Compilation: javac Deque.java
 * Execution: java Deque
 * 
 * Implementation of a double-ended queue or deque
 * using a doubly-linked list that supports adding
 * and removing items from either the front or the
 * back of the data structure.
 * 
 * Author: Andrea Clausen
 * Date: 2015-02-21
 *
 *************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;        // size of the queue
    private Node pre;     // sentinel before first item
    private Node post;    // sentinel after last item

    /**
     *  construct an empty deque
     */
    public Deque() {
        pre  = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;
        N = 0;
    }
    
    // helper doubly-linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
      
    /**
     * is the deque empty?
     * @return true if the deque is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;    
    }
    
    /**
     * return the number of items on the deque
     */
    public int size() {
        return N;
    }
    
    /**
     * add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Cannot add null item");
        Node first = pre.next;
        Node x = new Node();
        x.item = item;
        x.next = first;
        x.prev = pre;
        pre.next = x;
        first.prev = x;
        N++;
    }
    
    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Cannot add null item");
        Node last = post.prev;
        Node x = new Node();
        x.item = item;
        x.next = post;
        x.prev = last;
        post.prev = x;
        last.next = x;
        N++;
    }
    
    /**
     * remove and return the item from the front
     * @return the item from the front
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Node first = pre.next;
        Item item = first.item;    // save item to return
        first.next.prev = pre; 
        pre.next = first.next;        // delete first node
        N--;
        return item;               // return the saved item 
    }
    
    /**
     * remove and return the item from the end
     * @return the item from the end
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Node last = post.prev;
        Item item = last.item;    // save item to return
        last.prev.next = post;
        post.prev = last.prev;         // delete last node
        N--;
        return item;              // return the saved item
    }
    
    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new DoublyLinkedListIterator();
    }
    
    // an iterator, doesn't implement remove() since it's optional
    private class DoublyLinkedListIterator implements Iterator<Item> {
        private Node current = pre.next;
        private Node lastAccessed = null;
        private int index = 0;
        
        public boolean hasNext()      { return index < N; }
        public boolean hasPrevious()  { return index > 0; }
        public int previousIndex()    { return index - 1; }
        public int nextIndex()        { return index;     }
        
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            lastAccessed = current;
            Item item = current.item;
            current = current.next; 
            index++;
            return item;
        }
    }
         
    /**
     * test client
     */
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) d.addLast(item);
            else if (!d.isEmpty()) StdOut.print(d.removeLast() + " ");
        }
        StdOut.println("(" + d.size() + " left in deque)");
    } 
}