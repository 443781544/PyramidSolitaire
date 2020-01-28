import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.TriPeaksPyramidSolitaire;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for class PyramidSolitaireCreator.
 */
public class PyramidSolitaireCreatorTest {

  @Test
  public void testCreateBasic() {
    PyramidSolitaireModel model =
            PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.BASIC);
    assertTrue(model instanceof BasicPyramidSolitaire);
  }

  @Test
  public void testCreateRelaxed() {
    PyramidSolitaireModel model =
            PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.RELAXED);
    assertTrue(model instanceof RelaxedPyramidSolitaire);
  }

  @Test
  public void testCreateTripeaks() {
    PyramidSolitaireModel model =
            PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.TRIPEAKS);
    assertTrue(model instanceof TriPeaksPyramidSolitaire);
  }

  @Test
  public void testCreateException() {
    PyramidSolitaireModel model = PyramidSolitaireCreator.create(null);
    assertTrue(model == null);
  }
}