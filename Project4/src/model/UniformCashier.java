package model;

import java.util.Random;

/**
 * <p>Used for generating a random delay between serving customers.<p>
 */
public class UniformCashier extends Cashier 
{
	private Random myRandom;
	private int myMaxServiceTime;
	
	public UniformCashier(int maxTimeService, ServiceQueue<Customer> queue)
	{
		super(maxTimeService, queue);
		myMaxServiceTime = maxTimeService;
		myRandom = new Random();
	}
	
	/**
	 * <p>generate random service time for cashier to serve customer.<p>
	 */
	public int generateServiceTime()
	{	
		return myRandom.nextInt(myMaxServiceTime);
	}
}
