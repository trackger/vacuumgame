package game;

import game.Constants;
import sprites.CleanHallway;
import sprites.Dirt;
import sprites.Dumpster;
import sprites.DustBall;
import sprites.Sprite;
import sprites.Vacuum;
import sprites.Wall;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A class that represents the basic functionality of the vacuum game. This
 * class is responsible for performing the following operations: 1. At creation,
 * it initializes the instance variables used to store the current state of the
 * game. 2. When a move is specified, it checks if it is a legal move and makes
 * the move if it is legal. 3. It reports information about the current state of
 * the game when asked.
 */
public class VacuumGame {
  // a counter to tell when the game is over
  private int numDirt;
  // a random number generator to move the DustBalls
  private Random random;
  // the grid
  private Grid<Sprite> grid;
  // the first player
  private Vacuum vacuum1;
  /// the second player
  private Vacuum vacuum2;
  // the dirt (both static dirt and mobile dust balls)
  private List<Dirt> dirts;
  // the dumpsters
  private List<Dumpster> dumpsters;

  /**
   * Creates a new VacuumGame that corresponds to the given input text file.
   * Assumes that the input file has one or more lines of equal lengths, and
   * that each character in it (other than newline) is a character that
   * represents one of the sprites in this game.
   * 
   * @param layoutFileName
   *          path to the input grid file
   */
  public VacuumGame(String layoutFileName) throws IOException {
    dirts = new ArrayList<Dirt>();
    dumpsters = new ArrayList<Dumpster>();
    random = new Random();
    numDirt = 0;
    // open the file, read the contents, and determine
    // dimensions of the grid
    int[] dimensions = getDimensions(layoutFileName);
    grid = new ArrayGrid<Sprite>(dimensions[0], dimensions[1]);

    // open the file again, read the contents, and store them in grid
    Scanner sc = new Scanner(new File(layoutFileName));
    String newLine = sc.nextLine();
    for (int i = 0; i < dimensions[0]; i++) {
      for (int p = 0; p < dimensions[1]; p++) {
        // initialise sprites in grid right away
        char charAt = newLine.charAt(p);
        initSprite(charAt, i, p);
      }
      // if we are not at the end get the next line
      if (i != dimensions[0] - 1) {
        newLine = sc.nextLine();
      }
    }

    sc.close();
  }

  /**
   * takes an input character and matches it to a corresponding sprite and adds
   * the correct location for it.
   * 
   * @param character
   *          the character that represents a sprite
   * @param row
   *          the row where the sprite will be located
   * @param column
   *          the column where the sprite will be located
   */
  private void initSprite(char character, int row, int column) {
    Sprite obj = null;
    // initialises the Sprite based on the character
    if (character == Constants.WALL) {
      Wall wall = new Wall(character, row, column);
      obj = wall;
    }
    if (character == Constants.CLEAN) {
      CleanHallway hall = new CleanHallway(character, row, column);
      obj = hall;
    }
    if (character == Constants.DIRT) {
      Dirt dirt = new Dirt(character, row, column, Constants.DIRT_SCORE);
      // add to the dirt counter
      numDirt = numDirt + 1;
      obj = dirt;
    }
    if (character == Constants.DUMPSTER) {
      Dumpster dump = new Dumpster(character, row, column);
      this.dumpsters.add(dump);
      obj = dump;
    }
    if (character == Constants.DUST_BALL) {
      DustBall dustb = new DustBall(character, row, column, Constants.DUST_BALL_SCORE);
      // add to dirt counter
      numDirt = numDirt + 1;
      dirts.add(dustb);
      obj = dustb;
    }
    if (character == Constants.P1 || character == Constants.P2) {
      Vacuum vac = new Vacuum(character, row, column, Constants.CAPACITY);
      // make sure we set cleanhallway under the first vacuum
      CleanHallway under = new CleanHallway(Constants.CLEAN, row, column);
      vac.setUnder(under);
      // see if it is player one or 2
      if (character == Constants.P1) {
        this.vacuum1 = vac;
      }
      if (character == Constants.P2) {
        this.vacuum2 = vac;
      }
      obj = vac;
    }
    grid.setCell(row, column, obj);
  }

