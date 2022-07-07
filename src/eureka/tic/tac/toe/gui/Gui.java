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
    public List<JButton> buttons = new ArrayList<>();
    public boolean ready = false;

    public void start() {
        this.setTitle("TicTacToe");
        this.setBounds(100,50, 600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);

        for (int i = 0; i < 9; i++) {
            JButton button = new Button().transparent().get();

            buttons.add(button);
        }

        for (JButton button : buttons) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (ready || (Objects.equals(button.getText(), "O"))) return;
                    button.setText("X");
                    button.setFont(new Font("Bryndan Write", Font.PLAIN, 150));
                    ready = true;

                    new Thread(() -> {
                        try {
                            Thread.sleep(700);
                            List<JButton> empty = buttons.stream().filter(button -> button.getText().isEmpty()).toList();
                            if (empty.isEmpty()) return;

                            JButton button = empty.get(new Random().nextInt(empty.size() - 1));
                            button.setText("O");
                            button.setFont(new Font("Bryndan Write", Font.PLAIN, 150));

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
        this.setLayout(new GridLayout(3, 3));
        this.setVisible(true);
    }
}
