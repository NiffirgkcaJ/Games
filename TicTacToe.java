import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    static ArrayList <Integer> playerPositions = new ArrayList<Integer>();
    static ArrayList <Integer> computerPositions = new ArrayList<Integer>();
    public static void main(String[] args){
        char[][] gameBoard = {
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '}
        };
        gameBoardPrint(gameBoard);

        while(true){
            Scanner gameInput = new Scanner(System.in);
            System.out.print("\nPlease enter the number of the area. (1-9):");
            int gameInputPosition = gameInput.nextInt();
            while(playerPositions.contains(gameInputPosition) || computerPositions.contains(playerPositions)){
                System.out.print("Position taken! Please enter a blank position!");
                gameInputPosition = gameInput.nextInt();
            }
            gamePlaceInput(gameBoard, gameInputPosition, "Player");

            String result = winState();
            if(result.length() > 0){
                System.out.println(result);
                break;
            }

            Random computerRandom = new Random();
            int computerRandomPosition = computerRandom.nextInt(9) + 1;
            while(playerPositions.contains(computerRandomPosition) || computerPositions.contains(computerRandomPosition)){
                computerRandomPosition = computerRandom.nextInt(9) + 1;
            }
            gamePlaceInput(gameBoard, computerRandomPosition, "Computer");

            gameBoardPrint(gameBoard);

            result = winState();
            if(result.length() > 0){
                System.out.println(result);
                break;
            }
        }
    }

    public static void gameBoardPrint(char[][] gameBoard){
        for(char[] row : gameBoard){
            for(char column : row){
                System.out.print(column);
            }
            System.out.println();
        }
    }

    public static void gamePlaceInput(char[][] gameBoard, int gameInputPosition, String user){
        char symbol = ' ';
        if(user.equals("Player")){
            symbol = 'X';
            playerPositions.add(gameInputPosition);
        }
        else if(user.equals("Computer")){
            symbol = 'O';
         computerPositions.add(gameInputPosition);
        }

        switch(gameInputPosition){
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }
    }

    public static String winState(){

        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List lftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rgtCol = Arrays.asList(3, 6, 9);
        List ltDiag = Arrays.asList(1, 5, 9);
        List rtDiag = Arrays.asList(3, 5, 7);

        List<List> winningStates = new ArrayList<List>();
        winningStates.add(topRow);
        winningStates.add(midRow);
        winningStates.add(botRow);
        winningStates.add(lftCol);
        winningStates.add(midCol);
        winningStates.add(rgtCol);
        winningStates.add(ltDiag);
        winningStates.add(rtDiag);

        for(List winner : winningStates){
            if(playerPositions.containsAll(winner)){
                return "Congratulations! You won!";
            }
            else if (computerPositions.containsAll(winner)){
                return "Computer wins! Sorry... :(";
            }
            else if(playerPositions.size() + computerPositions.size() == 9){
                return "It's a tie!";
            }
        }
        return "";
    }
}