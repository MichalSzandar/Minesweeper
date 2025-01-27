package michal.projects;

import java.util.ArrayDeque;
import java.util.Random;

public class Board {
    /**represents current game state. */
    private GameState gameState;
    /**represents number of rows on the board. */
    private final int rows;
    /**represents number of columns on the board. */
    private final int cols;
    /**represents  number of bombs in the game. */
    private int activeBombs;
    /**represents number of revealed fields. */
    private int revealedFields;
    /**represents the content of the board. */
    private Field[][] fields;
    /**represents.  */
    private final int[][] dirs = {
        {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
    };

    public final int[] getDir(final int i) {
        return dirs[i];
    }

    /**
     * @return current game state
     */
    public final GameState getState() {
        return gameState;
    }

    /**
     * returns field at specific position.
     * @param row - index of row
     * @param col - index of column
     * @return Field object at specific position
     */
    public Field getField(final int row, final int col) {
        return fields[col][row];
    }

    /**
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * creates Board object with given parameters.
     * @param rows       - number of rows on the board
     * @param cols       - number of columns on the board
     * @param numOfBombs - number of bombs to plant on the board
     */
    public Board(final int rows, final int cols, final int numOfBombs) {
        this.rows = rows;
        this.cols = cols;
        activeBombs = numOfBombs;
        revealedFields = 0;
        gameState = GameState.CONTINUES;

        fields = new Field[cols][rows];

        initializeBoard();
        setUpBombs();
    }

    /**
     * initializes all fields on the board.
     */
    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                fields[j][i] = new Field(this);
            }
        }
    }

    /**
     * randomly places bombs on the board.
     */
    private void setUpBombs() {
        Random rand = new Random();
        for (int i = activeBombs; i > 0; i--) {
            int row = rand.nextInt(0, rows);
            int col = rand.nextInt(0, cols);
            while (fields[col][row].isBomb()) {
                row = rand.nextInt(0, rows);
                col = rand.nextInt(0, cols);
            }
            fields[col][row].setBomb();
        }
    }

    /**
     * checks if given position is out of bounds.
     * @param row - index of row of the position
     * @param col - index of column of the position
     * @return true if position is out of bounds, false otherwise
     */
    private boolean checkIfOutOfBounds(final int row, final int col) {
        return row >= rows || col >= cols || row < 0 || col < 0;
    }

    /**
     * reveals the number of bombs on given position
     *  or ends game if there's a bomb.
     * @param row - index of row
     * @param col - index of column
     */
    public void reveal(final int row, final int col) {
        if (!fields[col][row].getState().equals(State.HIDDEN)) {
            // System.err.println("field cannot be revealed
            //because it already is revealed
            // or it's marked");
            return;
        }

        if (fields[col][row].isBomb()) {
            endGame();
            fields[col][row].setText("-");
            System.out.println("you lose!");
            gameState = GameState.LOST;
            return;
        }

        ArrayDeque<int[]> deque = new ArrayDeque<>();
        deque.push(new int[] {col, row});

        while (!deque.isEmpty()) {
            int[] cur = deque.poll();
            int numOfBombs = countBombs(cur[0], cur[1]);

            if (fields[cur[0]][cur[1]].getState().equals(State.HIDDEN)) {
                fields[cur[0]][cur[1]].setText(Integer.toString(numOfBombs));
                fields[cur[0]][cur[1]].setState(State.REVEALED);
                revealedFields++;
            }

            if (numOfBombs > 0) {
                continue;
            }

            for (int[] dir : dirs) {
                int nextCol = cur[0] + dir[0];
                int nextRow = cur[1] + dir[1];

                if (checkIfOutOfBounds(nextCol, nextRow)) {
                    continue;
                }

                if (fields[nextCol][nextRow].getState().equals(State.HIDDEN)) {
                    deque.push(new int[] {nextCol, nextRow});
                }
            }

        }

        fields[col][row].setState(State.REVEALED);

        if (revealedFields == rows * cols - activeBombs) {
            endGame();
            gameState = GameState.WON;
        }
    }

    /**
     * reveals neighbours of field on given position
     * if player marked as many fields
     * around it as there are bombs around.
     * @param row - index of row
     * @param col - index of column
     */
    public void revealNearby(final int row, final int col) {
        if (countBombs(col, row) != countMarked(row, col)) {
            return;
        }

        for (int[] dir : dirs) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];
            if (checkIfOutOfBounds(nextRow, nextCol)) {
                continue;
            }
            reveal(nextRow, nextCol);
        }
    }

    /**
     * counts how many fields are marked around field on given position.
     * @param row - index of row
     * @param col - index of column
     * @return number of marked fields around position
     */
    private int countMarked(final int row, final int col) {
        int res = 0;

        for (int[] dir : dirs) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];

            if (checkIfOutOfBounds(nextRow, nextCol)) {
                continue;
            }

            if (fields[nextCol][nextRow].getState().equals(State.MARKED)) {
                res++;
            }
        }
        return res;
    }

    /**
     * counts bombs around given position.
     * @param row - index of row
     * @param col - index of column
     * @return number of bombs around given position
     */
    private int countBombs(final int col, final int row) {
        int res = 0;

        for (int[] dir : dirs) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];

            if (checkIfOutOfBounds(nextRow, nextCol)) {
                continue;
            }

            if (fields[nextCol][nextRow].isBomb()) {
                res++;
            }
        }
        return res;
    }

    /**
     * displays all bombs.
     * @see Field
     */
    private void endGame() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (fields[j][i].isBomb()) {
                    fields[j][i].setText("*");
                }
            }
        }
    }
}
