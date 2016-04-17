import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {  // save as Cell.java
   // package access
   Seed content; // content of this cell of type Seed.
                 // take a value of Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT
   int row, col; // row and column of this cell, not used in this program

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
		int x1 = col * Constants.CELL_SIZE + Constants.CELL_PADDING;
		int y1 = row * Constants.CELL_SIZE + Constants.CELL_PADDING;
		if (content == Seed.CROSS) {
			g2d.setColor(Color.RED);
			int x2 = (col + 1) * Constants.CELL_SIZE - Constants.CELL_PADDING;
			int y2 = (row + 1) * Constants.CELL_SIZE - Constants.CELL_PADDING;
			g2d.drawLine(x1, y1, x2, y2);
			g2d.drawLine(x2, y1, x1, y2);
		} else if (content == Seed.NOUGHT) {
			g2d.setColor(Color.BLUE);
			g2d.drawOval(x1, y1, Constants.SYMBOL_SIZE, Constants.SYMBOL_SIZE);
		}
	}
}