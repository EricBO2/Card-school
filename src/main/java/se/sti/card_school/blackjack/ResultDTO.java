package se.sti.card_school.blackjack;

import se.sti.card_school.cards.Card;
import java.util.List;

public class ResultDTO {

    private List<Card> dealerCards;
    private int dealerPoints;
    private boolean playerWins;

    public ResultDTO(List<Card> dealerCards, int dealerPoints, boolean playerWins) {
        this.dealerCards = dealerCards;
        this.dealerPoints = dealerPoints;
        this.playerWins = playerWins;
    }

    public List<Card> getDealerCards() { return dealerCards; }
    public int getDealerPoints() { return dealerPoints; }
    public boolean isPlayerWins() { return playerWins; }
}
