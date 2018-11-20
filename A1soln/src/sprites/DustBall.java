package sprites;

/**
 * class that represents dustballs in this game Vacuum try to eat DustBall.
 * DustBall also have the ability to create Dirt
 * 
 * @author gerrit
 *
 */
public class DustBall extends Dirt implements Moveable {

  public DustBall(char symbol, int row, int column, int value) {
    super(symbol, row, column, value);
  }

  @Override
  public void moveTo(int row, int column) {
    this.row = row;
    this.column = column;

  }

}
