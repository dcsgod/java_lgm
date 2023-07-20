import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificCalculator extends JFrame {
    private JTextField displayField;
    private double currentValue;
    private String currentOperator;
    private boolean isNewInput;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(300, 400);

        displayField = new JTextField();
        displayField.setFont(new Font("Arial", Font.PLAIN, 24));
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);

        JPanel buttonPanel = createButtonPanel();

        add(displayField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        currentValue = 0;
        currentOperator = "";
        isNewInput = true;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sin", "cos", "tan", "√"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        return panel;
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.matches("[0-9.]")) {
                handleDigitInput(command);
            } else if (command.matches("[+\\-*/=]")) {
                handleOperatorInput(command);
            } else if (command.equals("sin") || command.equals("cos") || command.equals("tan")) {
                handleTrigFunction(command);
            } else if (command.equals("√")) {
                handleSqrtFunction();
            }
        }

        private void handleDigitInput(String digit) {
            if (isNewInput) {
                displayField.setText(digit);
                isNewInput = false;
            } else {
                displayField.setText(displayField.getText() + digit);
            }
        }

        private void handleOperatorInput(String operator) {
            if (!isNewInput) {
                calculate();
                isNewInput = true;
            }
            currentOperator = operator;
            currentValue = Double.parseDouble(displayField.getText());
        }

        private void handleTrigFunction(String function) {
            double input = Double.parseDouble(displayField.getText());
            double result = 0.0;

            switch (function) {
                case "sin":
                    result = Math.sin(Math.toRadians(input));
                    break;
                case "cos":
                    result = Math.cos(Math.toRadians(input));
                    break;
                case "tan":
                    result = Math.tan(Math.toRadians(input));
                    break;
            }

            displayField.setText(String.valueOf(result));
            isNewInput = true;
        }

        private void handleSqrtFunction() {
            double input = Double.parseDouble(displayField.getText());
            double result = Math.sqrt(input);
            displayField.setText(String.valueOf(result));
            isNewInput = true;
        }

        private void calculate() {
            double secondValue = Double.parseDouble(displayField.getText());

            switch (currentOperator) {
                case "+":
                    currentValue += secondValue;
                    break;
                case "-":
                    currentValue -= secondValue;
                    break;
                case "*":
                    currentValue *= secondValue;
                    break;
                case "/":
                    currentValue /= secondValue;
                    break;
            }

            displayField.setText(String.valueOf(currentValue));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScientificCalculator().setVisible(true);
            }
        });
    }
}
