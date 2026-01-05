package se.sti.card_school.blackjack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sti.card_school.cards.Deck;
import se.sti.card_school.model.Dealer;
import se.sti.card_school.model.Player;

@RestController
@RequestMapping("/blackjack")
public class BlackJackController {

    private final BlackJackService blackJackService;
    private final BlackJackGameState gameState;

    @Autowired
    public BlackJackController(
            BlackJackService blackJackService,
            BlackJackGameState gameState
    ) {
        this.blackJackService = blackJackService;
        this.gameState = gameState;
    }

    // Gets state when refreshed page
    @GetMapping("/state")
    public ResponseEntity<BlackJackStateDTO> getState() {
        BlackJackStateDTO state = BlackJackStateDTO.from(
                gameState.getPlayer(),
                gameState.getDealer(),
                blackJackService,
                gameState.isGameOver()
        );
        return ResponseEntity.ok(state);
    }


    // Start new game and initial deal
    @GetMapping("/new-game")
    public ResponseEntity<BlackJackStateDTO> newGame() {

        gameState.reset();

        Deck deck = gameState.getDeck();
        Player player = gameState.getPlayer();
        Dealer dealer = gameState.getDealer();

        blackJackService.initialDeal(player, dealer, deck);

        return ResponseEntity.ok(
                BlackJackStateDTO.from(
                        player,
                        dealer,
                        blackJackService,
                        false
                )
        );
    }


    @PostMapping("/player-hit")
    public ResponseEntity<BlackJackStateDTO> playerHit() {

        // Blocks hit after game over
        if (gameState.isGameOver()) {
            return ResponseEntity.ok(
                    BlackJackStateDTO.from(
                            gameState.getPlayer(),
                            gameState.getDealer(),
                            blackJackService,
                            true
                    )
            );
        }

        Player player = gameState.getPlayer();
        Deck deck = gameState.getDeck();

        blackJackService.hit(player, deck);

        if (blackJackService.calculatePoints(player) > 21) {
            gameState.setGameOver(true);
        }

        //Sends state to frontend
        return ResponseEntity.ok(
                BlackJackStateDTO.from(
                        gameState.getPlayer(),
                        gameState.getDealer(),
                        blackJackService,
                        gameState.isGameOver()
                )
        );
    }


    // Player stays, then dealer plays
    @PostMapping("/stay")
    public ResponseEntity<BlackJackStateDTO> stay() {

        // Blocks stay after game over
        if (gameState.isGameOver()) {
            return ResponseEntity.ok(
                    BlackJackStateDTO.from(
                            gameState.getPlayer(),
                            gameState.getDealer(),
                            blackJackService,
                            true
                    )
            );
        }

        // After dealer is done playing
        blackJackService.dealerPlay(
                gameState.getDealer(),
                gameState.getDeck()
        );

        gameState.setGameOver(true);

        // Sends all dealer and player cards and gameOver boolean
        return ResponseEntity.ok(
                BlackJackStateDTO.from(
                        gameState.getPlayer(),
                        gameState.getDealer(),
                        blackJackService,
                        true
                )
        );
    }
}

