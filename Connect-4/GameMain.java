import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * The main class for the Tic-Tac-Toe
 * It acts as the overall controller of the game.
 */
public class GameMain extends JFrame {
	private Board board;            // the game board
	private GameState currentState; // the current state of the game (of enum GameState)
	private Seed currentPlayer;     // the current player (of enum Seed)

	private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
	private JLabel statusBar;  // Status Bar

	/** Constructor to setup the game */
	public GameMain() {
		canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
		canvas.setPreferredSize(new Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT));

		// The canvas (JPanel) fires a MouseEvent upon mouse-click
		canvas.addMouseListener(new MouseAdapter() {//ADAPTER
			@Override
			public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
  				int mouseX = e.getX();
				// Get the row and column clicked
				int colSelected = mouseX / Constants.CELL_SIZE;

				if (currentState == GameState.PLAYING) {
					if (colSelected >= 0 && colSelected < Constants.COLS) {
					// Look for an empty cell starting from the bottom row
						for (int row = Constants.ROWS -1; row >= 0; row--) {
							if (board.cells[row][colSelected].content == Seed.EMPTY) {
								board.cells[row][colSelected].content = currentPlayer; // Make a move
								updateGame(currentPlayer, row, colSelected); // update state
								canvas.repaint(colSelected * Constants.CELL_SIZE, row * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE); //paint only affected area
								// Switch player
								currentPlayer = (currentPlayer == Seed.BLUE) ? Seed.YELLOW : Seed.BLUE;
         						break;
								}
							}
						}
					} else {       // game over
				initGame(); // restart the game
				// Refresh the drawing canvas
				repaint();  // Call-back paintComponent().
				}

			}
		});

		// Setup the status bar (JLabel) to display status message
		statusBar = new JLabel("  ");
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();  // pack all the components in this JFrame
		setTitle("Tic Tac Toe");
		setVisible(true);  // show this JFrame

		board = new Board();  // allocate game-board
		initGame(); // initialize the game board contents and game variables
	}

	/** Initialize the game-board contents and the current states */
	public void initGame() {
		board.init();  // clear the board contents
		currentPlayer = Seed.BLUE;       // CROSS plays first
		currentState = GameState.PLAYING; // ready to play
	}

	/** Update the currentState after the player with "theSeed" has placed on
		(rowSelected, colSelected). */
	public void updateGame(Seed theSeed, int rowSelected, int colSelected) {
		if (board.hasWon(theSeed, rowSelected, colSelected)) {  // check for win
			currentState = (theSeed == Seed.BLUE) ? GameState.BLUE_WON : GameState.YELLOW_WON;
		} else if (board.isDraw()) {  // check for draw
			currentState = GameState.DRAW;
		}
		// Otherwise, no change to current state (still GameState.PLAYING).
	}

	class DrawCanvas extends JPanel {
		@Override
		public void paintComponent(Graphics g) {  // invoke via repaint()
			super.paintComponent(g);    // fill background
			setBackground(Color.WHITE); // set its background color

			// Draw the grid-lines
			board.paint(g);

			// Print status-bar message
			if (currentState == GameState.PLAYING) {
				statusBar.setForeground(Color.BLACK);
				if (currentPlayer == Seed.BLUE) {
					statusBar.setText("BLUE's Turn");
				} else {
					statusBar.setText("YELLOW's Turn");
				}
			} else if (currentState == GameState.DRAW) {
				statusBar.setForeground(Color.RED);
				statusBar.setText("It's a Draw! Click to play again.");
			} else if (currentState == GameState.BLUE_WON) {
				statusBar.setForeground(Color.BLUE);
				statusBar.setText("'BLUE' Won! Click to play again.");
			} else if (currentState == GameState.YELLOW_WON) {
				statusBar.setForeground(Color.YELLOW);
				statusBar.setText("'YELLOW' Won! Click to play again.");
			}
		}
	}

	/** The entry main() method */
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