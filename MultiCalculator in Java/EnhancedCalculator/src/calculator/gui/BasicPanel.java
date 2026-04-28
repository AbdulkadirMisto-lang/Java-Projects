package calculator.gui;

import calculator.core.CalculatorEngine;
import javax.swing.*;
import java.awt.*;

public class BasicPanel extends JPanel {

    private JTextField display;
    private CalculatorEngine engine = new CalculatorEngine();

    public BasicPanel(JTextField display) {
        this.display = display;

        setLayout(new GridLayout(6, 4, 8, 8));
        setBackground(Color.WHITE);

        String[] buttons = {
                "AC","DEL","%","√",
                "7","8","9","/",
                "4","5","6","*",
                "1","2","3","-",
                "0",".","=","+"
        };

        for (String t : buttons) {
            CalculatorButton b = new CalculatorButton(t);
            b.addActionListener(e -> handle(t));
            add(b);
        }
    }

    private void handle(String t) {
        try {
            switch (t) {
                case "AC":
                    display.setText("0");
                    break;
                case "DEL":
                    delete();
                    break;
                case "=":
                    display.setText("" + engine.evaluate(display.getText()));
                    break;
                case "√":
                    double v = Double.parseDouble(display.getText());
                    display.setText("" + Math.sqrt(v));
                    break;
                case "%":
                    display.setText("" + (Double.parseDouble(display.getText()) / 100));
                    break;
                default:
                    append(t);
            }
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private void append(String t) {
        if (display.getText().equals("0"))
            display.setText(t);
        else
            display.setText(display.getText() + t);
    }

    private void delete() {
        String s = display.getText();
        display.setText(s.length() > 1 ? s.substring(0, s.length() - 1) : "0");
    }
}
