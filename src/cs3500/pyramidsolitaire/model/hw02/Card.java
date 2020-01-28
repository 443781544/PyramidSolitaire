package cs3500.pyramidsolitaire.model.hw02;


import java.util.Objects;

/**
 * Represnet a Card.
 */
public class Card {
  private final int value;
  private final char suit;

  /**
   * Construct a card.
   *
   * @param value the value of the card (1-13)
   * @param suit  the suit of the card (one of '♣' '♦' '♥' '♠')
   */
  public Card(int value, char suit) {
    if (value < 1 || value > 13 || (suit != '♣' && suit != '♦' && suit != '♥' && suit != '♠')) {
      throw new IllegalArgumentException("Invalid Card");
    }
    this.value = value;
    this.suit = suit;
  }

  public int getValue() {
    return value;
  }


  @Override
  public String toString() {
    String v = "" + value;
    switch (value) {
      case 1:
        v = "A";
        break;
      case 11:
        v = "J";
        break;
      case 12:
        v = "Q";
        break;
      case 13:
        v = "K";
        break;
      default:
        break;
    }
    return "" + v + suit;
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof Card)) {
      return false;
    } else {
      Card c = (Card) that;
      return this.value == c.value && this.suit == c.suit;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, suit);
  }

}
