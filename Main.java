import java.util.Scanner;
import java.util.Random;

enum Move {
    ROCK,
    PAPER,
    SCISSORS
}

enum Result {
    PLAYER1,
    PLAYER2,
    TIE,
}

class Game {

    private static final Random random = new Random();

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static String stringFromMove(Move move) {
        return switch (move) {
            case ROCK -> "Rock";
            case PAPER -> "Paper";
            case SCISSORS -> "Scissors";
        };
    }

    private static Move moveFromNumber(int num) {
        return switch (num) {
            case 1 -> Move.ROCK;
            case 2 -> Move.PAPER;
            case 3 -> Move.SCISSORS;
            default -> throw new IllegalArgumentException(
                    String.format("Num must be number from 1-3 (got %s)", num));
        };
    }

    private static Move getCPUMove() {
        return moveFromNumber((random.nextInt(3)) + 1);
    }

    private static Move getPlayerMoveOrExit() {
        String input;
        boolean repeat = false;
        do {
            if (repeat) {
                System.out.println("Invalid input. Enter a number from 1 to 3, or type \"exit\".");
            }
            System.out.println("Rock (1), paper (2), or scissors (3)?" +
                            "\t-----\t Type \"exit\" to quit");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                System.out.print("Bye!");
                System.exit(0);
            }
            repeat = true;
        } while (!(isInteger(input) &&
                Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 3));
        return moveFromNumber(Integer.parseInt(input));
    }

    private static Result getResult(Move move1, Move move2) {
        if (move1 == Move.ROCK && move2 == Move.SCISSORS ||
                move1 == Move.PAPER && move2 == Move.ROCK ||
                move1 == Move.SCISSORS && move2 == Move.PAPER) {
            return Result.PLAYER1;
        } else if (move2 == Move.ROCK && move1 == Move.SCISSORS ||
                move2 == Move.PAPER && move1 == Move.ROCK ||
                move2 == Move.SCISSORS && move1 == Move.PAPER) {
            return Result.PLAYER2;
        } else {
            return Result.TIE;
        }
    }

    private void PrintResult(Move playerMove, Move CPUMove) {
        System.out.printf("You chose %s. CPU Chose %s.%n",
                stringFromMove(playerMove), stringFromMove(CPUMove));
        switch (getResult(playerMove, CPUMove)) {
            case PLAYER1:
                System.out.println("You win! Wanna go again?");
                break;
            case PLAYER2:
                System.out.println("You lost :( Try again!");
                break;
            case TIE:
                System.out.println("It's a tie! Play again!");
                break;
        }
    }

    public void gameLoop() {
        Move playerMove = getPlayerMoveOrExit();
        Move CPUMove = getCPUMove();
        PrintResult(playerMove, CPUMove);
    }
}

class Program {

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println("Welcome to rock paper scissors!");
        while (true) {
            game.gameLoop();
        }
    }
}
