package cs3500.pyramidsolitaire.view;

import java.io.IOException;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represent the view of a PyramidSolitaireModel.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;

  private final Appendable app;
  // ... any other fields you need


  /**
   * Constructor.
   *
   * @param model the PyramidSolitaireModel
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("input null");
    }
    this.model = model;
    app = System.out;
  }

  /**
   * Second Constructor.
   *
   * @param model the PyramidSolitaireModel.
   * @param app   Appendable.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable app) {
    if (model == null || app == null) {
      throw new IllegalArgumentException("input null");
    }
    this.model = model;
    this.app = app;
  }


  /**
   * If the game is not started, your toString should return the empty string "", and nothing else.
   * If the pyramid is emptied, your toString should simply return the string "You win!", and
   * nothing else. If there are no remaining moves available (and therefore the game is over), your
   * view should simply print  "Game over. Score: ##", where "##" is the current score of the
   * pyramid. Otherwise, render the following. An individual card should be rendered as its value
   * followed by its suit (which is one of the following four characters: '♣' '♦' '♥' '♠'). (Note:
   * to incorporate these characters into your program, copy and paste them from this Web page into
   * IntelliJ.) Each card should “visually” be three characters wide: for a one-digit card, you may
   * need to add an extra space for padding. If a position is blank, you should fill it with spaces.
   * There should be one blank space between each (3-character-wide) column of cards. If a row is
   * completely empty, you should still render a blank line. Beneath the pyramid is the word "Draw:
   * ", followed by a comma-space-separated list of the card(s) currently available in the stock.
   * The following shows a possible output, after some cards have been removed (the highlighting is
   * there only to illustrate exactly where any spaces are):
   *
   * @return
   */
  @Override
  public String toString() {
    //game not started
    if (model.getNumRows() == -1) {
      return "";
    }

    if (model.getScore() == 0) {
      return "You win!";
    }

    if (model.isGameOver()) {
      return "Game over. Score: " + model.getScore();
    }

    //... render the model here
    String result = "";
    for (int i = 0; i < model.getNumRows(); i++) {

      //add spaces to every row
      for (int k = 0; k < model.getNumRows() - i - 1; k++) {
        result += "  ";
      }

      for (int j = 0; j < model.getRowWidth(i); j++) {

        if (model.getCardAt(i, j) == null) {
          result += "   ";
        } else {
          String value = model.getCardAt(i, j).toString();

          if (value.startsWith("10") || j == model.getRowWidth(i) - 1) {
            //not adding a extra space for two digit or the last card every row
            result += model.getCardAt(i, j).toString();
          } else {
            result += model.getCardAt(i, j).toString() + " ";
          }

        }
        if (j != model.getRowWidth(i) - 1) {
          result += " ";
        }


      }
      result = trimSpace(result);
      result += "\n";
    }

    //draw pile
    result += "Draw: ";

    String[] array = new String[model.getDrawCards().size()];
    int index = 0;
    for (Object value : model.getDrawCards()) {
      if (value == null) {
        array[index] = "   ";
      } else {
        array[index] = value.toString();
      }
      index++;
    }
    result += String.join(", ", array);

    return result;
  }

  @Override
  public void render() throws IOException {
    this.app.append(this.toString());
  }

  private String trimSpace(String s) {
    s = "a" + s;
    s = s.trim();
    return s.substring(1);
  }
}