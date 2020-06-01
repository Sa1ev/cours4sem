package sample.WindowController;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;
import sample.DataWrapper.DriverWrapper;
import sample.DataWrapper.OrderWrapper;
import sample.DataWrapper.UserWrapper;
import sample.DataWrapper.VehicleWrapper;
import sample.Methods.AdminSQLMethods;
import sample.Thread.*;
import sample.Thread.Report.DriverReportCreateThread;
import sample.Thread.Report.UserReportCreateThread;
import sample.Thread.Report.VehicleReportCreateThread;
import sample.Utils.Global;
import sample.Utils.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminController {
    ExecutorService exec = Executors.newFixedThreadPool(1);
    public ObservableList<VehicleWrapper> vehicleTableItems = null;
    public ObservableList<DriverWrapper> driverTableItems= null;
    public ObservableList<UserWrapper> userTableItems= null;
    public ObservableList<OrderWrapper> orderTableItems= null;
    @FXML
    TextField searchTextBox;
    @FXML
    Button addButton;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    Button infoButton;
    @FXML
    Button reportButton;
    @FXML
    TabPane tabPane;

    @FXML
    public TableView<VehicleWrapper> vehicleTable;
    @FXML
    public TableView<DriverWrapper> driverTable;
    @FXML
    public TableView<UserWrapper> userTable;
    @FXML
    public TableView<OrderWrapper> orderTable;
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
        String winResult = WindowsCreator.createAuthorizationWindow();
        if (winResult != null){
        String[] value = winResult.split(Global.splitSymbol);
            if (Account.authorization("Driver", value[0], value[1])){
                ScreenController.getINSTANCE().activate("driver");
            }
        }
    }
    @FXML
    public void changeToUserClick(ActionEvent actionEvent) {
        String winResult = WindowsCreator.createAuthorizationWindow();
        if (winResult != null){
        String[] value = winResult.split(Global.splitSymbol);
            if (Account.authorization("User", value[0], value[1])){
                ScreenController.getINSTANCE().activate("user");
            }
        }
    }
    @FXML
    public void changeToGuestClick(ActionEvent actionEvent) {
        Account.authorization("Guest", null, null);
        ScreenController.getINSTANCE().activate("guest");
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
                        unselectEverything();
                        userTable.getSelectionModel().clearSelection();
                        driverTable.getSelectionModel().clearSelection();
                        vehicleTable.getSelectionModel().clearSelection();
                        orderTable.getSelectionModel().clearSelection();
                        searchTextBox.clear();
                        if (tabPane.getSelectionModel().getSelectedIndex()==3){
                            reportButton.setDisable(true);
                            addButton.setDisable(true);
                        }

                        else{
                            reportButton.setDisable(false);
                            addButton.setDisable(false);
                        }
                    }
                }
        );
        setTableListener(driverTable);
        setTableListener(userTable);
        setTableListener(vehicleTable);
        searchTextBox.textProperty().addListener(
                (observable, oldValue, newValue) -> filterTextBox(newValue));

    }

    void unselectEverything(){
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        infoButton.setDisable(true);
        userTable.getSelectionModel().clearSelection();
        driverTable.getSelectionModel().clearSelection();
        vehicleTable.getSelectionModel().clearSelection();
        orderTable.getSelectionModel().clearSelection();
    }

    void filterTextBox(String value){
        ObservableList filtered = FXCollections.observableArrayList();
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                if (vehicleTableItems==null){
                    vehicleTableItems = FXCollections.observableArrayList(vehicleTable.getItems());
                }
                for (int i = 0; i <vehicleTableItems.size() ; i++) {
                    VehicleWrapper item = vehicleTableItems.get(i);
                    if (item.isMatching(value)){
                        filtered.add(item);
                    }
                }
                vehicleTable.setItems(filtered);
                break;
            case 1:
                if (userTableItems==null){
                    userTableItems = FXCollections.observableArrayList(userTable.getItems());
                }
                for (int i = 0; i <userTableItems.size() ; i++) {
                    UserWrapper item = userTableItems.get(i);
                    if (item.isMatching(value)){
                        filtered.add(item);
                    }
                }
                userTable.setItems(filtered);
                break;
            case 2:
                if (driverTableItems==null){
                    driverTableItems = FXCollections.observableArrayList(driverTable.getItems());
                }
                for (int i = 0; i <driverTableItems.size() ; i++) {
                    DriverWrapper item = driverTableItems.get(i);
                    if (item.isMatching(value)){
                        filtered.add(item);
                    }
                }
                driverTable.setItems(filtered);
                break;

            case 3:
                if (orderTableItems==null){
                    orderTableItems = FXCollections.observableArrayList(orderTable.getItems());
                }
                for (int i = 0; i <orderTableItems.size() ; i++) {
                    OrderWrapper item = orderTableItems.get(i);
                    if (item.isMatching(value)){
                        filtered.add(item);
                    }
                }
                orderTable.setItems(filtered);
        }
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
        TableColumn<OrderWrapper, String> c3 = new TableColumn("Vehicle Id");
        TableColumn<OrderWrapper, String> c4 = new TableColumn("User Id");
        TableColumn<OrderWrapper, String> c5 = new TableColumn("Start Point");
        TableColumn<OrderWrapper, String> c6 = new TableColumn("Finish Point");
        TableColumn<OrderWrapper, String> c7 = new TableColumn("Distance");
        TableColumn<OrderWrapper, String> c8 = new TableColumn("Time");
        TableColumn<OrderWrapper, String> c9 = new TableColumn("Approved");
        TableColumn<OrderWrapper, String> c10 = new TableColumn("In queue");
        TableColumn<OrderWrapper, String> c11 = new TableColumn("Date");
        c1.setCellValueFactory(new PropertyValueFactory("orderid"));
        c2.setCellValueFactory(new PropertyValueFactory("driverid"));
        c3.setCellValueFactory(new PropertyValueFactory("vehicleid"));
        c4.setCellValueFactory(new PropertyValueFactory("userid"));
        c5.setCellValueFactory(new PropertyValueFactory("startPoint"));
        c6.setCellValueFactory(new PropertyValueFactory("finishPoint"));
        c7.setCellValueFactory(new PropertyValueFactory("distance"));
        c8.setCellValueFactory(new PropertyValueFactory("time"));
        c9.setCellValueFactory(new PropertyValueFactory("approved"));
        c10.setCellValueFactory(new PropertyValueFactory("inqueue"));
        c11.setCellValueFactory(new PropertyValueFactory("datatime"));
        orderTable.getColumns().add(c1);
        orderTable.getColumns().add(c2);
        orderTable.getColumns().add(c3);
        orderTable.getColumns().add(c4);
        orderTable.getColumns().add(c5);
        orderTable.getColumns().add(c6);
        orderTable.getColumns().add(c7);
        orderTable.getColumns().add(c8);
        orderTable.getColumns().add(c9);
        orderTable.getColumns().add(c10);
        orderTable.getColumns().add(c11);
    }



    public void infoButtonClick(ActionEvent actionEvent) {
        ArrayList<String> value ;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ClientThread thread;
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                thread = new ClientThread(()-> AdminSQLMethods.getAvgTimeForVehiclePerDay(vehicleTable.getSelectionModel().getSelectedItem().getId()));
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String time = (String) thread.result;

                String vehicleId = vehicleTable.getSelectionModel().getSelectedItem().getId();
                if (time == null){
                    time = "0";
                }
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText(String.format("Vehicle id: %s\nVehicle Avg Distance per day: %d meters",
                        vehicleId, Math.round(new Double(time))));
                alert.showAndWait();

                break;
            case 1:
                thread = new ClientThread(()-> AdminSQLMethods.getAvgTimeAndDistanceOfCertainId("Users",userTable.getSelectionModel().getSelectedItem().getId()));
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value = (ArrayList)thread.result;

                if (value.size()>0){
                    if (value.get(0)==null){
                        value.set(0, "0");
                        value.set(1, "0");
                    }
                    UserWrapper item = userTable.getSelectionModel().getSelectedItem();
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("User id: %s\nUser name: %s\nUserAvgTime: %s\nUserAvgDistance: %d",
                            item.getId(), item.getName(), value.get(0), Math.round(new Double(value.get(1)))));
                    alert.showAndWait();
                }
                break;
            case 2:
                thread = new ClientThread(()-> AdminSQLMethods.getAvgTimeAndDistanceOfCertainId("Driver",driverTable.getSelectionModel().getSelectedItem().getId()));
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value = (ArrayList)thread.result;
                if (value.size()>0){
                    if (value.get(0)==null){
                        value.set(0, "0");
                        value.set(1, "0");
                    }
                    DriverWrapper item = driverTable.getSelectionModel().getSelectedItem();
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("Driver id: %s\nDriver name: %s\nDriverAvgTime: %s\nDriverAvgDistance: %d",
                            item.getId(), item.getName(),  value.get(0), Math.round(new Double(value.get(1)))));
                    alert.showAndWait();
                }
                break;

        }
    }

    public void addButtonClick(ActionEvent actionEvent) {
        String[] value;
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                value = WindowsCreator.createVehicleWindow();
                if (value != null){
                    new ValuesChangeThread(new ClientThread(()-> AdminSQLMethods.addVehicle(value[0], value[1])), null).start();
                }
                break;
            case 1:
                value = WindowsCreator.createUserCreationWindow();
                if (value != null){
                    new ValuesChangeThread(new ClientThread(()-> AdminSQLMethods.addUser(value[0], value[1], value[2])), null).start();
                  }
                break;
            case 2:
                if (vehicleTableItems==null){
                    vehicleTableItems = FXCollections.observableArrayList(vehicleTable.getItems());
                }
                value = WindowsCreator.createDriverCreationWindow(vehicleTableItems);

                if (value != null){
                    new ValuesChangeThread(new ClientThread(()-> AdminSQLMethods.addDriver(value[0], value[1], value[2], value[3], value[4])), null).start();
                }
                break;
        }
        unselectEverything();
    }

    public void editButtonClick(ActionEvent actionEvent) {
        String[] value;
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                VehicleWrapper vehicleItem = vehicleTable.getSelectionModel().getSelectedItem();
                value = WindowsCreator.createVehicleEditWindow(vehicleItem);
                if (value != null){
                    new ValuesChangeThread(new ClientThread(()-> AdminSQLMethods.editVehicle(vehicleItem.getId(), value[0], value[1])), null).start();
                }

                break;
            case 1:
                UserWrapper userItem = userTable.getSelectionModel().getSelectedItem();
                value = WindowsCreator.createUserEditWindow(userItem);
                if (value != null){
                    new ValuesChangeThread(new ClientThread(()-> AdminSQLMethods.editUser(userItem.getId(), value[0], value[1], value[2])), null).start();
                }
                break;
            case 2:
                if (vehicleTableItems==null){
                    vehicleTableItems = FXCollections.observableArrayList(vehicleTable.getItems());
                }
                DriverWrapper driverItem = driverTable.getSelectionModel().getSelectedItem();
                value = WindowsCreator.createDriverEditWindow(driverTable.getSelectionModel().getSelectedItem(),vehicleTableItems);
                if (value != null){
                    new ValuesChangeThread(new ClientThread(()-> AdminSQLMethods.editDriver(driverItem.getId(), value[0], value[1], value[2], value[3], value[4])),null).start();
                }
                break;
        }
        unselectEverything();
    }

    public void deleteButtonClick(ActionEvent actionEvent) {

        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                new ValuesChangeThread(new ClientThread(
                        ()->AdminSQLMethods.deleteItemById("Vehicle",vehicleTable.getSelectionModel().selectedItemProperty().get().getId())),
                        ()->unselectEverything()) .start();
                break;
            case 1:
                new ValuesChangeThread(new ClientThread(
                        ()->AdminSQLMethods.deleteItemById( "User",userTable.getSelectionModel().selectedItemProperty().get().getId())),
                        ()->unselectEverything()) .start();
                break;
            case 2:
                new ValuesChangeThread(new ClientThread(
                        ()->AdminSQLMethods.deleteItemById("Driver",driverTable.getSelectionModel().selectedItemProperty().get().getId())),
                        ()->unselectEverything()) .start();
                break;
            case 3:
                new ValuesChangeThread(new ClientThread(
                        ()->AdminSQLMethods.deleteItemById("OrderList",orderTable.getSelectionModel().selectedItemProperty().get().getOrderid())),
                        ()->unselectEverything()) .start();
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



    public void reportButtonClick(ActionEvent actionEvent) {
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                new VehicleReportCreateThread().start();
                break;
            case 1:
                new UserReportCreateThread().start();
                break;
            case 2:
                new DriverReportCreateThread().start();
                break;
        }
    }

    public void infoClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText("Информация");
        alert.setContentText("Разработчик данного приложения студент\nгруппы ИКБО-08-18 Смирнов Алексей");
        alert.showAndWait();
    }

    public void fillClick(ActionEvent actionEvent) {
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
               Test.fillVehicleTable(10);
                break;
            case 1:
                Test.fillUserTable(10);
                break;
            case 2:
                Test.fillDriverTable(10);
                break;
            case 3:
                new ClientThread(()->Test.fillOrderTable(10, userTable.getItems())).start();

        }
    }

    public void clearClick(ActionEvent actionEvent) {
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                AdminSQLMethods.clearTable("vehicle");
                break;
            case 1:
                AdminSQLMethods.clearTable("users");
                break;
            case 2:
                AdminSQLMethods.clearTable("driver");
                break;
            case 3:
                AdminSQLMethods.clearTable("orderlist");

        }
    }

    public void onExitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void connectionClicked(ActionEvent actionEvent) {
        WindowsCreator.createConnectionEditer();
    }
}
