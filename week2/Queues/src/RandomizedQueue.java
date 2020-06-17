import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] arr;
	private int size = 0;
	
    // construct an empty randomized queue
    public RandomizedQueue() {
    	this.arr = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return size==0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return size;
    }

    // add the item
    public void enqueue(Item item) {
    	if(item == null) {throw new IllegalArgumentException();}
    	
    	arr[size] = item;
    	
    	size++;
    	
    	if(size == arr.length) {
    		resize(arr.length*2);
    	}
    }
    
    private void resize(int len) {
    	Item[] copy = (Item[]) new Object[len];
    	System.arraycopy(this.arr, 0, copy, 0, size);
    	arr = copy;
    }

    // remove and return a random item
    public Item dequeue() {
    	if(size == 0) {throw new java.util.NoSuchElementException("the queue is empty");}
    	int randomIndex = StdRandom.uniform(size);
    	
    	Item item = arr[randomIndex]; 
    	arr[randomIndex] = arr[size-1];
    	arr[size-1] = null;
    	size--;
    	
    	//if arrayneeds to be resized
    	//note to self, get rid of modulo if autograder goes sicko mode
    	if(arr.length/4 == size && arr.length%4 ==0) {
    		resize(arr.length/2);
    	}
    	
    	
    	
    	return item;
    	
    }
    //return random item from array but don't remove
	public Item sample() {
	
	if(isEmpty()) {
		throw new java.util.NoSuchElementException("the queue is empty");
	}
	int randomIndex = StdRandom.uniform(size);
	return arr[randomIndex];
    }
	
	
    

    // unit testing (required)
	public static void main(String[] args) {
		RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
		randQueue.enqueue(2);
        randQueue.enqueue(3);
        randQueue.enqueue(4);
        randQueue.enqueue(5);
        randQueue.enqueue(6);
        StdOut.print(randQueue.size());
        StdOut.print(randQueue.sample());
        StdOut.print(randQueue.dequeue());
        
        
    }

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new RandomizedQueueIterator();
	}
	private class RandomizedQueueIterator implements Iterator<Item> {
        int i = 0;
        Item[] randomArr;

        public RandomizedQueueIterator() {
            randomArr = (Item[]) new Object[size];
            System.arraycopy(arr, 0, randomArr, 0, size);
            StdRandom.shuffle(randomArr);
        }

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more elements to return");
            return randomArr[i++];
        }
    }


}