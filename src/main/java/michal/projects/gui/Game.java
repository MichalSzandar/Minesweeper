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
    /**Board object on which the game is played. */
    private Board board;
    /**GridPane on which fields are displayed. */
    private BoardGUI grid;
    /**map difficulty with number of bombs. */
    private HashMap<String, Integer> difficultyMap;
    /**Tracks the number of elapsed seconds. */
    private int elapsedSeconds = 0;
    /**Label to display the timer. */
    private Label timerLabel;
    /**Timer update mechanism. */
    private Timeline timeline;

    /**
     * displays new window with main minesweeper board.
     * @param rows - number of rows on the board
     * @param cols - number of columns on the board
     * @param difficulty - defines game difficult
     */
    public Game(final int rows, final int cols, final String difficulty) {
        Stage stage = new Stage();

        difficultyMap = new HashMap<>();
        difficultyMap.put("Easy", rows * cols / 10 + 1);
        difficultyMap.put("Medium", rows * cols / 5 + 1);
        difficultyMap.put("Hard", rows * cols / 3 + 1);

        board = new Board(rows, cols, difficultyMap.get(difficulty));

        grid = new BoardGUI(board);

        timerLabel = new Label("Time: 0s");

        startTimer();


        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(timerLabel, grid);

        Scene scene = new Scene(root, 60 * cols, 60 * rows);
        stage.setScene(scene);
        stage.show();
    }

    private void startTimer() {
        // Initialize the Timeline to update every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (board.getState() != GameState.CONTINUES) {
                timeline.stop();
                finishGame();
            }
            elapsedSeconds++;
            timerLabel.setText("Time: " + elapsedSeconds + "s");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void finishGame() {
        if (board.getState() == GameState.WON) {
            Platform.runLater(() ->
                Alerts.displayWinMessage(timerLabel.getText()));
        } else {
            Platform.runLater(Alerts::displayLoseMessage);
        }
        grid.disableButtons();
    }
}
