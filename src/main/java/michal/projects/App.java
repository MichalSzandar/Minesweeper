package michal.projects;

import javafx.application.Application;
import javafx.stage.Stage;
import michal.projects.gui.GameStartup;
import michal.projects.gui.Images;
import michal.projects.gui.StringToImageConverter;


/**
 * JavaFX App.
 */
public class App extends Application {

    @SuppressWarnings("exports")
    @Override
    public final void start(final Stage primaryStage) {
        // Main setup window
        Images.configureImages();
        StringToImageConverter.getInstance();
        new GameStartup(primaryStage);
    }

    /**
     * main method to start the app.
     * @param args
     */
    public static void main(final String[] args) {
        launch();
    }
}
