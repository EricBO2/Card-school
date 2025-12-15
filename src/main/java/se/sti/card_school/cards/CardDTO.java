package se.sti.card_school.cards;

public record CardDTO(
        int value,
        String suit,
        String imagePath,
        String displayName
) {

    // Creates image path that matches with sng images in frontend
    // 2-10
    public CardDTO(Card card) {
        this(
                card.getValue(),
                card.getSuit().toString(),
                "/cards/" + valueToString(card.getValue())
                        + "_of_"
                        + card.getSuit().toString().toLowerCase()
                        + ".svg",
                displayName(card)
        );
    }

    // Jack - Ace
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
