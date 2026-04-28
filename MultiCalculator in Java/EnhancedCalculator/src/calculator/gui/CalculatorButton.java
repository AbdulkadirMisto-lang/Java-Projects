package calculator.gui;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class CalculatorButton extends JButton {

    public CalculatorButton(String text) {
        super(text);
        setFont(new Font("Consolas", Font.BOLD, 14));
        setBackground(Color.DARK_GRAY);
        setForeground(new Color(8, 253, 0));
        setFocusPainted(false);
    }
}
