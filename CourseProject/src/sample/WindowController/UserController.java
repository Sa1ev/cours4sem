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
import sample.Methods.UserSQLMethods;
import sample.Thread.ClientThread;
import sample.Utils.Global;
import sample.Utils.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserController {
    ExecutorService exec = Executors.newFixedThreadPool(1);
    public ObservableList<OrderWrapper> orderQueueTableItems= null;
    public ObservableList<OrderWrapper> orderHistoryTableItems= null;
    public ObservableList<DriverWrapper> driverTableItems= null;
    @FXML
    public void changeToAdminClick(ActionEvent actionEvent) {
        Account.authorization("Admin", null, null);
        ScreenController.getINSTANCE().activate("admin");
    }
    @FXML
    public void changeToDriverClick(ActionEvent actionEvent) {
        String winResult = WindowsCreator.createAuthorizationWindow();
        if (winResult != null){
        String[] value = winResult.split(Global.splitSymbol);
        if (Account.authorization("Driver", value[0], value[1])){
            ScreenController.getINSTANCE().activate("driver");
        }}
    }
    @FXML
    public void changeToGuestClick(ActionEvent actionEvent) {
        Account.authorization("Guest", null, null);
        ScreenController.getINSTANCE().activate("guest");
    }
    @FXML
    TextField searchTextBox;
    @FXML
    Button createButton;
    @FXML
    Button rejectButton;
    @FXML
    public TableView<DriverWrapper> driverTable;
    @FXML
    public TableView<OrderWrapper> orderQueueTable;
    @FXML
    public TableView<OrderWrapper> orderHistoryTable;
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
                        searchTextBox.clear();
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
        searchTextBox.textProperty().addListener(
                (observable, oldValue, newValue) -> filterTextBox(newValue));


    }

    void unselectButtons(){
        createButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    void filterTextBox(String value){
        ObservableList filtred = FXCollections.observableArrayList();
        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                if (driverTableItems == null) {
                    driverTableItems = driverTable.getItems();
                }
                for (int i = 0; i < driverTableItems.size(); i++) {
                    DriverWrapper item = driverTableItems.get(i);
                    if (item.isMatching(value)) {
                        filtred.add(item);
                    }
                }
                driverTable.setItems(filtred);
                break;
            case 1:
                if (orderQueueTableItems == null) {
                    orderQueueTableItems = orderQueueTable.getItems();
                }
                for (int i = 0; i < orderQueueTableItems.size(); i++) {
                    OrderWrapper item = orderQueueTableItems.get(i);
                    if (item.isMatchingByDriver(value)) {
                        filtred.add(item);
                    }
                }
                orderQueueTable.setItems(filtred);
                break;
            case 2:
                if (orderHistoryTableItems == null) {
                    orderHistoryTableItems = orderHistoryTable.getItems();
                }
                for (int i = 0; i < orderHistoryTableItems.size(); i++) {
                    OrderWrapper item = orderHistoryTableItems.get(i);
                    if (item.isMatchingByDriver(value)) {
                        filtred.add(item);
                    }
                }
                orderHistoryTable.setItems(filtred);
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


    public void createButtonClick(ActionEvent actionEvent) {
        String[] value = WindowsCreator.createOrderWindow();
        if (value != null){
            new ClientThread(()->UserSQLMethods.addOrder(
                    driverTable.getSelectionModel().getSelectedItem().getId(),
                    Integer.toString(Account.id),
                    value[0],
                    value[1],
                    Util.getTime(12, value[2]),
                    value[2],
                    driverTable.getSelectionModel().getSelectedItem().getVehicleId())).start();

        }
    }




    public void rejectButtonClick(ActionEvent actionEvent) {
        unselectButtons();
        new ClientThread(()->UserSQLMethods.deleteOrder(orderQueueTable.getSelectionModel().getSelectedItem().getOrderid())).start();

    }



    public void infoClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText("Информация");
        alert.setContentText("Разработчик данного приложения студент\nгруппы ИКБО-08-18 Смирнов Алексей");
        alert.showAndWait();
    }

    public void onExitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void connectionClicked(ActionEvent actionEvent) {
        WindowsCreator.createConnectionEditer();
    }
}
