package se.sti.card_school.blackjack;

import org.springframework.stereotype.Component;
import se.sti.card_school.cards.Deck;
import se.sti.card_school.model.Dealer;
import se.sti.card_school.model.Player;

@Component
public class BlackJackGameState {

    private Player player;
    private Dealer dealer;
    private Deck deck;
    private boolean gameOver;

    public BlackJackGameState() { initGame(); }

    public void initGame() {
        this.player = new Player("Player");
        this.dealer = new Dealer();
        this.deck = new Deck();
        this.deck.shuffle();
        this.gameOver = false;
    }

    public void reset() { initGame(); }

    // Getters
    public Player getPlayer() { return player; }
    public Dealer getDealer() { return dealer; }
    public Deck getDeck() { return deck; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
}
