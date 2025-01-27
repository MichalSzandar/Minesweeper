package michal.projects.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import michal.projects.Board;

public class BoardGUI extends GridPane {
    /**
     * initializes BoardGUI object.
     * @param board - Board object responsible for logic
     */
    public BoardGUI(final Board board) {
        super();

        this.setAlignment(Pos.CENTER);

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                add(new FieldButton(i, j, board, this), j, i);
            }
        }
    }

    /**
     * method to disable all FieldButton objects.
     * @see FieldButton
     */
    public void disableButtons() {
        for (Node node : getChildren()) {
            FieldButton btn = (FieldButton) node;
            btn.setOnMouseClicked(null);
        }
    }

}
