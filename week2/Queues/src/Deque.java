import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node headPointer;
	private Node lastPointer;
	private int size=0;
	
    // construct an empty deque
    public Deque() {
    	
    }
    
    private class Node {
    	private Item item;
    	private Node next;
    	private Node prev; 
	}

    // is the deque empty?
    public boolean isEmpty() {
    	return size==0;
    } 

    // return the number of items on the deque
    public int size() {
    	return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	if(item == null) {
    		throw new IllegalArgumentException();
    	}
    	//assigns old to point to original head pointer so head can be reassigned
    	Node old = headPointer;
    	//sets hetpointer to a new node
    	headPointer = new Node();
    	//assigns new node's next to the old head pointer
    	headPointer.next = old;
    	headPointer.item = item;
    	
    	
    	if(size==0) {
    		lastPointer = headPointer;
    	}else {
    		headPointer.next.prev = headPointer;
    	}
    	size++;

    }

    // add the item to the back
    public void addLast(Item item) {
    	if(item == null) {
    		throw new IllegalArgumentException();
    	}
    	//sets old to point at the object lastPointer pointed at
    	Node old = lastPointer;
    	
    	lastPointer = new Node();
    	lastPointer.prev = old;
    	lastPointer.item = item;
    	
    	if(size == 0) {
    		headPointer = lastPointer;
    	}else{
    		lastPointer.prev.next = lastPointer;
    	}
    	
    	size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
    	if(size == 0) {throw new java.util.NoSuchElementException();}
    	
    	Item removedItem = headPointer.item;
    	
    	headPointer = headPointer.next;
    	if(size ==1) {
    		lastPointer = null;
    	}else {
    		headPointer.prev = null;
        	
    	}
    	size--;
    	return removedItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if(size == 0) { throw new java.util.NoSuchElementException();}
    	
    	Item removedItem = lastPointer.item;
    	
    	lastPointer = lastPointer.prev;
    	if(size ==1) {
    		headPointer = null;
    	}else {
    		lastPointer.next = null; 	
    	}
    	size--;
    	return removedItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
    	return new DequeueIterator();
    }
    private class DequeueIterator implements Iterator<Item> {
        private Node current = headPointer;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more elements to return");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    

    // unit testing (required)
    public static void main(String[] args) {
    	Deque<Integer> deque = new Deque<>();
        deque.addFirst(1); // 1
        System.out.println(deque);
        deque.addLast(3); // 1 3
        System.out.println(deque);
        System.out.println(deque.removeFirst()); // 1
        System.out.println(deque); //3
        deque.addFirst(2); // 2 3
        System.out.println(deque);
        System.out.println(deque.removeFirst()); // 2
        System.out.println(deque); // 3
        deque.addFirst(4); // 4 3
        System.out.println(deque.removeLast()); // 3
        System.out.println(deque);
    }

}