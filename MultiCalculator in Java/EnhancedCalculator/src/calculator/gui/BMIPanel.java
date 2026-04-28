package calculator.gui;

import calculator.utils.HistoryManager;

import javax.swing.*;
import java.awt.*;

public class BMIPanel extends JPanel {

    // Input fields
    private JTextField weightField;
    private JTextField heightField;
    private JTextField ageField;

    // Result labels
    private JLabel bmiValueLabel;
    private JLabel infoLabel;

    // History manager reference
    private HistoryManager historyManager;

    public BMIPanel(HistoryManager historyManager) {
        this.historyManager = historyManager;

        // Main layout
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        /* =======================
           INPUT PANEL (TOP)
           ======================= */
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("Weight (kg):"));
        weightField = new JTextField();
        inputPanel.add(weightField);

        inputPanel.add(new JLabel("Height (cm or m):"));
        heightField = new JTextField();
        inputPanel.add(heightField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        JButton calculateButton = new JButton("Calculate BMI");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        /* =======================
           RESULT PANEL (CENTER)
           ======================= */
        JPanel resultPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Big BMI value
        bmiValueLabel = new JLabel("--", JLabel.CENTER);
        bmiValueLabel.setFont(new Font("Consolas", Font.BOLD, 48));

        // Status and ideal range info
        infoLabel = new JLabel("", JLabel.CENTER);
        infoLabel.setFont(new Font("Consolas", Font.PLAIN, 16));

        resultPanel.add(bmiValueLabel);
        resultPanel.add(infoLabel);

        add(resultPanel, BorderLayout.CENTER);

        // Button action
        calculateButton.addActionListener(e -> calculateBMI());
    }

    /**
     * Calculates BMI and updates UI
     */
    private void calculateBMI() {
        try {
            double weight = Double.parseDouble(weightField.getText());
            double heightInput = Double.parseDouble(heightField.getText());
            int age = Integer.parseInt(ageField.getText());

            // Detect height unit automatically (cm or meter)
            double heightMeters = (heightInput < 3)
                    ? heightInput
                    : heightInput / 100.0;

            // BMI formula
            double bmi = weight / (heightMeters * heightMeters);

            // Format BMI to 1 decimal and use comma
            String bmiText = String.format("%.1f", bmi).replace('.', ',');

            String status = getBMIStatus(bmi);
            Color color = getColorByStatus(status);
            String idealRange = getIdealRangeByAge(age);

            // Update UI
            bmiValueLabel.setText(bmiText);
            bmiValueLabel.setForeground(color);

            infoLabel.setText(
                    "Status: " + status + " | Ideal BMI: " + idealRange
            );

            // Save to history
            historyManager.addEntry(
                    "BMI: " + bmiText + " (" + status + ")"
            );

        } catch (Exception e) {
            bmiValueLabel.setText("Error");
            bmiValueLabel.setForeground(Color.RED);
            infoLabel.setText("Invalid input");
        }
    }

    /**
     * Returns BMI category
     */
    private String getBMIStatus(double bmi) {
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Healthy";
        else if (bmi < 30) return "Overweight";
        else if (bmi < 40) return "Obese";
        else return "Extremely Obese";
    }

    /**
     * Returns color based on BMI category
     */
    private Color getColorByStatus(String status) {
        switch (status) {
            case "Healthy":
                return new Color(0, 150, 0);      // Green
            case "Overweight":
                return new Color(255, 165, 0);    // Orange
            case "Underweight":
                return Color.BLUE;
            default:
                return Color.RED;                 // Obese / Extremely Obese
        }
    }

    /**
     * Returns ideal BMI range based on age
     */
    private String getIdealRangeByAge(int age) {
        if (age >= 19 && age <= 24) return "19 - 24";
        else if (age <= 34) return "20 - 25";
        else if (age <= 44) return "21 - 26";
        else if (age <= 54) return "22 - 27";
        else if (age <= 64) return "23 - 28";
        else return "24 - 29";
    }
}
