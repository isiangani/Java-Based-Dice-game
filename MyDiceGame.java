/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mydicegame;

/**
 *
 * @author 25479
 */

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class MyDiceGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
   
        // Initialize score table for both players
        int[][] scoreTable = new int[7][2];

        // Create a Random object for dice rolls
        Random random = new Random();

        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Game loop
        for (int round = 1; round <= 7; round++) {
            // Initialize player's turn
            int currentPlayer = round % 2; // Alternates between 0 and 1
            System.out.println("Round " + round + ": Player " + (currentPlayer + 1) + "'s turn");

            // Roll five dice
            int[] dice = new int[5];
            for (int i = 0; i < 5; i++) {
                dice[i] = rollDie(random);
            }

            // Display the rolled dice
            System.out.print("Rolled dice: ");
            for (int dieValue : dice) {
                System.out.print(dieValue + " ");
            }
            System.out.println();

            // Player's turn logic
            int throwCount = 0;
            while (throwCount < 3) {
                System.out.println("Select your action:");
                System.out.println("1. Set aside dice and select category");
                System.out.println("2. Defer the throw");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        // Set aside dice and select category
                        int category;
                        do {
                            System.out.println("Select a category (1-6 for numbers, 7 for sequence):");
                            category = scanner.nextInt();
                        } while (category < 1 || category > 7 || scoreTable[category - 1][currentPlayer] != 0);

                        int count = countDice(dice, category);
                        scoreTable[category - 1][currentPlayer] = count * category;
                        break;
                    case 2:
                        // Defer the throw
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        continue;
                }

                throwCount++;

                // Check if the player has completed the turn
                if (throwCount == 3 || Arrays.stream(scoreTable[round - 1]).filter(s -> s == 0).count() == 0) {
                    break;
                }
            }

            // Display the updated score table after each turn
            displayScoreTable(scoreTable);

            // Clear the newline character from the input buffer
            scanner.nextLine();
        }

        // Determine the winner based on the total scores
        int player1Total = Arrays.stream(scoreTable).mapToInt(row -> row[0]).sum();
        int player2Total = Arrays.stream(scoreTable).mapToInt(row -> row[1]).sum();

        System.out.println("Game Over!");
        System.out.println("Player 1's total score: " + player1Total);
        System.out.println("Player 2's total score: " + player2Total);
        if (player1Total > player2Total) {
            System.out.println("Player 1 wins!");
        } else if (player2Total > player1Total) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    // Method to simulate rolling a single die
    private static int rollDie(Random random) {
        return random.nextInt(6) + 1;
    }

    // Method to count the occurrences of a specific value in the dice array
    private static int countDice(int[] dice, int value) {
        return (int) Arrays.stream(dice).filter(d -> d == value).count();
    }

    // Method to display the score table
    private static void displayScoreTable(int[][] scoreTable) {
        System.out.println("----------------------------------");
        System.out.println("| Category | Player 1 | Player 2 |");
        System.out.println("|----------|----------|----------|");
        for (int i = 0; i < 7; i++) {
            System.out.printf("|   %d's    |   %3d    |   %3d    |%n", i + 1, scoreTable[i][0], scoreTable[i][1]);
        }
        System.out.println("----------------------------------");
    }
}

//public class DiceGame {

    //public static void main(String[] args) {
     //   System.out.println("Hello World!");
   // }
//}

    
 