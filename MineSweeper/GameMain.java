import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameMain extends JFrame{
	private Board board;            // the game board
	Container cp;
	private JLabel[][] labels;

	JMenuBar menuBar;   // the menu-bar
	JMenu menu;         // each menu in the menu-bar
    JMenuItem menuItem; // an item in a menu

	int rows, cols, nrMines;
	public static final int CELL_SIZE = 20;
	int CANVAS_WIDTH;
	int CANVAS_HEIGHT;



   /** Constructor to setup the game */
   public GameMain() {
		cp = getContentPane();

		menuBar = new JMenuBar();

		menu = new JMenu("Options");
		menuBar.add(menu);  // the menu-bar adds this menu

		menuItem = new JMenuItem("New Game");
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				askInput(); //once you clicked the new game you can't go back to the other game (can't be bothered to change this)
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

		setJMenuBar(menuBar);

  // mouse-clicked handler
			cp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
  				int mouseX = e.getX();
  				int mouseY = e.getY();
				// Get the row and column clicked
				int colSelected = mouseX / CELL_SIZE;
				int rowSelected = mouseY / CELL_SIZE;

				Click prevClick = board.cells[rowSelected][colSelected].clicked; //current state of the cell
				if (SwingUtilities.isLeftMouseButton(e)) { //check if it was a left button click
					board.cells[rowSelected][colSelected].clicked = Click.LEFT;
					if (board.cells[rowSelected][colSelected].content == Seed.EMPTY) board.neighbours0(rowSelected, colSelected); //if cell has 0 mines
										//then it clicks on its neighbours
				}
				if (SwingUtilities.isRightMouseButton(e)) {
				   if (prevClick == Click.RIGHT) {board.cells[rowSelected][colSelected].clicked = Click.NO;} //right click switches between not clicking
				   else if (prevClick == Click.NO) {board.cells[rowSelected][colSelected].clicked = Click.RIGHT;}
				}

				drawboard();
				//check if player won or lost, and if so restart game
				if (board.lost()) {
					int answer = JOptionPane.showConfirmDialog(null, "You LOST! Play Again?",
						"You LOST!", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if (answer == JOptionPane.YES_OPTION) {
							askInput();
					} else {System.exit(0);
					}
				};
				if (board.win()) {
					int answer = JOptionPane.showConfirmDialog(null, "You WON! Play Again?",
						"You WON!", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if (answer == JOptionPane.YES_OPTION) {
							askInput();
					} else {System.exit(0);
					}
				}
			}
		});

		askInput(); //initialize the game

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();  // pack all the components in this JFrame
		setTitle("MineSweeper");
		setVisible(true);  // show this JFrame
   }

   /** Initialize the game-board contents and the current states */
   public void initGame(int nrMines) {
      board.init(nrMines);  // clear the board contents (implementation wise)
      }

	public void drawboard() {
		for (int row = 0; row < board.ROWS; ++row) {
			for (int col = 0; col < board.COLS; ++col) {
			  	if (board.cells[row][col].clicked == Click.NO) {
					labels[row][col].setText(""); //if it was not clicked yet then show nothing
				}
			  	if (board.cells[row][col].clicked == Click.RIGHT) {
					labels[row][col].setText("X"); //click right then green arrow
					labels[row][col].setForeground(Color.GREEN);
				}
			  	if (board.cells[row][col].clicked == Click.LEFT) {
					int val = board.cells[row][col].content.getValue();
			  		labels[row][col].setText(val + "");
			  		if (val == Seed.MINE.getValue()) {labels[row][col].setForeground(Color.RED);
			  		} else if (val == Seed.EMPTY.getValue()) {labels[row][col].setForeground(Color.BLACK);
			  		} else {labels[row][col].setForeground(Color.BLUE);
			  		}
				}
			}
		}
	}

	JTextField rowField = new JTextField(5);
	JTextField colField = new JTextField(5);
	JTextField minField = new JTextField(5);

	public void askInput() {
		JPanel myPanel = new JPanel(); //first we construct the window to start the game
		myPanel.setLayout(new BorderLayout(3, 3));

		JPanel mySubPanelTexts = new JPanel();
		mySubPanelTexts.add(new JLabel("# ROWS:"));
		mySubPanelTexts.add(rowField);
		mySubPanelTexts.add(Box.createHorizontalStrut(15)); // a spacer
		mySubPanelTexts.add(new JLabel("# COLUMNS:"));
		mySubPanelTexts.add(colField);
		mySubPanelTexts.add(Box.createHorizontalStrut(15)); // a spacer
		mySubPanelTexts.add(new JLabel("# MINES:"));
		mySubPanelTexts.add(minField);

		JPanel mySubPanelDifficulty = new JPanel(); //Difficulty inputs
		Button btnEasy = new Button("Easy");
      	mySubPanelDifficulty.add(btnEasy);
      	btnEasy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
            rowField.setText(10 + "");
            colField.setText(10 + "");
            minField.setText(10 + "");
			}
		});

		Button btnMedium = new Button("Medium");
      	mySubPanelDifficulty.add(btnMedium);
      	btnMedium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
            rowField.setText(15 + "");
            colField.setText(15 + "");
            minField.setText(50 + "");
			}
		});

		Button btnHard = new Button("Hard");
      	mySubPanelDifficulty.add(btnHard);
      	btnHard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
            rowField.setText(20 + "");
            colField.setText(20 + "");
            minField.setText(75 + "");
			}
		});

		myPanel.add(mySubPanelTexts, BorderLayout.CENTER);
		myPanel.add(mySubPanelDifficulty, BorderLayout.SOUTH);

		boolean validInput = false;
		int result = 0;

		while (!validInput) { //while it does not get a valid input it keeps giving dialog messages
		      result = JOptionPane.showOptionDialog(null, //Show Message Dialog
		      			myPanel, //put myPanel in the Pop up
		               "Start Game", //Title of the window
		               JOptionPane.OK_CANCEL_OPTION, //two buttons
						JOptionPane.PLAIN_MESSAGE, //no image
						null,
						new String[]{"Start", "Exit"}, //change the names: OK -> Start, Cancel -> Exit
 						"default");
		if (result == JOptionPane.OK_OPTION) { //if ok
			if (isInteger(rowField.getText()) && isInteger(colField.getText()) && isInteger(minField.getText())) {//check for valid numbers
			   rows = Integer.parseInt(rowField.getText()); //10<=rows<=30
			   cols = Integer.parseInt(colField.getText()); //10<=columns<=50
			   nrMines = Integer.parseInt(minField.getText()); //1<=mines<=rows*cols/2
			   if (10<=rows && rows <=30 && 10<=cols && cols<=50 && 1<=nrMines && nrMines <=rows*cols/2) {
				   validInput = true;} else {
					JOptionPane.showMessageDialog(null, "Admissible input: 10<=rows<=30, 10<=columns<=50, 1<=mines<=rows*cols/2",
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
		   		}
	   		}
		  	else {
		    JOptionPane.showMessageDialog(null, "Invalid Input, please enter integers!",
            "Invalid Input", JOptionPane.ERROR_MESSAGE);
		    }
      	} else {validInput = true;System.exit(0);} //if exit or close window then exit
	}

		//initialize the board
		cp = getContentPane();
		cp.removeAll(); //VERY IMPORTANT! Everytime we start a game with different labels we need to remove everything cp had and add everything again
						//else cp will just add to the previous ones and we get errors
						// clear the board contents (graphics wise)

		board = new Board(rows,cols);  // allocate game-board
		cp.setLayout(new GridLayout(board.ROWS, board.COLS));
		initGame(nrMines); // initialize the game board contents and game variables
		//set up visual part
		CANVAS_WIDTH = CELL_SIZE * board.COLS;
		CANVAS_HEIGHT = CELL_SIZE * board.ROWS;

		labels = new JLabel[board.ROWS][board.COLS]; //each cell is a label
		cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		setResizable( false ); //no resizing window

		pack(); //take previous changes into account

		for (int row = 0; row < board.ROWS; ++row) {
			for (int col = 0; col < board.COLS; ++col) {
				labels[row][col] = new JLabel();
				cp.add(labels[row][col]); //insert the cells in the panel
				labels[row][col].setHorizontalAlignment(JTextField.CENTER);
				labels[row][col].setFont(new Font("Monospaced", Font.BOLD, 14));
				labels[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				}
			}
		drawboard();
      }

	public static boolean isInteger(String s) {
   		try {
     	   Integer.parseInt(s);
    	} catch(NumberFormatException e) {
    	    return false;
    	}
    	// only got here if we didn't return false
    	return true;
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