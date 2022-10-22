package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TicTacToe extends JFrame {
    public static int countWhoTurn = 1;

    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        StatusGameBarPanel statusGameBarPanel = createStatusGameBar();
        FieldJPanel fieldJPanel = createField(statusGameBarPanel);

        statusGameBarPanel.setActionListenerForResetJButton(fieldJPanel.buttonArrayList, statusGameBarPanel);
        setVisible(true);
    }

    public FieldJPanel createField(StatusGameBarPanel statusGameBarPanel) {
        FieldJPanel fieldJPanel = new FieldJPanel();
        fieldJPanel.setSize(450, 380);
        fieldJPanel.addJButton(9, statusGameBarPanel.gameStatusLabel);
        add(fieldJPanel, BorderLayout.CENTER);
        return fieldJPanel;
    }

    public StatusGameBarPanel createStatusGameBar() {
        StatusGameBarPanel statusGameBarPanel = new StatusGameBarPanel();
        add(statusGameBarPanel, BorderLayout.SOUTH);
        return statusGameBarPanel;
    }


    public static void defineWinner(ArrayList<FieldButton> buttonArrayList, JLabel label) {
        if (countWhoTurn > 3) {
            boolean xWins = countWinner(buttonArrayList, "X");
            boolean oWins = countWinner(buttonArrayList, "O");

            if (xWins) {
                label.setText("X wins");
                buttonArrayList.forEach(button -> button.setEnabled(false));
            } else if (oWins) {
                label.setText("O wins");
                buttonArrayList.forEach(button -> button.setEnabled(false));
            } else if (countWhoTurn == 9) {
                label.setText("Draw");
            }
        }
    }

    private static boolean countWinner(ArrayList<FieldButton> buttonArrayList, String player) {
        String cellA3 = buttonArrayList.get(0).getText();
        String cellB3 = buttonArrayList.get(1).getText();
        String cellC3 = buttonArrayList.get(2).getText();

        String cellA2 = buttonArrayList.get(3).getText();
        String cellB2 = buttonArrayList.get(4).getText();
        String cellC2 = buttonArrayList.get(5).getText();

        String cellA1 = buttonArrayList.get(6).getText();
        String cellB1 = buttonArrayList.get(7).getText();
        String cellC1 = buttonArrayList.get(8).getText();

        boolean winnerXLine3 = cellA3.equals(player) && cellB3.equals(player) && cellC3.equals(player);
        boolean winnerXLine2 = cellA2.equals(player) && cellB2.equals(player) && cellC2.equals(player);
        boolean winnerXLine1 = cellA1.equals(player) && cellB1.equals(player) && cellC1.equals(player);

        boolean winnerYLine3 = cellA3.equals(player) && cellA2.equals(player) && cellA1.equals(player);
        boolean winnerYLine2 = cellB3.equals(player) && cellB2.equals(player) && cellB1.equals(player);
        boolean winnerYLine1 = cellC3.equals(player) && cellC2.equals(player) && cellC1.equals(player);

        boolean winnerMainDiagonal = cellA3.equals(player) && cellB2.equals(player) && cellC1.equals(player);
        boolean winnerSecondDiagonal = cellC3.equals(player) && cellB2.equals(player) && cellA1.equals(player);

        boolean resultXLine = winnerXLine3 || winnerXLine2 || winnerXLine1;
        boolean resultYLine = winnerYLine3 || winnerYLine2 || winnerYLine1;
        boolean resultDiagonal = winnerMainDiagonal || winnerSecondDiagonal;

        return resultXLine || resultYLine || resultDiagonal;
    }
}

class StatusGameBarPanel extends JPanel {
    public JLabel gameStatusLabel;
    JButton buttonStartReset;

    public StatusGameBarPanel() {
        setLayout(new BorderLayout());

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        add(rightPanel, BorderLayout.WEST);

        gameStatusLabel = new JLabel("Game is not started");
        gameStatusLabel.setName("LabelStatus");
        rightPanel.add(gameStatusLabel);

        JPanel leftPanel = new JPanel();
        add(leftPanel, BorderLayout.EAST);

        buttonStartReset = new JButton("Reset");
        buttonStartReset.setName("ButtonStartReset");
        leftPanel.add(buttonStartReset);
    }

    public void setActionListenerForResetJButton(ArrayList<FieldButton> buttonArrayList, StatusGameBarPanel statusGameBarPanel) {
        buttonStartReset.addActionListener(e -> {
            buttonArrayList.forEach(element -> {
                element.setText(" ");
                element.setEnabled(true);
            });
            statusGameBarPanel.gameStatusLabel.setText("Game is not started");
            TicTacToe.countWhoTurn = 1;
        });
    }
}

class FieldButton extends JButton {
    //TODO del excess label
    FieldButton(String name, Font font, JLabel label) {
        setText(" ");
        setName(name);
        setFont(font);
        setFocusPainted(false);
    }
}

class FieldJPanel extends JPanel {
    public ArrayList<FieldButton> buttonArrayList = new ArrayList<>();
    char startLiteral = 'A';
    int startCountLiteralNumber = 3;

    public FieldJPanel() {
        setLayout(new GridLayout(3, 3));
    }

    public void addJButton(int countJButton, JLabel label) {
        for (int i = 1; i <= countJButton; i++) {
            String textButton = String.format("Button%c%d", startLiteral, startCountLiteralNumber);
            FieldButton button = new FieldButton(textButton, new Font("Arial", Font.BOLD, 40), label);
            if (startLiteral == 'C') {
                startLiteral = 'A';
                startCountLiteralNumber--;
            } else {
                startLiteral++;
            }
            buttonArrayList.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent element) {
                    button.setText(TicTacToe.countWhoTurn % 2 == 0 ? "O" : "X");
                    label.setText("Game in progress");
                    button.setEnabled(false);
                    TicTacToe.defineWinner(buttonArrayList, label);
                    TicTacToe.countWhoTurn++;
                }
            });
            add(button);
        }
    }
}

