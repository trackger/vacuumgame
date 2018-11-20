package game;

/** a grid for the game. */
public interface Grid<T> {
  /**
   * sets a specific cell in the grid to an item T. in our game item must be of
   * class Sprite
   * 
   * @param row
   *          the row where the item is inserted
   * @param column
   *          the column where the item is inserted
   * @param item
   *          the item that is inserted
   */
  public void setCell(int row, int column, T item);

  /**
   * returns the cell at a location in the grid.
   * 
   * @param row
   *          of the location
   * @param column
   *          of the location
   * @return the cell
   */
  public T getCell(int row, int column);

  /**
   * returns the number of rows in the grid.
   * 
   * @return number of rows
   */
  public int getNumRows();

  /**
   * returns number of columns in the grid.
   * 
   * @return number of columns
   */
  public int getNumColumns();

  /**
   * checks if one grid is equal to another will be implemented by class.
   * ArrayGrid
   * 
   * @param other
   *          object that we are comparing to
   * @return boolean true if they are the same
   */
  public boolean equals(Object other);

  @Override
  public String toString();
}
