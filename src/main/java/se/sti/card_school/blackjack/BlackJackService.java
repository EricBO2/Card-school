package se.sti.card_school.blackjack;

import org.springframework.stereotype.Service;
import se.sti.card_school.cards.*;
import se.sti.card_school.model.Dealer;
import se.sti.card_school.model.Player;
import se.sti.card_school.model.User;

import java.util.List;

@Service
public class BlackJackService {

    // Player or Dealer hit
    public Card hit(User user, Deck deck) {
        if (deck.deckLength() == 0) {
            deck.creatNewDeck();
            deck.shuffle();
        }
        Card card = deck.drawCard();
        user.giveCard(card);
        return card;
    }

    // Deal start hand (2 cards)
    public void dealStartHand(User user, Deck deck) {
        hit(user, deck);
        hit(user, deck);
    }

    // Dealer plays automatically
    public void dealerPlay(Dealer dealer, Deck deck) {
        while (calculatePoints(dealer) <= 17) {
            hit(dealer, deck);
        }
    }

    // Calculate points
    public int calculatePoints(User user) {
        return calculatePoints(user.getCards());
    }

    public int calculatePoints(List<Card> cards) {
        int points = 0;
        int aces = 0;

        for (Card card : cards) {
            int value = card.getValue();
            if (value == 14) aces++;
            else if (value >= 11 && value <= 13) points += 10;
            else points += value;
        }

        for (int i = 0; i < aces; i++) {
            if (points + 11 + (aces - i - 1) <= 21) points += 11;
            else points += 1;
        }

        return points;
    }

    // Compare result (true = player wins)
    public boolean calculateResult(User player, User dealer) {
        int p = calculatePoints(player);
        int d = calculatePoints(dealer);

        if (p > 21) return false; // player bust
        if (d > 21) return true;  // dealer bust
        return p > d;
    }

    // Helper: dealer plays and return ResultDTO
    public BlackJackResultDTO dealerPlayAndReturnResult(Player player, Dealer dealer, Deck deck) {
        dealerPlay(dealer, deck);
        boolean playerWins = calculateResult(player, dealer);

        // Converts Card to CardDTO for result
        List<CardDTO> dealerCardsDTO = dealer.getCards().stream()
                .map(CardDTO::new)
                .toList();

        return new BlackJackResultDTO(
                dealerCardsDTO,
                calculatePoints(dealer),
                playerWins
        );
    }

}
