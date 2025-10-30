package se.sti.card_school;



import se.sti.card_school.blackjackmechanics.GameLogic;
import se.sti.card_school.cards.Card;

import java.util.ArrayList;

public abstract class User {
    private ArrayList<Card> cards = new ArrayList<>();

    public User() {}

    public void giveCard(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(int cardId) {
        return cards.get(cardId);
    }

    public void clearHand(){cards.clear();}


    
    public String toString(){
        return "User";
    }

    public int getPoints(){
        return  GameLogic.calculatePoints(cards);
    }


}