  /**
   * Returns the dimensions of the grid in the file named layoutFileName.
   * 
   * @param layoutFileName
   *          path of the input grid file
   * @return an array [numRows, numCols], where numRows is the number of rows
   *         and numCols is the number of columns in the grid that corresponds
   *         to the given input grid file
   * @throws IOException
   *           if cannot open file layoutFileName
   */
  private int[] getDimensions(String layoutFileName) throws IOException {

    Scanner sc = new Scanner(new File(layoutFileName));

    // find the number of columns
    String nextLine = sc.nextLine();
    int numCols = nextLine.length();

    int numRows = 1;

    // find the number of rows
    while (sc.hasNext()) {
      numRows++;
      nextLine = sc.nextLine();
    }

    sc.close();
    return new int[] { numRows, numCols };
  }

  /**
   * returns the grid of the game.
   * 
   * @return grid
   */
  public Grid<Sprite> getGrid() {
    return grid;
  }

  /**
   * returns Player1 of the game.
   * 
   * @return vacuum1
   */
  public Vacuum getVacuumOne() {
    return vacuum1;
  }

  /**
   * returns player2 of the game.
   * 
   * @return vacuum2
   */
  public Vacuum getVacuumTwo() {
    return vacuum2;
  }

  /**
   * returns number of rows of the grid in the game.
   * 
   * @return number of rows
   */
  public int getNumRows() {
    return grid.getNumRows();
  }

  /**
   * returns number of columns of the grid in the game.
   * 
   * @return number of columns
   */
  public int getNumColumns() {
    return grid.getNumColumns();
  }

  /**
   * returns the sprite in given location.
   * 
   * @param row
   *          of the sprite
   * @param column
   *          of the sprite
   * @return Sprite
   */
  public Sprite getSprite(int row, int column) {
    return grid.getCell(row, column);
  }

  /**
   * moves the vacuums in the grid based on user input also moves the dustballs
   * whether or not the user input is valid.
   * 
   * @param nextMove
   *          a character that designates a move
   * @return a boolean true if the move was valid
   */
  public boolean move(char nextMove) {
    int[] result = moveInit(nextMove);
    if (result[0] == 3) {
      return false;
    }
    if (result[0] == 1) {
      if (result[3] == 1) {
        moveHelper(vacuum1, result[1], result[2]);
      }
      if (result[3] == 2) {
        moveHelper(vacuum2, result[1], result[2]);
      }
    }
    // loop through the list of dirts to make sure all dustballs moves
    for (int i = 0; i < dirts.size(); i++) {
      DustBall dust = (DustBall) dirts.get(i);
      if (dust.getSymbol() == 'o') {
        moveDust(dust, i);
      }
    }
    return result[1] == 1;
  }

  /**
   * helps move the vacuums.
   * 
   * @param vacuum
   *          that is moving
   * @param row
   *          of the location to move to
   * @param column
   *          column of the location to move to
   */
  private void moveHelper(Vacuum vacuum, int row, int column) {
    // the sprite at the destination
    Sprite destination = grid.getCell(row, column);
    if (destination.getSymbol() == 'U') {
      vacuum.empty();
    }
    if (destination.getSymbol() == '.') {
      boolean result = vacuum.clean(Constants.DIRT_SCORE);
      // if we cleaned the dirt we want to subtract from our dirt counter
      CleanHallway hall = new CleanHallway(Constants.CLEAN, row, column);
      if (result == true) {
        grid.setCell(row, column, hall);
        numDirt = numDirt - 1;
      }
    }
    if (destination.getSymbol() == 'o') {
      // if we are cleaning a dustball we want to subtract from our dirt counter
      boolean result = vacuum.clean(Constants.DUST_BALL_SCORE);
      CleanHallway hall = new CleanHallway(Constants.CLEAN, row, column);
      if (result == true) {
        grid.setCell(row, column, hall);
        for (int i = 0; i < dirts.size(); i++) {
          DustBall dust = (DustBall) dirts.get(i);
          if (dust.getRow() == destination.getRow() 
              && dust.getColumn() == destination.getColumn()) {
            // remove the dustball from our list
            dirts.remove(i);
          }
        }
        numDirt = numDirt - 1;
      }
    }
    // make changes to the grid and set the new items for the parameters of the
    // vacuum
    Sprite under = vacuum.getUnder();
    vacuum.setUnder(grid.getCell(row, column));
    grid.setCell(vacuum.getRow(), vacuum.getColumn(), under);
    vacuum.moveTo(row, column);
    grid.setCell(row, column, vacuum);

  }

