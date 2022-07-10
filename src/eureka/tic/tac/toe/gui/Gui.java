package eureka.tic.tac.toe.gui;

import eureka.tic.tac.toe.utils.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Gui extends JFrame {
    private final Font font = new Font("Bryndan Write", Font.PLAIN, 150);
    public JMenuBar jMenuBar = new JMenuBar();

    public JButton playerWins = new Button().transparent().get();
    public JButton botWins = new Button().transparent().get();

    public List<JButton> buttons = new ArrayList<>();
    public boolean ready = false;

    private final int[] wins = new int[]{0, 0};

    public void start() {
        playerWins.setText("Player wins: 0");
        botWins.setText("Bot wins: 0");

        new Timer(200, action -> {
            if (!"None".equals(getWinner())) {
                String winner = getWinner().equals("X") ? "Player wins!" : (getWinner().equals("O") ? "Bot wins!" : "Draw!");
                new Thread(() -> JOptionPane.showMessageDialog(null, winner)).start();

                switch (winner) {
                    case "Player wins!" -> playerWins.setText("Player wins: " + ++wins[0]);
                    case "Bot wins!" -> botWins.setText("Bot wins: " + ++wins[1]);
                }

                buttons.forEach(button -> button.setText(""));
            }
        }).start();

        this.setTitle("TicTacToe");
        this.setBounds(100, 50, 600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);

        for (int i = 0; i < 9; i++) {
            JButton button = new Button().panel().get();

            buttons.add(button);
        }

        for (JButton button : buttons) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!"None".equals(getWinner()) || ready || (Objects.equals(button.getText(), "O") || Objects.equals(button.getText(), "X")))
                        return;
                    button.setText("X");
                    button.setFont(font);
                    ready = true;

                    new Thread(() -> {
                        try {
                            Thread.sleep(700);
                            List<JButton> empty = buttons.stream().filter(button -> button.getText().isEmpty()).toList();
                            if (empty.isEmpty()) return;

                            int size = empty.size() - 1;
                            JButton button = empty.get(new Random().nextInt(size < 1 ? empty.size() : size));
                            button.setText("O");
                            button.setFont(font);

                            ready = false;
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }).start();
                    super.mousePressed(e);
                }
            });
        }

        for (JButton button : buttons) this.add(button);
        jMenuBar.add(playerWins);
        jMenuBar.add(Box.createHorizontalGlue());
        jMenuBar.add(botWins);
        this.setJMenuBar(jMenuBar);
        this.setLayout(new GridLayout(3, 3));
        this.setVisible(true);
    }

    private String getWinner() {
        List<List<Integer>> places = List.of(
                // Horizontal
                List.of(1, 2, 3),
                List.of(4, 5, 6),
                List.of(7, 8, 9),

                // Vertical
                List.of(1, 4, 7),
                List.of(2, 5, 8),
                List.of(3, 6, 9),

                // Diagonal
                List.of(1, 5, 9),
                List.of(3, 5, 7)
        );

        for (List<Integer> place : places) {
            if ("None".equals(winner(place.get(0), place.get(1), place.get(2)))) continue;

            return winner(place.get(0), place.get(1), place.get(2));
        }

        if (isEmpty()) return "Draw";
        return "None";
    }

    private String winner(int one, int two, int three) {
        for (String player : List.of("X", "O")) {
            if (Objects.equals(buttons.get(one - 1).getText(), player) && Objects.equals(buttons.get(two - 1).getText(), player) && Objects.equals(buttons.get(three - 1).getText(), player))
                return player;
        }

        return "None";
    }

    private boolean isEmpty() {
        return buttons.stream().filter(button -> button.getText().isEmpty()).toList().isEmpty();
    }
}
