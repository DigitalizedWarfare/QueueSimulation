/**
 * This class is the cashier of the program and its task is to ask for cutomers so that it can serve them(dequeue)
 * 
 */
package model;

import javax.swing.JOptionPane;

public class Cashier implements Runnable
{
	private int myMaxTimeOfService;
	private UniformCashier myCashier;
	private boolean mySuspended;
	private Thread myThread;
	private ServiceQueue<Customer> myQueue;
	
	/**
	 * Constructor for the cashier sets up the Service queue for this specific cashier and the thread
	 * @param time for the max generation time, and a service queue for this cashier
	 * @param a service queue for this cashier
	 */
	public Cashier(int maxTimeService, ServiceQueue<Customer> queue)
	{
        myMaxTimeOfService = maxTimeService;
        myQueue = queue;
        myThread = new Thread(this);
	}
	
	/**
	 * continuously serves a customer after sleeping for its specified service time
	 * @throws InterruptedException
	 */
	public void serveCustomer() throws InterruptedException 
	{
		while(mySuspended == false)
		{
			Thread.sleep(this.generateServiceTime());
			myQueue.serveCustomer();
		}
	}
	
	/**
	 * generates the service time for the cashier
	 * @return
	 */
	public int generateServiceTime()
	{
        myCashier = new UniformCashier(myMaxTimeOfService, myQueue);
		return myCashier.generateServiceTime();
	}
	
	
	@Override
	public void run() 
	{
		try
    	{
    		mySuspended = false;
    		this.serveCustomer();
    	}
    	catch (InterruptedException e)
    	{
    		System.out.println("Thread suspended.");
    	}
	}
	
	public void start()
	{	 
	   try
	   {
	     myThread.start();
	   }
	   catch(IllegalThreadStateException e)
	   {
	     System.out.println("Thread already started");
	     
	     JOptionPane.showMessageDialog(null,"The simulation has already run, and your session has timed \n" +
	                                        "out. Now exiting... ");
	     System.exit(0);
	   }
	}
	
	/**
	 * sets mySuspended to false so that the run knows that the thread is active
	 */
    public void resume()
    {
    	System.out.println("resuming cashier");
    	mySuspended = false;
    }
    
    /**
     * sets mySuspended to true so that the run knows that the thread is not active
     */
    public void suspend()
    {
    	System.out.println("suspending cashier");
    	mySuspended = true;
    }
	
    
	public void setMaxTimeOfService(int time)
	{
		myMaxTimeOfService = time;
	}
	
	
	public int getMaxServiceTime()
	{
		return myMaxTimeOfService;
	}
	
	public ServiceQueue<Customer> getQueue()
	{
	    return myQueue;
	}
}