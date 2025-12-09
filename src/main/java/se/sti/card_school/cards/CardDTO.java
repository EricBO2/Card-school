package se.sti.card_school.cards;

public record CardDTO(int value, String suit, String display) {

    public CardDTO(Card card) {
        this(card.getValue(), card.getSuit().toString(), card.toString());
    }
}
