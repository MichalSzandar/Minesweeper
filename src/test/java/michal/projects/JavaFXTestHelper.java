package michal.projects;

import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXTestHelper extends Application {
    private static boolean initialized = false;

    public static void initJavaFX() {
        if (!initialized) {
            new Thread(() -> Application.launch(JavaFXTestHelper.class)).start();
            while (!initialized) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) {
        initialized = true;
    }
}
