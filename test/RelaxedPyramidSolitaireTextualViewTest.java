import org.junit.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static org.junit.Assert.assertEquals;

/**
 * Test for class PyramidSolitaireTextualView with RelaxedPyramidSolitaire model.
 */
public class RelaxedPyramidSolitaireTextualViewTest {

  PyramidSolitaireModel<Card> model = new RelaxedPyramidSolitaire();
  PyramidSolitaireTextualView a = new PyramidSolitaireTextualView(model);

  @Test
  public void testToString() {
    model.startGame(model.getDeck(), false, 3, 2);
    assertEquals("    A♣\n"
            + "  A♦  A♥\n"
            + "A♠  2♣  2♦\n"
            + "Draw: 2♥, 2♠", a.toString());
  }

  @Test
  public void testToStringNotStarted() {
    assertEquals("", a.toString());
  }

  @Test
  public void testToStringWin() {
    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }
    model.startGame(cards, false, 1, 2);
    model.remove(0, 0);
    assertEquals("You win!", a.toString());
  }

  @Test
  public void testToStringGameOver() {
    model.startGame(model.getDeck(), false, 9, 7);
    assertEquals(false, model.isGameOver());

    model.discardDraw(0);
    model.discardDraw(1);
    model.discardDraw(2);
    model.discardDraw(3);
    model.discardDraw(4);
    model.discardDraw(5);
    model.discardDraw(6);

    assertEquals("Game over. Score: 276", a.toString());
  }


  @Test
  public void testRender() throws IOException {
    StringBuffer out = new StringBuffer();
    PyramidSolitaireTextualView a = new PyramidSolitaireTextualView(model, out);

    model.startGame(model.getDeck(), false, 3, 2);
    a.render();
    assertEquals("    A♣\n"
            + "  A♦  A♥\n"
            + "A♠  2♣  2♦\n"
            + "Draw: 2♥, 2♠", out.toString());
  }

  @Test(expected = IOException.class)
  public void testRenderIOException() throws IOException {
    Appendable out = new StringBufferFail();
    PyramidSolitaireTextualView a = new PyramidSolitaireTextualView(model, out);

    model.startGame(model.getDeck(), false, 3, 2);
    a.render();
  }


}