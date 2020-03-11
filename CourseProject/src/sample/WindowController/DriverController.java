package sample.WindowController;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import sample.ScreenController;

public class DriverController {

    @FXML
    public void changeToAdminClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("admin");
    }
    @FXML
    public void changeToUserClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("user");
    }
    @FXML
    public void changeToGuestClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("guest");
    }
}
