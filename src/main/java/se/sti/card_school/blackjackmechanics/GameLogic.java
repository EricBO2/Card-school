package se.sti.card_school.blackjackmechanics;


import se.sti.card_school.User;
import se.sti.card_school.cards.Card;

import java.util.List;

public class GameLogic {
    public static void printResult(User user) {
        if (user.getPoints() > 21) {
            System.out.println(user + " went bust with " + user.getPoints() + " points\n");
        } else if (user.getPoints() == 21) {
            System.out.println(user + " got blackjack!\n");
        } else {
            System.out.println(user + " ended with " + user.getPoints() + " points\n");
        }
    }
    public static void printHiddenHand(User user) {
        System.out.println("Dealer's hand is: ");
        System.out.println(user.getCard(0));
        System.out.println("Hidden Card");
    }

    public static boolean calculateResult(User player, User dealer) {
        int playerPoints = player.getPoints();
        int dealerPoints = dealer.getPoints();
        boolean result = false;
        if (playerPoints > dealerPoints && playerPoints <= 21) {
            result = true;
        } else if (playerPoints <= 21 && dealerPoints > 21) {
            result = true;
        }
        return result;
    }
    public static int calculatePoints(List<Card> cards) {
        int points = 0;
        int numberOfAces = 0;

        for (Card card : cards) {
            if (card.getValue() <= 10) {
                points += card.getValue();
            } else if (card.getValue() <= 13) {
                points += 10;
            } else if (card.getValue() == 14) {
                numberOfAces += 1;
            }
        }
        for (int i = 0; i < numberOfAces; i++) {
            if ((points + 11)+(numberOfAces-(i+1)) <= 21) {
                points += 11;
            } else points += 1;
        }

        return points;
    }
}
