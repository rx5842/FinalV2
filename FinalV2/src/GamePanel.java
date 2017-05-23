import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;
import javax.swing.Timer;

import java.util.*;


public class GamePanel extends JPanel implements Runnable, MouseMotionListener, MouseListener, ActionListener
{
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 800;

	private static final int RECT_HEIGHT = (DRAWING_HEIGHT - 100) / 7;
	private static final int RECT_WIDTH = (DRAWING_WIDTH - 100) / 7;

	private boolean shiftHeld = false;
	
	private boolean fone=false;

	

	boolean canRun;

	private KeyHandler keyControl;

	private JButton backButton, resetButton, loadButton, saveButton;


	private Main w;
	private Game game;

	/**
	 * Creates a new GamePanel object that initializes the 2D array that will hold all the game tiles and another 2D array to hold the rectangles that get drawn on the screen.
	 */
	public GamePanel (Main w, Game game) {
		super();
		this.w = w;
		this.game = game;
		canRun = true;
		
		keyControl = new KeyHandler();
		setBackground(Color.YELLOW);
		backButton = new JButton("Back to Menu");
		backButton.addActionListener(this);
		add(backButton);
		
		

		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		add(resetButton);
		
		saveButton=new JButton("Save Game");
		saveButton.addActionListener(this);
		add(saveButton);
		
		loadButton=new JButton("Load Game");
		loadButton.addActionListener(this);
		add(loadButton);
		
		addMouseMotionListener(this);
		addMouseListener(this);
		new Thread(this).start();
	}

	/**
	 * The method that actually draws the game board and all the tiles on it, determining if the tiles should be red or black. Also draws text saying whose turn it currently is.
	 * @param g The Graphics object that allows the method to draw things on the screen.
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



		for (int row = 0; row < game.gridLength(); row++) {
			for(int col = 0; col < game.gridWidth(); col++) {
				Tile t = game.getTiles()[row][col];
				Rectangle r = game.getGrid()[row][col];
				g2.setColor(game.colors[row][col]);
				g2.fill(r);
				g2.setColor(Color.WHITE);
				if(t != null) {
					if(t.getPlayer())
						g2.setColor(Color.RED);
					else
						g2.setColor(Color.BLACK);
				} else
					g2.setColor(Color.WHITE);

				g2.fillOval((int)r.getX() + 5, (int)r.getY() + 5, (int)r.getWidth() - 10, (int)r.getHeight() - 10);
			}


		}

		g2.setFont(new Font("font", Font.BOLD, 50));
		if(!game.getCurrentPlayer()) {
			g2.setColor(Color.RED);
			g2.drawString("Red's Turn", 275, 795);
			g2.setFont(new Font("font", Font.BOLD, 10));
			if(game.getRowDelete() || game.getColumnDelete())
				g2.drawString(game.getRedTurns() + " Turns till Red can delete a row/column", 350, 50);
		} else {
			g2.setColor(Color.BLACK);
			g2.drawString("Black's Turn", 250, 795);
			g2.setFont(new Font("font", Font.BOLD, 10));
			if(game.getRowDelete() || game.getColumnDelete())
				g2.drawString(game.getBlackTurns() + " Turns till Black can delete a row/column", 350, 50);
		}

		


		// TODO Add any custom drawings here
	}

	


	/*
	 * Gets the keyControl field..
	 * @return The keyControl field.
	 */
	public KeyHandler getKeyHandler() {
		return keyControl;
	}


	
	private void stallGravityFor(int time)
	{
		oneSecond =time;
	}
	
	private void killBlueBar(int time, int rowOrCol,boolean isrow)
	{
		if(isrow)
		{
			blueBarTime=time;
			blueRow=rowOrCol;
		}
		else{
			blueBarTime=time;
			blueCol=rowOrCol;
		}

	}


	private void resetRowToOrig(int row)
	{
		for(int i = 0; i < 7; i++) {

			game.colors[i][row] = game.copyOfColors[i][row];

		}
		repaint();
	}

	private void resetColToOrig(int col)
	{
		for(int i = 0; i < 7; i++) {

			game.colors[col][i] = game.copyOfColors[col][i];

		}
	}


	int oneSecond=60;
	int blueBarTime=30;
	int blueRow=0;
	int blueCol=0;
	/**
	 * Runs the animations.
	 */
	public void run() {
		while (canRun) { // Modify this to allow quitting
			long startTime = System.currentTimeMillis();


			if(oneSecond>0)
			{
				oneSecond--;
			}
			else if(oneSecond==0)
			{
				oneSecond--;
				game.gravity();
			}

			if(blueBarTime>0)
			{
				blueBarTime--;
			}
			else if(blueBarTime==0)
			{
				blueBarTime--;
				resetRowToOrig(blueRow);
				resetColToOrig(blueCol);
			}
			repaint();

			long waitTime = 17 - (System.currentTimeMillis()-startTime);
			try {
				if (waitTime > 0)
					Thread.sleep(waitTime);
				else
					Thread.yield();
			} catch (InterruptedException e) {}
		}
	}


	/**
	 * 
	 * Used for handling keyboard interactions.
	 *
	 */
	public class KeyHandler implements KeyListener {

		private ArrayList<Integer> keys;

		/**
		 * Creates a new KeyHandler object and initializes the keys field.
		 */
		public KeyHandler() {
			keys = new ArrayList<Integer>();
		}

		/**
		 * Inherited abstract method from the KeyListener interface.
		 */
		public void keyPressed(KeyEvent e) {
			keys.add(e.getKeyCode());
			if(e.getKeyCode() == KeyEvent.VK_SHIFT) 
				shiftHeld=true;
			mouseMoved(null);

		}

