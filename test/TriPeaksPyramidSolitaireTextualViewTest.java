import org.junit.Test;


import java.io.IOException;
import java.util.Collections;
import java.util.List;


import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.TriPeaksPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static org.junit.Assert.assertEquals;

/**
 * Test for class PyramidSolitaireTextualView with TriPeaksPyramidSolitaire model.
 */
public class TriPeaksPyramidSolitaireTextualViewTest {

  PyramidSolitaireModel<Card> model = new TriPeaksPyramidSolitaire();
  PyramidSolitaireTextualView a = new PyramidSolitaireTextualView(model);

  @Test
  public void testToString() {
    model.startGame(model.getDeck(), false, 3, 2);
    assertEquals("    A♣  A♦  A♥\n" +
            "  A♠  2♣  2♦  2♥\n" +
            "2♠  3♣  3♦  3♥  3♠\n" +
            "Draw: 4♣, 4♦", a.toString());
  }

  @Test
  public void testToString4Rows() {
    model.startGame(model.getDeck(), false, 4, 2);
    assertEquals("      A♣      A♦      A♥\n" +
            "    A♠  2♣  2♦  2♥  2♠  3♣\n" +
            "  3♦  3♥  3♠  4♣  4♦  4♥  4♠\n" +
            "5♣  5♦  5♥  5♠  6♣  6♦  6♥  6♠\n" +
            "Draw: 7♣, 7♦", a.toString());
  }

  @Test
  public void testToString1Rows() {
    model.startGame(model.getDeck(), false, 1, 2);
    assertEquals("A♣\n" +
            "Draw: A♦, A♥", a.toString());
  }

  @Test
  public void testToStringNotStarted() {
    assertEquals("", a.toString());
  }

  @Test
  public void testToStringWin() {
    List<Card> cards = model.getDeck();
    Collections.reverse(cards);

    model.startGame(cards, false, 1, 2);
    model.remove(0, 0);
    assertEquals("You win!", a.toString());
  }

  @Test
  public void testToStringGameOver() {
    model.startGame(model.getDeck(), false, 1, 103);
    assertEquals(false, model.isGameOver());

    for (int i = 0; i < 103; i++) {
      model.discardDraw(i);
    }

    assertEquals("Game over. Score: 1", a.toString());
  }


  @Test
  public void testRender() throws IOException {
    StringBuffer out = new StringBuffer();
    PyramidSolitaireTextualView a = new PyramidSolitaireTextualView(model, out);

    model.startGame(model.getDeck(), false, 3, 2);
    a.render();
    assertEquals("    A♣  A♦  A♥\n" +
            "  A♠  2♣  2♦  2♥\n" +
            "2♠  3♣  3♦  3♥  3♠\n" +
            "Draw: 4♣, 4♦", out.toString());
  }

  @Test(expected = IOException.class)
  public void testRenderIOException() throws IOException {
    Appendable out = new StringBufferFail();
    PyramidSolitaireTextualView a = new PyramidSolitaireTextualView(model, out);

    model.startGame(model.getDeck(), false, 3, 2);
    a.render();
  }


}