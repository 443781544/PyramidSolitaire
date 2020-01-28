package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represent a Relaxed PyramidSolitaire Model, a variants of Pyramid Solitaire.
 * The remove rule is relaxed so that if a card is covered by only one other card,
 * and the player is trying to remove those two cards as a pair,
 * then we treat the pair as uncovered and permit it to be removed it if adds up to 13 as desired.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire
        implements PyramidSolitaireModel<Card> {

  /**
   * Constructor.
   */
  public RelaxedPyramidSolitaire() {
    super();
  }


  /**
   * Override remove method that allows player to remove two cards as a pair,
   * if a card is covered by only one other card.
   *
   * @param row1    row of first card position, numbered from 0 from the top of the pyramid
   * @param card1   card of first card position, numbered from 0 from left
   * @param row2    row of second card position
   * @param card2   card of second card position
   * @throws IllegalStateException if the game has not yet been started
   */
  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {


    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }


    Card c1 = getCardAt(row1, card1);
    Card c2 = getCardAt(row2, card2);

    if (c1 == null || c2 == null) {
      throw new IllegalArgumentException("invalid move, remove inputs null");
    }

    if (!checkExposed(row1, card1) || !checkExposed(row2, card2)) {
      if (!relaxedCovered(row1, card1, row2, card2)) {
        throw new IllegalArgumentException("invalid move, remove cards not exposed");
      }
    }


    if (c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("invalid move, cards not 13");
    }

    //remove two cards
    grid.get(row1).set(card1, null);
    grid.get(row2).set(card2, null);

  }


  // If a card is covered by only one other card,
  // and the player is trying to remove those two cards as a pair,
  // then we treat the pair as uncovered and permit it to be removed it if adds up to 13 as desired.
  private boolean relaxedCovered(int i1, int i2, int i3, int i4) {
    if (i1 == i3 + 1) {
      return (i2 == i4 && getCardAt(i1, i4 + 1) == null && checkExposed(i1, i2))  ||
              (i2 == i4 + 1 && getCardAt(i1, i4) == null && checkExposed(i1, i2));
    }
    if (i3 == i1 + 1) {
      return relaxedCovered(i3, i4, i1, i2);
    }
    return false;
  }


}
