import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * It acts as the overall controller of the game.
 */
public class GameMain extends JFrame{
   private Board board;            // the game board
   private GameState currentState; // the current state of the game (of enum GameState)
   private Seed currentPlayer;     // the current player (of enum Seed)

	private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
	private JLabel statusBar, blacksBar, whitesBar;  // Status Bar
	int turn;
	boolean numberOfPieces = false; //show the number of pieces a player gains with a move
	int AI = 1; // 0 -> player, 1 -> Easy/random computer, 2 -> Impossible/best move every time

   /** Constructor to setup the game */
   public GameMain() {
		canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
		canvas.setPreferredSize(new Dimension(Constants.CANVAS_WIDTH-10, Constants.CANVAS_HEIGHT-10));

		// The canvas (JPanel) fires a MouseEvent upon mouse-click
		canvas.addMouseListener(new MouseAdapter() {//ADAPTER
			@Override
			public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
  				int mouseX = e.getX();
  				int mouseY = e.getY();
				// Get the row and column clicked
				int colSelected = mouseX / Constants.CELL_SIZE;
				int rowSelected = mouseY / Constants.CELL_SIZE;

			if (currentState == GameState.PLAYING) {
				if (turn <=4) {//in the beginning you can only play on the inside square (wherever you'd like)
					if ((rowSelected == Constants.ROWS/2-1 || rowSelected == Constants.ROWS/2)
						&& (colSelected == Constants.COLS/2-1 || colSelected == Constants.COLS/2)
						&& board.cells[rowSelected][colSelected].content == Seed.EMPTY) {//if player played inside the square and it is empty
						board.cells[rowSelected][colSelected].content = currentPlayer; //play in that cell
						canvas.repaint();
						currentPlayer = (currentPlayer == Seed.BLACK) ? Seed.WHITE : Seed.BLACK; //change player
						turn++; //the first 4 turns are inside the middle square
						if(AI > 0 & currentPlayer == Seed.WHITE) { //if playing against the computer, it will play randomly
							boolean aiplayed = false;
							while (!aiplayed) {
								Random rn = new Random(); rowSelected = rn.nextInt(2);
								rn = new Random(); colSelected = rn.nextInt(2);
								if (board.cells[Constants.ROWS/2-rowSelected][Constants.COLS/2-colSelected].content == Seed.EMPTY) {
									board.cells[Constants.ROWS/2-rowSelected][Constants.COLS/2-colSelected].content = Seed.WHITE;
									aiplayed = true;
									currentPlayer = Seed.BLACK; //computer is white, player is black
								}
							}
							turn++;
							}
						}
					}
				else { //otherwise play normally
					if (rowSelected >= 0 && rowSelected < Constants.ROWS && colSelected >= 0 && colSelected < Constants.COLS
						&& board.validSquare(currentPlayer, rowSelected, colSelected)) {
						board.playMove(currentPlayer, rowSelected, colSelected);
						updateGame(currentPlayer);
						if(AI > 0 & currentPlayer == Seed.WHITE) computerMove(); //against the computer, after the player plays it is the computer's turn
						}
					}
				} else {       // game over
				initGame(); // restart the game
				turn = 1;
				}
				repaint(); // Refresh the drawing canvas
			}
		});
		/////////////////////////
		// Setup the status bar (JLabel) to display status message
		JPanel tfPanel = new JPanel(new FlowLayout());

		blacksBar = new JLabel("  "); //displat the number of black pieces
		blacksBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		tfPanel.add(new JLabel("Blacks: "));
		tfPanel.add(blacksBar);

		statusBar = new JLabel("  "); //display the current situation of the game
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		statusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		statusBar.setHorizontalAlignment(JLabel.CENTER);
		statusBar.setPreferredSize(new Dimension(300, 20));
		tfPanel.add(statusBar);

		whitesBar = new JLabel("  "); //displat the number of white pieces
		whitesBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		tfPanel.add(new JLabel("Whites: "));
		tfPanel.add(whitesBar);
		///////////////////////////////////////////////
		///////// Menu to Insert numbers and Computer
		//////////////////////////////////////////////////
		JMenuBar menuBar;   // the menu-bar
		JMenu menu;         // each menu in the menu-bar
		JMenuItem menuItem; // an item in a menu

		menuBar = new JMenuBar();

		// First Menu
		menu = new JMenu("Options");
		menuBar.add(menu);  // the menu-bar adds this menu

