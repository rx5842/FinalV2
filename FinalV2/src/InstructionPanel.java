import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;


public class InstructionPanel extends JPanel implements ActionListener {
	
	Main w;
	
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 800;
	/**
	 * Creates a new InstructionPanel object with a back button to return to the menu.
	 * @param w The Main object that this InstructionPanel will be a part of.
	 */
	public InstructionPanel(Main w) {
		this.w = w;
		JButton backButton = new JButton("Back to Menu");
		

		setBackground(Color.YELLOW);

		backButton.addActionListener(this);
		add(backButton);
	}
	
	/**
	 * Changes the visible JPanel back to the menu.
	 */
	public void actionPerformed(ActionEvent e) {
		w.changePanel("menu");
	}
	
	/**
	 * Draws the instruction text to the screen.
	 */
	public void paintComponent(Graphics g)
	  {
	    super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background

		Graphics2D g2 = (Graphics2D)g;
		
		int width = getWidth();
	    int height = getHeight();
	    
	    double ratioX = (double)width/DRAWING_WIDTH;
	    double ratioY = (double)height/DRAWING_HEIGHT;
	    
	    AffineTransform at = g2.getTransform();
	    g2.scale(ratioX, ratioY);


	    g2.setTransform(at);
	    
	    g2.setColor(Color.RED);
		g2.setFont(new Font("font", Font.BOLD, 75));
		g2.drawString("Instructions", 50, 150);
		g2.setFont(new Font("font", Font.BOLD, 15));
		g2.drawString("The objective of this game is to get 4 of the same color tile in a row.", 50, 250);
		g2.drawString("Use the mouse to click on the column that you want to drop your tile into.", 50, 300);
		g2.drawString("Hit the right arrow key to \"turn\" the board 90 degrees to the right.", 50, 350);
		g2.drawString("Hit the left arrow key to \"turn\" the board 90 degrees to the left.", 50, 400);
		g2.drawString("Press right click when selecting a column to delete all the tiles in it.", 50, 450);
		g2.drawString("Press shift and right mouse click to delete all the tiles in the corresponding row.", 50, 500);
		
	  }
}