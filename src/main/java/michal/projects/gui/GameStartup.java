package michal.projects.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameStartup {
    /**
     * opens new window responsible for selecting parameters for the game
     * @param primaryStage - stage to show new window
     */
    public GameStartup(Stage primaryStage){
        primaryStage.setTitle("Game Setup");

        // Create UI elements
        Label rowsLabel = new Label("Rows:");
        Spinner<Integer> rowsSpinner = new Spinner<>();
        rowsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 10));

        Label columnsLabel = new Label("Columns:");
        Spinner<Integer> columnsSpinner = new Spinner<>();
        columnsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 10));

        Label difficultyLabel = new Label("Difficulty:");
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.setValue("Medium");

        Button startButton = new Button("Start");

        // Layout configuration
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(rowsLabel, 0, 0);
        grid.add(rowsSpinner, 1, 0);
        grid.add(columnsLabel, 0, 1);
        grid.add(columnsSpinner, 1, 1);
        grid.add(difficultyLabel, 0, 2);
        grid.add(difficultyComboBox, 1, 2);
        grid.add(startButton, 0, 3, 2, 1);

        // Event handling
        startButton.setOnAction(e -> {
            int rows = rowsSpinner.getValue();
            int columns = columnsSpinner.getValue();
            String difficulty = difficultyComboBox.getValue();

            // Open new window with game configuration
            new Game(rows, columns, difficulty);
        });

        // Show setup scene
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
