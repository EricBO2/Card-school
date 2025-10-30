package se.sti.card_school.blackjackmechanics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import se.sti.card_school.User;
import se.sti.card_school.cards.Card;

@RestController
public class BlackJackController {
     private final BlackJackService blackJackService;
     @Autowired
     public BlackJackController(BlackJackService blackJackService) {
         this.blackJackService = blackJackService;
     }


    @GetMapping("/blackjack")
    public String gameStart(){

    }

    @PostMapping
    public ResponseEntity<Card> playerHit(){

    }
}
