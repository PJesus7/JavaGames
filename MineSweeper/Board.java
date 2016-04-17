import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
/**
 * The Board class models the game-board.
 */
public class Board {  // save as Board.java
	int nrMines;
	int ROWS;
	int COLS;

   // package access
   Cell[][] cells;  // a board composes of ROWS-by-COLS Cell instances

   /** Constructor to initialize the game board */
   public Board(int ROWS, int COLS) {
	   this.ROWS = ROWS; this.COLS = COLS;
      cells = new Cell[ROWS][COLS];  // allocate the array
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col] = new Cell(row, col); // allocate element of the array
         }
      }
   }

   /** Initialize (or re-initialize) the contents of the game board */
	public void init(int nrMines) {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				cells[row][col].clear();  // clear the cell content
			}
		}
		this.nrMines = nrMines; int i = 1, row, col;
		while (i<=nrMines) {
		Random rn = new Random(); row = rn.nextInt(ROWS);
		rn = new Random(); col = rn.nextInt(COLS);
		if (cells[row][col].content != Seed.MINE && cells[row][col].content != Seed.EIGHT) { //no mine and not sorrounded by more than 8 mines
			cells[row][col].content = Seed.MINE;
			if(row > 0 && col > 0) cells[row-1][col-1].content = cells[row-1][col-1].content.increase();
			if(row > 0) cells[row-1][col].content = cells[row-1][col].content.increase();
			if(row > 0 && col < COLS - 1) cells[row-1][col+1].content = cells[row-1][col+1].content.increase();
			if(col < COLS - 1) cells[row][col+1].content = cells[row][col+1].content.increase();
			if(row < ROWS - 1 && col < COLS - 1) cells[row+1][col+1].content = cells[row+1][col+1].content.increase();
			if(row < ROWS - 1) cells[row+1][col].content = cells[row+1][col].content.increase();
			if(row < ROWS - 1 && col > 0) cells[row+1][col-1].content = cells[row+1][col-1].content.increase();
			if(col > 0) cells[row][col-1].content = cells[row][col-1].content.increase();
			i++;
			}
		}
	}

	public void neighbours0(int row, int col) { //if we click a 0 we also click the cell's neighbours
			if(row > 0 && col > 0) {
				partialneighbours (row-1, col-1);}
			if(row > 0) {
				partialneighbours (row-1, col);}
			if(row > 0 && col < COLS - 1) {
				partialneighbours (row-1, col+1);}
			if(col < COLS - 1) {
				partialneighbours (row, col+1);}
			if(row < ROWS - 1 && col < COLS - 1) {
				partialneighbours (row+1, col+1);}
			if(row < ROWS - 1) {
				partialneighbours (row+1, col);}
			if(row < ROWS - 1 && col > 0) {
				partialneighbours (row+1, col-1);}
			if(col > 0) {
				partialneighbours (row, col-1);}
	}

	public void partialneighbours (int r, int c){//simplify program
		if(cells[r][c].clicked != Click.LEFT) { //if the neighbour was not clicked, then click it
			cells[r][c].clicked = Click.LEFT;
			if (cells[r][c].content == Seed.EMPTY) { //if it doesn't have mines too, then do recursion
				neighbours0(r, c);
				}
			}
		};

   /** Return true if you lose (i.e., left click on a mine) */
   public boolean lost() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (cells[row][col].content == Seed.MINE && cells[row][col].clicked == Click.LEFT) {
               return true;
            }
         }
      }
      return false;
   }

   public boolean win() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (cells[row][col].content != Seed.MINE && cells[row][col].clicked != Click.LEFT) {
               return false; //if any of the non-mined squares was not clicked, then it has not won yet
            }
         }
      }
      return true;
   }

}