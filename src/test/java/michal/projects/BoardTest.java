package michal.projects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardTest {

    @Test
    public void testCountBombs(){
        Board board = new Board(3, 3, 0);
        Field field = board.getField(1, 1);

        for(int i = 0; i<8; i++){
            field.setState(State.HIDDEN);
            board.getField(1 + board.getDir(i)[0], 1 + board.getDir(i)[1]).setBomb();
            board.reveal(1, 1);
            assertEquals(field.getDisplay(), Integer.toString(i+1));
        }
    }
}
