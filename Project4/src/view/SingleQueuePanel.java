package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * <p>This panel is for each specific grocery line in the view. It contains a nested characterPanel which 
 *    is used to add character images. It also contains a nested panel for the cashier and the 
 *    number of customers label. 5 of these panels are generated in the View to account for all
 *    5 grocery lines.<p>
 */
public class SingleQueuePanel extends JPanel implements Runnable 
{
	private static final long serialVersionUID = 1L;
	private Vector<ImageIcon> myImages;
	private JLabel[] myLabels;
	private int myLength;
	private Thread myThread;
	private boolean mySuspended;
	private JPanel myTopOfPanel, myCharacterPanel;
	private JLabel myNumberCustomersLabel, myCashierImg;
	private ImageIcon myBg;
	
	public SingleQueuePanel()
	{
		 setLayout(null);
		 
		 myTopOfPanel = new JPanel();
		 myTopOfPanel.setOpaque(false);
		 myTopOfPanel.setLayout(new FlowLayout());
		 myTopOfPanel.setBounds(this.getWidth()/2 + 60, 400, 100, 90);
		 add(myTopOfPanel);
		 
		 myCharacterPanel = new JPanel();
		 myCharacterPanel.setOpaque(false);
		 myCharacterPanel.setLayout(new FlowLayout());
		 myCharacterPanel.setBounds(this.getWidth()/2 + 70, 10, 70, 425);
		 add(myCharacterPanel);
		  
		 myNumberCustomersLabel = new JLabel("0");
		 myNumberCustomersLabel.setBounds(this.getWidth()/2 + 28, 407, 40, 60);
		 add(myNumberCustomersLabel);
		 
		  try 
		  {
			 Font myfont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/digital-7.ttf"));
			 myNumberCustomersLabel.setFont(myfont.deriveFont(55f)); 
			 myNumberCustomersLabel.setForeground(Color.GREEN);
		  } 
		  catch (IOException|FontFormatException e) 
		  {
			 e.getCause();
		  }
		 
		 myCashierImg = new JLabel();
		 myCashierImg.setIcon(new ImageIcon("Resources/receptionist.png"));
		 myTopOfPanel.add(myCashierImg);
		 
		 myImages = new Vector<ImageIcon>();
		 myImages.add(new ImageIcon("Resources/nasa_astronaut.png"));
		 myImages.add(new ImageIcon("Resources/pinkcreature.png"));
		 myImages.add(new ImageIcon("Resources/bluecreature.png"));
		 myImages.add(new ImageIcon("Resources/yellowcreature.png"));
		
		 myThread = new Thread(this);
		 
		 myBg = new ImageIcon("Resources/laneNewest.png");
		 
		 myLength = 0;
	 }
	 
	 public void paintComponent(Graphics g) 
	 {
	   super.paintComponent(g);
	   
	   g.drawImage(myBg.getImage(), 0, 0, getWidth(), getHeight(), this);
     }
	 
	 
	 /**
	  * <p>Return a random character image to display on the view.<p>
	  */
	 public ImageIcon getRandomImage()
	 {
		 Random myRand = new Random();
		 int randomImgSprite = myRand.nextInt(myImages.size());
		 return myImages.get(randomImgSprite);
	 }
	 
	 /**
	  * <p>Constantly update the number of labels based on the number of customers in the lane.<p>
	  */
	 public void updateLabels()
	 { 
		 myLabels = new JLabel[myLength];
		 
		 for(int i = 0; i < myLabels.length; i++)
		 {
			 myLabels[i] = new JLabel("");
			 myCharacterPanel.add(myLabels[i]);
		 }
	 }
	 
	 public void setImage()
	 {
		 for(int i = 0; i < myLabels.length; i++)
		 {
			 myLabels[i].setIcon(this.getRandomImage());
		 }
	 }
	 
	 /**
	  * <p>Called repeatedly in order to update the number of images in line.<p>
	  */
	 public void setLength(int length)
	 {
		 myLength = length;
	 }
	
	@Override
	public void run() 
	{
        while(!mySuspended)
        {
        	mySuspended = false;
    		this.updateLabels();
    		this.setImage();
    		this.repaint();
    		
    		try 
    		{
				Thread.sleep(300);
			} 
    		catch (InterruptedException e) 
    		{
				e.printStackTrace();
			}
    		
    		this.removeLabels();
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
	
    public void resume()
    {
    	mySuspended = false;
    }
    
    public void suspend()
    {
    	mySuspended = true;
    }
    
    public void setNumberCustomersLabel(String numbCustomers)
    {
    	myNumberCustomersLabel.setText(numbCustomers);
    }
	 
    /**
	 * <p>Remove all images so the line will update in real time.<p>
	 */
	 public void removeLabels()
	 {
		 for(int i = 0; i < myLabels.length; i++)
		 {
			 Container parent = myLabels[i].getParent();
			 parent.remove(myLabels[i]);
			 parent.validate();
			 parent.repaint();
		 }
	 }
	 
	 
	 /**
	  * <p>Used to hide cashier for lines that are not in use.<p>
	  */
	 public void hideCashier()
	 {
		 myCashierImg.setVisible(false);
	 }
	 
	 public JLabel getCashier()
	 {
		 return myCashierImg;
	 }
}