import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests Method PlayGame for class PyramidSolitaireTextualController.
 */

public class PyramidSolitaireTextualControllerTest {

  PyramidSolitaireModel<Card> model = new BasicPyramidSolitaire();

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInputApNull() {
    StringBuffer out = null;
    Readable in = new StringReader("");
    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInputRdNull() {
    StringBuffer out = new StringBuffer();
    Readable in = null;
    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
  }

  @Test
  public void testPlayGame() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          A♦  A♥\n" +
            "        A♠  2♣  2♦\n" +
            "      2♥  2♠  3♣  3♦\n" +
            "    3♥  3♠  4♣  4♦  4♥\n" +
            "  4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "6♦  6♥  6♠  7♣  7♦  7♥  7♠\n" +
            "Draw: 8♣, 8♦, 8♥\n" +
            "Score: 112\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣\n" +
            "          A♦  A♥\n" +
            "        A♠  2♣  2♦\n" +
            "      2♥  2♠  3♣  3♦\n" +
            "    3♥  3♠  4♣  4♦  4♥\n" +
            "  4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "6♦  6♥  6♠  7♣  7♦  7♥  7♠\n" +
            "Draw: 8♣, 8♦, 8♥\n" +
            "Score: 112\n", out.toString());
  }

  @Test
  public void testPlayGameQuit() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("q");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 7, 3);

    assertTrue(out.toString().contains("Game quit!"));

    assertEquals("            A♣\n" +
            "          A♦  A♥\n" +
            "        A♠  2♣  2♦\n" +
            "      2♥  2♠  3♣  3♦\n" +
            "    3♥  3♠  4♣  4♦  4♥\n" +
            "  4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "6♦  6♥  6♠  7♣  7♦  7♥  7♠\n" +
            "Draw: 8♣, 8♦, 8♥\n" +
            "Score: 112\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣\n" +
            "          A♦  A♥\n" +
            "        A♠  2♣  2♦\n" +
            "      2♥  2♠  3♣  3♦\n" +
            "    3♥  3♠  4♣  4♦  4♥\n" +
            "  4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "6♦  6♥  6♠  7♣  7♦  7♥  7♠\n" +
            "Draw: 8♣, 8♦, 8♥\n" +
            "Score: 112\n", out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameModelNullException() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("");
    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(null, model.getDeck(), false, 7, 3);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameModelUnableToStartGame() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("");
    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, -1, 3);
  }

  @Test
  public void testPlayGameRm1Invalid() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm1 1 1");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 7, 3);
    assertTrue(out.toString().startsWith("            A♣\n" +
            "          A♦  A♥\n" +
            "        A♠  2♣  2♦\n" +
            "      2♥  2♠  3♣  3♦\n" +
            "    3♥  3♠  4♣  4♦  4♥\n" +
            "  4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "6♦  6♥  6♠  7♣  7♦  7♥  7♠\n" +
            "Draw: 8♣, 8♦, 8♥\n" +
            "Score: 112\n" +
            "Invalid move. Play again. invalid move, remove1 not exposed\n"));
  }

  @Test
  public void testPlayGameRm1Valid() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm1 2 1");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);

    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    c.playGame(model, cards, false, 2, 3);

    assertEquals("  K♣\n" +
            "K♦  K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 39\n" +
            "  K♣\n" +
            "    K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 26\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "  K♣\n" +
            "    K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 26\n", out.toString());
  }

  @Test
  public void testPlayGameRm1ValidUnexpectedNum() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm1 w 2 rm2 w 1");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);

    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    c.playGame(model, cards, false, 2, 3);

    assertEquals("  K♣\n" +
            "K♦  K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 39\n" +
            "  K♣\n" +
            "    K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 26\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "  K♣\n" +
            "    K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 26\n", out.toString());
  }

  @Test
  public void testPlayGameRm1Quit() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm1 w 2 q w rm1 1");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);

    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    c.playGame(model, cards, false, 2, 3);

    assertEquals("  K♣\n" +
            "K♦  K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 39\n" +
            "Invalid move. Play again. invalid coordinate\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "  K♣\n" +
            "K♦  K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 39\n", out.toString());
  }

  @Test
  public void testPlayGameRm1QuitRow() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm1 q 2 w rm1 1");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);

    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    c.playGame(model, cards, false, 2, 3);

    assertEquals("  K♣\n" +
            "K♦  K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 39\n" +
            "Invalid move. Play again. invalid coordinate\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "  K♣\n" +
            "K♦  K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 39\n", out.toString());
  }


  @Test
  public void testPlayGameRm1QuitCard() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm1 w 2 q w rm1 1");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);

    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    c.playGame(model, cards, false, 2, 3);

    assertEquals("  K♣\n" +
            "K♦  K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 39\n" +
            "Invalid move. Play again. invalid coordinate\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "  K♣\n" +
            "K♦  K♥\n" +
            "Draw: K♠, Q♣, Q♦\n" +
            "Score: 39\n", out.toString());
  }

  @Test
  public void testPlayGameRm2Invalid() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm2 1 1 1 1 ");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals("    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Invalid move. Play again. invalid move, remove cards not exposed\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n", out.toString());
  }

  @Test
  public void testPlayGameRm2Valid() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm2 7 1 7 7 ");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          A♦  A♥\n" +
            "        A♠  2♣  2♦\n" +
            "      2♥  2♠  3♣  3♦\n" +
            "    3♥  3♠  4♣  4♦  4♥\n" +
            "  4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "6♦  6♥  6♠  7♣  7♦  7♥  7♠\n" +
            "Draw: 8♣, 8♦, 8♥\n" +
            "Score: 112\n" +
            "            A♣\n" +
            "          A♦  A♥\n" +
            "        A♠  2♣  2♦\n" +
            "      2♥  2♠  3♣  3♦\n" +
            "    3♥  3♠  4♣  4♦  4♥\n" +
            "  4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "    6♥  6♠  7♣  7♦  7♥\n" +
            "Draw: 8♣, 8♦, 8♥\n" +
            "Score: 99\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣\n" +
            "          A♦  A♥\n" +
            "        A♠  2♣  2♦\n" +
            "      2♥  2♠  3♣  3♦\n" +
            "    3♥  3♠  4♣  4♦  4♥\n" +
            "  4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "    6♥  6♠  7♣  7♦  7♥\n" +
            "Draw: 8♣, 8♦, 8♥\n" +
            "Score: 99\n", out.toString());
  }

  @Test
  public void testPlayGameRm2Quit() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm2 ww 1 c 1 q  ");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals("    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Invalid move. Play again. invalid coordinate\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n", out.toString());
  }


  @Test
  public void testPlayGameRmwdInvalid() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rmwd 1 1 1  ");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals("    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Invalid move. Play again. invalid move, card not exposed\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n", out.toString());
  }


  @Test
  public void testPlayGameRmwdValid() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rmwd 10 6 2  ");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 6, 10);
    assertEquals("          A♣\n" +
            "        A♦  A♥\n" +
            "      A♠  2♣  2♦\n" +
            "    2♥  2♠  3♣  3♦\n" +
            "  3♥  3♠  4♣  4♦  4♥\n" +
            "4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "Draw: 6♦, 6♥, 6♠, 7♣, 7♦, 7♥, 7♠, 8♣, 8♦, 8♥\n" +
            "Score: 66\n" +
            "          A♣\n" +
            "        A♦  A♥\n" +
            "      A♠  2♣  2♦\n" +
            "    2♥  2♠  3♣  3♦\n" +
            "  3♥  3♠  4♣  4♦  4♥\n" +
            "4♠      5♦  5♥  5♠  6♣\n" +
            "Draw: 6♦, 6♥, 6♠, 7♣, 7♦, 7♥, 7♠, 8♣, 8♦, 8♠\n" +
            "Score: 61\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "          A♣\n" +
            "        A♦  A♥\n" +
            "      A♠  2♣  2♦\n" +
            "    2♥  2♠  3♣  3♦\n" +
            "  3♥  3♠  4♣  4♦  4♥\n" +
            "4♠      5♦  5♥  5♠  6♣\n" +
            "Draw: 6♦, 6♥, 6♠, 7♣, 7♦, 7♥, 7♠, 8♣, 8♦, 8♠\n" +
            "Score: 61\n", out.toString());
  }

  @Test
  public void testPlayGameRmwdUnexpectedNum() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rmwd x 10 xx 6 xxx 2  ");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 6, 10);
    assertEquals("          A♣\n" +
            "        A♦  A♥\n" +
            "      A♠  2♣  2♦\n" +
            "    2♥  2♠  3♣  3♦\n" +
            "  3♥  3♠  4♣  4♦  4♥\n" +
            "4♠  5♣  5♦  5♥  5♠  6♣\n" +
            "Draw: 6♦, 6♥, 6♠, 7♣, 7♦, 7♥, 7♠, 8♣, 8♦, 8♥\n" +
            "Score: 66\n" +
            "          A♣\n" +
            "        A♦  A♥\n" +
            "      A♠  2♣  2♦\n" +
            "    2♥  2♠  3♣  3♦\n" +
            "  3♥  3♠  4♣  4♦  4♥\n" +
            "4♠      5♦  5♥  5♠  6♣\n" +
            "Draw: 6♦, 6♥, 6♠, 7♣, 7♦, 7♥, 7♠, 8♣, 8♦, 8♠\n" +
            "Score: 61\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "          A♣\n" +
            "        A♦  A♥\n" +
            "      A♠  2♣  2♦\n" +
            "    2♥  2♠  3♣  3♦\n" +
            "  3♥  3♠  4♣  4♦  4♥\n" +
            "4♠      5♦  5♥  5♠  6♣\n" +
            "Draw: 6♦, 6♥, 6♠, 7♣, 7♦, 7♥, 7♠, 8♣, 8♦, 8♠\n" +
            "Score: 61\n", out.toString());
  }


  @Test
  public void testPlayGameDdInvalid() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("dd -1 ");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals("    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Invalid move. Play again. invalid move, discardDraw drawIndex invalid\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n", out.toString());
  }

  @Test
  public void testPlayGameDdValid() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("dd 1 ");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals("    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 3♦, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 3♦, 2♠, 3♣\n" +
            "Score: 8\n", out.toString());
  }

  @Test
  public void testPlayGameDdQuit() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("dd xx dd q");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals("    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Invalid move. Play again. invalid move, discardDraw drawIndex invalid\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n", out.toString());
  }

  @Test
  public void testPlayGameDdUnexpectedNum() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("dd abc ss 1 ");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals("    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 3♦, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 3♦, 2♠, 3♣\n" +
            "Score: 8\n", out.toString());
  }


  @Test
  public void testPlayGameInvalidCommand() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm3 rmwdd xx qwer quit rmwd11");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals("    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "    A♣\n" +
            "  A♦  A♥\n" +
            "A♠  2♣  2♦\n" +
            "Draw: 2♥, 2♠, 3♣\n" +
            "Score: 8\n", out.toString());
  }


  @Test
  public void testPlayGameWin() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("rm1 1 1");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);

    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    c.playGame(model, cards, false, 1, 3);

    assertEquals("K♣\n" +
            "Draw: K♦, K♥, K♠\n" +
            "Score: 13\n" +
            "You win!", out.toString());
  }

  @Test
  public void testPlayGameWin2() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("dd xx 1 rm1 1 1");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);

    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    c.playGame(model, cards, false, 1, 3);

    assertEquals("K♣\n" +
            "Draw: K♦, K♥, K♠\n" +
            "Score: 13\n" +
            "K♣\n" +
            "Draw: Q♣, K♥, K♠\n" +
            "Score: 13\n" +
            "You win!", out.toString());
  }

  @Test
  public void testPlayGameLose() {
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("dd 1 dd 2 dd 3 dd 4 dd 5 dd 6 dd 7");

    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);

    List<Card> cards = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      cards.add(new Card(i, '♣'));
      cards.add(new Card(i, '♦'));
      cards.add(new Card(i, '♥'));
      cards.add(new Card(i, '♠'));
    }

    c.playGame(model, cards, false, 9, 7);

    assertTrue(out.toString().endsWith("\n" +
            "Draw:    ,    ,    ,    ,    ,    , A♠\n" +
            "Score: 354\n" +
            "Game over. Score: 354"));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameIOException() {
    Appendable out = new StringBufferFail();
    Readable in = new ReadableFail();
    PyramidSolitaireController c = new PyramidSolitaireTextualController(in, out);
    c.playGame(model, model.getDeck(), false, 7, 3);
  }


}