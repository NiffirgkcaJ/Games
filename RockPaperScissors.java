import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors{
    public static void main(String[] args){
        final String[] rps = {"Rock", "Paper", "Scissors"};
        String playerMove;
        Boolean continuity = true;

        List<String> usedMoves = new ArrayList<>();

        while(continuity){
            while(true){
                // Get player move.
                playerMove = getPlayerMove();

                // Generate computer move within the loop, using available moves or shuffled array.
                String computerMove = generateComputerMove(usedMoves, rps);
                System.out.println("The computer chooses: " + computerMove + "!");

                if(playerMove.equals("r") || playerMove.equals("rock")){
                    if(computerMove.equals("Paper")){
                        System.out.println("You lose!");
                    }
                    else if(computerMove.equals("Scissors")){
                        System.out.println("You win!");
                    }
                    else{
                        System.out.println("The game is a tie!");
                    }
                }
                else if(playerMove.equals("p") || playerMove.equals("paper")){
                    if(computerMove.equals("Scissors")){
                        System.out.println("You lose!");
                    }
                    else if(computerMove.equals("Rock")){
                        System.out.println("You win!");
                    }
                    else{
                        System.out.println("The game is a tie!");
                    }
                }
                else if(playerMove.equals("s") || playerMove.equals("scissors")){
                    if(computerMove.equals("Rock")){
                        System.out.println("You lose!");
                    }
                    else if(computerMove.equals("Paper")){
                        System.out.println("You win!");
                    }
                    else{
                        System.out.println("The game is a tie!");
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
                // Add computer move to used moves for the next round.
                usedMoves.add(computerMove);
            }
        }
    }

    private static String getPlayerMove(){
        Scanner playerInput = new Scanner(System.in);
        String playerMove;

        while(true){
            System.out.print("Please enter your move (R, P, or S): ");
            playerMove = playerInput.nextLine().toLowerCase();
    
            if(playerMove.equals("r") || playerMove.equals("rock") || playerMove.equals("p") || playerMove.equals("paper") || playerMove.equals("s") || playerMove.equals("scissors")){
                break;
            }
            else{
                System.out.println(playerMove + " is not a valid move.");
            }
        }
        return playerMove;
    }

    private static String generateComputerMove(List<String> usedMoves, final String[] rps){
        if (usedMoves.size() < 2){
            List<String> availableMoves = new ArrayList<>(Arrays.asList(rps));
            availableMoves.removeAll(usedMoves);
            return availableMoves.get(new Random().nextInt(availableMoves.size()));
        }
        else{
            List<String> shuffledRps = Arrays.asList(rps);
            Collections.shuffle(shuffledRps);
            return shuffledRps.get(0);
        }
    }
}
