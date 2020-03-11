package sample.WindowController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.ScreenController;

public class GuestController {

    @FXML
    public void changeToAdminClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("admin");
    }
    @FXML
    public void changeToDriverClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("driver");
    }
    @FXML
    public void changeToUserClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("user");
    }
}
