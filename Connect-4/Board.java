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
	// Check for 4-in-a-line on the rowSelected
	int count = 0;
	for (int col = 0; col < Constants.COLS; ++col) {
		if (cells[currentRow][col].content == theSeed) {
			++count;
			if (count == 4) return true;  // found
		} else {
			count = 0; // reset and count again if not consecutive
		}
	}
	count = 0;
	for (int row = 0; row < Constants.ROWS; ++row) {
		if (cells[row][currentCol].content == theSeed) {
			++count;
			if (count == 4) return true;  // found
		} else {
			count = 0; // reset and count again if not consecutive
		}
	}
	int diagNumber = currentRow + currentCol;
	count = 0;
	for (int col = 0; col < Constants.COLS; ++col) {
		int row = diagNumber - col;
		if (row >= 0 && row < Constants.ROWS) {
			if (cells[row][col].content == theSeed) {
				++count;
				if (count == 4) return true;  // found
			} else {
			count = 0; // reset and count again if not consecutive
			}
		}
	}
	int otherDiagNumber = currentRow - currentCol;
	count = 0;
	for (int col = 0; col < Constants.COLS; ++col) {
		int row = otherDiagNumber + col;
		if (row >= 0 && row < Constants.ROWS) {
			if (cells[row][col].content == theSeed) {
				++count;
				if (count == 4) return true;  // found
			} else {
			count = 0; // reset and count again if not consecutive
			}
		}
	}
	return false;
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