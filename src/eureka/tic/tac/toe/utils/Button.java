package eureka.tic.tac.toe.utils;

import javax.swing.*;
import java.awt.*;

public class Button {
    private final JButton jButton;

    public Button() {
        jButton = new JButton();
        jButton.setBackground(Color.LIGHT_GRAY);

        jButton.addChangeListener(event -> {
            ButtonModel model = jButton.getModel();

            jButton.setBackground(Color.LIGHT_GRAY);
            if (model.isPressed()) jButton.setBackground(Color.GRAY);
        });
    }

    public Button panel() {
        jButton.setBorderPainted(true);
        jButton.setFocusPainted(false);
        jButton.setContentAreaFilled(false);
        jButton.setOpaque(true);
        return this;
    }

    public Button transparent() {
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.setFocusable(false);
        return this;
    }

    public JButton get() {
        return jButton;
    }
}
