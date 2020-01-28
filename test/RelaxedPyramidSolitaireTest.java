import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Tests for class RelaxedPyramidSolitaire.
 */
public class RelaxedPyramidSolitaireTest {

  PyramidSolitaireModel<Card> model = new RelaxedPyramidSolitaire();

  @Test
  public void testGetDeck() {
    List<Card> cards = new ArrayList<Card>();
    for (int i = 1; i <= 13; i++) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    assertEquals(cards, model.getDeck());
  }


  @Test
  public void testStartGameNoShuffle() {
    assertEquals(-1, model.getNumDraw());
    assertEquals(-1, model.getNumRows());

    model.startGame(model.getDeck(), false, 7, 3);

    assertEquals(3, model.getNumDraw());
    assertEquals(7, model.getNumRows());
  }

  @Test
  public void testStartGameShuffle() {
    model.startGame(model.getDeck(), false, 7, 3);

    PyramidSolitaireModel<Card> model2 = new BasicPyramidSolitaire();
    model2.startGame(model2.getDeck(), true, 7, 3);

    assertNotSame(model.getCardAt(0, 0), model2.getCardAt(0, 0));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testStartExceptionNullDeck() {
    model.startGame(null, false, 7, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartExceptionInvalidDeck() {
    List<Card> cards = new ArrayList<Card>();
    model.startGame(cards, false, 7, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartExceptionInvalidNumRows() {
    model.startGame(model.getDeck(), false, -1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartExceptionInvalidNumRows1() {
    model.startGame(model.getDeck(), false, 11, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartExceptionInvalidNumDraw() {
    model.startGame(model.getDeck(), false, 6, -3);
  }


  @Test
  public void TestRemoveTwo() {
    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(112, model.getScore());

    model.remove(6, 0, 6, 3);
    assertEquals(99, model.getScore());
  }

  @Test
  public void TestRemoveTwoRelaxedLeftCovered() {
    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(112, model.getScore());

    model.remove(6, 0, 6, 6);
    model.remove(5, 5, 6, 5);
    assertEquals(86, model.getScore());

    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(112, model.getScore());

    model.remove(6, 0, 6, 6);
    model.remove(6, 5, 5, 5);
    assertEquals(86, model.getScore());

  }

  @Test
  public void TestRemoveTwoRelaxedRightCovered() {
    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(112, model.getScore());

    model.remove(6, 0, 6, 5);
    model.remove(5, 5, 6, 6);
    assertEquals(86, model.getScore());

    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(112, model.getScore());

    model.remove(6, 0, 6, 5);
    model.remove(6, 6, 5, 5);
    assertEquals(86, model.getScore());

  }



  @Test(expected = IllegalStateException.class)
  public void testRemoveTwoException() {
    model.remove(6, 0, 6, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwoExceptionNot13() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.remove(6, 0, 6, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwoExceptionNotExpose() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.remove(5, 5, 6, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwoExceptionInvalidCoordinate() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.remove(8, 8, 6, 4);
  }


  @Test
  public void removeOne() {
    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    model.startGame(cards, false, 2, 3);

    assertEquals(39, model.getScore());
    model.remove(1, 0);
    assertEquals(26, model.getScore());

  }


  @Test(expected = IllegalStateException.class)
  public void testRemoveOneExceptionNotStarted() {
    model.remove(1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveOneExceptionNotK() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.remove(6, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveOneExceptionNotExpose() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.remove(3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveOneExceptionInvalidCoordinate() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.remove(10, 0);
  }


  @Test
  public void removeUsingDraw() {
    model.startGame(model.getDeck(), false, 6, 5);

    assertEquals(66, model.getScore());

    //removed a 6 in the grid and a 7 in the draw pile
    model.removeUsingDraw(3, 5, 5);
    assertEquals(60, model.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveUsingDrawExceptionNotStarted() {
    model.removeUsingDraw(3, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawExceptionNot13() {
    model.startGame(model.getDeck(), false, 6, 5);
    model.removeUsingDraw(3, 5, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawExceptionNotExpose() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.removeUsingDraw(3, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawExceptionInvalidCoordinate() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.removeUsingDraw(3, 10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawExceptionInvalidDrawIndex() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.removeUsingDraw(-1, 6, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawExceptionInvalidDrawIndex1() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.removeUsingDraw(10, 6, 0);
  }


  @Test
  public void discardDraw() {
    model.startGame(model.getDeck(), false, 7, 2);
    ArrayList<Card> c = new ArrayList<Card>();
    c.add(new Card(8, '♣'));
    c.add(new Card(8, '♦'));
    assertEquals(c, model.getDrawCards());

    model.discardDraw(0);
    c.set(0, new Card(8, '♥'));
    assertEquals(c, model.getDrawCards());
  }

  @Test(expected = IllegalStateException.class)
  public void testDiscardDrawExceptionNotStarted() {
    model.discardDraw(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDrawExceptionInvalidIndex() {
    model.startGame(model.getDeck(), false, 7, 2);
    model.discardDraw(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDrawExceptionNoCardPresentThere() {
    model.startGame(model.getDeck(), false, 7, 0);
    model.discardDraw(0);
  }

  @Test
  public void getNumRows() {
    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(7, model.getNumRows());
  }

  @Test
  public void getNumRowsGameNotStarted() {
    assertEquals(-1, model.getNumRows());
  }

  @Test
  public void getNumDraw() {
    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(3, model.getNumDraw());
  }

  @Test
  public void getNumDrawsGameNotStarted() {
    assertEquals(-1, model.getNumDraw());
  }

  @Test
  public void getRowWidth() {
    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(1, model.getRowWidth(0));
    assertEquals(7, model.getRowWidth(6));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetRowWidthExceptionNotStarted() {
    model.getRowWidth(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRowWidthExceptionInvalidIndex() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.getRowWidth(10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRowWidthExceptionInvalidIndex1() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.getRowWidth(-5);
  }

  @Test
  public void isGameOver() {
    model.startGame(model.getDeck(), false, 9, 7);
    assertEquals(false, model.isGameOver());

    model.discardDraw(0);
    model.discardDraw(1);
    model.discardDraw(2);
    model.discardDraw(3);
    model.discardDraw(4);
    model.discardDraw(5);
    model.discardDraw(6);
    assertEquals(true, model.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testIsGameOverExceptionNotStarted() {
    model.isGameOver();
  }

  @Test
  public void getScore() {
    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(112, model.getScore());

    model.remove(6, 0, 6, 3);
    assertEquals(99, model.getScore());

    model.startGame(model.getDeck(), false, 2, 3);
    assertEquals(3, model.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetScoreExceptionNotStarted() {
    model.getScore();
  }


  @Test
  public void getCardAt() {
    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(new Card(1, '♣'), model.getCardAt(0, 0));
    assertEquals(new Card(1, '♦'), model.getCardAt(1, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardAtExceptionNotStarted() {
    model.getCardAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtExceptionInvalidCoordinate() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.getCardAt(8, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtExceptionInvalidCoordinate1() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.getCardAt(0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtExceptionInvalidCoordinate2() {
    model.startGame(model.getDeck(), false, 7, 3);
    model.getCardAt(-1, -1);
  }


  @Test
  public void getDrawCards() {
    model.startGame(model.getDeck(), false, 7, 2);
    ArrayList<Card> c = new ArrayList<Card>();
    c.add(new Card(8, '♣'));
    c.add(new Card(8, '♦'));
    assertEquals(c, model.getDrawCards());

  }

  @Test(expected = IllegalStateException.class)
  public void testGetDrawCardsExceptionNotStarted() {
    model.getDrawCards();
  }
}