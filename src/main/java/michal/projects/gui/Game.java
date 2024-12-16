package michal.projects.gui;

import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import michal.projects.Board;
import michal.projects.GameState;


public class Game {
    private Board board;
    private BoardGUI grid;
    private HashMap<String, Integer> difficultyMap;
    private int elapsedSeconds = 0; // Tracks the number of seconds elapsed
    private Label timerLabel; // Label to display the timer
    private Timeline timeline; // Timer update mechanism
    
    /**
     * displays new window with main minesweeper board
     * @param rows - number of rows on the board
     * @param cols - number of columns on the board
     * @param difficulty - defines game difficult
     */
    public Game(int rows, int cols, String difficulty){
        Stage stage = new Stage();          

        difficultyMap = new HashMap<>();
        difficultyMap.put("Easy", rows*cols/10 + 1);
        difficultyMap.put("Medium", rows*cols/5 + 1);
        difficultyMap.put("Hard", rows*cols/3 + 1);

        board = new Board(rows, cols, difficultyMap.get(difficulty));

        grid = new BoardGUI(board);

        timerLabel = new Label("Time: 0s");

        startTimer();


        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(timerLabel, grid);

        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.show();
        //Alerts.displayLoseMessage();
    }

    private void startTimer() {
        // Initialize the Timeline to update every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(board.getState() != GameState.CONTINUES){
                timeline.stop();
                finishGame();
            }
            elapsedSeconds++;
            timerLabel.setText("Time: " + elapsedSeconds + "s");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline
    }

    private void finishGame(){
        if(board.getState() == GameState.WON){
            Platform.runLater(Alerts::displayWinMessage);
        }else{
            Platform.runLater(Alerts::displayLoseMessage);
        }
        grid.disableButtons();   
    }
}
