import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {  // save as Cell.java
   // package access
   Seed content; // content of this cell of type Seed.
                 // take a value of Seed.EMPTY, Seed.BLACK, or Seed.WHITE
   int row, col; // row and column of this cell, not used in this class

   /** Constructor to initialize this cell */
   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      clear();  // clear content
   }

   /** Clear the cell content to EMPTY */
   public void clear() {
      content = Seed.EMPTY;
   }

	public void paint(Graphics g,int row,int col) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(Constants.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND));  // Graphics2D only
		int x = col * Constants.CELL_SIZE + Constants.CELL_PADDING;
		int y = row * Constants.CELL_SIZE + Constants.CELL_PADDING;
		if (content == Seed.BLACK) {
			g2d.setColor(Color.BLACK);
			g2d.fillOval(x, y, Constants.SYMBOL_SIZE, Constants.SYMBOL_SIZE);
		} else if (content == Seed.WHITE) {
			g2d.setColor(Color.WHITE);
			g2d.fillOval(x, y, Constants.SYMBOL_SIZE, Constants.SYMBOL_SIZE);
		}
	}

}
