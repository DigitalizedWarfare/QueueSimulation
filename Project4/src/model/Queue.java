package model;
/**
 * the queue itself that holds data
 */
import java.util.Iterator;
import java.util.LinkedList;

public class Queue<T> 
{
	private LinkedList<T> myList;
	
	/**
	 * Constructor for the queue, sets up the linkedlist to be used.
	 */
	public Queue()
	{
		myList = new LinkedList<T>();
	}
	
	/**
	 * adds data node to the queue
	 * @param datum
	 */
	public void enqueue(T datum)
	{
		myList.addLast(datum);
	}
	
	/**
	 * add removes data node form the queue
	 */
	public void dequeue()
	{
		myList.removeFirst();
	}
	
	/**
	 * determines if the queue is empty
	 * @return
	 */
	public boolean isEmpty()
	{
		return myList.isEmpty();
	}
	
	/**
	 * returns the size of the queue
	 * @return size of the queue
	 */
	public int getSize()
	{
		return myList.size();
	}
	
	/**
	 * gets the last node in the queue
	 * @return the last node in queue
	 */
	public T getTail()
	{
		return myList.peekLast();
	}
	
	/**
	 * gets the first node in the queue
	 * @return the first node in queue
	 */
	public T getHead()
	{
		return myList.peekFirst();
	}
	
	/**
	 * iterator that give information about the contents of the queue
	 */
	public void printQueue()
	{
		Iterator<T> myItr;
		myItr = myList.iterator();
		int counter = 0;
		
		while(myItr.hasNext())
		{
			counter++;
			System.out.println("# " + counter + " in line: " + myItr.next());
		}
	}

}