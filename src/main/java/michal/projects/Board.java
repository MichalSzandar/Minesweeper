package michal.projects;

import java.util.ArrayDeque;
import java.util.Random;

public class Board{
    private GameState gameState;
    private final int rows;
    private final int cols;
    private int activeBombs;
    private int revealedFields;
    private Field[][] fields;
    private final int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public int[] getDir(int i){
        return dirs[i];
    }

    public GameState getState(){
        return gameState;
    }

    /**
     * returns field at specific position
     * @param row - index of row
     * @param col - index of column
     * @return Field object at specific position
     */
    public Field getField(int row, int col){
        return fields[row][col];
    }

    /**
     * @return number of rows
     */
    public int getRows(){
        return rows;
    }

    /**
     * @return number of columns
     */
    public int getCols(){
        return cols;
    }

    /**
     * creates Board object with given parameters
     * @param rows - number of rows on the board
     * @param cols - number of columns on the board
     * @param numOfBombs - number of bombs to plant on the board
     */
    public Board(int rows, int cols, int numOfBombs){
        this.rows = rows;
        this.cols = cols;
        activeBombs = numOfBombs;
        revealedFields = 0;
        gameState = GameState.CONTINUES;

        fields = new Field[rows][cols];

        initializeBoard();
        setUpBombs();
    }

    /**
     * initializes all fields on the board
     */
    private void initializeBoard(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                fields[i][j] = new Field(i, j, this);
            }
        }
    }

    /**
     * randomly places bombs on the board
     */
    private void setUpBombs(){
        Random rand = new Random();
        for(int i = activeBombs; i>0; i--){
            int row = rand.nextInt(0, rows);
            int col = rand.nextInt(0, cols);
            while(fields[row][col].isBomb()){
                row = rand.nextInt(0, rows);
                col = rand.nextInt(0, cols);
            }
            fields[row][col].setBomb();
        }
    }

    /**
     * checks if given position is out of bounds 
     * @param row - index of row of the position
     * @param col - index of column of the position
     * @return true if position is out of bounds, false otherwise
     */
    private boolean checkIfOutOfBounds(int row, int col){
        return row >= rows || col >= cols || row < 0 || col < 0;
    }

    /**
     * reveals the number of bombs on given position or ends game if there's a bomb
     * @param row - index of row
     * @param col - index of column
     */
    public void reveal(int row, int col){
        if(!fields[row][col].getState().equals(State.HIDDEN)){
            //System.err.println("field cannot be revealed because it already is revealed or it's marked");
            return;
        }

        if(fields[row][col].isBomb()){
            endGame();
            fields[row][col].setText("-");
            System.out.println("you lose!");
            gameState = GameState.LOST;
            return;
        }
    
        ArrayDeque<int[]> deque = new ArrayDeque<>();
        deque.push(new int[]{row, col});
        
        while(!deque.isEmpty()){
            int[] cur = deque.poll();
            int numOfBombs = countBombs(cur[0], cur[1]);

            if(fields[cur[0]][cur[1]].getState().equals(State.HIDDEN)){
                fields[cur[0]][cur[1]].setText(Integer.toString(numOfBombs));
                fields[cur[0]][cur[1]].setState(State.REVEALED);
                revealedFields++;
            }
            
            if(numOfBombs > 0){
                continue;
            }

            for(int[] dir : dirs){
                int nextRow = cur[0] + dir[0];
                int nextCol = cur[1] + dir[1];

                if(checkIfOutOfBounds(nextRow, nextCol)) continue;

                if(fields[nextRow][nextCol].getState().equals(State.HIDDEN)){
                    deque.push(new int[]{nextRow, nextCol});
                }
            }

        }

        fields[row][col].setState(State.REVEALED);

        if(revealedFields == rows*cols - activeBombs){
            endGame();
            gameState = GameState.WON;
        }
    }

    /**
     * reveals neighbours of field on given position if player marked as many fields around it as there are bombs around
     * @param row - index of row 
     * @param col - index of column
     */
    public void revealNearby(int row, int col){
        if(countBombs(row, col) != countMarked(row, col)){
            return;
        }
        
        for(int[] dir: dirs){
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];
            if(checkIfOutOfBounds(nextRow, nextCol)) continue;
            reveal(nextRow, nextCol);
        }
    }

    /**
     * counts how many fields are marked around field on given position
     * @param row - index of row
     * @param col - index of column
     * @return number of marked fields around position 
     */
    private int countMarked(int row, int col){
        int res = 0;

        for(int[] dir: dirs){
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];

            if(checkIfOutOfBounds(nextRow, nextCol)) continue;

            if(fields[nextRow][nextCol].getState().equals(State.MARKED)){
                res++;
            }
        }
        return res;
    }

    /**
     * counts bombs around given position
     * @param row - index of row
     * @param col - index of column
     * @return number of bombs around given position
     */
    private int countBombs(int row, int col){
        int res = 0;

        for(int[] dir : dirs){
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];

            if(checkIfOutOfBounds(nextRow, nextCol)) continue;

            if(fields[nextRow][nextCol].isBomb()) {
                res++;
            }
        }
        return res;
    }

    /**
     * displays all bombs
     * @see Field
     */
    private void endGame(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(fields[i][j].isBomb()){
                    fields[i][j].setText("*");
                }
            }
        }
    }
}