		menuItem = new JMenuItem("New Game");
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initGame(); // restart the game
				turn = 1;
				repaint(); // Refresh the drawing canvas
			}
		});

		menuItem = new JMenuItem("Exit");
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
 			}
		});

		// Second Menu
		menu = new JMenu("Number of pieces");
		menuBar.add(menu);  // the menu-bar adds this menu

		ButtonGroup group = new ButtonGroup(); //radiobuttons, only one option is valid
		menuItem = new JRadioButtonMenuItem("On");
		group.add(menuItem); //insert this menu item in the radio buttons
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numberOfPieces = true; //if this is chosen then the number of pieces you gained is displayed
				repaint(); //since we change something we have to repaint the whole board
			}
		});

		menuItem = new JRadioButtonMenuItem("Off");
		menuItem.setSelected(true);
		group.add(menuItem);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numberOfPieces = false;
				repaint();
			}
		});

		// Third Menu
		menu = new JMenu("Opponent");
		menuBar.add(menu);  // the menu bar adds this menu

		group = new ButtonGroup(); //new set of radio buttons
		menuItem = new JRadioButtonMenuItem("Computer Easy");
		menuItem.setSelected(true);
		group.add(menuItem);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AI = 1;
			}
		});

		menuItem = new JRadioButtonMenuItem("Computer Impossible");
		group.add(menuItem);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AI = 2;
			}
		});

		menu.addSeparator(); //separate the submenus of computer from player

		menuItem = new JRadioButtonMenuItem("Player");
		group.add(menuItem);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AI = 0;
			}
		});

      setJMenuBar(menuBar);  // "this" JFrame sets its menu-bar, it appears on the JFrame

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(tfPanel, BorderLayout.PAGE_END); // same as SOUTH

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();  // pack all the components in this JFrame
		setTitle("Reversi");
		setVisible(true);  // show this JFrame
		setResizable( false );

		board = new Board();  // allocate game-board
		initGame(); // initialize the game board contents and game variables
		turn = 1;
	}
	////////////////////
   /** Initialize the game-board contents and the current states */
   public void initGame() {
      board.init();  // clear the board contents
      currentPlayer = Seed.BLACK;       // CROSS plays first
      currentState = GameState.PLAYING; // ready to play
   }


	///////////////////////////////////
   /** Update the currentState after the player with "theSeed" has moved */
	public void updateGame(Seed theSeed) { //if the other player has a valid move switch, if not the other plays again; if both do not have valid moves, the game ends
		Seed otherSeed = (theSeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
		if (board.playerCanPlay(otherSeed)) { //other player can play
			currentPlayer = otherSeed;
			} //if (board.playerCanPlay(otherSeed)) does not have any valid move, then do not change the player and this plays again
			else if (!board.playerCanPlay(theSeed)) {	// check for win, since it is game over
			currentState = board.hasWon();
			}
		// Otherwise, no change to current state (still GameState.PLAYING).
	}

	////////////////////////////////////
	public void computerMove() {
		int[][] res = board.boardGainer(Seed.WHITE);
		int max=0; //maximum number of pieces the computer will gain (used in the impossible case
		ArrayList<Integer> possibleRows = new ArrayList<Integer>( );
		ArrayList<Integer> possibleCols = new ArrayList<Integer>( );
		//if AI = 1 then computer will play randomly from any playable cell, if AI = 2 then it will play randomly from any maximum value cell
		for (int row = 0; row < Constants.ROWS; ++row) {
			for (int col = 0; col < Constants.COLS; ++col) {
				if (AI == 1) {if(board.validSquare(Seed.WHITE, row, col)) {//insert available move for computer
					possibleRows.add(row);possibleCols.add(col);
				}}
				if (AI == 2) {
					if (max < res[row][col]) {//if maximum is lower, then we have to reset the maximum and the arrays; and after that add the new element of each array
						max = res[row][col]; possibleRows.clear(); possibleCols.clear(); possibleRows.add(row);possibleCols.add(col);
					} else if (max == res[row][col]) {//if it is equal, then it is another possible move
						possibleRows.add(row);possibleCols.add(col);
					}
				}
			}
		}
		Random rn = new Random(); int pos = rn.nextInt(possibleRows.size()); board.playMove(Seed.WHITE, possibleRows.get(pos), possibleCols.get(pos)); //get a random cell from the available ones
		updateGame(currentPlayer);
	}

	//////////////////////////////////////
	/////////////////////////////////////
	class DrawCanvas extends JPanel {
		@Override
		public void paintComponent(Graphics g) {  // invoke via repaint()
			super.paintComponent(g);    // fill background

			// Draw the grid-lines
			board.paint(g); //paint the board (including the cells)

			if (turn <=4){//message in the first 4 turns
				statusBar.setForeground(Color.BLACK);
				if (currentPlayer == Seed.BLACK) {
					statusBar.setText("BLACK: play inside the square");
				} else {
					statusBar.setText("WHITE: play inside the square");
				}
			}

			// Print status-bar message
			blacksBar.setText(board.nrBlacks() + "");
			whitesBar.setText(board.nrWhites() + "");

			if (currentState == GameState.PLAYING) {
				statusBar.setForeground(Color.BLACK);
				if (turn > 4) {if (currentPlayer == Seed.BLACK) {
					statusBar.setText("BLACK's Turn");
				} else {
					statusBar.setText("WHITE's Turn");
				}}
			} else if (currentState == GameState.DRAW) {
				statusBar.setForeground(Color.RED);
				statusBar.setText("It's a Draw! Click to play again.");
			} else if (currentState == GameState.BLACK_WON) {
				statusBar.setForeground(Color.BLUE);
				statusBar.setText("'BLACK' Won! Click to play again.");
			} else if (currentState == GameState.WHITE_WON) {
				statusBar.setForeground(Color.BLUE);
				statusBar.setText("'WHITE' Won! Click to play again.");
			}
		}
	}

	//////////////////////////////////////
	public static void main(String[] args) {
		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameMain(); // Let the constructor do the job
			}
		});
	}
}