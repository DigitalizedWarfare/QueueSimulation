package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.Customer;
import model.ServiceQueue;
import controller.SimulationController;

/**
 * <p>The view is designed to be user friendly and simple. Click on a cashier to display its individual
 *    info. To start the program, press the launch button and enter in the appropriate data.<p>
 */
public class SimulationView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JFrame myMainView;
	private JFrame myUserInputView;
    private Container myContainer;
    private JPanel myCenterPanel;
    private GridLayout myGridLayout;
	private SimulationController myController;
	private SingleQueuePanel[] myQueuePanels;
	private JTextArea myGeneralLineTextArea, myCashierInfoTextArea;
	
	public SimulationView(SimulationController controller)
	{
		myController = controller;
		this.displayView();
	}
	
	/**
	 * <p>Set up the GUI and display its contents. The GUI has numerous nested panels, including
	 *    an array of 5 line panels which are used to distinguish between the 5 different lines. <p>
	 */
	public void displayView()
	{
		   myMainView = new JFrame();
		   myMainView.setSize(1150, 700);
		   myMainView.setTitle("Queue Simulation");
		   myMainView.setDefaultCloseOperation(EXIT_ON_CLOSE);
		   myMainView.setResizable(false);
		   myContainer = myMainView.getContentPane();
		   
		   myUserInputView = new JFrame();
		   myUserInputView.setSize(400, 450);
		   myUserInputView.setTitle("Enter Queue Simulation Input");
		   myUserInputView.setDefaultCloseOperation(EXIT_ON_CLOSE);
		   myUserInputView.setVisible(false);
		   myUserInputView.setLayout(null);
		   myUserInputView.setResizable(false);
		   Container inputContainer = myUserInputView.getContentPane();
		   
		   myQueuePanels = new SingleQueuePanel[5];
		   
		   JLabel numberCustomersLabel = new JLabel("Enter the number of customers: ");
		   final JTextArea numberCustomersInput = new JTextArea("# customers");
		   numberCustomersLabel.setBounds(20, 20, 250, 25);
		   inputContainer.add(numberCustomersLabel);
		   inputContainer.add(numberCustomersInput);
		   numberCustomersInput.setBounds(230, 20, 100, 25);
		   
		   JLabel numberQueuesLabel = new JLabel("Enter the number of queues: ");
		   String[] numberQueues = {"1", "2", "3", "4", "5"};
		   final JComboBox<String> numberQueuesList = new JComboBox<String>(numberQueues);
		   numberQueuesList.setBounds(210, 100, 100, 25);
		   numberQueuesLabel.setBounds(20, 100, 190, 25);
		   inputContainer.add(numberQueuesList);
		   inputContainer.add(numberQueuesLabel);
		   
		   JLabel cashierServiceTimeLabel = new JLabel("Enter max time for cashier to serve: ");
		   final JTextArea cashierServiceTimeInput = new JTextArea("Time (ms)");
		   cashierServiceTimeLabel.setBounds(20, 180, 250, 25);
		   inputContainer.add(cashierServiceTimeLabel);
		   inputContainer.add(cashierServiceTimeInput);
		   cashierServiceTimeInput.setBounds(260, 180, 100, 25);
		   
		   JLabel generationTimeLabel = new JLabel("Enter customer generation time: ");
		   final JTextArea generationTimeInput = new JTextArea("Time (ms)");
		   generationTimeLabel.setBounds(20, 280, 350, 25);
		   inputContainer.add(generationTimeLabel);
		   inputContainer.add(generationTimeInput);
		   generationTimeInput.setBounds(240, 280, 100, 25);
		   
		   JButton startButton = new JButton("Start Simulation");
		   startButton.setBounds(130, 400, 150, 25);
		   inputContainer.add(startButton);
		   
		   startButton.addActionListener(new ActionListener() 
		   {
			   public void actionPerformed(ActionEvent e) 
			   {
				   myUserInputView.setVisible(false);
				   
				   try
				   {
					   int size = numberQueuesList.getSelectedIndex();
					   int custTime = Integer.parseInt(generationTimeInput.getText());
					   int cashTime = Integer.parseInt(cashierServiceTimeInput.getText());
					   int customers = Integer.parseInt(numberCustomersInput.getText());
					   
					   for(int i = 5 ; i > (size + 1); i--)
					   {
						   myQueuePanels[i - 1].hideCashier();
					   }
					   
					   myController.setQueueManager(size, custTime , cashTime , customers);
				   }
				   catch(NumberFormatException exception)
				   {
					   JOptionPane.showMessageDialog(null, "The simulation can not be started. Please double check  \n"
					   		+ "that you have entered only numerical values, thank-you.");
				   }
		       }
		   });
	
		   myCenterPanel = new JPanel();
		   myGridLayout = new GridLayout(0, 5);
		   myCenterPanel.setLayout(myGridLayout);
		   myContainer.add(myCenterPanel, BorderLayout.CENTER);
		   
		   SimulationPanel bottomPanel = new  SimulationPanel();
		   myContainer.add(bottomPanel, BorderLayout.SOUTH);
		   
		   TitledBorder border;
		   border = BorderFactory.createTitledBorder("Quick Stats");
		   border.setTitleJustification(TitledBorder.CENTER);
		   border.setTitleColor(Color.black);
		   bottomPanel.setBorder(border);
		   
		   myCashierInfoTextArea = new JTextArea();
		   myCashierInfoTextArea.setSize(50, 50);
		   myCashierInfoTextArea.setEditable(false);
		   myCashierInfoTextArea.setBackground(Color.black);
		   myCashierInfoTextArea.setForeground(Color.green);
		   myCashierInfoTextArea.setBorder(new LineBorder(Color.WHITE));
		   bottomPanel.add(myCashierInfoTextArea);
		   
		   for(int i = 0; i < myQueuePanels.length; i++)
		   {
			   myQueuePanels[i] = new SingleQueuePanel();
			   myCenterPanel.add(myQueuePanels[i]);
			   myQueuePanels[i].start();
			   
			   final int t = i;
			   
			   myQueuePanels[i].getCashier().addMouseListener(new MouseAdapter()  
			   {  
				    public void mouseClicked(MouseEvent e)  
				    {  
				    	switch(t)
				    	{
				    	   case 0: displayCashierResults(0); break;
				    	   case 1: displayCashierResults(1); break;
				    	   case 2: displayCashierResults(2); break;
				    	   case 3: displayCashierResults(3); break;
				    	   case 4: displayCashierResults(4); break;
				    	   default: break;
				    	}
				    }  
				});  
		   }
		   
		   myGeneralLineTextArea = new JTextArea();
		   myGeneralLineTextArea.setSize(50, 50);
		   myGeneralLineTextArea.setEditable(false);
		   myGeneralLineTextArea.setBackground(Color.black);
		   myGeneralLineTextArea.setForeground(Color.green);
		   myGeneralLineTextArea.setBorder(new LineBorder(Color.WHITE));
		   bottomPanel.add(myGeneralLineTextArea);
		   
		   /*
		    * set custom fonts here
		    */
		   try 
		   {
			    Font myfont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/digital-7.ttf"));
			     
			    myGeneralLineTextArea.setFont(myfont.deriveFont(18f));
			    myGeneralLineTextArea.setText("General Line Information");
			    myCashierInfoTextArea.setFont(myfont.deriveFont(18f));
			    border.setTitleFont(myfont.deriveFont(18f));
			    myCashierInfoTextArea.setText("Click On Cashier For Information");
				inputContainer.setFont(myfont.deriveFont(35f));
				numberCustomersLabel.setFont(myfont.deriveFont(15f));
				numberQueuesLabel.setFont(myfont.deriveFont(15f));
				cashierServiceTimeLabel.setFont(myfont.deriveFont(15f));
				generationTimeLabel.setFont(myfont.deriveFont(15f));
				startButton.setFont(myfont.deriveFont(15f));
			} 
		    catch (IOException|FontFormatException e) 
		    {
			     e.getCause();
			}

		   JButton runAll = new JButton();
		   runAll.setOpaque(false);
		   runAll.setBorderPainted(false);
		   runAll.setIcon(new ImageIcon("Resources/launch2.jpeg"));
		   bottomPanel.add(runAll);
		   
		   runAll.addActionListener(new ActionListener() 
		   {
			   public void actionPerformed(ActionEvent e) 
			   {
		           myUserInputView.setVisible(true);
		       }
		   });
		 
		   this.pack();
		   myMainView.setVisible(true);
	}
	
    
	/**
	 * <p>This method is called from the controller thread to constantly update the GUI. This method
	 *    must be called in order to display the simulation properly. The array of line panels is used
	 *    to update the specified line.<p>
	 */
	public void displayQueueSimulation(ServiceQueue<Customer> queue, int columnNumber) 
	{	
		myQueuePanels[columnNumber].setNumberCustomersLabel(queue.getMyNumberOfCustomersInLane() + "");
		myQueuePanels[columnNumber].setLength(queue.getSize());
        myCenterPanel.validate();
	}
	
	public SingleQueuePanel[] getPanels()
	{
		return myQueuePanels;
	}
	
	/**
	 * <p>This method is used to display all of the cashier results. It is called in real-time in
	 *    order to update the text area as the simulation is running.<p>
	 */
	public void displayCashierResults(int i)
	{
		String results = "\n" +
				         "Number Served: " + myController.getServiceQueues().get(i).getMyNumberCustomersServedSoFar() + "\n\n" +
				         "Total service time: " + myController.getServiceQueues().get(i).getMyTotalServiceTime() + "\n\n" +
				         "Total wait time: " + myController.getServiceQueues().get(i).getMyTotalWaitTime() + "\n\n" +
				         "Total idle time: " + myController.getServiceQueues().get(i).getMyTotalIdleTime() + "\n\n" +
				         "Average wait time: " + myController.getServiceQueues().get(i).averageWaitTime() + "\n\n" +
				         "Average idle time: " + myController.getServiceQueues().get(i).averageIdleTime() + "\n\n" +
				         "Average service time: " + myController.getServiceQueues().get(i).averageServiceTime();
		
		myCashierInfoTextArea.setText(results);
	}
	
	/**
	 * <p>This method is used to display all of the simulation results. It is called in real-time in
	 *    order to update the text area as the simulation is running.<p>
	 */
	public void displayResults()
	{
		String results = "\n" +
				         "Total service time: " + myController.getTotalServiceTime()  + "\n\n" +
				         "Total waiting time: " + myController.getTotalWaitTime()  + "\n\n" +
				         "Total idle time: " + myController.getTotalIdleTime()  + "\n\n" +
				         "Total served: " + myController.getTotalServed()  + "\n\n"  +
				         "Average waiting time: " + myController.getAverageWaitingTime() + "\n\n" +
				         "Average service time: " + myController.getAverageServiceTime() + "\n\n" +
				         "Average idle time: " + myController.getAverageIdleTime() + "\n\n";
		
		myGeneralLineTextArea.setText(results);
	}
}