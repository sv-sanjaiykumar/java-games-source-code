import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 700;
    int boardHeight = 800; // Increased height to accommodate the scoreboard and restart button

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JPanel bottomPanel = new JPanel(); // Panel to hold both the scoreboard and the restart button

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    int scoreX = 0;
    int scoreO = 0;

    JLabel scoreLabelX = new JLabel("Player X: 0");
    JLabel scoreLabelO = new JLabel("Player O: 0");
    JButton restartButton = new JButton("Restart");

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.CENTER);

        scorePanel.setLayout(new GridLayout(1, 2));
        scorePanel.add(scoreLabelX);
        scorePanel.add(scoreLabelO);
        scoreLabelX.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabelO.setFont(new Font("Arial", Font.PLAIN, 20));

        // Bottom panel that holds the scoreboard and the restart button
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(scorePanel, BorderLayout.CENTER);
        bottomPanel.add(restartButton, BorderLayout.SOUTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().equals("")) {
                            tile.setText(currentPlayer);
                            turns++;
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

        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.setFocusable(false);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
    }

    void checkWinner() {
        // Horizontal check
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals("")) continue;
            if (board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                updateScore();
                return;
            }
        }

        // Vertical check
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals("")) continue;
            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                updateScore();
                return;
            }
        }

        // Diagonal check
        if (board[0][0].getText().equals(board[1][1].getText()) && 
            board[1][1].getText().equals(board[2][2].getText()) && 
            !board[0][0].getText().equals("")) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            updateScore();
            return;
        }

        // Anti-diagonal check
        if (board[0][2].getText().equals(board[1][1].getText()) && 
            board[1][1].getText().equals(board[2][0].getText()) && 
            !board[0][2].getText().equals("")) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            updateScore();
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }

    void updateScore() {
        if (currentPlayer.equals(playerX)) {
            scoreX++;
            scoreLabelX.setText("Player X: " + scoreX);
        } else {
            scoreO++;
            scoreLabelO.setText("Player O: " + scoreO);
        }
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
        currentPlayer = playerX;
        gameOver = false;
        turns = 0;
        textLabel.setText("Tic-Tac-Toe");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
