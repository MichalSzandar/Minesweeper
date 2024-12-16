package michal.projects;

import javafx.application.Application;
import javafx.stage.Stage;
import michal.projects.gui.GameStartup;
import michal.projects.gui.StringToImageConverter;


/**
 * JavaFX App
 */
public class App extends Application {
    
    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) {
         // Main setup window
        StringToImageConverter.getInstance();
        new GameStartup(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }


}