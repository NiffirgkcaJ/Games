import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Mastermind2{
    // Define the possible characters for the secret code
    private static final char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    // Variables to store the maximum number of turns and the length of the secret code
    private static int MAX_TURNS;
    private static int CODE_LENGTH;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean playAgain = true;
        while(playAgain){
            // Select the game difficulty and code length
            int currentDifficulty = selectDifficulty(scanner, true);
            int currentCodeLength = selectCodeLength(scanner, true);

            // Set the maximum number of turns and the code length based on user input
            MAX_TURNS = getGameMode(currentDifficulty);
            CODE_LENGTH = currentCodeLength;

            // Generate the secret code and a sample code for display
            char[] secretCode = generateSecretCode(random);
            char[] showCode = generateSecretCode(random);

            // Display the game introduction and rules
            System.out.println("\nWelcome to Mastermind! A game only for the sliest code breakers.\r\n" +
                    "I am Computer the Codemaker, the mastermind behind the code you will be cracking.\r\n" +
                    "\r\n" +
                    "The game is simple; I'll create a " + CODE_LENGTH + "-character code like this: " + Arrays.toString(showCode) + ".\r\n" +
                    "\r\n" +
                    "You can try and guess within these possible options: " + Arrays.toString(CHARACTERS) + ".\r\n" +
                    "Multiple characters in the same code are possible.\r\n" +
                    "\r\n" +
                    "I'll pity you with hints, and I'll tell you how many characters are correct, but I won't tell you which one.\r\n" +
                    "\r\n" +
                    "How many correct characters will you guess in the right position? We'll see.\r\n" +
                    "You'll get " + MAX_TURNS + " tries...\r\n" +
                    "\r\n" +
                    "Good luck, and let's begin!");

            // Main game loop with flag variable
            boolean isGameOver = false;
            int turn; // Declare the turn variable outside of the loop
            for(turn = 1; turn <= MAX_TURNS; turn++){
                // Get user input for the guess
                char[] guess = getInputWithValidation(scanner, turn);

                if(guess == null){ // Handle invalid input (e.g., not enough characters)
                    continue;
                }

                // Check if the guess is correct
                if(checkCorrect(secretCode, guess)){
                    System.out.println("Congratulations! You guessed the correct code.");
                    isGameOver = true; // Set flag to true when guess is correct
                    break; // Exit the loop if the guess is correct
                }
                else{
                    // Provide feedback on the guess
                    int[] feedback = generateFeedback(secretCode, guess);
                    System.out.println("You have found: " + feedback[0] + " correct character(s) and " + feedback[1] + " nearly correct character(s)");
                }
            }

            // Print the secret code based on the game state
            if(!isGameOver && turn > MAX_TURNS){ // Check if the game ended due to exhausting all turns
                System.out.println("\nSorry, you've run out of turns. The secret code is: " + Arrays.toString(secretCode));
            }

            // Ask the user if they want to play again
            while(true){
                System.out.print("\nDo you want to play again? [Y|N]: ");
                String confirm = scanner.nextLine().toLowerCase();

                if(confirm.equals("y") || confirm.equals("yes")){
                    break;
                }
                else if(confirm.equals("n") || confirm.equals("no")){
                    System.out.println("\nThank you for playing Mastermind!\n");
                    playAgain = false;
                    break;
                }
                else{
                    System.out.println("Error!");
                }
            }
        }
        scanner.close(); // Close the scanner to release system resources
    }

    // Method to determine the maximum number of turns based on the selected difficulty
    private static int getGameMode(int gameDifficulty){
        switch(gameDifficulty){
            case 1:
                return 20; // Easy
            case 2:
                return 12; // Medium
            case 3:
                return 5; // Hard
            default:
                return 0; // Handle invalid difficulty
        }
    }

    // Method to select the game difficulty
    private static int selectDifficulty(Scanner inputDifficulty, boolean isForDifficulty){
        while(true){
            try{
                if(isForDifficulty){
                    System.out.print("\nChoose your difficulty: Easy [1], Medium [2], Hard [3]: ");
                }
                int difficulty = Integer.parseInt(inputDifficulty.nextLine().trim());

                if(difficulty >= 1 && difficulty <= 3){
                    return difficulty;
                }
                else{
                    System.out.println("Invalid difficulty selection. Please choose 1, 2, or 3.");
                }
            }
            catch(NumberFormatException exception){
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            }
        }
    }

    // Method to select the length of the secret code
    private static int selectCodeLength(Scanner inputCodeLength, boolean isForCodeLength){
        while(true){ // Enclose the entire prompt and input logic within a loop
            System.out.print("Please enter the length of the code: ");
            try{
                int codeLength = inputCodeLength.nextInt();
                inputCodeLength.nextLine(); // Consume newline character

                if(codeLength > 0){
                    return codeLength;
                }
                else{
                    throw new IllegalArgumentException("\nInvalid code length. Please enter a positive integer.");
                }
            }
            catch(InputMismatchException exception){
                inputCodeLength.nextLine(); // Consume invalid input
                System.out.println("\nInvalid input. Please enter a positive integer.");
            }
            catch(IllegalArgumentException exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    // Method to get user input for the guess and validate it
    private static char[] getInputWithValidation(Scanner scanner, int turn){
        while(true){
            System.out.print("\nTurn " + turn + "/" + MAX_TURNS + ": Enter your guess: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if(input.length() != CODE_LENGTH){
                System.out.println("Invalid guess. Please enter exactly " + CODE_LENGTH + " characters.");
                continue;
            }
            char[] guess = input.toCharArray();

            if (isValidGuess(guess)){
                return guess;
            }
            else{
                System.out.println("Invalid guess. Please use only the allowed characters: " + Arrays.toString(CHARACTERS));
            }
        }
    }

    // Method to check if the guess is valid
    private static boolean isValidGuess(char[] guess){
        for(char c : guess){
            boolean isValid = false;
            for(char color : CHARACTERS){
                if(c == color){
                    isValid = true;
                    break;
                }
            }

            if(!isValid){
                return false;
            }
        }
        return true;
    }

    // Method to generate a random secret code
    private static char[] generateSecretCode(Random random){
        char[] secretCode = new char[CODE_LENGTH];
        for(int i = 0; i < CODE_LENGTH; i++){
            secretCode[i] = CHARACTERS[random.nextInt(CHARACTERS.length)];
        }
        return secretCode;
    }

    // Method to check if the guess is correct
    private static boolean checkCorrect(char[] secretCode, char[] guess){
        if(secretCode.length != guess.length){
            return false;
        }
        for(int i = 0; i < secretCode.length; i++){
            if(secretCode[i] != guess[i]){
                return false;
            }
        }
        return true;
    }

    // Method to generate feedback on the guess
    private static int[] generateFeedback(char[] secretCode, char[] guess){
        int blackPegs = 0;
        int whitePegs = 0;
        boolean[] checkedGuess = new boolean[CODE_LENGTH];

        HashMap<Character, Integer> secretColorCounts = new HashMap<>();
        for(char color : secretCode){
            secretColorCounts.put(color, secretColorCounts.getOrDefault(color, 0) + 1);
        }

        for(int i = 0; i < CODE_LENGTH; i++){
            if(secretCode[i] == guess[i]){
                blackPegs++;
                checkedGuess[i] = true;
            }
        }

        for(int i = 0; i < CODE_LENGTH; i++){
            if(!checkedGuess[i]){
                for(int j = 0; j < CODE_LENGTH; j++){
                    if (guess[i] == secretCode[j] && !checkedGuess[j] && secretColorCounts.get(guess[i]) > 0){
                        whitePegs++;
                        secretColorCounts.put(guess[i], secretColorCounts.get(guess[i]) - 1);
                        checkedGuess[i] = true;
                        break;
                    }
                }
            }
        }
        return new int[]{blackPegs, whitePegs};
    }
}
