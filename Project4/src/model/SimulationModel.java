package model;

import java.util.Vector;

import controller.SimulationController;

/**
 * <p>The model is leveraged by the controller to update the underlying data as the simulation is
 *    running.<p>
 */
public class SimulationModel 
{
	private SimulationController myController;
	private CustomerGenerator myCustomerGen;
	private ServiceQueueManager<Queue<Customer>> myManager;
	private Vector<Cashier> myCashiers;
	private int myCustomers;
	
	public SimulationModel(SimulationController controller)
	{
		myController = controller;
		myCashiers = new Vector<Cashier>();
		myCustomers = 0;
	}
    
	/**
	 * <p>Start the simulation by beginning the threads. It will start the customer generator thread,
	 *    and it will also start each cashier thread. We then set the manager in the controller.<p>
	 *    @param size is number of managers
	 *    @param time is used for customer generation time
	 *    @param cashierTime is used for cashier generation time
	 *    @param customers is the # of customers to be generated
	 */
	public void setQueueManager(int size, int time , int cashierTime , int customers) 
	{
		myCustomers = customers;
		myManager = new ServiceQueueManager<Queue<Customer>>(size);
		myCustomerGen = new CustomerGenerator( time , customers , myManager);
		myCustomerGen.start();
		
	    for ( int i  = 0 ; i < myManager.getServiceQueues().size(); i ++)
	    {
	       myCashiers.add(new Cashier( cashierTime , myManager.getServiceQueues().get(i)));
	       myCashiers.get(i).start();
	    }
	    
	    myController.setManager(myManager);
	}
	
	public CustomerGenerator getCustomerGen()
	{
		return myCustomerGen;
	}
	
	public Vector<Cashier> getCashiers()
	{
		return myCashiers;
	}
	
	public ServiceQueueManager<Queue<Customer>> getManager()
	{
		return myManager;
	}
	
	public Vector<ServiceQueue<Customer>> getServiceQueues()
	{
		return myManager.getServiceQueues();
	}
	
	/**
	 * <p>Called when all customers have been served from controller to halt the threads.<p>
	 */
	public void suspendThreads()
	{ 
		System.out.println("suspending threads!");
		
	    for ( int i  = 0 ; i < myCashiers.size(); i ++)
	    {
	       myCashiers.get(i).suspend();
	    }
	    
	    for(int i = 0; i < myController.getPanels().length; i++)
	    {
	    	 myController.getPanels()[i].suspend();
	    }  	 	
	}
	
	
	public int getCustomers()
	{
		return myCustomers;
	}
}