package michal.projects.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class Alerts {
    public static void displayWinMessage(){
        Alert info = new Alert(AlertType.INFORMATION);
        info.setHeight(500);
        info.setWidth(300);
        info.setHeaderText("You won!");
        info.setTitle("Game ended");
        info.setContentText("Congratulations!");
        info.showAndWait();
    }
    public static void displayLoseMessage(){
        Alert info = new Alert(AlertType.INFORMATION);
        info.setHeight(500);
        info.setWidth(300);
        info.setHeaderText("You lost!");
        info.setTitle("Game ended");
        info.setContentText("Better luck next time...");
        info.showAndWait();
    }
}
