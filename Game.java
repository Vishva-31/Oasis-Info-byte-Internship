package Project;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);      
        Random random = new Random();
        
        boolean playAgain = true;
        int totScore = 0;
        final int maxAtt = 7;   
        
        while (playAgain) {
            int Comp = random.nextInt(100) + 1;
            int attempts = 0;
            boolean hasGuessedCorrectly = false;
            
            System.out.println("\n===== Guess the Number Challenge =====");
            System.out.println("Guess a number between 1 and 100.");
            
            while (attempts < maxAtt) {
                System.out.print("Enter your guess: ");
                int player = scanner.nextInt();
                attempts++;
                
                if (player == Comp) {
                    hasGuessedCorrectly = true;
                    int roundScore = (maxAtt - attempts + 1) * 10;
                    totScore += roundScore;
                    System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                    System.out.println("You earned " + roundScore + " points.");
                    break;
                } else if (player < Comp) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }
            
            if (!hasGuessedCorrectly) {
                System.out.println("Sorry! You've used all attempts. The correct number was " + Comp + ".");
            }
            
            System.out.println("Your total score: " + totScore);
            
            System.out.print("Do you want to play again? (y/n): ");
            String response = scanner.next().trim().toLowerCase();
            playAgain = response.equals("y");
        }
        
        System.out.println("Thank you for playing! Your final score is " + totScore + ".");
        scanner.close();
    }
}
