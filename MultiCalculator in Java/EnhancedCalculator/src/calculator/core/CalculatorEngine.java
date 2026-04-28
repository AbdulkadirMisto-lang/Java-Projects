package calculator.core;

import java.util.Stack;

public class CalculatorEngine {

    // Evaluate full mathematical expression
    public double evaluate(String expression) {
        return evaluatePostfix(infixToPostfix(expression));
    }

    /* ======================
       INFIX → POSTFIX
       ====================== */
    private String infixToPostfix(String exp) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            // If number or dot
            if (Character.isDigit(c) || c == '.') {
                output.append(c);
            }
            // Operator
            else if (isOperator(c)) {
                output.append(' ');
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty())
            output.append(' ').append(stack.pop());

        return output.toString();
    }

    /* ======================
       POSTFIX EVALUATION
       ====================== */
    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();

                switch (token.charAt(0)) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        return stack.pop();
    }

    /* ======================
       HELPERS
       ====================== */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char c) {
        if (c == '+' || c == '-') return 1;
        if (c == '*' || c == '/') return 2;
        return -1;
    }

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
