package michal.projects.gui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import michal.projects.Board;
import michal.projects.Field;
import michal.projects.State;

public class FieldButton extends Button{
    private BoardGUI grid;
    private Image flag;
    private Image defaultDisplay;
    private ImageView view;
    private Field field;

    /**
     * returns Field object that is in the same row and column as this button
     * @return corresponding Field object
     */
    public Field getCorrespondingField(){
        return field;
    }

    /**
     * initializes FieldButton object 
     * @param row - index of row of corresponding Field object
     * @param col - index of column of corresponding Field object
     * @param board - board containing corresponding Field object
     * @param grid - BoardGUI object containing this button
     */
    public FieldButton(int row, int col, Board board, BoardGUI grid){
        super("");
        //setting up flag and default images
        flag = new Image(getClass().getResource("/images/flag.png").toExternalForm());
        defaultDisplay = new Image(getClass().getResource("/images/minesweeper_default.png").toExternalForm());

        //handling invalid image loading
        if (flag.isError() || defaultDisplay.isError()){
            System.out.println("Error loading flag image");
        }

        this.grid = grid;

        //setting up corresponding Field object
        field = board.getField(row, col);

        //setting up default ImageView
        view = new ImageView();
        view.setFitHeight(50);
        view.setFitWidth(50);
        view.setImage(defaultDisplay);
        setGraphic(view);

        this.setStyle("-fx-font-size: 18px; -fx-min-width: 50px; -fx-min-height: 50px; -fx-max-width: 50px; -fx-max-height: 50px;");
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if(event.getButton().equals(MouseButton.PRIMARY) && field.getState().equals(State.HIDDEN)){
                    board.reveal(row, col);
                } else if(event.getButton().equals(MouseButton.PRIMARY)){
                    board.revealNearby(row, col);
                } else if(event.getButton().equals(MouseButton.SECONDARY)){
                    if(field.getState().equals(State.MARKED)){
                        field.setState(State.HIDDEN);
                        setGraphic(null);
                    } else{
                        view.setImage(flag);
                        setGraphic(view);
                        field.setState(State.MARKED);
                    }       
                }
                refreshGrid();
            }
        });
    }

    /**
     * sets image displayed on this button
     * @param image - image to display
     */
    public void setImage(Image image){
        view.setImage(image);
        setGraphic(view);
    }

    /**
     * refreshes parent grid
     */
    private void refreshGrid(){
        for(Node node : grid.getChildren()){
            FieldButton btn = (FieldButton)node;
            String txt = btn.getCorrespondingField().getDisplay();
            if(txt!=""){
                Image display = StringToImageConverter.getInstance().getImage(txt);
                btn.setImage(display);
            }
        }
    }

}
