package ui;

/**
 * a user interface for the game.
 * 
 * @author gerrit
 *
 */
public interface Ui {

  /**
   * launches the game.
   */
  public void launchGame();

  /**
   * if the game is over displays the winner of the game with a message.
   */
  public void displayWinner();

}