		/**
		 * Inherited abstract method from the KeyListener interface that enables players to delete columns and rows from the board or rotate the board.
		 */
		public void keyReleased(KeyEvent e) {
			Integer code = e.getKeyCode();
			while(keys.contains(code))
				keys.remove(code);


			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				//Timer t = new Timer();
				game.turnRight();
				stallGravityFor(30);
				checkWinner(game.winner());


			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				game.turnLeft();
				stallGravityFor(30);
				checkWinner(game.winner());



			}

			if(e.getKeyCode() == KeyEvent.VK_SHIFT) 
			{
				shiftHeld=false;
				mouseMoved(null);

			}

		}

		/**
		 * Inherited abstract method from the KeyListener interface.
		 */
		public void keyTyped(KeyEvent e) {

		}

		/**
		 * Returns true if a key is pressed.
		 * @param code the code of the key that is being checked.
		 * @return true if the specified key is pressed, false otherwise.
		 */
		public boolean isPressed(int code) {
			return keys.contains(code);
		}
	}


	/**
	 * Inherited abstract method from the MouseListener interface.
	 */
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	/**
	 * Inherited abstract method that highlights the column a tile would be added to if the player clicked the mouse.
	 */
	public void mouseMoved(MouseEvent arg0) {

		if(shiftHeld)
		{
			for (int row = 0; row < game.gridLength(); row++) {
				for(int col = 0; col < game.gridWidth(); col++) {

					if(getMousePosition()!=null)
					{
						boolean test=false;
						try
						{
							test = game.getGrid()[row][col].contains(getMousePosition());
						}
						catch(NullPointerException e)
						{
						}
						if(test)
						{

							// chnages the current select colum to GRAY
							for (int i = 0; i < game.gridLength(); i++)
							{
								game.colors[i][col]=Color.gray;
								game.copyOfColors[i][col]=Color.gray;
							}

							// Resets all other Colums back to YELLOW
							for(int k = 0; k<7; k++)
							{
								if(k!=col)
								{
									for(int j=0; j<7; j++)
									{
										game.colors[j][k]=Color.yellow;
										game.copyOfColors[j][k]=Color.yellow;
									}
								}

							}
							repaint();
							break;
						}
					}
				}
			}
		}
		else
		{
			for (int row = 0; row < game.gridLength(); row++) {
				for(int col = 0; col < game.gridWidth(); col++) {

					if(getMousePosition()!=null){

						boolean test=false;
						try
						{
							test = game.getGrid()[row][col].contains(getMousePosition());
						}
						catch(NullPointerException e)
						{
						}

						if(test)
						{

							// chnages the current select colum to GRAY
							for (int i = 0; i<game.gridLength(); i++)
							{
								game.colors[row][i]=Color.gray;
								game.copyOfColors[row][i]=Color.gray;
							}

							// Resets all other Colums back to YELLOW
							for(int k = 0; k<7; k++)
							{
								if(k!=row)
								{
									for(int j=0; j<7; j++)
									{
										game.colors[k][j]=Color.yellow;
										game.copyOfColors[k][j]=Color.yellow;
									}
								}

							}
							repaint();
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Inherited abstract method from the MouseListener interface.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	/**
	 * Inherited abstract method from the MouseListener interface.
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Inherited abstract method from the MouseListener interface.
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Inherited abstract method from the MouseListener interface that allows players to add tiles to the board using the mouse.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = getMousePosition();

		int butt= e.getButton();

		if(p!=null&&butt==MouseEvent.BUTTON1){
			for (int row = 0; row < game.gridLength(); row++) {
				for(int col = 0; col < game.gridWidth(); col++) {
					if(game.getGrid()[row][col].contains(p))
					{
						game.addTile(row+1);
						checkWinner(game.winner());
					}
				}
			}

		}
		else if(p!=null&&butt==MouseEvent.BUTTON3)
		{
			if(shiftHeld)
			{
				for (int row = 0; row < game.gridLength(); row++) {
					for(int col = 0; col < game.gridWidth(); col++) {
						if(game.getGrid()[row][col].contains(p)) {
							game.deleteRow(col);
							stallGravityFor(30);
							checkWinner(game.winner());
						}

					}
				}
			}

			else{
				for (int row = 0; row < game.gridLength(); row++) {
					for(int col = 0; col < game.gridWidth(); col++) {
						if(game.getGrid()[row][col].contains(p))
							game.deleteColumn(row);
						checkWinner(game.winner());
					}
				}
			}
		}
	}



	/**
	 * Method from the MouseListener interface.
	 */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	

	/**
	 * Returns the fone
	 * @return the fone
	 */
	public boolean getFone()
	{
		return fone;
	}

	/**
	 * Allows players to go back to the menu from the game screen or to reset the game.
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(backButton))
			w.changePanel("menu");
		else if(o.equals(resetButton)) {
			fone=true;
			w=new Main("Connect 4.0",1);
			

		}
		else if(o.equals(saveButton))
		{
			game.saveState("savefile.txt");
		}
		else if(o.equals(loadButton))
		{
			 FileIO reader=new FileIO();
		     Game ng = (Game) reader.readObject("savefile.txt");
		     if(ng!=null)
		      {
	
		    	  game=ng;
		    	 
		    	  revalidate();
		    	  repaint();
		    	  
		    	  
		      }
		}

	}

	public void checkWinner(String winner) {
		if(winner != null) {
			JOptionPane.showMessageDialog(this, winner);
			canRun = false;
		}

	}

	


}
