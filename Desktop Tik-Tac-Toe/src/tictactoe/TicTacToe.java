package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class TicTacToe extends JFrame {
    //Каждый ход увеличивается, чтобы определить, кто ходит ! Чётные это "Х", нечётные "О"
    public static int countWhoTurn = 1;
    protected static boolean isGameStart = false; //Требуется для кнопки, которая запускает игру или сбрасывает её
    //Имена игроков, чтобы "Human" или "Robot" ! Так я объясняю кто ходит человек или робот
    public static String player1;
    public static String player2;
    public static JMenuBar jMenuBar;

    public TicTacToe() {
        //Конструктор Создаём главное окно, к которому прикрепим кнопки и т.п.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        StatusGameBar statusGameBar = createStatusGameBar();
        //наше игровое поле
        BattlefieldJPanel battlefieldJPanel = createBattlefield(statusGameBar);

        statusGameBar.setActionListenerForResetJButton(battlefieldJPanel.buttonArrayList, statusGameBar, battlefieldJPanel);

        statusGameBar.setActionListenerForButtonPlayer(statusGameBar.buttonPlayer1);
        statusGameBar.setActionListenerForButtonPlayer(statusGameBar.buttonPlayer2);
//
        jMenuBar = createJMenuBar(statusGameBar, battlefieldJPanel);
        setJMenuBar(jMenuBar);

        setVisible(true);
    }

    public JMenuBar createJMenuBar(StatusGameBar statusGameBar, BattlefieldJPanel battlefieldJPanel) {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Game");
        fileMenu.setName("MenuGame");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem humanHumanItem = new JMenuItem("Human vs Human");
        humanHumanItem.setName("MenuHumanHuman");
        humanHumanItem.addActionListener(event -> {
            statusGameBar.resetGame(statusGameBar, battlefieldJPanel.buttonArrayList);
            statusGameBar.buttonPlayer1.setText("Human");
            statusGameBar.buttonPlayer2.setText("Human");
            statusGameBar.buttonReset.doClick();
        });

        JMenuItem humanRobotItem = new JMenuItem("Human vs Robot");
        humanRobotItem.setName("MenuHumanRobot");
        humanRobotItem.addActionListener(e -> {
            statusGameBar.resetGame(statusGameBar, battlefieldJPanel.buttonArrayList);
            statusGameBar.buttonPlayer1.setText("Human");
            statusGameBar.buttonPlayer2.setText("Robot");
            statusGameBar.buttonReset.doClick();
        });

        JMenuItem robotHumanItem = new JMenuItem("Robot vs Human");
        robotHumanItem.setName("MenuRobotHuman");
        robotHumanItem.addActionListener(e -> {
            statusGameBar.resetGame(statusGameBar, battlefieldJPanel.buttonArrayList);
            statusGameBar.buttonPlayer1.setText("Robot");
            statusGameBar.buttonPlayer2.setText("Human");

            statusGameBar.buttonReset.doClick();
        });

        JMenuItem robotRobotItem = new JMenuItem("Robot vs Robot");
        robotRobotItem.setName("MenuRobotRobot");
        robotRobotItem.addActionListener(e -> {
            statusGameBar.resetGame(statusGameBar, battlefieldJPanel.buttonArrayList);
            statusGameBar.buttonPlayer1.setText("Robot");
            statusGameBar.buttonPlayer2.setText("Robot");
            statusGameBar.buttonReset.doClick();
        });

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.addActionListener(event -> System.exit(0));


        fileMenu.add(humanHumanItem);
        fileMenu.add(humanRobotItem);
        fileMenu.add(robotHumanItem);
        fileMenu.add(robotRobotItem);

        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        jMenuBar.add(fileMenu);

        return jMenuBar;
    }

    public BattlefieldJPanel createBattlefield(StatusGameBar statusGameBar) {
        BattlefieldJPanel battlefieldJPanel = new BattlefieldJPanel();
        battlefieldJPanel.setSize(450, 380);
        battlefieldJPanel.addJButtonCell(9, statusGameBar.gameStatusLabel, battlefieldJPanel, statusGameBar);
        add(battlefieldJPanel, BorderLayout.CENTER);
        return battlefieldJPanel;
    }

    public StatusGameBar createStatusGameBar() {
        StatusGameBar statusGameBar = new StatusGameBar();
        add(statusGameBar, BorderLayout.SOUTH);
        return statusGameBar;
    }

    //Определяет победителя.
    public static boolean defineWinner(ArrayList<FieldButton> buttonArrayList, JLabel label) {
        if (countWhoTurn > 3) {
            boolean xWins = checkStatusGameBattlefield(buttonArrayList, "X");
            boolean oWins = checkStatusGameBattlefield(buttonArrayList, "O");

            if (xWins) {
                label.setText(String.format("The %s Player (X) wins", player1));
                buttonArrayList.forEach(button -> button.setEnabled(false));
                return true;
            } else if (oWins) {
                label.setText(String.format("The %s Player (O) wins", player2));
                buttonArrayList.forEach(button -> button.setEnabled(false));
                return true;
            } else if (countWhoTurn == 10) {
                label.setText("Draw");
                buttonArrayList.forEach(button -> button.setEnabled(false));
                return true;
            }
        }
        return false;
    }


    // считает победителя по всем линиям и осям
    private static boolean checkStatusGameBattlefield(ArrayList<FieldButton> buttonArrayList, String player) {
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

class StatusGameBar extends JPanel {
    //Эти поля видят другие методы, в конструкторе мы их присваиваем
    public JLabel gameStatusLabel;
    JButton buttonReset;
    JButton buttonPlayer1;
    JButton buttonPlayer2;

    public StatusGameBar() {
        setLayout(new BorderLayout());

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        add(rightPanel, BorderLayout.WEST);

        gameStatusLabel = new JLabel("Game is not started");
        gameStatusLabel.setName("LabelStatus");
        rightPanel.add(gameStatusLabel);

        JPanel leftPanel = new JPanel();
        add(leftPanel, BorderLayout.EAST);


        buttonPlayer1 = new JButton("Human");
        buttonPlayer1.setName("ButtonPlayer1");
        buttonPlayer1.setEnabled(true);
        leftPanel.add(buttonPlayer1);

        buttonPlayer2 = new JButton("Human");
        buttonPlayer2.setName("ButtonPlayer2");
        buttonPlayer2.setEnabled(true);
        leftPanel.add(buttonPlayer2);

        buttonReset = new JButton("Start");
        buttonReset.setName("ButtonStartReset");
        leftPanel.add(buttonReset);
    }

    // Устанавливаем реакцию на нажатие кнопки Start и запуск игры
    public void setActionListenerForResetJButton(ArrayList<FieldButton> buttonArrayList,
                                                 StatusGameBar statusGameBar, BattlefieldJPanel battlefieldJPanel) {
        //При запуске старта ! Меняется статус игры, поле разблокируется, а название меняется на ресет
        buttonReset.addActionListener(e -> {
            if (!TicTacToe.isGameStart) {
                buttonReset.setText("Reset");
                TicTacToe.isGameStart = true;
                TicTacToe.countWhoTurn = 1;
                statusGameBar.buttonPlayer1.setEnabled(false);
                statusGameBar.buttonPlayer2.setEnabled(false);
                buttonArrayList.forEach(element -> {
                    element.setText(" ");
                    element.setEnabled(true);
                });
                TicTacToe.player1 = statusGameBar.buttonPlayer1.getText();
                TicTacToe.player2 = statusGameBar.buttonPlayer2.getText();
                statusGameBar.gameStatusLabel.setText(String.format("The turn of %s Player (X)", TicTacToe.player1));
                if (TicTacToe.player1.equals("Robot")) {
                    battlefieldJPanel.playWithAi(battlefieldJPanel, statusGameBar);
                }
                //Второй вариант ! Кнопка блокирует и очищает поля, меняет статус игры, выполняет ресет
            } else {
                resetGame(statusGameBar, buttonArrayList);

            }
        });
    }

    public void resetGame(StatusGameBar statusGameBar, ArrayList<FieldButton> buttonArrayList) {
        buttonReset.setText("Start");
        TicTacToe.isGameStart = false;
        statusGameBar.buttonPlayer1.setEnabled(true);
        statusGameBar.buttonPlayer2.setEnabled(true);
        statusGameBar.gameStatusLabel.setText("Game is not started");
        buttonArrayList.forEach(element -> {
            element.setText(" ");
            element.setEnabled(false);
        });
    }

    //Название кнопок. Код при старте игры выбирает, что там написанно: робот против робота ! Или Человек vs Человек !
    public void setActionListenerForButtonPlayer(JButton buttonPlayer) {
        buttonPlayer.addActionListener(e -> {
            if ("Human".equals(buttonPlayer.getActionCommand())) {
                buttonPlayer.setText("Robot");
            } else {
                buttonPlayer.setText("Human");
            }
        });
    }
}

class FieldButton extends JButton {

    FieldButton(String name, Font font, JLabel label) {
        setText(" ");
        setName(name);
        setFont(font);
        setFocusPainted(false);
    }
}

//Игровое поле
class BattlefieldJPanel extends JPanel {
    public ArrayList<FieldButton> buttonArrayList = new ArrayList<>();
    char startLiteral = 'A';
    int startCountLiteralNumber = 3;

    public BattlefieldJPanel() {
        setLayout(new GridLayout(3, 3));
    }

    //Создаёт ячейки поле боя, и делает реакцию на нажатие этих ячеек (кнопок)
    public void addJButtonCell(int countJButton, JLabel label, BattlefieldJPanel battlefieldJPanel, StatusGameBar statusGameBar) {
        for (int i = 1; i <= countJButton; i++) {
            String textButton = String.format("Button%c%d", startLiteral, startCountLiteralNumber);
            FieldButton button = new FieldButton(textButton, new Font("Arial", Font.BOLD, 40), label);
            if (startLiteral == 'C') {
                startLiteral = 'A';
                startCountLiteralNumber--;
            } else {
                startLiteral++;
            }
            button.setEnabled(false);
            buttonArrayList.add(button);

            button.addActionListener(element -> {
                //Если на кнопках стояла надпись робот и робот, то комп играет сам с собой
                if (TicTacToe.player1.equals("Robot") && TicTacToe.player2.equals("Robot")) {
                    playWithAi(battlefieldJPanel, statusGameBar);
                    //Если первый игрок робот и число нечётное (Крестики всегда нечётные)
                } else if (TicTacToe.player1.equals("Robot") && TicTacToe.countWhoTurn % 2 != 0) {
                    playWithAi(battlefieldJPanel, statusGameBar);
                } else if (TicTacToe.player2.equals("Robot") && TicTacToe.countWhoTurn % 2 == 0) {
                    playWithAi(battlefieldJPanel, statusGameBar);
                } else {
                    //Если играют люди
                    String whoNextTurn = TicTacToe.countWhoTurn % 2 == 0 ? "X" : "O";
                    String whoCurrentTurn = TicTacToe.countWhoTurn % 2 == 0 ? "O" : "X";
                    String whoPlayer = TicTacToe.countWhoTurn % 2 == 0 ? TicTacToe.player2 : TicTacToe.player1;
                    statusGameBar.gameStatusLabel.setText(String.format("The turn of %s Player (%s)", whoPlayer, whoNextTurn));
                    button.setText(whoCurrentTurn);
                    button.setEnabled(false);

                    TicTacToe.countWhoTurn++;
                    //Если один из игроков робот
                    if (!TicTacToe.defineWinner(buttonArrayList, label)
                            && ((TicTacToe.player1.equals("Robot")
                            || TicTacToe.player2.equals("Robot")))) {
                        playWithAi(battlefieldJPanel, statusGameBar);
                    }
                }
            });
            add(button);
        }
    }

    //Местный AI. Рандомно перебирает ячейкам, пока не найдёт пустую, чтобы совершить ход
    public void playWithAi(BattlefieldJPanel battlefieldJPanel, StatusGameBar statusGameBar) {
        ArrayList<FieldButton> buttonArrayList = battlefieldJPanel.buttonArrayList;
        int turn = (int) (Math.random() * 9);
        boolean emptyCell = false;

        //разорвать петлю
        int countForEndLoop = 1;
        boolean reservPath = false;


        while (!emptyCell) {
            turn = (int) (Math.random() * 9);
            emptyCell = buttonArrayList.get(turn).getText().equals(" ");
            countForEndLoop++;
            // Если бот после 10 попыток мы просто пробежимя по всему полю, выберем свободную ячейку и активируем.
            if (countForEndLoop > 10) {
                battlefieldJPanel.buttonArrayList.stream()
                        .filter(e -> e.getText().equals(" "))
                        .limit(1)
                        .forEach(x -> {
                            String whoNextTurn = TicTacToe.countWhoTurn % 2 == 0 ? "X" : "O";
                            String whoCurrentTurn = TicTacToe.countWhoTurn % 2 == 0 ? "O" : "X";
                            String whoPlayer = TicTacToe.countWhoTurn % 2 == 0 ? TicTacToe.player2 : TicTacToe.player1;
                            statusGameBar.gameStatusLabel.setText(String.format("The turn of %s Player (%s)", whoPlayer, whoNextTurn));
                            x.setText(whoCurrentTurn);
                            x.setEnabled(false);
                        });

                emptyCell = true;
                reservPath = true;
            }
        }

        //если бот смог выбрать ячейку используя < 10 попыток
        if (!reservPath) {
            String whoNextTurn = TicTacToe.countWhoTurn % 2 == 0 ? "X" : "O";
            String whoCurrentTurn = TicTacToe.countWhoTurn % 2 == 0 ? "O" : "X";
            String whoPlayer = TicTacToe.countWhoTurn % 2 == 0 ? TicTacToe.player2 : TicTacToe.player1;
            statusGameBar.gameStatusLabel.setText(String.format("The turn of %s Player (%s)", whoPlayer, whoNextTurn));

            buttonArrayList.get(turn).setEnabled(false);
            buttonArrayList.get(turn).setText(whoCurrentTurn);
        }

        TicTacToe.countWhoTurn++;

        //Если игра не закончена ! И оба игрока боты ! То мы снова вызовем текущий метод, чтобы боты играли !
        if (!TicTacToe.defineWinner(buttonArrayList, statusGameBar.gameStatusLabel)
                && TicTacToe.player1.equals("Robot")
                && TicTacToe.player2.equals("Robot")) {
            playWithAi(battlefieldJPanel, statusGameBar);
        }
    }
}

