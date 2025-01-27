package michal.projects;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    private Board board;
    private Field mid;

    @Before
    public void setup() {
        board = new Board(3, 3, 0);
        mid = board.getField(1, 1);
    }

    @Test
    public void testCountBombs() {
        for(int i = 0; i<8; i++) {
            mid.setState(State.HIDDEN);
            board.getField(1 + board.getDir(i)[0], 1 + board.getDir(i)[1]).setBomb();
            board.reveal(1, 1);
            assertEquals(mid.getDisplay(), Integer.toString(i+1));
        }
    }

    @Test
    public void testReavealNearbySuccess() {
        mid.setBomb();
        mid.setState(State.MARKED);
        board.reveal(0, 1);
        board.revealNearby(0, 1);

        assertEquals(State.REVEALED, board.getField(0, 0).getState());
        assertEquals(State.REVEALED, board.getField(0, 1).getState());
        assertEquals(State.REVEALED, board.getField(0, 2).getState());
        assertEquals(State.REVEALED, board.getField(1, 0).getState());
        assertEquals(State.REVEALED, board.getField(1, 2).getState());

        assertEquals("1", board.getField(0, 1).getDisplay());
    }

    @Test
    public void testRevealNearbyFailure() {
        mid.setBomb();
        board.getField(1,0).setState(State.MARKED);

        board.reveal(0, 1);
        board.revealNearby(0, 1);

        assertEquals(GameState.LOST, board.getState());
    }

    @Test
    public void testRevealSuccess() {
        board.getField(0, 2).setBomb();
        board.reveal(2, 0);

        assertEquals("1", mid.getDisplay());
        assertEquals(State.REVEALED, mid.getState());
    }
}
