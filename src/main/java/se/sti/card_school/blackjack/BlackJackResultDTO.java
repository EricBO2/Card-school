package se.sti.card_school.blackjack;

import se.sti.card_school.cards.Card;
import se.sti.card_school.cards.CardDTO;

import java.util.List;

public record BlackJackResultDTO(
        List<CardDTO> dealerCards,
        int dealerPoints,
        boolean playerWins
) {
    public static BlackJackResultDTO from(List<Card> dealerCards, int dealerPoints, boolean playerWins) {
        List<CardDTO> dtoCards = dealerCards.stream()
                .map(CardDTO::new)
                .toList();

        return new BlackJackResultDTO(dtoCards, dealerPoints, playerWins);
    }
}
