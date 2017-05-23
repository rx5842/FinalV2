


import java.io.Serializable;

public class Tile implements Serializable {

	public static final int MARIO_WIDTH = 90;
	public static final int MARIO_HEIGHT = 90;

	private boolean player;
	

	/**
	 * Creates a new Tile object.
	 * @param player The player that this Tile object belongs to (whether it is a red or black tile).
	 */
	public Tile(boolean player) {
		this.player = player;
	}


	/**
	 * Returns the player that this Tile object belongs to.
	 * @return player The player that this tiles belongs to.
	 */
	public boolean getPlayer() {
		return player;
	}
	
	/**
	 * Returns the string representation of the tile.
	 * @return the string representation of the tile.
	 */
	public String toString()
	{
		String result= "";
		if(player)
		{
			result = "Player 2 and Black";
		}
		else
			result = "Player 1 and Red";
		return result;
				
	}

}
