import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    public static void main(String[] args){
        String[] rps = {"Rock", "Paper", "Scissors"};
        String computerMove = rps[new Random().nextInt(rps.length)];
        Scanner playerInput = new Scanner(System.in);
        String playerMove;
        Boolean continuity = true;

        while(continuity){
            while(true){
                System.out.print("Please enter your move (R, P, or S): ");
                playerMove = playerInput.nextLine().toLowerCase();
    
                if(playerMove.equals("r") || playerMove.equals("rock") || playerMove.equals("p") || playerMove.equals("paper") || playerMove.equals("s") || playerMove.equals("scissors")){
                    break;
                }
                System.out.println(playerMove + " is not a valid move.");
            }
            System.out.println("The computer chooses: " + computerMove + "!");
    
            if(playerMove.equals(computerMove)){
                System.out.println("The game is a tie!");
            }
            else if(playerMove.equals("r") || playerMove.equals("rock")){
                if(computerMove.equals("Paper")){
                    System.out.println("You lose!");
                }
                else if(computerMove.equals("Scissors")){
                    System.out.println("You win!");
                }
            }
            else if(playerMove.equals("p") || playerMove.equals("paper")){
                if(computerMove.equals("Scissors")){
                    System.out.println("You lose!");
                }
                else if(computerMove.equals("Rock")){
                    System.out.println("You win!");
                }
            }
            else if(playerMove.equals("s") || playerMove.equals("scissor")){
                if(computerMove.equals("Rock")){
                    System.out.println("You lose!");
                }
                else if(computerMove.equals("Paper")){
                    System.out.println("You win!");
                }
            }

            while(true){
                System.out.print("Do you want to restart the game? [Y|N]: ");
                Scanner confirmInput = new Scanner(System.in);
                String confirm = confirmInput.nextLine().toLowerCase();

                if(confirm.equals("y") || confirm.equals("yes")){
                    break;
                }
                else if(confirm.equals("n") || confirm.equals("no")){
                    System.out.println("Thank you for playing my game! :)");
                    continuity = false;
                    break;
                }
                else{
                    System.out.println("Invalid Choice!");
                    continue;
                }
            }
        }
    }
}