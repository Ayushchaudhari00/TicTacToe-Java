import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel borderPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;

    public TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic Tac Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        borderPanel.setLayout(new GridLayout(3, 3));
        borderPanel.setBackground(Color.DARK_GRAY);
        frame.add(borderPanel);

        initializeBoard();
    }

    void initializeBoard() {
        borderPanel.removeAll();

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                borderPanel.add(tile);

                tile.setBackground(Color.DARK_GRAY);
                tile.setForeground(Color.WHITE);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.setText("");

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;

                        JButton clickedTile = (JButton) e.getSource();
                        if (clickedTile.getText().equals("")) {
                            clickedTile.setText(currentPlayer);
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }

        frame.revalidate();
        frame.repaint();
    }

    void checkWinner() {
        // Rows
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals("")) continue;

            if (board[r][0].getText().equals(board[r][1].getText()) &&
                    board[r][1].getText().equals(board[r][2].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                textLabel.setText(currentPlayer + " is the winner!");
                showRestartDialog(currentPlayer + " wins! Play again?");
                return;
            }
        }


        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals("")) continue;

            if (board[0][c].getText().equals(board[1][c].getText()) &&
                    board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                textLabel.setText(currentPlayer + " is the winner!");
                showRestartDialog(currentPlayer + " wins! Play again?");
                return;
            }
        }


        if (!board[0][0].getText().equals("") &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            setWinner(board[0][0]);
            setWinner(board[1][1]);
            setWinner(board[2][2]);
            gameOver = true;
            textLabel.setText(currentPlayer + " is the winner!");
            showRestartDialog(currentPlayer + " wins! Play again?");
            return;
        }


        if (!board[0][2].getText().equals("") &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            textLabel.setText(currentPlayer + " is the winner!");
            showRestartDialog(currentPlayer + " wins! Play again?");
            return;
        }


        boolean boardFull = true;
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c].getText().equals(""))
                    boardFull = false;

        if (boardFull) {
            gameOver = true;
            textLabel.setText("It's a draw!");
            setDrawColors();
            showRestartDialog("It's a draw! Play again?");
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.GREEN);
        tile.setBackground(Color.GRAY);
    }

    void setDrawColors() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setBackground(Color.GRAY);
                board[r][c].setForeground(Color.GREEN);
            }
        }
    }

    void showRestartDialog(String message) {
        int choice = JOptionPane.showConfirmDialog(frame, message, "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0); // close game
        }
    }

    void restartGame() {
        gameOver = false;
        currentPlayer = playerX;
        textLabel.setText("Tic Tac Toe");
        initializeBoard();
    }
}
