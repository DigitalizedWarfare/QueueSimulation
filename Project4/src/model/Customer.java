package model;
/**
 * A customer that holds the data that needs to be accessed while calculating global and individual queue statistics
 *
 *
 */
public class Customer 
{
   private long myServiceTime;
   private long myEntryTime;
   private long myWaitTime;
   
   /**
    * Constructor that sets the service time, entrytime, and wait time.
    */
   public Customer()
   {
	   myServiceTime = 0;
	   myEntryTime = 0;
	   myWaitTime= 0;
   }
   
   public void setServiceTime(long time)
   {
	   myServiceTime = time;
   }
   
   public void setEntryTime(long time)
   {
	   myEntryTime = time;
   }
   
   public void setWaitTime(long time)
   {
	   myWaitTime = time;
   }
   
   public long getServiceTime()
   {
	   return myServiceTime;
   }
   
   public long getEntryTime()
   {
	   return myEntryTime;
   }
   
   public long getWaitTime()
   {
	   return myWaitTime;
   }
}