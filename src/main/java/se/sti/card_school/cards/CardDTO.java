package se.sti.card_school.cards;

public record CardDTO(
        int rank,            // 11 = Jack, 12 = Queen, 13 = King
        int blackjackValue,  // 10 for J/Q/K, 1 or 11 for Ace
        String suit,
        String imagePath,
        String displayName,
        boolean faceDown
) {
    // Constructor that takes Card + faceDown
    public CardDTO(Card card, boolean faceDown) {
        this(
                card.getValue(),
                blackjackValue(card),
                card.getSuit().toString(),
                faceDown
                        ? "/cards/hidden_card.svg"
                        : "/cards/" + valueToString(card.getValue()) + "_of_" + card.getSuit().toString().toLowerCase() + ".svg",
                displayName(card),
                faceDown
        );
    }

    private static int blackjackValue(Card card) {
        int v = card.getValue();
        if (v >= 11 && v <= 13) return 10; // J Q K
        if (v == 14) return 11;           // Ace (dealer logic can downgrade later)
        return v;
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
