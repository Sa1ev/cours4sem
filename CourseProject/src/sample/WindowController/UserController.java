package sample.WindowController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;
import sample.DataWrapper.DriverWrapper;
import sample.DataWrapper.OrderWrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserController {
    ExecutorService exec = Executors.newFixedThreadPool(1);
    @FXML
    public void changeToAdminClick(ActionEvent actionEvent) {
        Account.authorization("Admin", null, null);
        ScreenController.getINSTANCE().activate("admin");
    }
    @FXML
    public void changeToDriverClick(ActionEvent actionEvent) {
        String[] value = WindowsCreator.createAuthorizationWindow().split(Global.splitSymbol);
        if (Account.authorization("Driver", value[0], value[1])){
            ScreenController.getINSTANCE().activate("driver");
        }
    }
    @FXML
    public void changeToGuestClick(ActionEvent actionEvent) {
        Account.authorization("Guest", null, null);
        ScreenController.getINSTANCE().activate("guest");
    }

    @FXML
    Button createButton;
    @FXML
    Button rejectButton;
    @FXML
    TableView<DriverWrapper> driverTable;
    @FXML
    TableView<OrderWrapper> orderQueueTable;
    @FXML
    TableView<OrderWrapper> orderHistoryTable;
    @FXML
    TabPane tabPane;
    @FXML
    public void initialize() {
        initializeDriverTable();
        initializeOrderQueueTable();
        initializeOrderHistoryTable();
        createButton.setDisable(true);
        rejectButton.setDisable(true);
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        createButton.setDisable(true);
                        rejectButton.setDisable(true);
                        orderQueueTable.getSelectionModel().clearSelection();
                        driverTable.getSelectionModel().clearSelection();
                        orderHistoryTable.getSelectionModel().clearSelection();
                    }
                }
        );
        driverTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                createButton.setDisable(false);
            }
        });
        orderQueueTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                rejectButton.setDisable(false);
            }
        });


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

    void initializeOrderQueueTable(){

        TableColumn<OrderWrapper, String> c3 = new TableColumn("Driver Id");
        TableColumn<OrderWrapper, String> c4 = new TableColumn("Start Point");
        TableColumn<OrderWrapper, String> c5 = new TableColumn("Finish Point");
        TableColumn<OrderWrapper, String> c6 = new TableColumn("Distance");
        TableColumn<OrderWrapper, String> c7 = new TableColumn("Time");
        c3.setCellValueFactory(new PropertyValueFactory("driverid"));
        c4.setCellValueFactory(new PropertyValueFactory("startPoint"));
        c5.setCellValueFactory(new PropertyValueFactory("finishPoint"));
        c6.setCellValueFactory(new PropertyValueFactory("distance"));
        c7.setCellValueFactory(new PropertyValueFactory("time"));
        orderQueueTable.getColumns().add(c3);
        orderQueueTable.getColumns().add(c4);
        orderQueueTable.getColumns().add(c5);
        orderQueueTable.getColumns().add(c6);
        orderQueueTable.getColumns().add(c7);
    }
    void initializeOrderHistoryTable(){

        TableColumn<OrderWrapper, String> c3 = new TableColumn("Driver Id");
        TableColumn<OrderWrapper, String> c4 = new TableColumn("Start Point");
        TableColumn<OrderWrapper, String> c5 = new TableColumn("Finish Point");
        TableColumn<OrderWrapper, String> c6 = new TableColumn("Distance");
        TableColumn<OrderWrapper, String> c7 = new TableColumn("Time");
        TableColumn<OrderWrapper, String> c8 = new TableColumn("Approved");
        c3.setCellValueFactory(new PropertyValueFactory("driverid"));
        c4.setCellValueFactory(new PropertyValueFactory("startPoint"));
        c5.setCellValueFactory(new PropertyValueFactory("finishPoint"));
        c6.setCellValueFactory(new PropertyValueFactory("distance"));
        c7.setCellValueFactory(new PropertyValueFactory("time"));
        c8.setCellValueFactory(new PropertyValueFactory("approved"));
        orderHistoryTable.getColumns().add(c3);
        orderHistoryTable.getColumns().add(c4);
        orderHistoryTable.getColumns().add(c5);
        orderHistoryTable.getColumns().add(c6);
        orderHistoryTable.getColumns().add(c7);
        orderHistoryTable.getColumns().add(c8);
    }

    public void updateTable(int index){
        switch (index){
            case 0:
                exec.submit(new UIUpdateThread(driverTable ,new ClientThread(1301, "Driver"), "DriverWrapper"));
                break;
            case 1:
                exec.submit(new UIUpdateThread(orderQueueTable , new ClientThread(1321, Integer.toString(Account.id)), "OrderWrapper"));
                break;
            case 2:
                exec.submit(new UIUpdateThread(orderHistoryTable , new ClientThread(1322, Integer.toString(Account.id)), "OrderWrapper"));
        }
    }

    public void createButtonClick(ActionEvent actionEvent) {
        String value = WindowsCreator.createOrderWindow();
        if (value != null){
            new ClientThread(1302, driverTable.getSelectionModel().getSelectedItem().getId()+Global.splitSymbol+
                    Account.id+Global.splitSymbol+
                    value+Global.splitSymbol+getTime(11, value)).start();
            updateTable(0);
        }
    }


    String getTime(int speedMS, String orderValues){
        int distance = new Integer(orderValues.split(Global.splitSymbol)[2]);
        int hours = distance/3600;
        distance-=hours*3600;
        int minutes  = distance/60;
        distance -= minutes*60;
        return String.format("%d:%d:%d", hours, minutes, distance);

    }

    public void rejectButtonClick(ActionEvent actionEvent) {
        new ClientThread(1304, orderQueueTable.getSelectionModel().getSelectedItem().getOrderid()).start();
        updateTable(1);
    }

    public void updateButtonClick(ActionEvent actionEvent) {
        new UIPreloadThread(this).start();
    }
}
