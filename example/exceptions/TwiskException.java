package twiskIG.exceptions;

import javafx.scene.control.Alert;

public class TwiskException extends Exception {
    public TwiskException(String message) {
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.show();
    }
}
