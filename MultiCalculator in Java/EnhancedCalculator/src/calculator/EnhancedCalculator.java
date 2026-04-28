package calculator;

import javax.swing.SwingUtilities;
import calculator.gui.CalculatorFrame;

public class EnhancedCalculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorFrame frame = new CalculatorFrame();
            frame.setVisible(true);
        });
    }
}
