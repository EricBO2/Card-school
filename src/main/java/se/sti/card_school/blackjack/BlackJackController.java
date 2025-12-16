package se.sti.card_school.blackjack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sti.card_school.cards.Card;
import se.sti.card_school.cards.CardDTO;
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

    // Helper: block requests if game is already over
    private ResponseEntity<?> blockIfGameOver() {
        if (gameState.isGameOver()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Game is already over");
        }
        return null;
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

    // Player Hit
    @PostMapping("/player-hit")
    public ResponseEntity<CardDTO> playerHit() {

        ResponseEntity<?> blocked = blockIfGameOver();
        if (blocked != null) return (ResponseEntity<CardDTO>) blocked;

        Player player = gameState.getPlayer();
        Deck deck = gameState.getDeck();

        Card drawnCard = blackJackService.hit(player, deck);
        CardDTO cardDTO = new CardDTO(drawnCard);

        return ResponseEntity.ok(cardDTO);
    }

    // Player stays, then dealer plays
    @PostMapping("/stay")
    public ResponseEntity<BlackJackResultDTO> stay() {

        ResponseEntity<?> blocked = blockIfGameOver();
        if (blocked != null) return (ResponseEntity<BlackJackResultDTO>) blocked;

        Player player = gameState.getPlayer();
        Dealer dealer = gameState.getDealer();
        Deck deck = gameState.getDeck();

        BlackJackResultDTO result =
                blackJackService.dealerPlayAndReturnResult(player, dealer, deck);

        gameState.setGameOver(true);

        return ResponseEntity.ok(result);
    }

    // Get current game state (used by frontend)
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

}
