package main.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SavingConfirmationDialog extends Alert {

    private ButtonType result;

    public SavingConfirmationDialog() {
        super(AlertType.CONFIRMATION);
        setTitle("Paint");
        setHeaderText("Confirmation");
        setContentText("Do you want to save?");
        getButtonTypes().clear();
        getButtonTypes().addAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        result = ButtonType.CANCEL;
    }

    public void showToUser() {
        result = this.showAndWait().get();
    }

    public boolean doUserPressYes() {
        return (result == ButtonType.YES);
    }

    public boolean doUserPressNo() {
        return (result == ButtonType.NO);
    }
}
