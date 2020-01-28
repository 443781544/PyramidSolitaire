package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Represent a Basic PyramidSolitaire Model.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  protected enum GameState {
    Starting, Playing;
  }

  protected ArrayList<ArrayList<Card>> grid;

  protected LinkedList<Card> stock;

  protected ArrayList<Card> drawPile;

  protected GameState gs;

  /**
   * Constructor with zero arguments.
   */
  public BasicPyramidSolitaire() {
    grid = new ArrayList<ArrayList<Card>>();
    stock = new LinkedList<Card>();
    drawPile = new ArrayList<Card>();
    gs = GameState.Starting;
  }


  @Override
  public List<Card> getDeck() {
    List<Card> cards = new ArrayList<Card>();
    for (int i = 1; i <= 13; i++) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    return cards;
  }

  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw) {
    grid = new ArrayList<ArrayList<Card>>();
    stock = new LinkedList<Card>();
    drawPile = new ArrayList<Card>();

    if (deck == null) {
      throw new IllegalArgumentException("deck is null");
    }


    if (!(deck.containsAll(getDeck()) && getDeck().containsAll(deck) && deck.size() == 52)) {
      throw new IllegalArgumentException("deck is invalid");
    }

    if (numRows < 1 || numDraw < 0 || numRows > 9) {
      throw new IllegalArgumentException("invalid input");
    }

    ArrayList<Card> deckCopy = new ArrayList<Card>(deck);

    if (shouldShuffle) {
      Collections.shuffle(deckCopy);
    }


    int index = 0;
    for (int row = 0; row < numRows; row++) {
      ArrayList<Card> c = new ArrayList<Card>();
      for (int col = 0; col <= row; col++) {
        c.add(deckCopy.get(index));
        index++;
      }
      grid.add(c);
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


  protected boolean checkExposed(int row, int card) {
    return (row == getNumRows() - 1)
            || (getCardAt(row + 1, card) == null
            && getCardAt(row + 1, card + 1) == null);

  }


  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    //IllegalStateException
    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }

    //invalid: == null !exposed addup!=13
    Card c1 = getCardAt(row1, card1);
    Card c2 = getCardAt(row2, card2);

    if (c1 == null || c2 == null) {
      throw new IllegalArgumentException("invalid move, remove inputs null");
    }

    if (!checkExposed(row1, card1) || !checkExposed(row2, card2)) {
      throw new IllegalArgumentException("invalid move, remove cards not exposed");
    }

    if (c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("invalid move, cards not 13");
    }

    //remove two cards
    grid.get(row1).set(card1, null);
    grid.get(row2).set(card2, null);


  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    //IllegalStateException
    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }

    Card c1 = getCardAt(row, card);

    if (c1 == null) {
      throw new IllegalArgumentException("invalid move, remove1 null");
    }

    if (!checkExposed(row, card)) {
      throw new IllegalArgumentException("invalid move, remove1 not exposed");
    }

    if (c1.getValue() != 13) {
      throw new IllegalArgumentException("invalid move, remove1 not 13");
    }

    grid.get(row).set(card, null);

  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    //IllegalStateException
    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }

    Card c1 = getCardAt(row, card);

    if (c1 == null) {
      throw new IllegalArgumentException("invalid move, removeUsingDraw input null");
    }

    if (drawIndex < 0 || drawIndex >= drawPile.size() || drawPile.get(drawIndex) == null) {
      throw new IllegalArgumentException("invalid move, removeUsingDraw drawIndex invalid");
    }

    if (!checkExposed(row, card)) {
      throw new IllegalArgumentException("invalid move, card not exposed");
    }

    if (drawPile.get(drawIndex).getValue() + getCardAt(row, card).getValue() != 13) {
      throw new IllegalArgumentException("invalid move, removeUsingDraw add up != 13");
    }

    //remove
    grid.get(row).set(card, null);

    //replace draw card
    if (stock.size() > 0) {
      drawPile.set(drawIndex, stock.poll());
    } else {
      drawPile.set(drawIndex, null);
    }


  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    //IllegalStateException
    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }

    if (drawIndex < 0 || drawIndex >= drawPile.size() || drawPile.get(drawIndex) == null) {
      throw new IllegalArgumentException("invalid move, discardDraw drawIndex invalid");
    }

    //replace draw card
    if (stock.size() > 0) {
      drawPile.set(drawIndex, stock.poll());
    } else {
      drawPile.set(drawIndex, null);
    }

  }

  @Override
  public int getNumRows() {
    if (checkGameNotStarted()) {
      return -1;
    }

    int num = 0;
    boolean allNull = true;
    for (int i = grid.size() - 1; i >= 0; i--) {
      for (Card c : grid.get(i)) {
        if (c != null) {
          allNull = false;
        }
      }
      if (allNull) {
        num += 1;
      }
    }
    return grid.size() - num;
  }

  @Override
  public int getNumDraw() {
    if (checkGameNotStarted()) {
      return -1;
    }

    //if return -1
    return drawPile.size();
  }

  @Override
  public int getRowWidth(int row) {
    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }

    //exception
    if (row < 0 || row >= getNumRows()) {
      throw new IllegalArgumentException("input row is invalid");
    }

    return grid.get(row).size();
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    //IllegalStateException
    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }

    //no move left: no king, or no two cards addup 13, or no stock
    //no remaining move available

    boolean drawPileAllEmpty = true;
    for (Card c : drawPile) {
      if (c != null) {
        drawPileAllEmpty = false;
      }
    }
    return (drawPileAllEmpty && stock.size() == 0 && noMoveLeft()) || getScore() == 0;

  }

  private boolean noMoveLeft() {
    List<Card> exposedCard = new ArrayList<Card>();
    for (int row = 0; row < getNumRows(); row++) {
      for (int col = 0; col < getRowWidth(row); col++) {
        if (checkExposed(row, col) && getCardAt(row, col) != null) {
          exposedCard.add(getCardAt(row, col));
        }
      }
    }
    for (int i = 0; i < drawPile.size(); i++) {
      if (drawPile.get(i) != null) {
        exposedCard.add(drawPile.get(i));
      }
    }

    if (exposedCard.size() == 1) {
      if (exposedCard.get(0).getValue() == 13) {
        return false;
      }
    }
    for (int i = 0; i < exposedCard.size() - 1; i++) {
      for (int j = i + 1; j < exposedCard.size(); j++) {
        if (exposedCard.get(i).getValue() + exposedCard.get(j).getValue() == 13 ||
                exposedCard.get(i).getValue() == 13 || exposedCard.get(j).getValue() == 13) {
          return false;
        }
      }
    }
    return true;
  }


  @Override
  public int getScore() throws IllegalStateException {
    //IllegalStateException
    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }

    int score = 0;
    for (int i = 0; i < getNumRows(); i++) {
      for (int j = 0; j < grid.get(i).size(); j++) {
        if (grid.get(i).get(j) != null) {
          score += grid.get(i).get(j).getValue();
        }
      }
    }
    return score;
  }


  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    //IllegalStateException
    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }


    if (row < 0 || row >= getNumRows() || card >= getRowWidth(row) || card < 0) {

      throw new IllegalArgumentException("invalid coordinate");
    }

    return grid.get(row).get(card);

  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    //IllegalStateException
    if (checkGameNotStarted()) {
      throw new IllegalStateException();
    }

    return new ArrayList<Card>(drawPile);
  }

  protected boolean checkGameNotStarted() {
    return gs == GameState.Starting;
  }


}
