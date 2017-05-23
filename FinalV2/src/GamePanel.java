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

	//private Rectangle screenRect;
	private boolean shiftHeld = false;

	//private Tile mario;
	/*private Tile[][] tiles;
	private Rectangle[][] grid;
	private Color[][] colors;
	private Color[][] copyOfColors;*/


	//private boolean currentPlayer, rowDelete, rotation, columnDelete;

	private KeyHandler keyControl;

	private JButton backButton, resetButton, loadButton, saveButton;

	//private int BlackTurnsTillDelete, RedTurnsTillDelete;

	private Main w;
	private Game game;

	/**
	 * Creates a new GamePanel object that initializes the 2D array that will hold all the game tiles and another 2D array to hold the rectangles that get drawn on the screen.
	 */
	public GamePanel (Main w, Game game) {
		super();
		this.w = w;
		this.game = game;
		/*currentPlayer = true;
		rowDelete = true;
		rotation = true;
		columnDelete = true;
		BlackTurnsTillDelete = 3;
		RedTurnsTillDelete = 3;*/
		keyControl = new KeyHandler();
		setBackground(Color.YELLOW);
		//screenRect = new Rectangle(0,0,DRAWING_WIDTH,DRAWING_HEIGHT);
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
		//tiles = new Tile[7][7];
		//tiles[4][4] = new Tile(true);
		/*grid = new Rectangle[7][7];
		colors = new Color[7][7];
		copyOfColors = new Color[7][7];
		for(int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[0].length; col++) {
				grid[row][col] = new Rectangle(50 + row * RECT_WIDTH, 50 + col * RECT_HEIGHT, RECT_WIDTH, RECT_HEIGHT);
				colors[row][col]= Color.yellow;
				copyOfColors[row][col]=Color.YELLOW;
			}
		}*/
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
				// g2.draw(col);
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

		//g2.setFont(new Font("font", Font.BOLD, 50));
		/*g2.setColor(Color.BLACK);
		g2.drawString("1", 20, 120);
		g2.drawString("2", 20, 220);
		g2.drawString("3", 20, 320);
		g2.drawString("4", 20, 420);
		g2.drawString("5", 20, 520);
		g2.drawString("6", 20, 620);
		g2.drawString("7", 20, 720);*/



		//mario.draw(g2,this);


		// TODO Add any custom drawings here
	}

	/*
	 * Adds a new tile to the first open spot in the specified column, updating the 2D array that holds all the tiles.
	 * @param col The column that the tile should be added to.
	 */
	/*public void addTile(int col) {
		for(int row = game.gridLength() - 1; row > -1; row--) {
			if(game.getTiles()[col-1][row] == null) {
				turnCounterDeduction();
				game.togglePlayer();

				tiles[col-1][row] = new Tile(currentPlayer);
				return;
			}
		}
	}*/

	/*
	 * Makes the tiles fall to fill the lowest open spaces in their columns.
	 */
	/*public void gravity() {
		/*int delay = 1000; //milliseconds
		ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {*

		for(int x = 0; x < tiles.length; x++) {
			for(int y = tiles[0].length - 2; y >= 0; y--) {
				if(tiles[x][y + 1] == null && tiles[x][y] != null) {
					tiles[x][y + 1] = tiles[x][y];
					tiles[x][y] = null;
					repaint();
					if(y < tiles[0].length - 2)
						y += 2;
				}

			}
		}
		winner();/*}
		};
		Timer t = new Timer(delay, taskPerformer);
		t.start();*
		//t.stop();
	}*/

	/*
	 * Deletes all the tiles in a specified column.
	 * @param col The column from which all the tiles shall be deleted.
	 */
	/*public void deleteRow(int row) {
		if(rowDelete) {
			if(currentPlayer && BlackTurnsTillDelete == 0) {

				for(int i = 0; i < 7; i++) {
					tiles[i][row] = null;
					colors[i][row]=Color.BLUE;
				}	
				BlackTurnsTillDelete = 3;
				currentPlayer = !currentPlayer;

			} else if(!currentPlayer && RedTurnsTillDelete == 0) {

				for(int i = 0; i < 7; i++) {
					tiles[i][row] = null;
					colors[i][row]=Color.BLUE;
				}
				RedTurnsTillDelete = 3;
				currentPlayer = !currentPlayer;
			}
			killBlueBar(30,row,true);
			repaint();
			stallGravityFor(30);
			winner();

		}
	}*/





	/*
	 * Deletes all the tiles in a specified column.
	 * @param col The column from which all the tiles shall be deleted.
	 */
	/*public void deleteColumn(int col) {
		if(columnDelete) {
			if(currentPlayer && BlackTurnsTillDelete == 0) {
				for(int i = 0; i < 7; i++) {
					tiles[col][i] = null;
					colors[col][i]=Color.BLUE;
				}
				BlackTurnsTillDelete = 3;
				currentPlayer = !currentPlayer;
			} else if(!currentPlayer && RedTurnsTillDelete == 0) {
				for(int i = 0; i < 7; i++) {
					tiles[col][i] = null;
					colors[col][i]=Color.BLUE;
				}
				RedTurnsTillDelete = 3;	
				currentPlayer = !currentPlayer;
			}
			killBlueBar(30,col,false);
			winner();
		}
	}*/

	/*
	 * Returns whether the current player is Player 1 or Player 2.
	 * @return The corresponding number to the current player.
	 */
	/*public int playerInt() {
		if(currentPlayer)
			return 1;
		else
			return 2;
	}*/

	/*
	 * Returns the String representation of the current player
	 * @return Red if it is Red's Turn, and Black if it is Black's turn/
	 */
	/*public String playerColor() {
		if(currentPlayer)
			return "Red";
		else
			return "Black";
	}*/

	/*
	 * Determines if the given player has gotten four in a row on the screen.
	 * @param player the player to be checked for if they have gotten four in a row
	 * @return true if the player has four in a row, false otherwise
	 */
	/*public boolean hasWon(boolean player) {
		for(int row = 0; row < tiles.length; row++) {
			for(int col = 0; col < tiles[0].length; col++) {
				//int numInARow = 1;
				if(tiles[row][col] != null && tiles[row][col].getPlayer() == player) {
					try{
						if(tiles[row - 1][col].getPlayer() == player && tiles[row + 1][col].getPlayer() == player && tiles[row + 2][col].getPlayer() == player) {
							//System.out.println("The winner is Player " + player);
							//w.winnerSong();
							//JOptionPane.showMessageDialog(this, "Game Over! " +  playerColor() + " wins!");
							return true;
						}
					} catch(ArrayIndexOutOfBoundsException e) {

					} catch(NullPointerException e){}
					try{
						if(tiles[row][col - 1].getPlayer() == player && tiles[row][col + 1].getPlayer() == player && tiles[row][col + 2].getPlayer() == player) {
							//System.out.println("The winner is Player " + player);
							//w.winnerSong();
							//JOptionPane.showMessageDialog(this, "Game Over! " +  playerColor() + " wins!");
							return true;
						}
					} catch(ArrayIndexOutOfBoundsException e) {

					} catch(NullPointerException e){}

					try{
						if(tiles[row - 1][col - 1].getPlayer() == player && tiles[row + 1][col + 1].getPlayer() == player && tiles[row + 2][col + 2].getPlayer() == player) {
							//System.out.println("The winner is Player " + player);
							//w.winnerSong();
							//JOptionPane.showMessageDialog(this, "Game Over! " +  playerColor() + " wins!");
							return true;
						}
					} catch(ArrayIndexOutOfBoundsException e) {

					} catch(NullPointerException e){}
					try{
						if(tiles[row - 1][col + 1].getPlayer() == player && tiles[row + 1][col - 1].getPlayer() == player && tiles[row + 2][col - 2].getPlayer() == player) {
							//System.out.println("The winner is Player " + player);
							//w.winnerSong();
							//JOptionPane.showMessageDialog(this, "Game Over! " +  playerColor() + " wins!");
							return true;
						}
					} catch(ArrayIndexOutOfBoundsException e) {

					} catch(NullPointerException e){}
				}
			}
		}
		return false;
	}*/

	/*
	 * Determines if either player has won the game, and if they have, it creates a pop-up window that tells the players who has won.
	 */
	/*public void winner() {
		boolean a = hasWon(currentPlayer);
		boolean b = hasWon(!currentPlayer);
		if(a && b)
			JOptionPane.showMessageDialog(this, "Game Over! Both players tied!");
		else if(a)
			JOptionPane.showMessageDialog(this, "Game Over! " +  playerColor() + " wins!");
		else if(b) {
			currentPlayer = !currentPlayer;
			JOptionPane.showMessageDialog(this, "Game Over! " +  playerColor() + " wins!");
			currentPlayer = !currentPlayer;
		}*

	}*/


	/*
	 * Gets the keyControl field..
	 * @return The keyControl field.
	 */
	public KeyHandler getKeyHandler() {
		return keyControl;
	}

	/*
	 * Toggles whether the players can delete rows of tiles from the board.
	 */
	/*public void toggleRowDeletion() {
		rowDelete = !rowDelete;
	}*/

	/*
	 * Toggles whether the players can delete columns of tiles from the board.
	 */
	/*public void toggleColumnDeletion() {
		columnDelete = !columnDelete;
	}*/

	/*
	 * Toggles whether the players can rotate the board.
	 */
	/*public void toggleRotation() {
		rotation = !rotation;
	}*/

	/*
	 * Gets the current state of the rowDelete field.
	 */
	/*public boolean getRowDelete() {
		return rowDelete;
	}*/

	/*
	 * Gets the current state of the columnDelete field.
	 */
	/*public boolean getColumnDelete() {
		return columnDelete;
	}*/

	/*
	 * Gets the current state of the rotation field.
	 */
	/*public boolean getRotation() {
		return rotation;
	}*/


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
		while (true) { // Modify this to allow quitting
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
							//System.out.println("out of screen but lag");
						}
						if(test)
						{
							//System.out.println(getMousePosition());

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
							//System.out.println("out of screen but lag");
						}

						if(test)
						{
							//System.out.println(getMousePosition());

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

	/*
	 * Rotates the board to the right 90 degrees 
	 */
	/*public void turnRight()
	{
		if(game.getRotation()) {
			Tile[][] temp= new Tile[7][7];

			for (int row = 0; row < game.gridLength(); row++) {
				for(int col = 0; col < game.gridWidth(); col++) {
					temp[row][col] = game.getTiles()[col][6 - row];
				}
			}
			tiles=temp;
			repaint();
			turnCounterDeduction();
			currentPlayer = !currentPlayer;
		}*
		/*if(rotation) {
			Tile[][] temp= new Tile[7][7];

			for(int row = 6; row>=0; row--)
			{
				Tile[] hold= new Tile[7];

				for(int i = 0; i< 7; i++)
					hold[i]= tiles[i][row];

				temp[6-row]=hold;
			}
			tiles=temp;
			repaint();
			if(currentPlayer && BlackTurnsTillDelete > 0) {
				BlackTurnsTillDelete--;
			} else if(!currentPlayer && RedTurnsTillDelete > 0) {
				RedTurnsTillDelete--;
			}
			currentPlayer = !currentPlayer;

		}*
	}*/

	/*
	 * Rotates the board to the Left 90 degrees 
	 */
	/*public void turnLeft()
	{
		if(rotation) {
			Tile[][] temp= new Tile[7][7];

			for (int row = 0; row < tiles.length; row++) {
				for(int col = 0; col < tiles[0].length; col++) {
					temp[row][col] = tiles[6 - col][row];
				}
			}
			tiles=temp;
			repaint();
			turnCounterDeduction();
			currentPlayer = !currentPlayer;
		}*
		/*turnRight();
		turnRight();
		turnRight();*
	}*/

	@Override

	/**
	 * Allows players to go back to the menu from the game screen or to reset the game.
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(backButton))
			w.changePanel("menu");
		else if(o.equals(resetButton)) {
			/*for(int x = 0; x < tiles.length; x++) {
				for(int y = 0; y < tiles[0].length; y++) {
					tiles[x][y] = null;
				}
			}*/
			/*resetCounter++;
		    GamePanel game = new GamePanel(w);
		    addKeyListener(game.getKeyHandler());
		    w.cardPanel.add(game,"game" + resetCounter);
		    w.changePanel("game" + resetCounter);*/
			//tiles = new Tile[7][7];
			w.newGame();

		}
		else if(o.equals(saveButton))
		{
			game.saveState("savefile.txt");
			//System.out.println("saved");
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
		if(winner != null)
			JOptionPane.showMessageDialog(this, winner);

	}

	/*private void turnCounterDeduction() {
		if(currentPlayer == true && BlackTurnsTillDelete > 0) {
			BlackTurnsTillDelete--;
		} else if(currentPlayer == false && RedTurnsTillDelete > 0) {
			RedTurnsTillDelete--;
		}
		repaint();
	}*

	public void explode(int row, int col) {
		try {
		tiles[row][col] = null;
		tiles[row - 1][col] = null;
		tiles[row + 1][col] = null;
		tiles[row][col - 1] = null;
		tiles[row][col + 1] = null;
		tiles[row - 1][col - 1] = null;
		tiles[row - 1][col + 1] = null;
		tiles[row + 1][col - 1] = null;
		tiles[row + 1][col - 1] = null;
		} catch(ArrayIndexOutOfBoundsException e) {}

	}*/


}
