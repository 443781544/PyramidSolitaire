package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;


/**
 * A textual controller for interacting with a game of Pyramid Solitaire.
 * Transmit game state to the Appendable object,
 * and obtain the player's input to mutate the game state.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private final Appendable out;
  private PyramidSolitaireModel<?> model;
  private boolean quit;
  private final Scanner scan;

  /**
   * The Constructor for class PyramidSolitaireTextualController.
   *
   * @param rd Readable: existing interfaces in Java that abstract input.
   * @param ap Appendable: existing interfaces in Java that abstract output.
   * @throws IllegalArgumentException if and only if either of its arguments are null.
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
          throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("PyramidSolitaireTextualController input null");
    }

    out = ap;
    model = null;
    quit = false;
    scan = new Scanner(rd);
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck,
                           boolean shuffle, int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("model is null");
    } else {
      this.model = model;
    }

    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException();
    }

    PyramidSolitaireView view = new PyramidSolitaireTextualView(model, out);

    try {

      while (!quit && !model.isGameOver()) {
        view.render();

        print("\n" + "Score: " + model.getScore() + "\n");

        if (scan.hasNext()) {
          scanForInput();
        } else {
          break;
        }
      }

      if (model.isGameOver()) {
        view.render();
      } else {
        print("Game quit!" + "\n" + "State of game when quit:" + "\n");
        view.render();
        print("\n" + "Score: " + model.getScore() + "\n");
      }


    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }


  private void print(String s) {
    try {
      out.append(s);
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  private void scanForInput() throws IOException {

    if (scan.hasNext()) {

      switch (scan.next().toLowerCase()) {
        case "q":
          quit = true;
          return;

        case "rm1":

          int i1 = scanNextInt();

          int i2 = scanNextInt();

          try {
            model.remove(i1 - 1, i2 - 1);
          } catch (IllegalArgumentException e) {
            out.append("Invalid move. Play again. " + e.getMessage() + "\n");
            //scanForInput();
          }

          break;
        case "rm2":

          int rm2i1 = scanNextInt();

          int rm2i2 = scanNextInt();

          int rm2i3 = scanNextInt();

          int rm2i4 = scanNextInt();

          try {
            model.remove(rm2i1 - 1, rm2i2 - 1, rm2i3 - 1, rm2i4 - 1);
          } catch (IllegalArgumentException e) {
            out.append("Invalid move. Play again. " + e.getMessage() + "\n");
          }

          break;
        case "rmwd":

          int rmwd1 = scanNextInt();

          int rmwd2 = scanNextInt();

          int rmwd3 = scanNextInt();

          try {
            model.removeUsingDraw(rmwd1 - 1, rmwd2 - 1, rmwd3 - 1);
          } catch (IllegalArgumentException e) {
            out.append("Invalid move. Play again. " + e.getMessage() + "\n");
          }

          break;
        case "dd":

          int dd1 = scanNextInt();

          try {
            model.discardDraw(dd1 - 1);
          } catch (IllegalArgumentException e) {
            out.append("Invalid move. Play again. " + e.getMessage() + "\n");
          }

          break;

        default:
          scanForInput();
          break;
      }
    }
  }


  private int scanNextInt() {

    while (scan.hasNext() && !scan.hasNextInt()) {
      if (scan.next().equals("q")) {
        quit = true;
        return 0;
      }
    }
    if (scan.hasNext()) {
      return scan.nextInt();
    } else {
      return 0;
    }
  }


}
