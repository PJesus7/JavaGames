import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * The Board class models the game-board.
 */
public class Board {  // save as Board.java

   // package access
   Cell[][] cells;  // a board composes of ROWS-by-COLS Cell instances

   /** Constructor to initialize the game board */
   public Board() {
      cells = new Cell[Constants.ROWS][Constants.COLS];  // allocate the array
      for (int row = 0; row < Constants.ROWS; ++row) {
         for (int col = 0; col < Constants.COLS; ++col) {
            cells[row][col] = new Cell(row, col); // allocate element of the array
         }
      }
   }

   /** Initialize (or re-initialize) the contents of the game board */
   public void init() {
      for (int row = 0; row < Constants.ROWS; ++row) {
         for (int col = 0; col < Constants.COLS; ++col) {
            cells[row][col].clear();  // clear the cell content
         }
      }
   }

   /** Return true if it is a draw (i.e., no more EMPTY cell) */
   public boolean isDraw() {
      for (int row = 0; row < Constants.ROWS; ++row) {
         for (int col = 0; col < Constants.COLS; ++col) {
            if (cells[row][col].content == Seed.EMPTY) {
               return false; // an empty seed found, not a draw, exit
            }
         }
      }
      return true; // no empty cell, it's a draw
   }

   /** Return true if the player with "theSeed" has won after placing at
       (currentRow, currentCol) */
   public boolean hasWon(Seed theSeed,int currentRow,int currentCol) {
      return (cells[currentRow][0].content == theSeed         // 3-in-the-row
                   && cells[currentRow][1].content == theSeed
                   && cells[currentRow][2].content == theSeed
              || cells[0][currentCol].content == theSeed      // 3-in-the-column
                   && cells[1][currentCol].content == theSeed
                   && cells[2][currentCol].content == theSeed
              || currentRow == currentCol            // 3-in-the-diagonal
                   && cells[0][0].content == theSeed
                   && cells[1][1].content == theSeed
                   && cells[2][2].content == theSeed
              || currentRow + currentCol == 2    // 3-in-the-opposite-diagonal
                   && cells[0][2].content == theSeed
                   && cells[1][1].content == theSeed
                   && cells[2][0].content == theSeed);
   }

	public void paint(Graphics g){
		// Draw the grid-lines
		g.setColor(Color.LIGHT_GRAY);
		for (int row = 1; row < Constants.ROWS; ++row) {
			g.fillRoundRect(0, Constants.CELL_SIZE * row - Constants.GRID_WIDHT_HALF,
			Constants.CANVAS_WIDTH-1, Constants.GRID_WIDTH, Constants.GRID_WIDTH, Constants.GRID_WIDTH);
		}
		for (int col = 1; col < Constants.COLS; ++col) {
			g.fillRoundRect(Constants.CELL_SIZE * col - Constants.GRID_WIDHT_HALF, 0,
			Constants.GRID_WIDTH, Constants.CANVAS_HEIGHT-1, Constants.GRID_WIDTH, Constants.GRID_WIDTH);
		}
		for (int row = 0; row < Constants.ROWS; ++row) { //draw everything everytime the game changes
			for (int col = 0; col < Constants.COLS; ++col) { //consider using repaint instead
				cells[row][col].paint(g, row, col);
					// Draw the Seeds of all the cells if they are not empty
					// Use Graphics2D which allows us to set the pen's stroke
				}
			}
	}

}