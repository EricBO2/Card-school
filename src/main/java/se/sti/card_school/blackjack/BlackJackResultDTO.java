package se.sti.card_school.blackjack;

// Sends game result to frontend
public record BlackJackResultDTO(
        int playerPoints,
        int dealerPoints,
        boolean playerWins
) {}
