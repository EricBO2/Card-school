package se.sti.card_school.blackjack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sti.card_school.cards.Deck;
import se.sti.card_school.model.Dealer;
import se.sti.card_school.model.Player;
import se.sti.card_school.messaging.GameResultPublisher;
import se.sti.card_school.messaging.PlayerScoreDTO;

@RestController
@RequestMapping("/blackjack")
public class BlackJackController {

    private final BlackJackService blackJackService;
    private final BlackJackGameState gameState;
    private final GameResultPublisher gameResultPublisher;

    @Autowired
    public BlackJackController(BlackJackService blackJackService,
                               BlackJackGameState gameState,
                               GameResultPublisher gameResultPublisher) {
        this.blackJackService = blackJackService;
        this.gameState = gameState;
        this.gameResultPublisher = gameResultPublisher;
    }

    // Start new game
    @GetMapping("/new-game")
    public ResponseEntity<String> newGame() {
        gameState.reset();
        Deck deck = gameState.getDeck();
        blackJackService.dealStartHand(gameState.getPlayer(), deck);
        blackJackService.dealStartHand(gameState.getDealer(), deck);

        return ResponseEntity.ok("New Blackjack game started!");
    }

    // Player hit
    @PostMapping("/player-hit")
    public ResponseEntity<Integer> playerHit() {
        Player player = gameState.getPlayer();
        Deck deck = gameState.getDeck();

        int pointsAfterHit = blackJackService.calculatePoints(player);
        blackJackService.hit(player, deck);

        return ResponseEntity.ok(pointsAfterHit); // Skicka aktuell poäng till frontend
    }

    // Player stays, then dealer plays
    // Return result to frontend and send score to security
    @PostMapping("/stay")
    public ResponseEntity<BlackJackResultDTO> stay() {
        Player player = gameState.getPlayer();
        Dealer dealer = gameState.getDealer();
        Deck deck = gameState.getDeck();

        BlackJackResultDTO result = blackJackService.dealerPlayAndReturnResult(player, dealer, deck);

        // Sends player score to security microservice
        PlayerScoreDTO scoreDTO = new PlayerScoreDTO(result.playerPoints());
        gameResultPublisher.sendScore(scoreDTO);

        gameState.setGameOver(true);

        return ResponseEntity.ok(result);
    }
}
