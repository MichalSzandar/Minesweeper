package michal.projects.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class Alerts {

    private Alerts() {

    }

    /**
     * displays "game ended you won" message.
     * @param time - num of seconds it took player to win
     */
    public static void displayWinMessage(final String time) {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setHeight(500);
        info.setWidth(300);
        info.setHeaderText("You won!");
        info.setTitle("Game ended");
        info.setContentText("Congratulations! Your " + time);
        info.showAndWait();
    }

    /**
     * displays "you lost" message.
     */
    public static void displayLoseMessage() {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setHeight(500);
        info.setWidth(300);
        info.setHeaderText("You lost!");
        info.setTitle("Game ended");
        info.setContentText("Better luck next time...");
        info.showAndWait();
    }
}

