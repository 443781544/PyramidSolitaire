package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represent a Tripeaks PyramidSolitaire Model, a variants of Pyramid Solitaire. It uses the same
 * rules as the original game, but uses a larger board and a larger deck of cards. To play this game
 * we need a double deck, containing 104 cards (two of each unique card). The three pyramids should
 * overlap for half their height (rounding up).
 */
public class TriPeaksPyramidSolitaire extends BasicPyramidSolitaire
        implements PyramidSolitaireModel<Card> {

  /**
   * Constructor.
   */
  public TriPeaksPyramidSolitaire() {
    super();
  }


  /**
   * Override getDeck method.
   *
   * @return a list of Card that contains a double deck, containing 104 (two of each unique card).
   */
  @Override
  public List<Card> getDeck() {

    List<Card> result = new ArrayList<Card>();
    result.addAll(super.getDeck());
    result.addAll(super.getDeck());
    return result;
  }

  /**
   * Override startGame method that set up the grid correctly.
   *
   * @param deck          the deck to be dealt
   * @param shouldShuffle if {@code false}, use the order as given by {@code deck}, otherwise
   *                      shuffle the cards
   * @param numRows       number of rows in the pyramid
   * @param numDraw       number of draw cards available at a time
   */
  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw) {
    grid = new ArrayList<ArrayList<Card>>();
    stock = new LinkedList<Card>();
    drawPile = new ArrayList<Card>();

    if (deck == null) {
      throw new IllegalArgumentException("deck is null");
    }

    List<Card> deckCopy = new ArrayList<Card>(deck);
    boolean deckValid = true;
    for (Card c : getDeck()) {
      if (deckCopy.contains(c)) {
        deckCopy.remove(c);
      } else {
        deckValid = false;
      }
    }
    if (!deckValid || deck.size() != 104) {
      throw new IllegalArgumentException("deck is invalid");
    }

    if (numRows < 1 || numDraw < 0 || numRows > 8) {
      throw new IllegalArgumentException("invalid input");
    }

    deckCopy = new ArrayList<Card>(deck);

    if (shouldShuffle) {
      Collections.shuffle(deckCopy);
    }


    int index = 0;
    for (int row = 0; row < numRows - Math.round((float) numRows / 2) - 1; row++) {
      ArrayList<Card> c = new ArrayList<Card>();

      int emptySpaceNum = numRows - Math.round((float) numRows / 2) - (1 + row);

      for (int col = 0; col < ((row + 1) * 3) + (emptySpaceNum * 2); col++) {

        if (col == (row + 1) || col == ((row + 1) * 2) + emptySpaceNum) {
          for (int i = 0; i < emptySpaceNum; i++) {
            c.add(null);
            if (i != emptySpaceNum - 1) {
              col++;
            }
          }
        } else {
          c.add(deckCopy.get(index));
          index++;
        }
      }
      grid.add(c);
    }

    if (numRows == 1) {
      ArrayList<Card> c = new ArrayList<Card>();
      c.add(deckCopy.get(index));
      index++;
      grid.add(c);
    } else {
      for (int row = 0; row < Math.round((float) numRows / 2) + 1; row++) {
        ArrayList<Card> c = new ArrayList<Card>();
        for (int col = 0; col < (numRows - Math.round((float) numRows / 2)) * 3 + row; col++) {
          c.add(deckCopy.get(index));
          index++;
        }
        grid.add(c);
      }
    }


    for (int i = index; i < deckCopy.size(); i++) {
      stock.add(deckCopy.get(i));
    }

    gs = GameState.Playing;


    if (numDraw > stock.size()) {
      throw new IllegalArgumentException("not enough cards in the stock");
    }

    for (int i = 0; i < numDraw; i++) {
      drawPile.add(stock.poll());
    }
  }


}
