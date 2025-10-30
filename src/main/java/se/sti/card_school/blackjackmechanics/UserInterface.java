package se.sti.card_school.blackjackmechanics;



import se.sti.card_school.User;

import java.util.Scanner;

public class UserInterface {
    private static final Scanner scanner;
    static {
        scanner = new Scanner(System.in);
    }

    public static String playerChoice(){
        boolean validChoice;
        String choice;

        do {
            System.out.println("Hit \nStay \nView hand");
            System.out.print("\nEnter Your Choice: \n");
            choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                    case "view hand" , "view" , "stay", "hit": validChoice = true; break;
                default:
                    validChoice = false;
                    System.out.println("\nInvalid choice\n");
                break;
            }
        } while (!validChoice);
        return choice;
    }

    public static void pause(){
        try{
            for(int i =0; i<3;i++){
                System.out.print(".");
                Thread.sleep(1000);
            }
        } catch(InterruptedException e){
            System.out.println(e);
        }
        System.out.println();
    }

    public static String getUserName(){
        System.out.print("Enter your name: ");
        String name =scanner.nextLine();
        System.out.println("Welcome "+name+"\n");
        return name;
    }

    public static Boolean getContinue(){
        do {
            System.out.print("Do you want to continue? (Y/N): \n");
            String choice = scanner.nextLine().toLowerCase();
            if(choice.equals("y") || choice.equals("yes")){return true;}
            else if(choice.equals("n") || choice.equals("no")){return false;}
            System.out.println("\nInvalid choice\n");
        }while(true);
    }
    public static boolean choiceMade(String choice, User user, Dealer dealer) {
        return switch (choice) {
            case "hit" -> {
                Play.hit(user, dealer.getDeck());
                yield true;
            }
            case "stay" -> false;
            case "view hand", "view" -> {
                GameLogic.printHiddenHand(dealer);
                yield true;
            }
            default -> true;
        };
    }
}
