package model;

import java.util.Random;

/**
 * <p>Used for generating a random delay between adding customers.<p>
 */
public class UniformCustomerGenerator extends CustomerGenerator
{
	private Random myRandom;
	private int myTimeForCustomer;
	
	public UniformCustomerGenerator(int maxTimeBetweenCustomers , int customers , ServiceQueueManager<Queue<Customer>> manager) 
	{
		super(maxTimeBetweenCustomers , customers , manager);
		myTimeForCustomer = maxTimeBetweenCustomers;
		myRandom = new Random();
	}
	
	/**
	 * <p>generate random time for delay when generating new customers.<p>
	 */
	public int generateTimeBetweenCustomers()
	{
		return myRandom.nextInt(myTimeForCustomer - 10) + 10;
	}
}
