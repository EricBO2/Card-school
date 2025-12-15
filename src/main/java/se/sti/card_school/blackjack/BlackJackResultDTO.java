package se.sti.card_school.blackjack;

import se.sti.card_school.cards.CardDTO;
import java.util.List;

// Sends blackjack results to frontend
public record BlackJackResultDTO(
        List<CardDTO> dealerCards,
        int playerPoints,
        int dealerPoints,
        boolean playerWins
) {
}
