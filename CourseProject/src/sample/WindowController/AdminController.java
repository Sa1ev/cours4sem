package sample.WindowController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;
import sample.DataWrapper.DriverWrapper;
import sample.DataWrapper.OrderWrapper;
import sample.DataWrapper.UserWrapper;
import sample.DataWrapper.VehicleWrapper;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminController {
    ExecutorService exec = Executors.newFixedThreadPool(1);
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
        ScreenController.getINSTANCE().activate("driver");
    }
    @FXML
    public void changeToUserClick(ActionEvent actionEvent) {
        String[] value = AuthorizationCreator.createAuthorizationWindow().split(" ");
        Account.authorization("User", value[0], value[1]);
        //ScreenController.getINSTANCE().activate("user");
    }
    @FXML
    public void changeToGuestClick(ActionEvent actionEvent) {
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
        updateTable(0);

//        tabPane.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<Tab>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
//                        updateTable(tabPane.getSelectionModel().getSelectedIndex());
//                    }
//                }
//        );
    }


    void initializeVehicleTable() {
        TableColumn<VehicleWrapper, String> c1 = new TableColumn<VehicleWrapper, String>("Id");
        TableColumn<VehicleWrapper, String> c2 = new TableColumn<VehicleWrapper, String>("Model");
        TableColumn<VehicleWrapper, String> c3 = new TableColumn<VehicleWrapper, String>("Licence Number");
        TableColumn<VehicleWrapper, String> c4 = new TableColumn<VehicleWrapper, String>("Driver Id");
        TableColumn<VehicleWrapper, String> c5 = new TableColumn<VehicleWrapper, String>("Mileage");
        TableColumn<VehicleWrapper, String> c6 = new TableColumn<VehicleWrapper, String>("AvgTime");
        TableColumn<VehicleWrapper, String> c7 = new TableColumn<VehicleWrapper, String>("AvgMileage");
        c1.setCellValueFactory(new PropertyValueFactory("id"));
        c2.setCellValueFactory(new PropertyValueFactory("Model"));
        c3.setCellValueFactory(new PropertyValueFactory("LicenceNumber"));
        c4.setCellValueFactory(new PropertyValueFactory("DriverId"));
        c5.setCellValueFactory(new PropertyValueFactory("Mileage"));
        c6.setCellValueFactory(new PropertyValueFactory("AvgTime"));
        c7.setCellValueFactory(new PropertyValueFactory("AvgMileage"));
        vehicleTable.getColumns().add(c1);
        vehicleTable.getColumns().add(c2);
        vehicleTable.getColumns().add(c3);
        vehicleTable.getColumns().add(c4);
        vehicleTable.getColumns().add(c5);
        vehicleTable.getColumns().add(c6);
        vehicleTable.getColumns().add(c7);
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

    void initializeUserTable(){
        TableColumn<UserWrapper, String> c1 = new TableColumn("Id");
        TableColumn<UserWrapper, String> c2 = new TableColumn("Name");
        TableColumn<UserWrapper, String> c3 = new TableColumn("Phone Number");
        TableColumn<UserWrapper, String> c4 = new TableColumn("Password");
        TableColumn<UserWrapper, String> c5 = new TableColumn("AvgDriveTime");
        TableColumn<UserWrapper, String> c6 = new TableColumn("AvgDriveDistance");
        c1.setCellValueFactory(new PropertyValueFactory("id"));
        c2.setCellValueFactory(new PropertyValueFactory("Name"));
        c3.setCellValueFactory(new PropertyValueFactory("PhoneNumber"));
        c4.setCellValueFactory(new PropertyValueFactory("Password"));
        c5.setCellValueFactory(new PropertyValueFactory("AvgDriveTime"));
        c6.setCellValueFactory(new PropertyValueFactory("AvgDriveDistance"));
        userTable.getColumns().add(c1);
        userTable.getColumns().add(c2);
        userTable.getColumns().add(c3);
        userTable.getColumns().add(c4);
        userTable.getColumns().add(c5);
        userTable.getColumns().add(c6);
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
        c5.setCellValueFactory(new PropertyValueFactory("fiishPoint"));
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

    public void updateTable(int index){
        switch (index){
            case 0:
                exec.submit(new UIUpdateThread(vehicleTable , new ClientThread(1101, "Vehicle"), "VehicleWrapper"));
                break;
            case 1:
                exec.submit( new UIUpdateThread(userTable ,new ClientThread(1101, "User"), "UserWrapper"));
                break;
            case 2:
                new UIUpdateThread(driverTable ,new ClientThread(1101, "Driver"), "DriverWrapper").start();
                break;
            case 3:
                new UIUpdateThread(orderTable ,new ClientThread(1101, "OrderList"), "OrderWrapper").start();
                break;
        }
    }
}
