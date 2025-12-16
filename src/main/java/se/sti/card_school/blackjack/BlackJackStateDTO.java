package se.sti.card_school.blackjack;

import se.sti.card_school.cards.CardDTO;
import se.sti.card_school.model.Player;
import se.sti.card_school.model.Dealer;

import java.util.List;

public record BlackJackStateDTO(
        List<CardDTO> playerCards,
        List<CardDTO> dealerCards,
        int playerPoints,
        Integer dealerPoints,
        boolean gameOver
) {
    public static BlackJackStateDTO from(
            Player player,
            Dealer dealer,
            BlackJackService service,
            boolean gameOver
    ) {
        List<CardDTO> playerDTOs = player.getCards().stream()
                .map(CardDTO::new)
                .toList();

        List<CardDTO> dealerDTOs = dealer.getCards().stream()
                .map(CardDTO::new)
                .toList();

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
