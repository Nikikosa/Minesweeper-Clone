package MinesweeperEngine;
/**
 * 960 lines of code
 * The game class is the driver for the gameBoard class. The only fuctions this class has is a main method,
 * reset method and a win method. gameBoard.java depends on this class for those three methods. These classes are
 * supposed to simulate a regular game of Minesweeper, which was created back in 1989. 
 * @author Nikikosa
 */
public class game {
	private static gameBoard mineSweeper; 	// Global variable for a gameBoard object.

	/**
	 * The main methods sole purpose is to create a gameBoard object.
	 * @param args
	 */
	public static void main(String[] args) {
		mineSweeper = new gameBoard();
	}
	
	/**
	 * The reset method gets called from the gameBoard object we created in the main method to create a new object. This is done
	 * because we will not have to do manually reset the board.
	 */
	public static void reset() {
		mineSweeper = new gameBoard();
	}
	
	/**
	 * The win method was added to fix a static issue within the gameBoard class. Basically if a win condition is met,
	 * this method will be called from the gameboard class, and the game will be won.
	 */
	public static void win() {
		mineSweeper.win();
	}

}