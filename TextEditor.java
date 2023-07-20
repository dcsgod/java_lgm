import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class TextEditor extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;

    public TextEditor() {
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(textArea);

        fileChooser = new JFileChooser();

        JMenuBar menuBar = createMenuBar();

        JPanel buttonPanel = new JPanel();
        JButton saveAndSubmitButton = new JButton("Save and Submit");
        saveAndSubmitButton.addActionListener(new SaveAndSubmitButtonListener());
        buttonPanel.add(saveAndSubmitButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setJMenuBar(menuBar);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem closeMenuItem = new JMenuItem("Close");
        JMenuItem printMenuItem = new JMenuItem("Print");

        openMenuItem.addActionListener(new OpenMenuItemListener());
        saveMenuItem.addActionListener(new SaveMenuItemListener());
        closeMenuItem.addActionListener(new CloseMenuItemListener());
        printMenuItem.addActionListener(new PrintMenuItemListener());

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(closeMenuItem);
        fileMenu.add(printMenuItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");

        cutMenuItem.addActionListener(new CutMenuItemListener());
        copyMenuItem.addActionListener(new CopyMenuItemListener());
        pasteMenuItem.addActionListener(new PasteMenuItemListener());

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        return menuBar;
    }

    private class OpenMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = fileChooser.showOpenDialog(TextEditor.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (Scanner scanner = new Scanner(file)) {
                    StringBuilder content = new StringBuilder();
                    while (scanner.hasNext()) {
                        content.append(scanner.nextLine()).append("\n");
                    }
                    textArea.setText(content.toString());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TextEditor.this,
                            "Error reading file: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class SaveMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = fileChooser.showSaveDialog(TextEditor.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(file)) {
                    writer.write(textArea.getText());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TextEditor.this,
                            "Error writing to file: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class CloseMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
        }
    }

    private class PrintMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                textArea.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(TextEditor.this,
                        "Error while printing: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class CutMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.cut();
        }
    }

    private class CopyMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.copy();
        }
    }

    private class PasteMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.paste();
        }
    }

    private class SaveAndSubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = fileChooser.showSaveDialog(TextEditor.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(file)) {
                    writer.write(textArea.getText());
                    textArea.setText("");
                    JOptionPane.showMessageDialog(TextEditor.this,
                            "File saved successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TextEditor.this,
                            "Error writing to file: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextEditor().setVisible(true);
            }
        });
    }
}
