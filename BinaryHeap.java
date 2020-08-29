package sxa190016;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * @author sxa190016
 * @author bsv180000
 * @version 1.0 Binary Heap: Short project 4
 * 				Generic Binary Heap using array
 * 				which extends Comparable and can be 
 * 				initialized with the initial capacity
 * 				of the heap but can be extended to accommodate 
 * 				more elements.
 */
public class BinaryHeap<T extends Comparable<? super T>> {
	
	/**
	 * Generic array to store Comparable elements
	 */
    Comparable[] pq;
    
    /**
     * Integer which stores the current number of elements in the array
     */
    int size;

    /**
     * Constructor for building an empty priority queue using natural ordering of T
     * 
     * @param maxCapacity	The initial capacity of the Comparable array
     */
    public BinaryHeap(int maxCapacity) 
    {
		pq = new Comparable[maxCapacity];
		size = 0;
    }

    /**
     * Adds a new generic element to the heap
     * 
     * @param x		Generic element to be added to the heap
     * @return		True if add operation is successful else False
     */
    public boolean add(T x) 
    {
    	//Double the capacity of the array if it is full
    	if(this.size() == this.pq.length)
    	{
    		this.resize();
    	}
    	this.move(size, x);
    	this.percolateUp(size);
    	this.size++;
    	return true;
    }
    
    /**
     * Adds a new generic element to the heap.
     * Calls the add method
     * 
     * @param x		Generic element to be added to the heap
     * @return		True if add operation is successful else False
     */
    public boolean offer(T x) 
    {
    	return add(x);
    }


    /**
     * Removes an element from the top of the heap
     * Calls the poll() method
     * 
     * @return		the minimum generic element at the top of the heap
     * @throws NoSuchElementException		if heap is empty
     */
    public T remove() throws NoSuchElementException 
    {
    	/**
    	 * Stores the result to be returned
    	 */
		T result = poll();
		if(result == null) 
		{
		    throw new NoSuchElementException("Priority queue is empty");
		} 
		else 
		{
		    return result;
		}
    }


    /**
     * Returns the minimum element at the top of the heap.
     * Calls the peek() method
     * 
     * @return 		the minimum element at the top of the heap or null if empty
     */
    public T poll() 
    {
    	/**
    	 * Stores the element at the root of the heap.
    	 */
    	T root = this.peek();
    	this.size--;
    	if(root != null)
    	{
    		//Move the last element to the top of the heap
    		this.move(0, this.pq[this.size()]);
    		//Heapify downwards
    		this.percolateDown(0);
    	}
    	return root;
    }
    
    /**
     * Returns the minimum element in the heap
     * 
     * @return		The minimum element at the top of the heap
     */
    public T min() 
    { 
    	return peek();
    }


    /**
     * Returns the element at the top of the heap
     * without removing it.
     * 
     * @return		The element at the top of the heap or null if empty.
     */
    public T peek() 
    {
    	if (!this.isEmpty())
    	{
    		return (T) this.pq[0];
    	}
    	return null;
    }

    /**
     * Finds the index of the parent of the current element
     * 
     * @param i		The index of the current element
     * @return		The index of it's parent
     */
    int parent(int i) 
    {
    	return (i-1)/2;
    }

    /**
     * Finds the index of the left child of the current element
     * 
     * @param i		The index if the current element
     * @return		The index of it's leftchild.
     */
    int leftChild(int i) 
    {
    	return 2*i + 1;
    }

    /**
     * Heapify the array from the child to it's parent till the root.
     * 
     * @param index		The index of the current element.
     */
    void percolateUp(int index) {
    	if (index > 0)
    	{
    		//If current element is smaller than it's parent
    		if(this.pq[index].compareTo(this.pq[this.parent(index)]) < 0)
    		{
    			Comparable temp  = this.pq[this.parent(index)];
    			this.move(this.parent(index), this.pq[index]);
    			this.move(index, temp);
    			//Heapify the parent
    			this.percolateUp(this.parent(index));
    		}
    	}
    }

    /**
     * Heapify the array from the parent to it's child till the last level
     * 
     * @param index		The index of the current element.
     */
    void percolateDown(int index) {
    	/**
    	 * Stores the index of the leftchild of the current element
    	 */
    	int leftChild = this.leftChild(index);
    	
    	/**
    	 * Stores the index of the rightchild of the current element
    	 */
    	int rightChild = leftChild+1;
    	
    	/**
    	 * Stores the index of the child which has to be swapped with the parent
    	 */
    	int swapWith = -1;
    			
    	//If rightchild is in the array bounds
    	if(rightChild < this.size())
    	{
    		//If parent is greater than the leftchild
    		if(this.pq[index].compareTo(this.pq[leftChild]) > 0)
    		{
    			//If leftchild is greater than the rightchild
    			if(this.pq[leftChild].compareTo(this.pq[rightChild]) > 0)
    			{
    				swapWith = rightChild;
    			}
    			else
    			{
    				swapWith = leftChild;
    			}
    		}
    		//If parent is greater than the rightchild
    		else if(this.pq[index].compareTo(this.pq[rightChild]) > 0)
    		{
    			swapWith = rightChild;
    		}
    	}
    	//If leftchild is within array bounds
    	else if(leftChild < this.size())
    	{
    		//If parent is greater than the leftchild
    		if(this.pq[index].compareTo(this.pq[leftChild]) > 0)
    		{
    			swapWith = leftChild;
    		}
    	}
    	//If either of the children have to swapped with it's parents
    	if(swapWith > -1)
    	{
    		Comparable temp = this.pq[index];
			this.move(index, this.pq[swapWith]);
			this.move(swapWith, temp);
			//Heapify the child element
			this.percolateDown(swapWith);
    	}
    }


