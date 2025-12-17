package se.sti.card_school.cards;

public record CardDTO(
        int value,
        String suit,
        String imagePath,
        String displayName,
        boolean faceDown
) {

    // Creates CardDTO with default faceDown=false
    public CardDTO(Card card) {
        this(card, false);
    }

    // Creates CardDTO with optional faceDown
    public CardDTO(Card card, boolean faceDown) {
        this(
                card.getValue(),
                card.getSuit().toString(),
                "/cards/" + valueToString(card.getValue())
                        + "_of_" + card.getSuit().toString().toLowerCase()
                        + ".svg",
                displayName(card),
                faceDown
        );
    }

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
