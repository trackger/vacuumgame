package sprites;

import game.Constants;

/**
 * class that represents the main players in this game. vacuums eat dirt and
 * dustballs
 * 
 * @author gerrit
 *
 */
public class Vacuum extends Sprite implements Moveable {

  private int score;
  private int capacity;
  private int fullness;
  private Sprite under;

  /**
   * initialises the vacuum.
   * 
   * @param symbol
   *          of the vacuum
   * @param row
   *          of the location
   * @param column
   *          column of the location
   * @param capacity
   *          for the vacuum
   */
  public Vacuum(char symbol, int row, int column, int capacity) {
    super(symbol, row, column);
    this.capacity = capacity;
  }

  /**
   * the operations the vacuum performs when it lands on dirt or DustBall.
   * 
   * @param score
   *          it gets for object it is cleaning
   * @return true if it was able to clean object
   */
  public boolean clean(int score) {

    if (this.fullness == this.capacity) {
      return false;
    } else {
      this.fullness = this.fullness + Constants.FULLNESS_INC;
      this.score = this.score + score;
      return true;

    }

  }

  /**
   * returns score vacuum has accumulated.
   * 
   * @return score
   */
  public int getScore() {
    return this.score;
  }

  /**
   * sets sprite under the vacuum.
   * 
   * @param under
   *          the sprite to be put under
   */
  public void setUnder(Sprite under) {
    this.under = under;
  }

  /**
   * returns the sprite that is under the vacuum.
   * 
   * @return sprite under vacuum
   */
  public Sprite getUnder() {
    return this.under;
  }

  @Override
  public void moveTo(int row, int column) {
    this.row = row;
    this.column = column;

  }

  /**
   * empties out the vacuum.
   */
  public void empty() {
    this.fullness = Constants.EMPTY;
  }

}
