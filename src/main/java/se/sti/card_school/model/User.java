package se.sti.card_school.model;


import java.util.ArrayList;
import java.util.List;

import se.sti.card_school.cards.Card;

public abstract class User {

    protected List<Card> cards = new ArrayList<>();

    public void giveCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public void clearHand() {
        cards.clear();
    }
}

