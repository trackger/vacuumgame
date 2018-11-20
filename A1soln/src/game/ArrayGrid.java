package game;

/**
 * the grid for the game.
 * @author gerrit
 */
public class ArrayGrid<T> implements Grid<T> {
  private T[][] grid; //the grid
  private int rows; // the rows
  private int columns; // the columns

  /**
   * intialises the grid of the game.
   * @param numRows the number of rows of the grid
   * @param numColumns the number of columns of the grid
   */
  @SuppressWarnings("unchecked")
  public ArrayGrid(int numRows, int numColumns) {
    rows = numRows;
    columns = numColumns;
    // initialise the grid here
    grid = (T[][]) new Object[rows][columns];
  }
  
  @Override
  public void setCell(int row, int column, T item) {
    grid[row][column] = item;

  }
  
  @Override
  public T getCell(int row, int column) {
    return grid[row][column];
  }
  
  @Override
  public int getNumRows() {
    return rows;
  }
  
  @Override
  public int getNumColumns() {
    return columns;
  }
  
  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < grid.length; i++) {
      for (int p = 0; p < grid[i].length; p++) {
        string.append(grid[i][p]);
      }
      if (i != grid.length - 1) {
        string.append("\r\n");
      }
    }
    return string.toString();
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object other) {
    // set the original result to true
    boolean result = true;
    // compare the class types first
    if (other.getClass() == this.getClass()) {
      //if they're the same compare size of the grid
      if (((ArrayGrid<T>)other).rows == this.rows 
          && ((ArrayGrid<T>)other).columns == this.columns) {
        for (int i = 0; i < this.rows; i++) {
          for (int p = 0; p < this.columns; p++) {
            //need to compare the actual values of the string in order to have a comparison
            String var1 = this.getCell(i, p).toString();
            String var2 = ((ArrayGrid<T>) other).getCell(i, p).toString();
            boolean bool = var1.equals(var2);
            if (bool == false) {
              //if we get to this point we don't want to continue looping
              return false;
            }
          }
        }
      } else {
        result = false;
      }
    } else {
      result = false;
    }
    return result;
  }
}

