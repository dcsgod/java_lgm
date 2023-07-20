import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
public class CurrencyConverterGUI extends JFrame {
    private JTextField amountField;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JButton convertButton;
    private JLabel resultLabel;

    //EXCHANGE RATES IN JULY 2023
    private static final double USD_TO_RS = 80;
    private static final double USD_TO_EUR = 0.85;
    private static final double USD_TO_GBP = 0.73;
    private static final double USD_TO_JPY = 110.17;
    

    public CurrencyConverterGUI() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        amountField = new JTextField(10);
        fromCurrencyComboBox = new JComboBox<>(new String[]{"RS","USD", "EUR", "GBP", "JPY"});
        toCurrencyComboBox = new JComboBox<>(new String[]{"RS","USD", "EUR", "GBP", "JPY"});
        convertButton = new JButton("Convert");
        resultLabel = new JLabel();

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        add(new JLabel("Amount:"));
        add(amountField);
        add(new JLabel("From:"));
        add(fromCurrencyComboBox);
        add(new JLabel("To:"));
        add(toCurrencyComboBox);
        add(convertButton);
        add(resultLabel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String fromCurrency = fromCurrencyComboBox.getSelectedItem().toString();
            String toCurrency = toCurrencyComboBox.getSelectedItem().toString();
            double result = 0.0;

            // Convert to USD as a common intermediate currency
            switch (fromCurrency) {
                case "RS":
                    result=amount / USD_TO_RS;
                    break;
                case "USD":
                    result = amount;
                    break;
                case "EUR":
                    result = amount / USD_TO_EUR;
                    break;
                case "GBP":
                    result = amount / USD_TO_GBP;
                    break;
                case "JPY":
                    result = amount / USD_TO_JPY;
                    break;
            }

            // Convert from USD to the desired currency
            switch (toCurrency) {
                 case "RS":
                    result *= USD_TO_RS;
                    break;
                case "USD":
                    break;
                case "EUR":
                    result *= USD_TO_EUR;
                    break;
                case "GBP":
                    result *= USD_TO_GBP;
                    break;
                case "JPY":
                    result *= USD_TO_JPY;
                    break;
            }

            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            resultLabel.setText(decimalFormat.format(result) + " " + toCurrency);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CurrencyConverterGUI();
            }
        });
    }
}
