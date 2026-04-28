package calculator.gui;

import calculator.core.CalculatorEngine;
import calculator.utils.HistoryManager;
import calculator.utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class CalculatorFrame extends JFrame {

    public CalculatorFrame() {
        setTitle("Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextField display = new JTextField("0");
        display.setFont(new Font("Consolas", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        HistoryManager history = new HistoryManager();
        CalculatorEngine engine = new CalculatorEngine();

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Basic", new BasicPanel(display));
        tabs.add("Matrix", new MatrixPanel());
        tabs.add("BMI", new BMIPanel(history));
        
        
        add(tabs, BorderLayout.CENTER);

        setJMenuBar(new CalculatorMenuBar(history, new ThemeManager("dark"), this));
        setLocationRelativeTo(null);
    }
}
