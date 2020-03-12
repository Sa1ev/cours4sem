package sample.WindowController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DataWrapper.DriverWrapper;
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

    @FXML
    TableView<DriverWrapper> driverTable;

    @FXML
    public void initialize() {
        initializeDriverTable();
    }

    void initializeDriverTable(){
        TableColumn<DriverWrapper, String> c1 = new TableColumn("Id");
        TableColumn<DriverWrapper, String> c2 = new TableColumn("Name");
        TableColumn<DriverWrapper, String> c3 = new TableColumn("Phone Number");
        TableColumn<DriverWrapper, String> c4 = new TableColumn("Password");
        TableColumn<DriverWrapper, String> c5 = new TableColumn("Vehicle Id");
        TableColumn<DriverWrapper, String> c6 = new TableColumn("Licence Id");
        TableColumn<DriverWrapper, String> c7 = new TableColumn("AvgDriveTime");
        TableColumn<DriverWrapper, String> c8 = new TableColumn("AvgDriveDistance");
        c1.setCellValueFactory(new PropertyValueFactory("id"));
        c2.setCellValueFactory(new PropertyValueFactory("Name"));
        c3.setCellValueFactory(new PropertyValueFactory("PhoneNumber"));
        c4.setCellValueFactory(new PropertyValueFactory("Password"));
        c5.setCellValueFactory(new PropertyValueFactory("VehicleId"));
        c6.setCellValueFactory(new PropertyValueFactory("LicenceId"));
        c7.setCellValueFactory(new PropertyValueFactory("AvgDriveTime"));
        c8.setCellValueFactory(new PropertyValueFactory("AvgDriveDistance"));
        driverTable.getColumns().add(c1);
        driverTable.getColumns().add(c2);
        driverTable.getColumns().add(c3);
        driverTable.getColumns().add(c4);
        driverTable.getColumns().add(c5);
        driverTable.getColumns().add(c6);
        driverTable.getColumns().add(c7);
        driverTable.getColumns().add(c8);
    }
}
