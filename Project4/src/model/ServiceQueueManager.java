package model;

import java.util.Vector;
/**
 * The managers that communicates with the other classes to make sure that the right information is relayed between
 * the classes that need them.
 *
 * @param <T>
 */
public class ServiceQueueManager<T>
{
    private int myNumberOfServiceQueues;
    private Vector<ServiceQueue<Customer>> myServiceQueues;
    private long myTotalWaitTime;
    private long myTotalServiceTime;
    private long myTotalIdleTime;
    private double myAverageWaitTime;
    private double myAverageServiceTime;
    private double myAverageIdleTime;
    private long myStartTime;
    
    /**
     * default constructor for the manager
     */
    public ServiceQueueManager()
    {
    	myServiceQueues = new Vector<ServiceQueue<Customer>>(5);
    	myTotalWaitTime = 0;
        myTotalServiceTime = 0;
        myTotalIdleTime = 0;
        myAverageWaitTime = 0;
        myAverageServiceTime = 0;
        myAverageIdleTime = 0;
        myStartTime = 0;
        
        for(int i = 0; i < 5; i++)
        {
        	myServiceQueues.add(new ServiceQueue<Customer>());
        }
        
        myStartTime = System.currentTimeMillis();
    }
    
    /**
     * Constructor for the manager that sets values based on parameters
     * @param numberQueues
     * @param maxTime
     */
    public ServiceQueueManager(int numberQueues)
    {
    	myServiceQueues = new Vector<ServiceQueue<Customer>>(numberQueues);
    	myTotalWaitTime = 0;
        myTotalServiceTime = 0;
        myTotalIdleTime = 0;
        myAverageWaitTime = 0;
        myAverageServiceTime = 0;
        myAverageIdleTime = 0;
        myStartTime = 0;
               
        for(int i = 0; i < numberQueues + 1; i++)
        {
        	myServiceQueues.add(new ServiceQueue<Customer>());
        }
        
        System.out.println(myServiceQueues.size());
        
        myStartTime = System.currentTimeMillis();
        
        myNumberOfServiceQueues = numberQueues;
    }
    
    /**
     * returns the total number of customers served for all the queues
     * @return customers served 
     */
    public int totalServedSoFar()
    {
    	int totalServed = 0;
    	
    	for(int i = 0; i < myServiceQueues.size(); i++)
    	{
    		totalServed += myServiceQueues.get(i).getMyNumberCustomersServedSoFar();
    	}
    	
    	return totalServed;
    }
    
    /**
     * returns the total waiting time for all the queues
     * @return total waiting time
     */
    public long totalWaitingTime()
    {
    	for(int i = 0; i < myServiceQueues.size(); i++)
    	{
    		myTotalWaitTime += myServiceQueues.get(i).getMyTotalWaitTime();
    	}
    	
    	return myTotalWaitTime;
    }
    
    /**
     * returns the total service time for all the queues
     * @return total service time
     */
    public long totalServiceTime()
    {
     	for(int i = 0; i < myServiceQueues.size(); i++)
     	{
     		myTotalServiceTime += myServiceQueues.get(i).getMyTotalServiceTime();
     	}
     	
     	return myTotalServiceTime;
    }
    
    /**
     * returns the total idle time for all the queues
     * @return total idle time
     */
    public long totalIdleTime()
    {
     	for(int i = 0; i < myServiceQueues.size(); i++)
     	{
     		myTotalIdleTime += myServiceQueues.get(i).getMyTotalIdleTime();
     	}
     	
     	return myTotalIdleTime;
    }
    
    /**
     * Determines the shortest queue and returns it
     * @return the shortest queue
     * @throws InterruptedException
     */
    public ServiceQueue<Customer> determinesShortestQueue() throws InterruptedException
    {
    	ServiceQueue<Customer> tempQueue = (myServiceQueues.get(0));
    	
    	for(int i = 0; i < myServiceQueues.size(); i++)
     	{
     	     if(tempQueue.getSize() >= myServiceQueues.get(i).getSize())
     	     {
     	    	 tempQueue = (myServiceQueues.get(i));
     	     }
     	}
    	
    	return tempQueue;
    }
    
    /**
     * calculates the average waiting time for all the queues
     * @return the average waiting time
     */
    public double averageWaitingTime()
    {
    	myAverageWaitTime = this.totalWaitingTime();
    	
    	return myAverageWaitTime / myNumberOfServiceQueues;
    }
    
    /**
     * calculates the average service time for all the queues
     * @return the average service time
     */
    public double averageServiceTime()
    {
    	myAverageServiceTime = this.totalServiceTime();
    	
    	return myAverageServiceTime / myNumberOfServiceQueues;
    }
    
    /**
     * calculates the average idle time for all the queues
     * @return the average idle time
     */
    public double averageIdleTime()
    {
    	myAverageIdleTime = this.totalIdleTime();
    	
    	return myAverageIdleTime / myNumberOfServiceQueues;
    }
    
    public Vector<ServiceQueue<Customer>> getServiceQueues()
    {
    	return myServiceQueues;
    }
    
    public long getStartTime()
    {
    	return myStartTime;
    }
}