    /**
     * Store an element in it's destination
     * use this whenever an element moved/stored in heap. 
     * Will be overridden by IndexedHeap
     * 
     * @param dest		Index where the element has to stored
     * @param x			The generic element to be stored at the given destination index
     */
    void move(int dest, Comparable x) 
    {
    	pq[dest] = x;
    }

    /**
     * Compare two comparable elements a and b,
     * and return their difference
     * 
     * @param a		The first element
     * @param b		The second element
     * @return		The difference of the two elements
     */
    int compare(Comparable a, Comparable b) 
    {
    	return ((T) a).compareTo((T) b);
    }
    
    /**
     * Heapify the given array of Comparable elements
     */
    void buildHeap() 
    {
		for(int i=parent(size-1); i>=0; i--) 
		{
		    percolateDown(i);
		}
    }

    /**
     * Check if the heap is empty
     * 
     * @return		True if heap if empty else False
     */
    public boolean isEmpty() 
    {
    	return size() == 0;
    }

    /**
     * Find the number of elements in the heap
     * 
     * @return		The number of elements in the heap
     */
    public int size() 
    {
    	return size;
    }

    /**
     * Double the capacity of the heap
     */
    void resize() 
    {
    	/**
    	 * Temporary array to store the elements of the heap
    	 */
    	Comparable[] temp = pq;
    	pq = new Comparable[pq.length*2];
    	//Bulk-Copy the elements from the temporary array back into the heap
    	System.arraycopy(temp, 0, pq, 0, temp.length);
    }
    
    /**
     * @author sxa190016
     * @author bsv180000
     * @version 1.0 Index: Short project 4
     * 				Interface which defines two methods:
     * 				putIndex and getIndex 
     * 				Which have to be implemented
     */
    public interface Index 
    {
    	/**
    	 * Save index of the current element
    	 * 
    	 * @param index		The index of the current element
    	 */
        public void putIndex(int index);
        /**
         * Find the index of the current element
         * 
         * @return		The index of the current element
         */
        public int getIndex();
    }

    /**
     * @author sxa190016
     * @author bsv180000
     * @version 1.0 IndexedHeap: Short project 4
     * 				Has to be implemented
     */
    public static class IndexedHeap<T extends Index & Comparable<? super T>> extends BinaryHeap<T> {
    	
    	/**
    	 * Build a priority queue with a given array
    	 * 
    	 * @param capacity		The initial capacity of the IndexedHeap
    	 */
        IndexedHeap(int capacity) {
            super(capacity);
	}

        /**
         * Restore heap order property after the priority of x has decreased
         * 
         * @param x		The element whose priority has been decreased
         */
        void decreaseKey(T x) {
        	this.percolateUp(x.getIndex());
        }

	@SuppressWarnings("unchecked")
	@Override
        void move(int i, @SuppressWarnings("rawtypes") Comparable x) {
            super.move(i, x);
            ((T) x).putIndex(i);
        }
    }

    /**
	 * main function to test the BinaryHeap class
	 * 
	 * @param args		Arguments to be passed to the main function
	 */
    public static void main(String[] args) 
    {
    	/**
    	 * Create an unordered String array
    	 */
		String[] arr = {"b", "d", "f", "c", "a"};
		
		/**
		 * Create a BinaryHeap to store the elements of the String array
		 */
		BinaryHeap<String> h = new BinaryHeap<String>(arr.length);
	
		//Print the original order of elements in the array
		System.out.print("Before:");
		for(String x: arr) 
		{
		    h.offer(x);
		    System.out.print(" " + x);
		}
		
		System.out.println();
		
		//Print the size of the heap
		System.out.println(h.size());
		
		//Offer 3 more elements to resize the heap
		h.offer("g");
	    System.out.print(" " + "g");
	    h.offer("e");
	    System.out.print(" " + "e");
	    h.offer("z");
	    System.out.print(" " + "z");
	
		System.out.println();
		
		//Print the size of the heap
		System.out.println(h.size());
		
		//Print the order of elements in the heap
		for(int i=0; i<h.size(); i++)
		{
			System.out.print(" " + h.pq[i]);
		}
		
		System.out.println();
	
		//Store the elements back into the array in an ordered fashion
		arr = new String [h.size()];
		for(int i=0; i<arr.length; i++) 
		{
		    arr[i] = h.poll();
		}
	
		//Print the sorted order of elements in the array
		System.out.print("After :");
		for(String x: arr) 
		{
		    System.out.print(" " + x);
		}
		System.out.println();
		
		/**
		 * Create an integer array of elements
		 */
		Integer[] int_arr = {5, 4, 3, 2, 1};
		
		/**
		 * A BinaryHeap for integers
		 */
		BinaryHeap<Integer> bh = new BinaryHeap<Integer>(int_arr.length);
		
		//Save the elements in the Binary Heap in unordered fashion
		for(int i=0; i<int_arr.length; i++)
		{
			bh.move(i, int_arr[i]);
			System.out.println(bh.pq[i]);
		}
		//Set the size of the heap
		bh.size = int_arr.length;
		
		//Heapify the unordered elements in the heap
		bh.buildHeap();
		
		//Print the order of elements in the heapified heap-array pq
		for(int i=0; i<bh.size(); i++)
		{
			System.out.print(" " + bh.pq[i]);
		}
    }
}