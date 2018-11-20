package sprites;

/**
 * abstract class that represents the units of the grid in this game.
 * 
 * @author gerrit
 *
 */
public abstract class Sprite {

  protected char symbol; // the character symbol for the sprite
  protected int row; // the row of the sprite
  protected int column; // the column of the sprite

  /**
   * initialises the sprite in a location.
   * 
   * @param symbol
   *          the character for the sprite
   * @param row
   *          the row for the sprite
   * @param column
   *          the column for the sprite
   */
  public Sprite(char symbol, int row, int column) {
    this.symbol = symbol;
    this.row = row;
    this.column = column;
  }

  /**
   * returns the symbol for the sprte.
   * 
   * @return the symbol
   */
  public char getSymbol() {
    return symbol;
  }

  /**
   * returns the row the sprite is in.
   * 
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * returns the column the sprite is in.
   * 
   * @return the column
   */
  public int getColumn() {
    return column;
  }

  @Override
  public String toString() {
    return String.valueOf(symbol);
  }

}
