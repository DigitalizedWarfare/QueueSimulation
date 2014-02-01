package controller;
/**
 * The controller that communicates between the model and the view. 
 */
import java.util.Vector;
import model.Customer;
import model.Queue;
import model.ServiceQueue;
import model.ServiceQueueManager;
import model.SimulationModel;
import view.SimulationView;
import view.SingleQueuePanel;

public class SimulationController implements Runnable
{
   private SimulationView myView;
   private SimulationModel myModel;
   private ServiceQueueManager<Queue<Customer>> myManager;
   private Thread myThread;
   private boolean mySuspended;
   private long myStartTime;
   
   /**
    * Constructor that sets up the view, model, thread, and starts the thread.
    */
   public SimulationController()
   {
	  myView = new SimulationView(this);
	  myModel = new SimulationModel(this);
	  myThread = new Thread(this);
	  myThread.start();
   }

   /**
    * Sets the models manager for initialization of the program.
    * @param size
    * @param time
    * @param cashierTime
    * @param customers
    */
   public void setQueueManager(int size, int time , int cashierTime , int customers) 
   {
	  myModel.setQueueManager(size, time , cashierTime , customers);
   }

   /**
    * method that updates the view constantly by passing it the queue for the respective panel to update and the results for 
    * the timing.
    * @param manager
    */
   public void updateDisplay(ServiceQueueManager<Queue<Customer>> manager) 
   {
	   while(mySuspended == false)
	   {
		   try
		   {
			   Thread.sleep(100);
			   
			   for(int i =  0; i < 5; i++)
			   {
					myView.displayQueueSimulation(myManager.getServiceQueues().get(i), i);
					myView.displayResults();
					
					if(myModel.getManager().totalServedSoFar() >= myModel.getCustomers())
				   	{
				   	    myModel.suspendThreads();
				   	    mySuspended = true;
				   	}
			   }
		   }
		   catch(Exception e)
		   {
			   //do nothing
		   }
	   }  
   }
   
   public SingleQueuePanel[] getPanels()
   {
	   return myView.getPanels();
   }
   
   public int getTotalServed() 
   {
		return myManager.totalServedSoFar();
   }
   
   public long getTotalServiceTime() 
   {
		return myManager.totalServiceTime();
   }
   
   public long getTotalWaitTime() 
   {
		return myManager.totalWaitingTime();
   }
   
   public long getTotalIdleTime() 
   {
       return myManager.totalIdleTime();
   }
   
   public double getAverageIdleTime() 
   {
	  return myManager.averageIdleTime();
   }
   
   public double getAverageServiceTime() 
   {
	  return myManager.averageServiceTime();
   }
   
   public double getAverageWaitingTime() 
   {
	  return myManager.averageWaitingTime();
   }
   
   
   public void setManager(ServiceQueueManager<Queue<Customer>> manager)
   {
	    myManager = manager;
	    myStartTime = myManager.getStartTime();
   }
   
   
   public Vector<ServiceQueue<Customer>> getServiceQueues()
   {
	   return myModel.getServiceQueues();
   }
   
   
   @Override
   public void run() 
   {
	  try
   	  {
		 mySuspended = false;
   		 this.updateDisplay(myModel.getManager());
   	  }
   	  catch(Exception e)
   	  {
   		 e.printStackTrace();
   	  }
   }
   
   public long getGlobalStartTime()
   {
	   return myStartTime;
   }
}