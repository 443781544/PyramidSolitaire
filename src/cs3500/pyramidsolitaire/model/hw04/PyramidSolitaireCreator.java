package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 *  The class defines a public enum GameType with three possible values:
 *  BASIC, RELAXED and TRIPEAKS.
 *  It offers a static factory method create(GameType type) that returns
 *  an instance of (an appropriate subclass of) PyramidSolitaireModel,
 *  depending on the value of the parameter.
 */
public class PyramidSolitaireCreator {

  /**
   * GameType with three possible values:
   * BASIC, RELAXED and TRIPEAKS.
   * Representing PyramidSolitaireModels.
   */
  public enum GameType {
    BASIC, RELAXED, TRIPEAKS;
  }

  /**
   * returns an instance of (an appropriate subclass of) PyramidSolitaireModel.
   * @param type enum GameType
   * @return
   */
  public static PyramidSolitaireModel create(GameType type) {
    if (type == null) {
      return null;
    }
    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case TRIPEAKS:
        return new TriPeaksPyramidSolitaire();
      default:
        return null;
    }
  }
}