  /**
   * initialises the move by matching the character and deciding the coordinates
   * for the move.
   * 
   * @param nextMove
   *          character that was inputted
   * @return an array of integers in the form [0,0,0,0] where array[0] = is the
   *         move valid? { 0 is false 1 is true 3 is invalid character} array[1] = the row that is
   *         being moved to array[2] = the column that is being moved to
   *         array[3] = 1 or 2 depending on whether it's player1 thats moving or
   *         player2
   */
  private int[] moveInit(char nextMove) {
    int[] result = new int[4];
    char[] valid = { 'w', 'a', 's', 'd', 'i', 'j', 'k', 'l' };
    for (int i = 0; i < valid.length; i++) {
      if (String.valueOf(valid[i]).equals(String.valueOf(nextMove))) {
        break;
      }
      if (i == valid.length - 1) {
        result[0] = 3;
      }
      if (result[0] == 3) {
        return result;
      }
    }
    if (nextMove == Constants.P1_DOWN) {
      result = checkMove(vacuum1.getRow() + Constants.DOWN, vacuum1.getColumn());
      result[3] = 1;
    }
    if (nextMove == Constants.P1_LEFT) {
      result = checkMove(vacuum1.getRow(), vacuum1.getColumn() + Constants.LEFT);
      result[3] = 1;
    }
    if (nextMove == Constants.P1_RIGHT) {
      result = checkMove(vacuum1.getRow(), vacuum1.getColumn() + Constants.RIGHT);
      result[3] = 1;
    }
    if (nextMove == Constants.P1_UP) {
      result = checkMove(vacuum1.getRow() + Constants.UP, vacuum1.getColumn());
      result[3] = 1;
    }
    if (nextMove == Constants.P2_DOWN) {
      result = checkMove(vacuum2.getRow() + Constants.DOWN, vacuum2.getColumn());
      result[3] = 2;
    }
    if (nextMove == Constants.P2_LEFT) {
      result = checkMove(vacuum2.getRow(), vacuum2.getColumn() + Constants.LEFT);
      result[3] = 2;
    }
    if (nextMove == Constants.P2_RIGHT) {
      result = checkMove(vacuum2.getRow(), vacuum2.getColumn() + Constants.RIGHT);
      result[3] = 2;
    }
    if (nextMove == Constants.P2_UP) {
      result = checkMove(vacuum2.getRow() + Constants.UP, vacuum2.getColumn());
      result[3] = 2;
    }
    return result;
  }

  /**
   * intialises a move for a dustball they have different limits for moving than
   * vacuums.
   * 
   * @param nextMove
   *          an integer that represents a direction must be one of [0,1,2,3]
   * @param dust
   *          the dustball that is moving
   * @return an array of integers in the form [0,0,0] where array[0] = is the
   *         move valid? {0 is false 1 is true} array[1] = row of the move
   *         array[2] = column of the move
   */
  private int[] moveDirtInit(int nextMove, DustBall dust) {
    int[] result = new int[3];
    if (nextMove == 0) {
      result = checkMoveDirt(dust.getRow() + Constants.DOWN, dust.getColumn());
    }
    if (nextMove == 1) {
      result = checkMoveDirt(dust.getRow(), dust.getColumn() + Constants.LEFT);
    }
    if (nextMove == 2) {
      result = checkMoveDirt(dust.getRow(), dust.getColumn() + Constants.RIGHT);
    }
    if (nextMove == 3) {
      result = checkMoveDirt(dust.getRow() + Constants.UP, dust.getColumn());
    }
    return result;
  }

  /**
   * helps with the clean function if the vacuum capacity is full.
   * 
   * @param vacuum
   *          the vacuum that is cleaning
   * @param dirt
   *          the dirt that its being cleaned
   */
  private void fullCleanHelper(Vacuum vacuum, Dirt dirt) {
    boolean result = vacuum.clean(Constants.DIRT_SCORE);
    if (result == true) {
      CleanHallway hall = new CleanHallway(Constants.CLEAN, vacuum.getRow(), vacuum.getColumn());
      vacuum.setUnder(hall);
      // if we are cleaning the dirt take away from the counter
      numDirt = numDirt - 1;
    } else {
      vacuum.setUnder(dirt);
    }
  }

