package michal.projects;

public class Field{
    private State state;
    private boolean isBomb;
    private String display;

    /**
     * returns string representing current display
     * @return string representing display
     */
    public String getDisplay(){
        return display;
    }

    /**
     * sets text to be displayed on this field
     * @param text - text that is supposed to be displayed
     */
    public void setText(String text){
        display = text;
    }

    /**
     * initializes Field object
     * @param row 
     * @param col
     * @param board
     */
    public Field(int row, int col, Board board){      
        state = State.HIDDEN;
        display = "";
    }

    /**
     * returns current state of field
     * @return current state
     */
    public State getState(){
        return state;
    }

    /**
     * sets current state to given state
     * @param state
     */
    public void setState(State state){
        this.state = state;
    }

    /**
     * checks if there's  bomb on that field
     * @return true if there's a bomb, false otherwise
     */
    public boolean isBomb(){
        return isBomb;
    }

    /**
     * sets a bomb on this field
     */
    public void setBomb(){
        isBomb = true;
    }
}
