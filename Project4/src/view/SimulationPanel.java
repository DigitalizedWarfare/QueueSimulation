package view;

import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * <p>This Class has the sole purpose of applying a custom background image to the bottom panel.<p>
 */
public class SimulationPanel extends JPanel
{
     private static final long serialVersionUID = 1L;
	 ImageIcon bg = new ImageIcon("Resources/bottomPanel.png");
	 
	 public SimulationPanel()
	 {
		 GridLayout gl =  new GridLayout(0, 3);
		 gl.setHgap(40);
		 setLayout(gl);
	 }
	 
	 @Override
	 public void paintComponent(Graphics g) 
	 {
	   super.paintComponent(g);
	   g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
     }
}