  /**
   * performs the operations for moving the dust.
   * 
   * @param dust
   *          the dust that is being moved
   * @param index
   *          the index of the dust in the dirts List that is a private variable
   *          for VacuumGame
   */
  private void moveDust(DustBall dust, int index) {
    // use the helper to get a random direction
    int[] direction = moveDustHelper(dust);
    // set the new location
    Sprite newLocation = grid.getCell(direction[1], direction[2]);
    if (direction[0] == 1) {
      // create a new dirt instance
      Dirt dirt = new Dirt(Constants.DIRT, dust.getRow(), dust.getColumn(), Constants.DIRT_SCORE);
      // mark the old location
      Sprite oldLoc = grid.getCell(dust.getRow(), dust.getColumn());
      if (oldLoc.getSymbol() == Constants.P1) {
        // use the clean helper in case the vacuum is full
        fullCleanHelper(vacuum1, dirt);
      } else if (oldLoc.getSymbol() == Constants.P2) {
        fullCleanHelper(vacuum2, dirt);
      } else if (oldLoc.getSymbol() != Constants.P1 && oldLoc.getSymbol() != Constants.P2) {
        if (oldLoc.getSymbol() != Constants.DIRT) {
          grid.setCell(dust.getRow(), dust.getColumn(), dirt);
        }
      }
      DustBall ndust = new DustBall(Constants.DUST_BALL, direction[1],
          direction[2], Constants.DUST_BALL_SCORE);
      if (newLocation.getSymbol() == Constants.CLEAN) {
        // want to add to the counter from the location the DustBall is moving
        // to
        numDirt = numDirt + 1;
      }
      grid.setCell(direction[1], direction[2], ndust);
      dirts.set(index, ndust);
    } else {
      ;
    }
  }

  /**
   * helps with the dust move operations by providing a random location to move
   * to.
   * 
   * @param dust
   *          the DustBall that is moving
   * @return an array of int in the form [0,0,0] where array[0] = is the move
   *         valid? {0 is false 1 is true} array[1] = the row of the move
   *         array[2] = the column of the move
   */
  private int[] moveDustHelper(DustBall dust) {
    int[][] directions = { moveDirtInit(0, dust), moveDirtInit(1, dust),
        moveDirtInit(2, dust), moveDirtInit(3, dust) };
    List<int[]> valid = new ArrayList<int[]>();
    // store valid moves in the list
    for (int i = 0; i < directions.length; i++) {
      if (directions[i][0] == 1) {
        valid.add(directions[i]);
      }
    }
    if (valid.isEmpty() == true) {
      int[] nonvalid = { 0, 0, 0 };
      return nonvalid;
    } else {
      int length = valid.size();
      // choose one move randomly from the list
      int sumnum = random.nextInt(length) + 0;
      return valid.get(sumnum);
    }
  }

  /**
   * checks if the move is a valid move for DustBall only.
   * 
   * @param row
   *          of the move
   * @param column
   *          of the move
   * @return an int array of the form [0,0,0] where array[0] = is the move
   *         valid? {0 is false, 1 is true} array[1] = the row of the move
   *         array[2] = the column of the move
   */
  private int[] checkMoveDirt(int row, int column) {
    int[] result = new int[3];
    Sprite cell = grid.getCell(row, column);
    if (cell.getSymbol() == Constants.CLEAN || cell.getSymbol() == Constants.DIRT) {
      result[0] = 1;
      result[1] = row;
      result[2] = column;
    }
    return result;
  }

  /**
   * checks if a move is valid for Vacuum.
   * 
   * @param row
   *          the row of the move
   * @param column
   *          the column of the move
   * @return an int array of the form [0,0,0,0] where array[0] = is the move
   *         valid? {0 is false, 1 is true} array[1] = the row of the move
   *         array[2] = the column of the move array[3] = 1 or 2 depending if
   *         its player 1 or player 2
   */
  private int[] checkMove(int row, int column) {
    Sprite cell = grid.getCell(row, column);
    int[] result = new int[4];
    if (cell.getSymbol() == Constants.CLEAN || cell.getSymbol() == Constants.DIRT
        || cell.getSymbol() == Constants.DUMPSTER || cell.getSymbol() == Constants.DUST_BALL) {
      result[0] = 1;
      result[1] = row;
      result[2] = column;
      result[3] = 0;
    }
    return result;
  }

  /**
   * ends the game if its over.
   * 
   * @return true if the game is over
   */
  public boolean gameOver() {
    return numDirt == 0;
  }

  /**
   * determines the winner for the game.
   * 
   * @return 1 or 2 if the winner is player 1 or player 2
   */
  public int getWinner() {
    int winner = 0;
    int score1 = vacuum1.getScore();
    int score2 = vacuum2.getScore();
    if (score1 > score2) {
      winner = 1;
    }
    if (score1 < score2) {
      winner = 2;
    }
    return winner;
  }
}
