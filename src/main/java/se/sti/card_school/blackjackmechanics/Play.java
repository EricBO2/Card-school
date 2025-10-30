package se.sti.card_school.blackjackmechanics;





public class Play {}
/*
    public static void startGame(Dealer dealer, Player player) {
        boolean cont;
        do {
            dealStartHand(player,dealer.getDeck());
            dealStartHand(dealer,dealer.getDeck());

            GameLogic.printHiddenHand(dealer);
            player.printHand();

            Play.playerPlay(player, dealer);
            dealer.printHand();

            Play.dealerPlay(dealer);

            boolean win = GameLogic.calculateResult(player, dealer);

            if (win) {
                System.out.println(player + " Wins !!!!!!!!!!!!!!!!!!!!!!!\n");
            } else {
                System.out.println(dealer + " Wins :(\n");
            }
            dealer.clearHand();
            player.clearHand();
            cont = UserInterface.getContinue();
        }while(cont);
    }

    public static void dealerPlay(Dealer dealer) {
        while (dealer.getPoints()<=17) {
            UserInterface.pause();
            hit(dealer, dealer.getDeck());
            dealer.printHand();
        }
        GameLogic.printResult(dealer);
    }

    public static void playerPlay(User player, Dealer dealer) {
        boolean cont;
        do {
            String choice = UserInterface.playerChoice();
            cont = UserInterface.choiceMade(choice, player, dealer);
            player.printHand();
        } while (cont && player.getPoints() <= 21);

        GameLogic.printResult(player);
    }

    public static void hit(User user, Deck deck) {
        if(deck.deckLength()>0) {
            Card card = deck.drawCard();
            user.giveCard(card);
        }else{
            System.out.println("\nDeck empty\n");
            deck.creatNewDeck();
            deck.shuffle();
            hit(user, deck);
        }
    }

    public static void dealStartHand(User user, Deck deck) {
        hit(user, deck);
        hit(user, deck);
    }
}
