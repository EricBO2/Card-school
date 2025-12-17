package se.sti.card_school.blackjack;

import org.springframework.stereotype.Service;
import se.sti.card_school.cards.Card;
import se.sti.card_school.cards.CardDTO;
import se.sti.card_school.cards.Deck;
import se.sti.card_school.model.Dealer;
import se.sti.card_school.model.Player;
import se.sti.card_school.model.User;

import java.util.ArrayList;
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

    // Dealer plays automatically after stay
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

        if (p > 21) return false;
        if (d > 21) return true;
        return p > d;
    }

    // Initial deal: player (2 open), dealer (1 open, 1 hidden)
    public BlackJackInitialDealDTO initialDeal(Player player, Dealer dealer, Deck deck) {

        List<CardDTO> playerCards = new ArrayList<>();
        List<CardDTO> dealerCards = new ArrayList<>();

        // Player: two face-up cards
        for (int i = 0; i < 2; i++) {
            Card card = hit(player, deck);
            playerCards.add(new CardDTO(card, false));
        }

        // Dealer: first open
        Card firstDealerCard = hit(dealer, deck);
        dealerCards.add(new CardDTO(firstDealerCard, false));

        // Dealer: second hidden
        Card secondDealerCard = hit(dealer, deck);
        dealerCards.add(new CardDTO(secondDealerCard, true));

        return new BlackJackInitialDealDTO(playerCards, dealerCards);
    }

    // Dealer plays and return final result
    public BlackJackResultDTO dealerPlayAndReturnResult(Player player, Dealer dealer, Deck deck) {

        dealerPlay(dealer, deck);

        boolean playerWins = calculateResult(player, dealer);

        List<CardDTO> dealerCardsDTO = dealer.getCards().stream()
                .map(card -> new CardDTO(card, false)) // all revealed
                .toList();

        int playerPoints = calculatePoints(player);
        int dealerPoints = calculatePoints(dealer);

        return new BlackJackResultDTO(
                dealerCardsDTO,
                playerPoints,
                dealerPoints,
                playerWins
        );
    }
}
