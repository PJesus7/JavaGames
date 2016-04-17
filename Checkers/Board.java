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
      // initialize the game
      for (int row = 0; row < 3; ++row) {
         for (int col = 0; col < Constants.COLS; ++col) {
            if((row + col) % 2 == 0) cells[row][col].content = Seed.BLACK;
         }
      }
       for (int row = Constants.ROWS - 3; row < Constants.ROWS; ++row) {
         for (int col = 0; col < Constants.COLS; ++col) {
            if((row + col) % 2 == 0) cells[row][col].content = Seed.WHITE;
         }
      }
   }

	//Black's move
	public boolean canMoveLeftBlack(int rowSelected, int colSelected) {//just move
		if(colSelected > 0 & cells[rowSelected+1][colSelected-1].content == Seed.EMPTY) return true
	}
	public boolean canMoveRightBlack(int rowSelected, int colSelected) {//just move
		if(colSelected < Constants.COLS - 1 & cells[rowSelected+1][colSelected+1].content == Seed.EMPTY) return true
	}

	//Black's eat
	public boolean canEatLeftBlack(int rowSelected, int colSelected) {//just eat
		if(colSelected > 1 & cells[rowSelected+1][colSelected-1].content == Seed.WHITE & cells[rowSelected+2][colSelected-2].content == Seed.EMPTY) return true
	}
	public boolean canEatRightBlack(int rowSelected, int colSelected) {//just eat
		if(colSelected < Constants.COLS - 2 & cells[rowSelected+1][colSelected+1].content == Seed.WHITE & cells[rowSelected+2][colSelected+2].content == Seed.EMPTY) return true
	}

	//White's move
	public boolean canMoveLeftWhite(int rowSelected, int colSelected) {//just move
		if(colSelected > 0 & cells[rowSelected-1][colSelected-1].content == Seed.EMPTY) return true
	}
	public boolean canMoveRightWhite(int rowSelected, int colSelected) {//just move
		if(colSelected < Constants.COLS - 1 & cells[rowSelected-1][colSelected+1].content == Seed.EMPTY) return true
	}

	//White's eat
	public boolean canEatLeftWhite(int rowSelected, int colSelected) {//just eat
		if(colSelected > 1 & cells[rowSelected-1][colSelected-1].content == Seed.BLACK & cells[rowSelected-2][colSelected-2].content == Seed.EMPTY) return true
	}
	public boolean canEatRightWhite(int rowSelected, int colSelected) {//just eat
		if(colSelected < Constants.COLS - 2 & cells[rowSelected-1][colSelected+1].content == Seed.BLACK & cells[rowSelected-2][colSelected+2].content == Seed.EMPTY) return true
	}

	public int canPlayCrowned(Seed mySeed, int rowSelected, int colSelected) {
	}

	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
   //do the move
	public void playRight(Seed mySeed, int rowSelected, int colSelected, int nextPiece) { //nextPiece is the col from the canPlayRight function, which checks for valid move
		for (int colFlip = colSelected; colFlip <= nextPiece; ++colFlip) {
			cells[rowSelected][colFlip].content = mySeed;
		}
	}
	//////////////////////////////////////////
	public void playUp(Seed mySeed, int rowSelected, int colSelected, int nextPiece) {
		for (int rowFlip = rowSelected; rowFlip >= nextPiece; --rowFlip) {
			cells[rowFlip][colSelected].content = mySeed;
		}
	}
	/////////////////////////////////////////
	public void playLeft(Seed mySeed, int rowSelected, int colSelected, int nextPiece) {
		for (int colFlip = colSelected; colFlip >= nextPiece; --colFlip) {
			cells[rowSelected][colFlip].content = mySeed;
		}
	}
	////////////////////////////////////////
	public void playDown(Seed mySeed, int rowSelected, int colSelected, int nextPiece) {
		for (int rowFlip = rowSelected; rowFlip <= nextPiece; ++rowFlip) {
			cells[rowFlip][colSelected].content = mySeed;
		}
	}
	//DIAGONALS
	public void playUpRight(Seed mySeed, int rowSelected, int colSelected, int nextPiece) {
		int row = rowSelected;
		for (int colFlip = colSelected; colFlip <= nextPiece; ++colFlip) {
			cells[row][colFlip].content = mySeed; row--;
		}
	}
	//////////////////////////////////////////
	public void playUpLeft(Seed mySeed, int rowSelected, int colSelected, int nextPiece) {
		int row = rowSelected;
		for (int colFlip = colSelected; colFlip >= nextPiece; --colFlip) {
			cells[row][colFlip].content = mySeed; row--;
		}
	}
	/////////////////////////////////////////
	public void playDownLeft(Seed mySeed, int rowSelected, int colSelected, int nextPiece) {
		int row = rowSelected;
		for (int colFlip = colSelected; colFlip >= nextPiece; --colFlip) {
			cells[row][colFlip].content = mySeed; row++;
		}
	}
	////////////////////////////////////////
	public void playDownRight(Seed mySeed, int rowSelected, int colSelected, int nextPiece) {
		int row = rowSelected;
		for (int colFlip = colSelected; colFlip <= nextPiece; ++colFlip) {
			cells[row][colFlip].content = mySeed; row++;
		}
	}

	///////////////////////////////////////////////////////////
	//valid square to play (if one canPlay works, then it is a good square to play: boolean)
	public boolean validSquare(Seed mySeed, int rowSelected, int colSelected) {
		if (cells[rowSelected][colSelected].content != Seed.EMPTY) return false;
		if (canPlayRight(mySeed, rowSelected, colSelected) != -1) {return true;
	} else if (canPlayUpRight(mySeed, rowSelected, colSelected) != -1) {return true;
	} else if (canPlayUp(mySeed, rowSelected, colSelected) != -1) {return true;
	} else if (canPlayUpLeft(mySeed, rowSelected, colSelected) != -1) {return true;
	} else if (canPlayLeft(mySeed, rowSelected, colSelected) != -1) {return true;
	} else if (canPlayDownLeft(mySeed, rowSelected, colSelected) != -1) {return true;
	} else if (canPlayDown(mySeed, rowSelected, colSelected) != -1) {return true;
	} else if (canPlayDownRight(mySeed, rowSelected, colSelected) != -1) {return true;
	} return false;
	}

	//check the whole grid for valid moves
	public boolean playerCanPlay(Seed mySeed) {
		for (int row = 0; row < Constants.ROWS; ++row) {
			for (int col = 0; col < Constants.COLS; ++col) {
				if(validSquare(mySeed, row, col)) return true;  //there is at least 1 available square to play
			}
		}
		return false;
	}

	//PLAY (first use the valid square thingy, and then for the ones that are not -1 use the play Functions)
	public void playMove(Seed mySeed, int rowSelected, int colSelected) {
		int aux;
		aux = canPlayRight(mySeed, rowSelected, colSelected);
		if (aux != -1) {playRight(mySeed, rowSelected, colSelected, aux);}
		aux = canPlayUpRight(mySeed, rowSelected, colSelected);
		if (aux != -1) {playUpRight(mySeed, rowSelected, colSelected, aux);}
		aux = canPlayUp(mySeed, rowSelected, colSelected);
		if (aux != -1) {playUp(mySeed, rowSelected, colSelected, aux);}
		aux = canPlayUpLeft(mySeed, rowSelected, colSelected);
		if (aux != -1) {playUpLeft(mySeed, rowSelected, colSelected, aux);}
		aux = canPlayLeft(mySeed, rowSelected, colSelected);
		if (aux != -1) {playLeft(mySeed, rowSelected, colSelected, aux);}
		aux = canPlayDownLeft(mySeed, rowSelected, colSelected);
		if (aux != -1) {playDownLeft(mySeed, rowSelected, colSelected, aux);}
		aux = canPlayDown(mySeed, rowSelected, colSelected);
		if (aux != -1) {playDown(mySeed, rowSelected, colSelected, aux);}
		aux = canPlayDownRight(mySeed, rowSelected, colSelected);
		if (aux != -1) {playDownRight(mySeed, rowSelected, colSelected, aux);}
	}


   public int nrWhites() {
		int nrWhite=0;
		for (int row = 0; row < Constants.ROWS; ++row) {
			for (int col = 0; col < Constants.COLS; ++col) {
				if (cells[row][col].content == Seed.WHITE) {nrWhite++;}
			}
		}
		return nrWhite;
	}

   public int nrBlacks() {
		int nrBlack=0;
		for (int row = 0; row < Constants.ROWS; ++row) {
			for (int col = 0; col < Constants.COLS; ++col) {
				if (cells[row][col].content == Seed.BLACK) {nrBlack++;}
			}
		}
		return nrBlack;
	}

   /** declare the winner */
   public GameState hasWon() {
		int nrWhite=nrWhites(), nrBlack=nrBlacks();
		if (nrWhite > nrBlack) return GameState.WHITE_WON;
		if (nrWhite < nrBlack) return GameState.BLACK_WON;
		return GameState.DRAW;
	}

	public void paint(Graphics g){
		//draw the squares
		for (int row = 0; row < Constants.ROWS; ++row) {
			for (int col = 0; col < Constants.COLS; ++col) {
				if ((row + col) % 2 == 0) {
					g.setColor(new Color(139,69,19));
					g.fillRect(Constants.CELL_SIZE * row, Constants.CELL_SIZE * col, Constants.CELL_SIZE, Constants.CELL_SIZE);
				} else {
					g.setColor(new Color(244,164,96));
					g.fillRect(Constants.CELL_SIZE * row, Constants.CELL_SIZE * col, Constants.CELL_SIZE, Constants.CELL_SIZE);
				}
			}
		}
		// Draw the grid-lines
		g.setColor(Color.BLACK);
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