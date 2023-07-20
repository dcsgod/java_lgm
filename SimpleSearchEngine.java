import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimpleSearchEngine extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private List<String> documents;

    public SimpleSearchEngine() {
        setTitle("Simple Search Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 300);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.addActionListener(new SearchButtonListener());

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setEditable(false);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        documents = new ArrayList<>();
        documents.add("Java is a programming language.");
        documents.add("Python is easy to learn.");
        documents.add("C++ is widely used in competitive programming.");
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = searchField.getText().toLowerCase();
            List<String> results = searchDocuments(query);
            displaySearchResults(results);
        }

        private List<String> searchDocuments(String query) {
            List<String> results = new ArrayList<>();
            for (String doc : documents) {
                if (doc.toLowerCase().contains(query)) {
                    results.add(doc);
                }
            }
            return results;
        }

        private void displaySearchResults(List<String> results) {
            if (results.isEmpty()) {
                resultArea.setText("No results found.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (String result : results) {
                    sb.append(result).append("\n");
                }
                resultArea.setText(sb.toString());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleSearchEngine().setVisible(true);
            }
        });
    }
}
