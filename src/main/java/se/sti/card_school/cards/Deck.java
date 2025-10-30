package se.sti.card_school.cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> deck = new ArrayList<Card>();
    public Deck(){
        creatNewDeck();
    }

    public Deck(ArrayList<Card> cards) {        //for testing and rigging
        deck = cards;
    }

    public void printDeck(){
        for (Card card : deck){
            System.out.println(card);
        }
    }
    public void shuffle(){
        Collections.shuffle(deck);
    }
    public Card drawCard(){
        Card card = deck.removeFirst();
        return card;
    }
    public void creatNewDeck(){
        for (int i = 0; i < 4; i++){
            for (int j = 2; j < 15; j++){
                Suit suit;
                switch (i){
                    case 0: suit = Suit.SPADES; break;
                    case 1: suit = Suit.CLUBS; break;
                    case 2: suit = Suit.DIAMONDS; break;
                    case 3: suit = Suit.HEARTS; break;
                    default: suit = Suit.SPADES;
                }
                deck.add(new Card(j,suit));
            }
        }
    }

    public void clearDeck(){deck.clear();}
    public int deckLength(){return deck.size();}
}
