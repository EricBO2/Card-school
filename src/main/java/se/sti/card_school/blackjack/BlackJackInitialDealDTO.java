package se.sti.card_school.blackjack;

import se.sti.card_school.cards.CardDTO;
import java.util.List;

public record BlackJackInitialDealDTO(
        List<CardDTO> playerCards,
        List<CardDTO> dealerCards
) {
}
