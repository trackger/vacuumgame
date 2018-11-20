package ui;

import game.VacuumGame;

import java.util.Scanner;



/**
 * a ui that prints the results of the changes in the game.
 * 
 * @author gerrit
 *
 */
public class TextUi implements Ui {
  private VacuumGame game; // the game

  /**
   * initialises the ui
   * 
   * @param game
   *          the game that it is based on.
   */
  public TextUi(VacuumGame game) {
    this.game = game;
  }

  /**
   * returns the game for this ui.
   * 
   * @return game
   */
  public VacuumGame getGame() {
    return game;
  }

  @Override
  public void launchGame() {
    System.out.println(game.getGrid().toString());
    @SuppressWarnings("resource")
    Scanner input = new Scanner(System.in);
    String inputKey;
    while (!game.gameOver()) {
      inputKey = input.next();
      game.move(inputKey.charAt(0));
      System.out.println(game.getGrid().toString());
    }
  }

  @Override
  public void displayWinner() {

    if (!game.gameOver()) {
      return;
    }

    int won = game.getWinner();
    String message;

    if (won == 0) {
      message = "It's a tie!";
    } else if (won == 1) {
      message = "Congratulations Player 1! You won the game with a score of " 
        + game.getVacuumOne().getScore() + ".";
    } else {
      message = "Congratulations Player 2! You won the game with a score of " 
        + game.getVacuumTwo().getScore() + ".";
    }
    System.out.println(message);
  }
}
