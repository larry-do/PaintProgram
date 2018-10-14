package main.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SavingConfirmationDialog extends Alert {

    public SavingConfirmationDialog() {
        super(AlertType.CONFIRMATION);
        setTitle("Paint");
        setHeaderText("Confirmation");
        setContentText("Do you want to save?");
    }

    public boolean showToUser() {
        return this.showAndWait().get() == ButtonType.OK;
    }
}
