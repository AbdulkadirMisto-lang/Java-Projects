package calculator.gui;

import calculator.utils.HistoryManager;
import calculator.utils.ThemeManager;

import javax.swing.*;

public class CalculatorMenuBar extends JMenuBar {

    public CalculatorMenuBar(HistoryManager h, ThemeManager t, JFrame f) {
        JMenu menu = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        menu.add(exit);
        add(menu);
    }
}
