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

   /** Return true if there are no more EMPTY cells) **/
   public boolean isBoardFilled() {
      for (int row = 0; row < Constants.ROWS; ++row) {
         for (int col = 0; col < Constants.COLS; ++col) {
            if (cells[row][col].content == Seed.EMPTY) {
               return false;
            }
         }
      }
      return true;
   }

   //check for available moves
	public int canPlayRight(Seed mySeed, int rowSelected, int colSelected) { //to the right it will eat pieces; my new piece -> opponent pieces -> my old pieces
   	//gives the column of the row with the next mySeed piece; gives -1 if there is no such piece or player can't play
		Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
		int col, row, nrOpponent=0;

		// Flip opponent's seeds along the row to the right if any
		col = colSelected + 1;
		// Look for adjacent opponent's seeds up to 2nd last column
		while (col < Constants.COLS - 1 && cells[rowSelected][col].content == opponentSeed) {
			++col;  ++nrOpponent;
		}
		// Look for my seed immediately after opponent's seeds
		if (nrOpponent > 0 && col <= Constants.COLS - 1 && cells[rowSelected][col].content == mySeed) {
			return col; //RETURNS THE COLUMN
		}
		return -1;
	}

	/////////////////////////////////////////////////////////////////
 	public int canPlayUp(Seed mySeed, int rowSelected, int colSelected) { //my new piece ^(below is) opponent pieces (below is) my old piece
		Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
		int col, row, nrOpponent=0;

		// Flip opponent's seeds along the column upwards if any
		row = rowSelected - 1;
		// Look for adjacent opponent's seeds up to 2nd last column
		while (row > 0 && cells[row][colSelected].content == opponentSeed) {
			--row;  ++nrOpponent;
		}
		// Look for my seed immediately after opponent's seeds
		if (nrOpponent > 0 && row >= 0 && cells[row][colSelected].content == mySeed) {
			return row; //RETURNS THE ROW
		}
		return -1;
	}

	/////////////////////////////////////////////////////////////////
	public int canPlayLeft(Seed mySeed, int rowSelected, int colSelected) { // my old piece <- opponent pieces <- my new piece
		Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
		int col, row, nrOpponent=0;

		// Flip opponent's seeds along the row to the left if any
		col = colSelected - 1;
		// Look for adjacent opponent's seeds up to 2nd row
		while (col > 0 && cells[rowSelected][col].content == opponentSeed) {
			--col;  ++nrOpponent;
		}
		// Look for my seed immediately after opponent's seeds
		if (nrOpponent > 0 && col >= 0 && cells[rowSelected][col].content == mySeed) {
			return col; //RETURN THE COLUMN
		}
		return -1;
	}

	/////////////////////////////////////////////////////////////////
 	public int canPlayDown(Seed mySeed, int rowSelected, int colSelected) { //my new piece (is under) opponent's pieces (is under) my old piece
		Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
		int col, row, nrOpponent=0;

		// Flip opponent's seeds along the column downwards if any
		row = rowSelected + 1;
		// Look for adjacent opponent's seeds up to 2nd last row
		while (row < Constants.ROWS - 1 && cells[row][colSelected].content == opponentSeed) {
			++row;  ++nrOpponent;
		}
		// Look for my seed immediately after opponent's seeds
		if (nrOpponent > 0 && row <= Constants.ROWS - 1 && cells[row][colSelected].content == mySeed) {
			return row; //RETURNS THE ROW
		}
		return -1;
	}
	//DIAGONALS
	////////////////////////////////////////////////////////////////
	public int canPlayUpRight(Seed mySeed, int rowSelected, int colSelected) {
		Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
		int col, row, nrOpponent=0;

		// Flip opponent's seeds along the row to the right if any
		row = rowSelected - 1; col = colSelected + 1;
		// Look for adjacent opponent's seeds up to 2nd last column or 2nd row
		while (col < Constants.COLS - 1 && row > 0 && cells[row][col].content == opponentSeed) {
			--row; ++col;  ++nrOpponent;
		}
		// Look for my seed immediately after opponent's seeds
		if (nrOpponent > 0 && col <= Constants.COLS - 1 && row >= 0 && cells[row][col].content == mySeed) {
			return col; //RETURN THE COLUMN
			//the corresponding row + col = rowSelected + colSelected <=> row  = rowSelected + colSelected - col
		}
		return -1;
	}
	//////////////////////////////////////////////////////////////////
 	public int canPlayUpLeft(Seed mySeed, int rowSelected, int colSelected) {
		Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
		int col, row, nrOpponent=0;

		//
		row = rowSelected - 1; col = colSelected - 1;
		//
		while (row > 0 && col > 0 && cells[row][col].content == opponentSeed) {
			--row; --col; ++nrOpponent;
		}
		// Look for my seed immediately after opponent's seeds
		if (nrOpponent > 0 && row >= 0 && col >= 0 && cells[row][col].content == mySeed) {
			return col; //RETURN THE COLUMN
			//the corresponding row - col = rowSelected - colSelected <=> row = rowSelected - colSelected + col
		}
		return -1;
	}
	/////////////////////////////////////////////////////////////////
	public int canPlayDownLeft(Seed mySeed, int rowSelected, int colSelected) {
		Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
		int col, row, nrOpponent=0;

		// Flip opponent's seeds along the row to the left if any
		row = rowSelected + 1; col = colSelected - 1;
		// Look for adjacent opponent's seeds up to 2nd row
		while (row < Constants.ROWS - 1 && col > 0 && cells[row][col].content == opponentSeed) {
			++row; --col;  ++nrOpponent;
		}
		// Look for my seed immediately after opponent's seeds
		if (nrOpponent > 0 && row <= Constants.ROWS - 1 && col >= 0 && cells[row][col].content == mySeed) {
			return col; //RETURN THE COLUMN
			//the corresponding row  = rowSelected + colSelected - col
		}
		return -1;
	}
	/////////////////////////////////////////////////////////////////
	public int canPlayDownRight(Seed mySeed, int rowSelected, int colSelected) {
		Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
		int col, row, nrOpponent=0;

		// Flip opponent's seeds along the row to the left if any
		row = rowSelected + 1; col = colSelected + 1;
		// Look for adjacent opponent's seeds up to 2nd row
		while (row < Constants.ROWS - 1 && col < Constants.ROWS - 1 && cells[row][col].content == opponentSeed) {
			++row; ++col;  ++nrOpponent;
		}
		// Look for my seed immediately after opponent's seeds
		if (nrOpponent > 0 && row <= Constants.ROWS - 1 && col <= Constants.ROWS - 1 && cells[row][col].content == mySeed) {
			return col; //RETURN THE COLUMN
			//the corresponding row = rowSelected - colSelected + col
		}
		return -1;
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

	//number of pieces that player will gain (use for AI)
		public int piecesGained(Seed mySeed, int rowSelected, int colSelected) {
			int aux, res = 1; // the piece that will be played
			aux = canPlayRight(mySeed, rowSelected, colSelected);
			if (aux != -1) {res += Math.abs(colSelected - aux)-1;} //-1 corresponds to the piece in the aux place (which was already there)
			aux = canPlayUpRight(mySeed, rowSelected, colSelected);
			if (aux != -1) {res += Math.abs(colSelected - aux)-1;} //always use the colSelected since aux gives the column where the old piece is located (except for the UP and DOWN cases), abs to make it easier
			aux = canPlayUp(mySeed, rowSelected, colSelected);
			if (aux != -1) {res += Math.abs(rowSelected - aux)-1;}
			aux = canPlayUpLeft(mySeed, rowSelected, colSelected);
			if (aux != -1) {res += Math.abs(colSelected - aux)-1;}
			aux = canPlayLeft(mySeed, rowSelected, colSelected);
			if (aux != -1) {res += Math.abs(colSelected - aux)-1;}
			aux = canPlayDownLeft(mySeed, rowSelected, colSelected);
			if (aux != -1) {res += Math.abs(colSelected - aux)-1;}
			aux = canPlayDown(mySeed, rowSelected, colSelected);
			if (aux != -1) {res += Math.abs(rowSelected - aux)-1;}
			aux = canPlayDownRight(mySeed, rowSelected, colSelected);
			if (aux != -1) {res += Math.abs(colSelected - aux)-1;};
			return res;
	}

	//check the whole grid for the number of pieces the player will gain
	public int[][] boardGainer(Seed mySeed) {
		int[][] res = new int[Constants.ROWS][Constants.COLS];
		for (int row = 0; row < Constants.ROWS; ++row) {
			for (int col = 0; col < Constants.COLS; ++col) {
				if (!validSquare(mySeed,row,col)) {res[row][col] = 0;
				} else {
					res[row][col] = piecesGained(mySeed, row, col);
				}
			}
		}
		return res;
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

	// Draw the number of pieces you gain
	public void paintNumbers(Graphics g,Seed mySeed){
		int[][] aux = boardGainer(mySeed);
		g.setColor(Color.RED);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Constants.CELL_SIZE));
		for (int row = 0; row < Constants.ROWS; ++row) {
			for (int col = 0; col < Constants.COLS; ++col) {
				if (aux[row][col] != 0) {
				g.drawString(""+aux[row][col], col * Constants.CELL_SIZE + Constants.CELL_PADDING, (row + 1)* Constants.CELL_SIZE - Constants.CELL_PADDING 	/*strings are written giving the bottom left corner*/);}
				}
			}
	}

}