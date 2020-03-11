package sample.WindowController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.ScreenController;

public class UserController {

    @FXML
    public void changeToAdminClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("admin");
    }
    @FXML
    public void changeToDriverClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("driver");
    }
    @FXML
    public void changeToGuestClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("guest");
    }
}
