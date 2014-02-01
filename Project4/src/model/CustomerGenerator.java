package model;
/**
 * Generates customers for use in adding and removing from the queue.
 *
 */
public class CustomerGenerator implements Runnable
{
	private int myMaxTimeBetweenCustomers;
	private UniformCustomerGenerator myCustomGen;
	private Thread myThread;
	private int myCustomers;
	private ServiceQueueManager<Queue<Customer>> myManager;
	private boolean mySuspended;
	private long myStartTime;
	
	/**
	 * Constructor for setting up the basic properties for the generator
	 * @param maxTimeBetweenCustomers
	 * @param numCustomers
	 * @param manager
	 */
	public CustomerGenerator(int maxTimeBetweenCustomers , int numCustomers , ServiceQueueManager<Queue<Customer>> manager)
	{
		myMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
		myThread = new Thread(this);
		myManager = manager;
		myCustomers = numCustomers;
		myStartTime = System.currentTimeMillis();
	}
	
	@Override
	public void run() 
	{
		try
    	{
		   System.out.println("in run of custom gen");
    	   mySuspended = false;
    	   this.generateCustomer();
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
	    }
	}
	
	/**
	 * generates time for a customer to be generated
	 * @return returns the time for a customer to be generated
	 */
	public int generateTimeBetweenCustomers()
	{
		myCustomGen = new UniformCustomerGenerator(myMaxTimeBetweenCustomers , myCustomers , myManager);
		return myCustomGen.generateTimeBetweenCustomers();
	}
	
	/**
	 * generates a customer based on the cutomer generation time, then determines the shortest queue and inserts the customer
	 * into that queue
	 * @throws InterruptedException
	 */
	public void generateCustomer() throws InterruptedException 
	{
	    int i = 0;
	    
	    while ( i < myCustomers )
	    {
	        Customer customer = new Customer();
	        
	        try
	        {
	           Thread.sleep(this.generateTimeBetweenCustomers());
	        }
	        catch (InterruptedException e)
	        {
	           mySuspended = false;
	        }
	        
	        ServiceQueue<Customer> shortestQueue = myManager.determinesShortestQueue();
	        customer.setEntryTime(System.currentTimeMillis() - myStartTime);
	        shortestQueue.insertCustomer(customer);

	        i ++;
	    }   
	}
	
	public int getMaxTimeBetweenCustomers()
	{
		return myMaxTimeBetweenCustomers;
	}
	
	/**
	 * sets the thread to be active
	 */
    public void resume()
    {
    	System.out.println("resuming generator");
    	mySuspended = false;
    }
    
    /**
     * sets the thread to be inactive
     */
    public void suspend()
    {
    	System.out.println("suspending generator");
    	mySuspended = true;
    }
}