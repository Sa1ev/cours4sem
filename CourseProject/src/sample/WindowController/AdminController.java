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
import sample.DataWrapper.UserWrapper;
import sample.DataWrapper.VehicleWrapper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminController {
    ExecutorService exec = Executors.newFixedThreadPool(1);
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    Button infoButton;
    @FXML
    TabPane tabPane;
    @FXML
    TableView<VehicleWrapper> vehicleTable;
    @FXML
    TableView<DriverWrapper> driverTable;
    @FXML
    TableView<UserWrapper> userTable;
    @FXML
    TableView<OrderWrapper> orderTable;
    @FXML
    Tab vehicleTab;
    @FXML
    Tab driverTab;
    @FXML
    Tab userTab;
    @FXML
    Tab orderTab;
    @FXML
    public void changeToDriverClick(ActionEvent actionEvent) {
        String[] value = WindowsCreator.createAuthorizationWindow().split(Global.splitSymbol);
        if (Account.authorization("Driver", value[0], value[1])){
            ScreenController.getINSTANCE().activate("driver");
        }
    }
    @FXML
    public void changeToUserClick(ActionEvent actionEvent) {
        String[] value = WindowsCreator.createAuthorizationWindow().split(Global.splitSymbol);
        if (Account.authorization("User", value[0], value[1])){
            ScreenController.getINSTANCE().activate("user");
        }
    }
    @FXML
    public void changeToGuestClick(ActionEvent actionEvent) {
        Account.authorization("Guest", null, null);
        ScreenController.getINSTANCE().activate("guest");
    }
    @FXML
    public void button1Click(ActionEvent actionEvent) throws InterruptedException {

    }

    public AdminController(){

    }
    @FXML
    public void initialize() {
        initializeVehicleTable();
        initializeDriverTable();
        initializeUserTable();
        initializeOrderTable();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        infoButton.setDisable(true);
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        editButton.setDisable(true);
                        deleteButton.setDisable(true);
                        infoButton.setDisable(true);
                        userTable.getSelectionModel().clearSelection();
                        driverTable.getSelectionModel().clearSelection();
                        vehicleTable.getSelectionModel().clearSelection();
                        orderTable.getSelectionModel().clearSelection();
                    }
                }
        );
        setTableListener(driverTable);
        setTableListener(userTable);
        setTableListener(vehicleTable);


    }


    void initializeVehicleTable() {
        TableColumn<VehicleWrapper, String> c1 = new TableColumn<VehicleWrapper, String>("Id");
        TableColumn<VehicleWrapper, String> c2 = new TableColumn<VehicleWrapper, String>("Model");
        TableColumn<VehicleWrapper, String> c3 = new TableColumn<VehicleWrapper, String>("Licence Number");
        TableColumn<VehicleWrapper, String> c4 = new TableColumn<VehicleWrapper, String>("Driver Id");
        c1.setCellValueFactory(new PropertyValueFactory("id"));
        c2.setCellValueFactory(new PropertyValueFactory("Model"));
        c3.setCellValueFactory(new PropertyValueFactory("LicenceNumber"));
        c4.setCellValueFactory(new PropertyValueFactory("DriverId"));
        vehicleTable.getColumns().add(c1);
        vehicleTable.getColumns().add(c2);
        vehicleTable.getColumns().add(c3);
        vehicleTable.getColumns().add(c4);
    }

    void initializeDriverTable(){
        TableColumn<DriverWrapper, String> c1 = new TableColumn("Id");
        TableColumn<DriverWrapper, String> c2 = new TableColumn("Name");
        TableColumn<DriverWrapper, String> c3 = new TableColumn("Phone Number");
        TableColumn<DriverWrapper, String> c4 = new TableColumn("Password");
        TableColumn<DriverWrapper, String> c5 = new TableColumn("Vehicle Id");
        TableColumn<DriverWrapper, String> c6 = new TableColumn("Licence Id");
        c1.setCellValueFactory(new PropertyValueFactory("id"));
        c2.setCellValueFactory(new PropertyValueFactory("Name"));
        c3.setCellValueFactory(new PropertyValueFactory("PhoneNumber"));
        c4.setCellValueFactory(new PropertyValueFactory("Password"));
        c5.setCellValueFactory(new PropertyValueFactory("VehicleId"));
        c6.setCellValueFactory(new PropertyValueFactory("LicenceId"));
        driverTable.getColumns().add(c1);
        driverTable.getColumns().add(c2);
        driverTable.getColumns().add(c3);
        driverTable.getColumns().add(c4);
        driverTable.getColumns().add(c5);
        driverTable.getColumns().add(c6);
    }

    void initializeUserTable(){
        TableColumn<UserWrapper, String> c1 = new TableColumn("Id");
        TableColumn<UserWrapper, String> c2 = new TableColumn("Name");
        TableColumn<UserWrapper, String> c3 = new TableColumn("Phone Number");
        TableColumn<UserWrapper, String> c4 = new TableColumn("Password");
        c1.setCellValueFactory(new PropertyValueFactory("id"));
        c2.setCellValueFactory(new PropertyValueFactory("Name"));
        c3.setCellValueFactory(new PropertyValueFactory("PhoneNumber"));
        c4.setCellValueFactory(new PropertyValueFactory("Password"));
        userTable.getColumns().add(c1);
        userTable.getColumns().add(c2);
        userTable.getColumns().add(c3);
        userTable.getColumns().add(c4);
    }

    void initializeOrderTable(){
        TableColumn<OrderWrapper, String> c1 = new TableColumn("Order Id");
        TableColumn<OrderWrapper, String> c2 = new TableColumn("Driver Id");
        TableColumn<OrderWrapper, String> c3 = new TableColumn("User Id");
        TableColumn<OrderWrapper, String> c4 = new TableColumn("Start Point");
        TableColumn<OrderWrapper, String> c5 = new TableColumn("Finish Point");
        TableColumn<OrderWrapper, String> c6 = new TableColumn("Distance");
        TableColumn<OrderWrapper, String> c7 = new TableColumn("Time");
        TableColumn<OrderWrapper, String> c8 = new TableColumn("Approved");
        TableColumn<OrderWrapper, String> c9 = new TableColumn("In queue");
        c1.setCellValueFactory(new PropertyValueFactory("orderid"));
        c2.setCellValueFactory(new PropertyValueFactory("driverid"));
        c3.setCellValueFactory(new PropertyValueFactory("userid"));
        c4.setCellValueFactory(new PropertyValueFactory("startPoint"));
        c5.setCellValueFactory(new PropertyValueFactory("finishPoint"));
        c6.setCellValueFactory(new PropertyValueFactory("distance"));
        c7.setCellValueFactory(new PropertyValueFactory("time"));
        c8.setCellValueFactory(new PropertyValueFactory("approved"));
        c9.setCellValueFactory(new PropertyValueFactory("inqueue"));
        orderTable.getColumns().add(c1);
        orderTable.getColumns().add(c2);
        orderTable.getColumns().add(c3);
        orderTable.getColumns().add(c4);
        orderTable.getColumns().add(c5);
        orderTable.getColumns().add(c6);
        orderTable.getColumns().add(c7);
        orderTable.getColumns().add(c8);
        orderTable.getColumns().add(c9);
    }

    public void updateTable(int index) {
        switch (index) {
            case 0:

                exec.submit(new UIUpdateThread(vehicleTable, new ClientThread(1101, "Vehicle"), "VehicleWrapper"));
                break;
            case 1:
                exec.submit(new UIUpdateThread(userTable, new ClientThread(1101, "User"), "UserWrapper"));
                break;
            case 2:
                exec.submit(new UIUpdateThread(driverTable, new ClientThread(1101, "Driver"), "DriverWrapper"));
                break;
            case 3:
                exec.submit(new UIUpdateThread(orderTable, new ClientThread(1101, "OrderList"), "OrderWrapper"));
                break;
        }
    }

    public void updateButtonClick(ActionEvent actionEvent) {
       new UIPreloadThread(this).start();
    }

    public void infoButtonClick(ActionEvent actionEvent) {
        ArrayList<String> value ;
        ClientThread thread;
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 1:
                thread = new ClientThread(1123, "User"+Global.splitSymbol+userTable.getSelectionModel().getSelectedItem().getId());
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value = (ArrayList)thread.result;
                if (value.size()>0){
                    UserWrapper item = userTable.getSelectionModel().getSelectedItem();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("User id: %s\nUser name: %s\nUserAvgTime: %s\nUserAvgDistance: %s",
                            item.getId(), item.getName(), value.get(0), value.get(1)));
                    alert.showAndWait();
                }
                break;
            case 2:
                thread = new ClientThread(1123, "Driver"+Global.splitSymbol+driverTable.getSelectionModel().getSelectedItem().getId());
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value = (ArrayList)thread.result;
                if (value.size()>0){
                    DriverWrapper item = driverTable.getSelectionModel().getSelectedItem();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("Driver id: %s\nDriver name: %s\nDriverAvgTime: %s\nDriverAvgDistance: %s",
                            item.getId(), item.getName(), value.get(0), value.get(1)));
                    alert.showAndWait();
                }
                break;

        }
    }

    public void addButtonClick(ActionEvent actionEvent) {
        String value;
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                value = WindowsCreator.createVehicleWindow();
                if (value != null){
                    new ClientThread(1102, "Vehicle"+Global.splitSymbol+value).start();
                    updateTable(0);
                }
                break;
            case 1:
                value = WindowsCreator.createUserCreationWindow();
                if (value != null){
                    new ClientThread(1102, "User"+Global.splitSymbol+value).start();
                    updateTable(1);}
                break;
            case 2:value = WindowsCreator.createDriverCreationWindow();

                if (value != null){
                    new ClientThread(1102, "Driver"+Global.splitSymbol+value).start();
                    updateTable(2);}
                break;
        }
    }

    public void editButtonClick(ActionEvent actionEvent) {
        String value;
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                VehicleWrapper vehicleItem = vehicleTable.getSelectionModel().getSelectedItem();
                value = WindowsCreator.createVehicleEditWindow(vehicleItem);
                if (value != null){
                    new ClientThread(1103, "Vehicle"+Global.splitSymbol+vehicleItem.getId()+Global.splitSymbol+value).start();
                    updateTable(0);
                }
                break;
            case 1:
                UserWrapper userItem = userTable.getSelectionModel().getSelectedItem();
                value = WindowsCreator.createUserEditWindow(userItem);
                if (value != null){
                new ClientThread(1103, "User"+Global.splitSymbol+userItem.getId()+Global.splitSymbol+value).start();
                updateTable(1);}
                break;
            case 2:
                DriverWrapper driverItem = driverTable.getSelectionModel().getSelectedItem();
                value = WindowsCreator.createDriverEditWindow(driverTable.getSelectionModel().getSelectedItem());
                if (value != null){
                new ClientThread(1103, "Driver"+Global.splitSymbol+driverItem.getId()+Global.splitSymbol+value).start();
                updateTable(2);}
                break;
        }
    }

    public void deleteButtonClick(ActionEvent actionEvent) {
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                new ClientThread(1104, "Vehicle"+Global.splitSymbol+vehicleTable.getSelectionModel().selectedItemProperty().get().getId()).start();
                updateTable(0);
                break;
            case 1:
                new ClientThread(1104, "User"+Global.splitSymbol+userTable.getSelectionModel().selectedItemProperty().get().getId()).start();
                updateTable(1);
                break;
            case 2:
                new ClientThread(1104, "Driver"+Global.splitSymbol+driverTable.getSelectionModel().selectedItemProperty().get().getId()).start();
                updateTable(2);
                break;
            case 3:
                new ClientThread(1104, "OrderList"+Global.splitSymbol+orderTable.getSelectionModel().selectedItemProperty().get().getOrderid()).start();
                updateTable(3);
                break;
        }



    }

    private void setTableListener(TableView table){
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
                infoButton.setDisable(false);
            }
        });
    }
}
