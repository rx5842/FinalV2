import java.awt.*;
import java.io.File;
import java.io.Serializable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;




public class Game implements Serializable{

	private static final long serialVersionUID = 10203L;
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 800;

	private static final int RECT_HEIGHT = (DRAWING_HEIGHT - 100) / 7;
	private static final int RECT_WIDTH = (DRAWING_WIDTH - 100) / 7;

	
	private Tile[][] tiles;
	private Rectangle[][] grid;
	public Color[][] colors;
	public Color[][] copyOfColors;
	
	

	private boolean currentPlayer, rowDelete, rotation, columnDelete;

	
	
	private int blackTurnsTillDelete, redTurnsTillDelete;


	/**
	 * Creates a new GamePanel object that initializes the 2D array that will hold all the game tiles and another 2D array to hold the rectangles that get drawn on the screen.
	 */
	
	public Game() {
		currentPlayer = true;
		rowDelete = true;
		rotation = true;
		columnDelete = true;
		blackTurnsTillDelete = 6;
		redTurnsTillDelete = 6;
		
		tiles = new Tile[7][7];
		grid = new Rectangle[7][7];
		colors = new Color[7][7];
		copyOfColors = new Color[7][7];
		for(int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[0].length; col++) {
				grid[row][col] = new Rectangle(50 + row * RECT_WIDTH, 50 + col * RECT_HEIGHT, RECT_WIDTH, RECT_HEIGHT);
				colors[row][col]= Color.yellow;
				copyOfColors[row][col]=Color.YELLOW;
			}
		}
		
	}

	
	
	/**
	 * Saves the current state of the game
	 * @param filename the name of the file the game data will be written to
	 */
	public void saveState(String filename)
	{
		FileIO writer= new FileIO();
		writer.writeObject(filename,this);
	}

	/**
	 * Plays the game sound
	 */
	private static synchronized void playSound() {

		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("AddTile.wav"));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();

	}
	
	/**
	 * Plays a sound when a player deletes a row or column.
	 */
	private static synchronized void playDeleteSound() {

		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Delete.wav"));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();

	}

	/**
	 * Adds a new tile to the first open spot in the specified column, updating the 2D array that holds all the tiles.
	 * @param col The column that the tile should be added to.
	 */
	public void addTile(int col) {
		for(int row = tiles.length - 1; row > -1; row--) {
			if(tiles[col-1][row] == null) {
				turnCounterDeduction();
				currentPlayer = !currentPlayer;
				
				tiles[col-1][row] = new Tile(currentPlayer);
				playSound();
				//winner = winner();
				return;
			}
		}
	}

	/**
	 * Makes the tiles fall to fill the lowest open spaces in their columns.
	 */
	public void gravity() {
		

		for(int x = 0; x < tiles.length; x++) {
			for(int y = tiles[0].length - 2; y >= 0; y--) {
				if(tiles[x][y + 1] == null && tiles[x][y] != null) {
					tiles[x][y + 1] = tiles[x][y];
					tiles[x][y] = null;
					//repaint();
					if(y < tiles[0].length - 2)
						y += 2;
				}

			}
		}
		
	}

	/**
	 * Deletes all the tiles in a specified column.
	 * @param col The column from which all the tiles shall be deleted.
	 */
	public void deleteRow(int row) {
		if(rowDelete) {
			if(currentPlayer && blackTurnsTillDelete == 0) {

				for(int i = 0; i < 7; i++) {
					tiles[i][row] = null;
					colors[i][row]=Color.BLUE;
					playDeleteSound();
				}	
				blackTurnsTillDelete = 6;
				currentPlayer = !currentPlayer;

			} else if(!currentPlayer && redTurnsTillDelete == 0) {
			
				for(int i = 0; i < 7; i++) {
					tiles[i][row] = null;
					colors[i][row]=Color.BLUE;
					playDeleteSound();
				}
				redTurnsTillDelete = 6;
				currentPlayer = !currentPlayer;
			}
			
		}
	
	}





	/**
	 * Deletes all the tiles in a specified column.
	 * @param col The column from which all the tiles shall be deleted.
	 */
	public void deleteColumn(int col) {
		if(columnDelete) {
			if(currentPlayer && blackTurnsTillDelete == 0) {
				for(int i = 0; i < 7; i++) {
					tiles[col][i] = null;
					colors[col][i]=Color.BLUE;
					playDeleteSound();
				}
				blackTurnsTillDelete = 6;
				currentPlayer = !currentPlayer;
			} else if(!currentPlayer && redTurnsTillDelete == 0) {
				for(int i = 0; i < 7; i++) {
					tiles[col][i] = null;
					colors[col][i]=Color.BLUE;
					playDeleteSound();
				}
				redTurnsTillDelete = 6;	
				currentPlayer = !currentPlayer;
			}
		}
		
	}

	/**
	 * Returns whether the current player is Player 1 or Player 2.
	 * @return The corresponding number to the current player.
	 */
	public int playerInt() {
		if(currentPlayer)
			return 1;
		else
			return 2;
	}

	/**
	 * Returns the String representation of the current player
	 * @return Red if it is Red's Turn, and Black if it is Black's turn/
	 */
	public String playerColor() {
		if(currentPlayer)
			return "Red";
		else
			return "Black";
	}

	/**
	 * Determines if the given player has gotten four in a row on the screen.
	 * @param player the player to be checked for if they have gotten four in a row
	 * @return true if the player has four in a row, false otherwise
	 */
	public boolean hasWon(boolean player) {
		for(int row = 0; row < tiles.length; row++) {
			for(int col = 0; col < tiles[0].length; col++) {
				//int numInARow = 1;
				if(tiles[row][col] != null && tiles[row][col].getPlayer() == player) {
					try{
						if(tiles[row - 1][col].getPlayer() == player && tiles[row + 1][col].getPlayer() == player && tiles[row + 2][col].getPlayer() == player) {
							
							return true;
						}
					} catch(ArrayIndexOutOfBoundsException e) {

					} catch(NullPointerException e){}
					try{
						if(tiles[row][col - 1].getPlayer() == player && tiles[row][col + 1].getPlayer() == player && tiles[row][col + 2].getPlayer() == player) {
							
							return true;
						}
					} catch(ArrayIndexOutOfBoundsException e) {

					} catch(NullPointerException e){}

					try{
						if(tiles[row - 1][col - 1].getPlayer() == player && tiles[row + 1][col + 1].getPlayer() == player && tiles[row + 2][col + 2].getPlayer() == player) {
							
							return true;
						}
					} catch(ArrayIndexOutOfBoundsException e) {

					} catch(NullPointerException e){}
					try{
						if(tiles[row - 1][col + 1].getPlayer() == player && tiles[row + 1][col - 1].getPlayer() == player && tiles[row + 2][col - 2].getPlayer() == player) {
							
							return true;
						}
					} catch(ArrayIndexOutOfBoundsException e) {

					} catch(NullPointerException e){}
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines if either player has won the game, and if they have, it creates a pop-up window that tells the players who has won.
	 */
	public String winner() {
		boolean a = hasWon(currentPlayer);
		boolean b = hasWon(!currentPlayer);
		if(a && b) {
			return "Game Over! Both players tied!";
		} else if(a) {
			return "Game Over! " + playerColor() + " won!";
		} else if(b) {
			currentPlayer = !currentPlayer;
			return "Game Over! " + playerColor() + " won!";
			
		}
		return null;
	}




	/**
	 * Toggles whether the players can delete rows of tiles from the board.
	 */
	public void toggleRowDeletion() {
		rowDelete = !rowDelete;
	}

	/**
	 * Toggles whether the players can delete columns of tiles from the board.
	 */
	public void toggleColumnDeletion() {
		columnDelete = !columnDelete;
	}

	/**
	 * Toggles whether the players can rotate the board.
	 */
	public void toggleRotation() {
		rotation = !rotation;
	}

	/**
	 * Gets the current state of the rowDelete field.
	 */
	public boolean getRowDelete() {
		return rowDelete;
	}

	/**
	 * Gets the current state of the columnDelete field.
	 */
	public boolean getColumnDelete() {
		return columnDelete;
	}

	/**
	 * Gets the current state of the rotation field.
	 */
	public boolean getRotation() {
		return rotation;
	}


	/**
	 * Rotates the board to the right 90 degrees 
	 */
	public void turnRight()
	{
		if(rotation) {
			Tile[][] temp= new Tile[7][7];

			for (int row = 0; row < tiles.length; row++) {
				for(int col = 0; col < tiles[0].length; col++) {
					temp[row][col] = tiles[col][6 - row];
				}
			}
			tiles=temp;
			turnCounterDeduction();
			currentPlayer = !currentPlayer;
		}
		
	}

	/**
	 * Rotates the board to the Left 90 degrees 
	 */
	public void turnLeft()
	{
		if(rotation) {
			Tile[][] temp= new Tile[7][7];

			for (int row = 0; row < tiles.length; row++) {
				for(int col = 0; col < tiles[0].length; col++) {
					temp[row][col] = tiles[6 - col][row];
				}
			}
			tiles=temp;
			//repaint();
			turnCounterDeduction();
			currentPlayer = !currentPlayer;
		}
		
	}

	
	/**
	 * Reduces the number of turns till a player can delete a row or column.
	 */
	private void turnCounterDeduction() {
		if(currentPlayer == true && blackTurnsTillDelete > 0) {
			blackTurnsTillDelete--;
		} else if(currentPlayer == false && redTurnsTillDelete > 0) {
			redTurnsTillDelete--;
		}
	}
	
	/**
	 * Getter method
	 * @return the length of the grid array
	 */
	public int gridLength() {
		return tiles.length;
	}
	
	/**
	 * Getter method
	 * @return the length of the one of the arrays inside the 2D grid array
	 */
	public int gridWidth() {
		return tiles[0].length;
	}
	
	/**
	 * Getter method
	 * @return the current state of the 2D tile array
	 */
	public Tile[][] getTiles() {
		return tiles;
	}
	
	/**
	 * Getter method
	 * @return the current state of the 2D rectangle array
	 */
	public Rectangle[][] getGrid() {
		return grid;
	}
	
	
	/**
	 * Getter method
	 * @return the current player
	 */
	public boolean getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Getter method
	 * @return the red's turns till deletion
	 */
	public int getRedTurns() {
		return redTurnsTillDelete;
	}
	
	/**
	 * Getter method
	 * @return the black's turns till deletion
	 */
	public int getBlackTurns() {
		return blackTurnsTillDelete;
	}
	
	
}
