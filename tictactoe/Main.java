package tictactoe;

import java.util.Scanner;

public class Main {
    static Scanner SCANNER = new Scanner(System.in);
    static String[][] ARRAY_BOARD = new String[3][3];
    static boolean X_WINS = false;
    static boolean Y_WINS = false;

    public static void main(String[] args) {
        makeMove();
    }

    public static void printBoard() {
        if (ARRAY_BOARD[0][0] == null) {
            createBoard();
        }
        System.out.println("Select the coordinates of the cell from 1 to 3");
        System.out.println("---------");

        for (String[] strings : ARRAY_BOARD) {
            System.out.print("| ");

            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
        chekResult();
    }

    static void createBoard() {
        for (int i = 0, z = 0; i < ARRAY_BOARD.length; i++) {
            for (int y = 0; y < ARRAY_BOARD[i].length; y++, z++) {
                ARRAY_BOARD[i][y] = " ";
            }
        }
    }

    static void chekResult() {
        chekHorizontalWinnerLine();
        chekVerticallyWinnerLine();
        chekMainDiagonallyWinnerLine();
        chekSecondDiagonallyWinnerLine();
    }

    static void chekHorizontalWinnerLine() {
        for (String[] strings : ARRAY_BOARD) {
            StringBuilder winnerLine = new StringBuilder();

            for (String string : strings) {
                winnerLine.append(string);

                switch (winnerLine.toString()) {
                    case "XXX" -> X_WINS = true;
                    case "OOO" -> Y_WINS = true;
                }
            }
        }
    }

    static void chekVerticallyWinnerLine() {
        for (int i = 0; i < ARRAY_BOARD.length; i++) {
            StringBuilder winnerLine = new StringBuilder();

            for (int y = 0; y < ARRAY_BOARD[i].length; y++) {
                winnerLine.append(ARRAY_BOARD[y][i]);

                switch (winnerLine.toString()) {
                    case "XXX" -> X_WINS = true;
                    case "OOO" -> Y_WINS = true;
                }
            }
        }
    }

    static void chekMainDiagonallyWinnerLine() {
        StringBuilder winnerLine = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            winnerLine.append(ARRAY_BOARD[i][i]);

            switch (winnerLine.toString()) {
                case "XXX" -> X_WINS = true;
                case "OOO" -> Y_WINS = true;
            }
        }
    }

    static void chekSecondDiagonallyWinnerLine() {
        StringBuilder winnerLine = new StringBuilder();

        for (int i = 0, y = 2; i < 3; i++, y--) {
            winnerLine.append(ARRAY_BOARD[i][y]);

            switch (winnerLine.toString()) {
                case "XXX" -> X_WINS = true;
                case "OOO" -> Y_WINS = true;
            }
        }
    }

    public static void makeMove() {
        int count = 0;
        printBoard();
        while (true) {
            String userInputX = SCANNER.next();
            String userInputY = SCANNER.next();

            try {
                int x = Integer.parseInt(userInputX);
                int y = Integer.parseInt(userInputY);

                if ((x < 1 || x > 3) || (y < 1 || y > 3)) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                } else if (ARRAY_BOARD[x - 1][y - 1].equals("X") || ARRAY_BOARD[x - 1][y - 1].equals("O")) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                String mark = count % 2 == 0 ? "X" : "O";
                ARRAY_BOARD[x - 1][y - 1] = mark;
                chekResult();
                printBoard();
                count++;

                if (X_WINS || Y_WINS || count == 9) {
                    System.out.println(X_WINS ? "X wins" : Y_WINS ? "O wins" : "Draw");
                    break;
                }
            } catch (Exception e) {
                System.out.println("You should enter numbers by 1 from 3!");
            }
        }
    }
}

