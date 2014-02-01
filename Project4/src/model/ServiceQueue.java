package model;

/**
 * <p>The service queue is used to keep track of each grocery line and its individual statistics.<p>
 *
 * @param <T>
 */
public class ServiceQueue<T> extends Queue<Customer>
{
  private int myNumberCustomersServedSoFar;
  private int myNumberOfCustomersInLane;
  private long myTotalWaitTime;
  private long myTotalServiceTime;
  private long myTotalIdleTime;
  private long myElapsedTime;
  private long myStartTime;
  
  public ServiceQueue()
  {
	  myElapsedTime = 0;
	  myTotalIdleTime = 0;
	  myTotalWaitTime = 0;
	  myTotalServiceTime = 0;
	  myNumberOfCustomersInLane = 0;
	  myNumberCustomersServedSoFar = 0;
	  myStartTime = System.currentTimeMillis();
  }
  
  public void addToElapsedTime(int elapsed)
  {
	  myElapsedTime+=elapsed;
  }
  
  public void addToIdleTime(long l)
  {
	  myTotalIdleTime+=l;
  }
  
  public void addToWaitTime(long l)
  {
	  myTotalWaitTime+=l;
  }
  
  public void addToServiceTime(long l)
  {
	  myTotalServiceTime+=l;
  }
  
  /**
   * <p>Insert a customer into the service queue.<p>
   */
  public void insertCustomer(Customer customer)
  {
	  this.enqueue(customer);
	  myNumberOfCustomersInLane = this.getSize();
  }
  
  /**
   * <p>Check if the line is empty, if so then it is idle. Otherwise, perform logic to update 
   *    the customers and their timing statistics.<p>
   */
  public void serveCustomer()
  {
      if(this.isEmpty())
      {
        this.addToIdleTime(10);
      }
      else
      {
	    myNumberCustomersServedSoFar++;
	    
//	        this.getHead().setServiceTime(System.currentTimeMillis() - this.getHead().getEntryTime());
//  	    this.getHead().setWaitTime(this.getHead().getServiceTime() - this.getHead().getEntryTime());
//  	    this.addToServiceTime(this.getHead().getServiceTime());
//  	    this.addToWaitTime(this.getHead().getWaitTime());
//  	    this.dequeue();
//  	    myNumberOfCustomersInLane = this.getSize();
	    
	    if(this.getHead() != null)
	    {
	    	this.getHead().setServiceTime(System.currentTimeMillis() - this.getHead().getEntryTime());
	  	    this.getHead().setWaitTime(this.getHead().getServiceTime() - this.getHead().getEntryTime());
	  	    this.addToServiceTime(this.getHead().getServiceTime());
	  	    this.addToWaitTime(this.getHead().getWaitTime());
	  	    this.dequeue();
	  	    myNumberOfCustomersInLane = this.getSize();
	    }
	    else
	    {
	    	System.out.println("head is null");
	    }
      }
  }
  
  /**
   * <p>This method calculates the average wait time by dividing the total wait time by the
   *    number of customers served so far.<p>
   * @return average wait time
   */
  public long averageWaitTime()
  {
	  return myTotalWaitTime /  myNumberCustomersServedSoFar;
  }
  
  /**
   * <p>This method calculates the average service time by dividing the total service time by the
   *    number of customers served so far.<p>
   * @return average service time
   */
  public long averageServiceTime()
  {
	  return myTotalServiceTime /  myNumberCustomersServedSoFar;
  }
  
  /**
   * <p>This method calculates the average idle time by dividing the total idle time by the
   *    number of customers served so far.<p>
   * @return average idle time
   */
  public long averageIdleTime()
  {
	  return myTotalIdleTime /  myNumberCustomersServedSoFar;
  }
  
  /*
   * start accessors & mutators
   */
  public int getMyNumberCustomersServedSoFar() 
  {
	 return myNumberCustomersServedSoFar;
  }

  public void setMyNumberCustomersServedSoFar(int myNumberCustomersServed) 
  {
	 myNumberCustomersServedSoFar = myNumberCustomersServed;
  }

  public int getMyNumberOfCustomersInLane() 
  {
	 return myNumberOfCustomersInLane;
  }

  public void setMyNumberOfCustomersInLane(int myNumberOfCustomers) 
  {
	 myNumberOfCustomersInLane = myNumberOfCustomers;
  }

  public long getMyTotalWaitTime() 
  {
	 return myTotalWaitTime;
  }

  public void setMyTotalWaitTime(long myTotalWait) 
  {
	 myTotalWaitTime = myTotalWait;
  }

  public long getMyTotalServiceTime() 
  {
	 return myTotalServiceTime;
  }

  public void setMyTotalServiceTime(long myTotalService) 
  {
	 myTotalServiceTime = myTotalService;
  }

  public long getMyTotalIdleTime() 
  {
	return myTotalIdleTime;
  }

  public void setMyTotalIdleTime(long myTotalIdle) 
  {
	myTotalIdleTime = myTotalIdle;
  }
}