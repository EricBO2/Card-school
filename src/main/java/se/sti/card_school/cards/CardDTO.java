package se.sti.card_school.cards;

public record CardDTO(
        int value,
        String suit,
        String imagePath,
        String displayName,
        boolean faceDown
) {

    // Constructor that takes Card + faceDown
    public CardDTO(Card card, boolean faceDown) {
        this(
                card.getValue(),
                card.getSuit().toString(),
                faceDown
                        ? "/cards/hidden_card.svg"
                        : "/cards/" + valueToString(card.getValue()) + "_of_" + card.getSuit().toString().toLowerCase() + ".svg",
                displayName(card),
                faceDown
        );
    }

    // Handles jack - ace
    private static String valueToString(int value) {
        return switch (value) {
            case 11 -> "jack";
            case 12 -> "queen";
            case 13 -> "king";
            case 14 -> "ace";
            default -> String.valueOf(value);
        };
    }

    private static String displayName(Card card) {
        return valueToString(card.getValue()) + " of " + card.getSuit().toString().toLowerCase();
    }
}
