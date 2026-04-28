package calculator.gui;

import javax.swing.*;
import java.awt.*;

public class MatrixPanel extends JPanel {

    // Spinners for matrix size
    private JSpinner rowA, colA, rowB, colB;

    // Matrix panels
    private JPanel matrixAPanel, matrixBPanel, resultPanel;

    // Matrix fields
    private JTextField[][] matrixA;
    private JTextField[][] matrixB;

    public MatrixPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(createMatrixASection());
        topPanel.add(createMatrixBSection());

        add(topPanel, BorderLayout.NORTH);
        add(createOperationPanel(), BorderLayout.CENTER);
    }

    /* =======================
       MATRIX A SECTION
       ======================= */
    private JPanel createMatrixASection() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Matrix A"));

        rowA = new JSpinner(new SpinnerNumberModel(2, 1, 6, 1));
        colA = new JSpinner(new SpinnerNumberModel(2, 1, 6, 1));

        panel.add(createSizePanel(rowA, colA, true), BorderLayout.NORTH);

        matrixAPanel = new JPanel();
        panel.add(matrixAPanel, BorderLayout.CENTER);

        updateMatrixA();
        return panel;
    }

    /* =======================
       MATRIX B SECTION
       ======================= */
    private JPanel createMatrixBSection() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Matrix B"));

        rowB = new JSpinner(new SpinnerNumberModel(2, 1, 6, 1));
        colB = new JSpinner(new SpinnerNumberModel(2, 1, 6, 1));

        panel.add(createSizePanel(rowB, colB, false), BorderLayout.NORTH);

        matrixBPanel = new JPanel();
        panel.add(matrixBPanel, BorderLayout.CENTER);

        updateMatrixB();
        return panel;
    }

    /* =======================
       SIZE PANEL
       ======================= */
    private JPanel createSizePanel(JSpinner row, JSpinner col, boolean isA) {
        JPanel p = new JPanel();
        p.add(new JLabel("Row"));
        p.add(row);
        p.add(new JLabel("Column"));
        p.add(col);

        row.addChangeListener(e -> {
            if (isA) updateMatrixA();
            else updateMatrixB();
        });

        col.addChangeListener(e -> {
            if (isA) updateMatrixA();
            else updateMatrixB();
        });

        return p;
    }

    /* =======================
       OPERATION PANEL
       ======================= */
    private JPanel createOperationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel();
        JButton add = new JButton("A + B");
        JButton sub = new JButton("A - B");
        JButton mul = new JButton("A × B");

        buttonPanel.add(add);
        buttonPanel.add(sub);
        buttonPanel.add(mul);

        resultPanel = new JPanel();
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));

        add.addActionListener(e -> calculateAdd());
        sub.addActionListener(e -> calculateSub());
        mul.addActionListener(e -> calculateMul());

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);

        return panel;
    }

    /* =======================
       MATRIX BUILDERS
       ======================= */
    private void updateMatrixA() {
        int r = (int) rowA.getValue();
        int c = (int) colA.getValue();

        matrixAPanel.removeAll();
        matrixAPanel.setLayout(new GridLayout(r, c, 5, 5));

        matrixA = new JTextField[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                matrixA[i][j] = new JTextField(3);
                matrixAPanel.add(matrixA[i][j]);
            }
        }

        revalidate();
        repaint();
    }

    private void updateMatrixB() {
        int r = (int) rowB.getValue();
        int c = (int) colB.getValue();

        matrixBPanel.removeAll();
        matrixBPanel.setLayout(new GridLayout(r, c, 5, 5));

        matrixB = new JTextField[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                matrixB[i][j] = new JTextField(3);
                matrixBPanel.add(matrixB[i][j]);
            }
        }

        revalidate();
        repaint();
    }

    /* =======================
       OPERATIONS
       ======================= */
    private void calculateAdd() {
        if (!sameSize()) {
            showError("Matrices must have same size");
            return;
        }

        int r = matrixA.length;
        int c = matrixA[0].length;
        double[][] result = new double[r][c];

        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                result[i][j] = get(matrixA, i, j) + get(matrixB, i, j);

        showResult(result);
    }

    private void calculateSub() {
        if (!sameSize()) {
            showError("Matrices must have same size");
            return;
        }

        int r = matrixA.length;
        int c = matrixA[0].length;
        double[][] result = new double[r][c];

        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                result[i][j] = get(matrixA, i, j) - get(matrixB, i, j);

        showResult(result);
    }

    private void calculateMul() {
        if (matrixA[0].length != matrixB.length) {
            showError("A column must equal B row");
            return;
        }

        int r = matrixA.length;
        int c = matrixB[0].length;
        int n = matrixB.length;

        double[][] result = new double[r][c];

        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                for (int k = 0; k < n; k++)
                    result[i][j] += get(matrixA, i, k) * get(matrixB, k, j);

        showResult(result);
    }

    /* =======================
       HELPERS
       ======================= */
    private boolean sameSize() {
        return matrixA.length == matrixB.length &&
                matrixA[0].length == matrixB[0].length;
    }

    private double get(JTextField[][] m, int i, int j) {
        return Double.parseDouble(m[i][j].getText());
    }

    private void showResult(double[][] data) {
        resultPanel.removeAll();
        resultPanel.setLayout(new GridLayout(data.length, data[0].length, 5, 5));

        for (double[] row : data)
            for (double v : row)
                resultPanel.add(new JLabel(String.valueOf(v), JLabel.CENTER));

        revalidate();
        repaint();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
