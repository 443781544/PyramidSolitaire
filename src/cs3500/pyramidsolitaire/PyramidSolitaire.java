package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;

/**
 * This class contains a main method that run the game of Pyramidsolitaire.
 */
public final class PyramidSolitaire {

  /**
   * This is the entry for the game.
   * @param args  command-line argument that specify the game type to start with.
   *              You may optionally pass two more arguments R D,
   *              both of which should be parsed as integers,
   *              where R specifies the number of rows, and D specifies the number of draw cards.
   *              If unspecified, you should use 7 rows and 3 draw cards as the defaults.
   */
  public static void main(String[] args) {
    PyramidSolitaireController control = new PyramidSolitaireTextualController(
            new InputStreamReader(System.in), System.out);
    PyramidSolitaireModel model = null;

    if (args.length > 0) {
      try {
        model = PyramidSolitaireCreator.create(
                PyramidSolitaireCreator.GameType.valueOf(args[0].toUpperCase()));
      } catch (IllegalArgumentException e) {
        System.err.println("Invalid Input");
        System.exit(1);
      }

      try {

        if (args.length > 2) {
          try {
            int numRow = Integer.parseInt(args[1]);
            int numDraw = Integer.parseInt(args[2]);
            control.playGame(model, model.getDeck(), true, numRow, numDraw);
          } catch (NumberFormatException e) {
            System.err.println("two arguments are not integers");
            System.exit(1);
          }
        } else {
          control.playGame(model, model.getDeck(), true, 7, 3);
        }

      } catch (IllegalStateException e) {
        System.err.println("game cannot be started");
        System.exit(1);
      }

    }


  }
}