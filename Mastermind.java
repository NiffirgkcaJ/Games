import java.util.*;

public class Mastermind{
    private static final int CODE_LENGTH = 4;
    private static final int MAX_TURNS = 10;
    private static final char[] COLORS = {'R', 'O', 'Y', 'G', 'B', 'P'};

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean playAgain = true;
        while(playAgain){
            char[] secretCode = generateSecretCode(random);
            System.out.println("\nWelcome to Mastermind! Try to guess the secret code. The colours are as follows:\nR for Red, O for Orange, Y for Yellow, G for Green, B for Blue, or P for Purple.");

            for (int turn = 1; turn <= MAX_TURNS;){
                char[] guess = getInputWithValidation(scanner, turn); // Pass "turn"

                if (guess != null){ // Check for valid input before incrementing turn
                    if (checkCorrect(secretCode, guess)){
                        System.out.println("Congratulations! You guessed the correct code.");
                        break;
                    }
                    else{
                        int[] feedback = generateFeedback(secretCode, guess);
                        System.out.println("Feedback: " + feedback[0] + " black peg(s) and " + feedback[1] + " white peg(s)");
                        turn++; // Increment turn only after valid guess
                    }
                }

                if (turn > MAX_TURNS){
                    System.out.println("\nSorry, you've run out of turns. The secret code was: " + Arrays.toString(secretCode));
                }
            }

            // Restart the game
            while(true){
                System.out.print("Do you want to play again? [Y|N]: ");
                Scanner confirmInput = new Scanner(System.in);
                String confirm = confirmInput.nextLine().toLowerCase();

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
                    continue;
                }
            }
        }
    }

    // New method to validate input with breaking on invalid characters
    private static char[] getInputWithValidation(Scanner scanner, int turn){
        while(true){
            // Print prompt before the loop
            System.out.print("\nTurn " + turn + "/" + MAX_TURNS + ": Enter your guess (e.g., RGBY): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if(input.length() != CODE_LENGTH){
                System.out.println("Invalid guess. Please enter exactly " + CODE_LENGTH + " letters.");
                continue; // Start the next loop iteration
            }

            char[] guess = input.toCharArray();

            if(isValidGuess(guess)){
                return guess; // Return valid guess
            }
            else{
                System.out.println("Invalid guess. Please use only the allowed colors: " + Arrays.toString(COLORS));
            }
        }
    }

    private static boolean isValidGuess(char[] guess){
        for(char c : guess){
            boolean isValid = false;
            for(char color : COLORS){
                if(c == color){
                    isValid = true;
                    break;
                }
            }

            if(!isValid){
                return false; // Early return if invalid character found
            }
        }
        return true;
    }

    private static char[] generateSecretCode(Random random){
        char[] secretCode = new char[CODE_LENGTH];
        for(int i = 0; i < CODE_LENGTH; i++) {
            secretCode[i] = COLORS[random.nextInt(COLORS.length)];
        }
        return secretCode;
    }

    private static boolean checkCorrect(char[] secretCode, char[] guess){
        if(secretCode.length != guess.length){
            return false;
        }
        for(int i = 0; i < secretCode.length; i++){
            if (secretCode[i] != guess[i]){
                return false;
            }
        }
        return true;
    }

    private static int[] generateFeedback(char[] secretCode, char[] guess){
        int blackPegs = 0;
        int whitePegs = 0;
        boolean[] checkedSecret = new boolean[CODE_LENGTH];
        boolean[] checkedGuess = new boolean[CODE_LENGTH];

        // First pass: Check for correct color and position
        for(int i = 0; i < CODE_LENGTH; i++){
            if(secretCode[i] == guess[i]){
                blackPegs++;
                checkedSecret[i] = true;
                checkedGuess[i] = true;
            }
        }

        // Second pass: Check for correct color but wrong position
        for(int i = 0; i < CODE_LENGTH; i++){
            if(guess[i] != secretCode[i]) {
                for(int j = 0; j < CODE_LENGTH; j++){
                    if(!checkedGuess[i] && !checkedSecret[j] && guess[i] == secretCode[j]){
                        whitePegs++;
                        checkedSecret[j] = true;
                        checkedGuess[i] = true;
                        break;
                    }
                }
            }
        }
        return new int[]{blackPegs, whitePegs};
    }
}
