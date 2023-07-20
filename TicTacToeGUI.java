import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;
    private char[][] board;
    private static final int BOARD_SIZE = 3;
    private static final char EMPTY_CELL = ' ';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        setSize(300, 300);

        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        board = new char[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = PLAYER_X;

        initializeBoard();
        createButtons();
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    private void createButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }

    private void makeMove(int row, int col) {
        board[row][col] = currentPlayer;
        buttons[row][col].setText(Character.toString(currentPlayer));
        buttons[row][col].setEnabled(false);
    }

    private boolean isWinningMove(int row, int col) {
        return checkRow(row) || checkColumn(col) || checkDiagonals(row, col);
    }

    private boolean checkRow(int row) {
        return board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0] != EMPTY_CELL;
    }

    private boolean checkColumn(int col) {
        return board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[0][col] != EMPTY_CELL;
    }

    private boolean checkDiagonals(int row, int col) {
        if (row == col) {
            return board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTY_CELL;
        }
        if (row + col == BOARD_SIZE - 1) {
            return board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != EMPTY_CELL;
        }
        return false;
    }

    private void showWinnerDialog() {
        JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        initializeBoard();
        resetButtons();
    }

    private void showDrawDialog() {
        JOptionPane.showMessageDialog(this, "It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        initializeBoard();
        resetButtons();
    }

    private void resetButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (board[row][col] == EMPTY_CELL) {
                makeMove(row, col);
                if (isWinningMove(row, col)) {
                    showWinnerDialog();
                } else if (isBoardFull()) {
                    showDrawDialog();
                } else {
                    switchPlayer();
                }
            }
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUI().setVisible(true);
            }
        });
    }
}
