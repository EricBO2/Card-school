package se.sti.card_school.blackjack;

import se.sti.card_school.cards.Card;
import se.sti.card_school.cards.CardDTO;
import se.sti.card_school.model.Dealer;
import se.sti.card_school.model.Player;

import java.util.List;

// DTO to keep a snapshot of the current Blackjack game state
public record BlackJackStateDTO(
        List<CardDTO> playerCards,
        List<CardDTO> dealerCards,
        int playerPoints,
        Integer dealerPoints, // null if game not over
        boolean gameOver
) {

    public static BlackJackStateDTO from(
            Player player,
            Dealer dealer,
            BlackJackService service,
            boolean gameOver
    ) {
        // Convert player's cards to DTOs (all faceUp)
        List<CardDTO> playerDTOs = player.getCards().stream()
                .map(card -> new CardDTO(card, false))
                .toList();

        List<CardDTO> dealerDTOs;

        // Only first dealer card faceUp, second faceDown if game is not over
        List<Card> dealerCards = dealer.getCards();
        if (!gameOver && dealerCards.size() >= 2) {
            dealerDTOs = List.of(
                    new CardDTO(dealerCards.get(0), false), // first card faceUp
                    new CardDTO(dealerCards.get(1), true)   // second card faceDown
            );
        } else {
            // Game over or less than 2 cards, show all faceUp
            dealerDTOs = dealerCards.stream()
                    .map(card -> new CardDTO(card, false))
                    .toList();
        }

        int playerPoints = service.calculatePoints(player);

        Integer dealerPoints = gameOver
                ? service.calculatePoints(dealer)
                : null;

        return new BlackJackStateDTO(
                playerDTOs,
                dealerDTOs,
                playerPoints,
                dealerPoints,
                gameOver
        );
    }
}
