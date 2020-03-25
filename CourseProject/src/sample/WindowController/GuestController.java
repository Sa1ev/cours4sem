package sample.WindowController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;
import sample.DataWrapper.DriverWrapper;
import sample.DataWrapper.OrderWrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuestController {
    ExecutorService exec = Executors.newFixedThreadPool(1);
    ObservableList<DriverWrapper> driverTableItems= null;
    @FXML
    public void changeToAdminClick(ActionEvent actionEvent) {
        Account.authorization("Admin", null, null);
        ScreenController.getINSTANCE().activate("admin");
    }
    @FXML
    public void changeToUserClick(ActionEvent actionEvent) {
        String winResult =  WindowsCreator.createAuthorizationWindow();
        if (winResult != null){
            String[] value =winResult.split(Global.splitSymbol);
                if (Account.authorization("User", value[0], value[1])){
                ScreenController.getINSTANCE().activate("user");
            }
        }
    }
    @FXML
    public void changeToDriverClick(ActionEvent actionEvent) {
        String winResult = WindowsCreator.createAuthorizationWindow();
        if (winResult != null){
        String[] value = winResult.split(Global.splitSymbol);
            if (Account.authorization("Driver", value[0], value[1])){
                ScreenController.getINSTANCE().activate("driver");
            }
        }
    }
    @FXML
    TextField searchTextBox;
    @FXML
    TableView<DriverWrapper> driverTable;
    @FXML
    TabPane tabPane;
    @FXML
    public void initialize() {
        initializeDriverTable();
        new UIPreloadThread( this).start();
        searchTextBox.textProperty().addListener(
                (observable, oldValue, newValue) -> filterTextBox(newValue));
    }
    void filterTextBox(String value){
        ObservableList filtered = FXCollections.observableArrayList();
        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                if (driverTableItems == null) {
                    driverTableItems = driverTable.getItems();
                }
                for (int i = 0; i < driverTableItems.size(); i++) {
                    DriverWrapper item = driverTableItems.get(i);
                    if (item.isMatching(value)) {
                        filtered.add(item);
                    }
                }
                driverTable.setItems(filtered);
                break;
        }
    }
    void initializeDriverTable(){
        TableColumn<DriverWrapper, String> c1 = new TableColumn("Id");
        TableColumn<DriverWrapper, String> c2 = new TableColumn("Name");
        TableColumn<DriverWrapper, String> c3 = new TableColumn("Phone Number");
        TableColumn<DriverWrapper, String> c5 = new TableColumn("Vehicle Id");
        TableColumn<DriverWrapper, String> c6 = new TableColumn("Licence Id");
        c1.setCellValueFactory(new PropertyValueFactory("id"));
        c2.setCellValueFactory(new PropertyValueFactory("Name"));
        c3.setCellValueFactory(new PropertyValueFactory("PhoneNumber"));
        c5.setCellValueFactory(new PropertyValueFactory("VehicleId"));
        c6.setCellValueFactory(new PropertyValueFactory("LicenceId"));
        driverTable.getColumns().add(c1);
        driverTable.getColumns().add(c2);
        driverTable.getColumns().add(c3);
        driverTable.getColumns().add(c5);
        driverTable.getColumns().add(c6);
    }
    public void updateTable(int index){
        switch (index){
            case 0:
                exec.submit(new UIUpdateThread(driverTable ,new ClientThread(1301, "Driver"), "DriverWrapper"));
                driverTableItems= null;
                break;

        }
    }

    public void updateButtonClick(ActionEvent actionEvent) {
        new UIPreloadThread(this).start();
    }
}
