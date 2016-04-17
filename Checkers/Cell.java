import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {  // save as Cell.java
   // package access
   Seed content; // content of this cell of type Seed.
   boolean crowned; //double piece
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
      crowned = false;
   }

	public void paint(Graphics g,int row,int col) {
		Graphics2D g2d = (Graphics2D)g;
		int x = col * Constants.CELL_SIZE + Constants.CELL_PADDING;
		int y = row * Constants.CELL_SIZE + Constants.CELL_PADDING;
		if (content == Seed.BLACK) {
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(Constants.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,	BasicStroke.JOIN_ROUND));
			g2d.fillOval(x, y, Constants.SYMBOL_SIZE, Constants.SYMBOL_SIZE);
			if (crowned) {
				g2d.setColor(Color.WHITE);
				g2d.setStroke(new BasicStroke(Constants.SYMBOL_STROKE_WIDTH/4, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
				g2d.drawOval(x+Constants.SYMBOL_SIZE/4, y+Constants.SYMBOL_SIZE/4, Constants.SYMBOL_SIZE/2, Constants.SYMBOL_SIZE/2);}
		} else if (content == Seed.WHITE) {
			g2d.setColor(Color.WHITE);
			g2d.fillOval(x, y, Constants.SYMBOL_SIZE, Constants.SYMBOL_SIZE);
			if (crowned) {
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(Constants.SYMBOL_STROKE_WIDTH/4, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
				g2d.drawOval(x+Constants.SYMBOL_SIZE/4, y+Constants.SYMBOL_SIZE/4, Constants.SYMBOL_SIZE/2, Constants.SYMBOL_SIZE/2);}
		}
	}

}
