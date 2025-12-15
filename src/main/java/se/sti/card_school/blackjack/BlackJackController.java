package se.sti.card_school.blackjack;

import org.springframework.beans.factory.annotation.Autowired;
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
    public BlackJackController(BlackJackService blackJackService,
                               BlackJackGameState gameState) {
        this.blackJackService = blackJackService;
        this.gameState = gameState;
    }

    // 1. Start new game
    @GetMapping("/new-game")
    public ResponseEntity<String> newGame() {
        gameState.reset();
        Deck deck = gameState.getDeck();
        blackJackService.dealStartHand(gameState.getPlayer(), deck);
        blackJackService.dealStartHand(gameState.getDealer(), deck);

        return ResponseEntity.ok("New Blackjack game started!");
    }

    // 2. Player Hit
    @PostMapping("/player-hit")
    public ResponseEntity<CardDTO> playerHit() {
        Player player = gameState.getPlayer();
        Deck deck = gameState.getDeck();

        Card drawnCard = blackJackService.hit(player, deck);
        CardDTO cardDTO = new CardDTO(drawnCard);

        return ResponseEntity.ok(cardDTO);
    }

    // 3. Player stays, then dealer plays
    @PostMapping("/stay")
    public ResponseEntity<BlackJackResultDTO> stay() {
        Player player = gameState.getPlayer();
        Dealer dealer = gameState.getDealer();
        Deck deck = gameState.getDeck();

        BlackJackResultDTO result = blackJackService.dealerPlayAndReturnResult(player, dealer, deck);
        gameState.setGameOver(true);

        return ResponseEntity.ok(result);
    }
}
