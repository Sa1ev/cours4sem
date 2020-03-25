package sample.WindowController;

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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DriverController {
    ExecutorService exec = Executors.newFixedThreadPool(1);
    ObservableList<OrderWrapper> orderQueueTableItems= null;
    ObservableList<OrderWrapper> orderHistoryTableItems= null;
    @FXML
    public void changeToAdminClick(ActionEvent actionEvent) {
        Account.authorization("Admin", null, null);
        ScreenController.getINSTANCE().activate("admin");
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
    @FXML
    TextField searchTextBox;
    @FXML
    Button approveButton;
    @FXML
    Button rejectButton;
    @FXML
    TabPane tabPane;
    @FXML
    TableView<OrderWrapper> orderQueueTable;
    @FXML
    TableView<OrderWrapper> orderHistoryTable;

    @FXML
    public void initialize() {
        initializeOrderQueueTable();
        initializeOrderHistoryTable();
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        approveButton.setDisable(true);
                        rejectButton.setDisable(true);
                        orderQueueTable.getSelectionModel().clearSelection();
                        orderHistoryTable.getSelectionModel().clearSelection();
                        searchTextBox.clear();
                    }
                }
        );

        orderQueueTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                rejectButton.setDisable(false);
                approveButton.setDisable(false);
            }
        });
        searchTextBox.textProperty().addListener(
                (observable, oldValue, newValue) -> filterTextBox(newValue));
    }

    void filterTextBox(String value){
        ObservableList filtered = FXCollections.observableArrayList();
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0:
                if (orderQueueTableItems==null){
                    orderQueueTableItems = orderQueueTable.getItems();
                }
                for (int i = 0; i <orderQueueTableItems.size() ; i++) {
                    OrderWrapper item = orderQueueTableItems.get(i);
                    if (item.isMatchingByUser(value)){
                        filtered.add(item);
                    }
                }
                orderQueueTable.setItems(filtered);
                break;
            case 1:
                if (orderHistoryTableItems==null){
                    orderHistoryTableItems = orderHistoryTable.getItems();
                }
                for (int i = 0; i <orderHistoryTableItems.size() ; i++) {
                    OrderWrapper item = orderHistoryTableItems.get(i);
                    if (item.isMatchingByUser(value)){
                        filtered.add(item);
                    }
                }
                orderHistoryTable.setItems(filtered);
                break;
        }
    }
    void initializeOrderQueueTable(){
        TableColumn<OrderWrapper, String> c1 = new TableColumn("Order Id");
        TableColumn<OrderWrapper, String> c3 = new TableColumn("User Id");
        TableColumn<OrderWrapper, String> c4 = new TableColumn("Start Point");
        TableColumn<OrderWrapper, String> c5 = new TableColumn("Finish Point");
        TableColumn<OrderWrapper, String> c6 = new TableColumn("Distance");
        TableColumn<OrderWrapper, String> c7 = new TableColumn("Time");
        c1.setCellValueFactory(new PropertyValueFactory("orderid"));
        c3.setCellValueFactory(new PropertyValueFactory("userid"));
        c4.setCellValueFactory(new PropertyValueFactory("startPoint"));
        c5.setCellValueFactory(new PropertyValueFactory("finishPoint"));
        c6.setCellValueFactory(new PropertyValueFactory("distance"));
        c7.setCellValueFactory(new PropertyValueFactory("time"));
        orderQueueTable.getColumns().add(c1);
        orderQueueTable.getColumns().add(c3);
        orderQueueTable.getColumns().add(c4);
        orderQueueTable.getColumns().add(c5);
        orderQueueTable.getColumns().add(c6);
        orderQueueTable.getColumns().add(c7);
    }
    void initializeOrderHistoryTable(){
        TableColumn<OrderWrapper, String> c1 = new TableColumn("Order Id");
        TableColumn<OrderWrapper, String> c3 = new TableColumn("User Id");
        TableColumn<OrderWrapper, String> c4 = new TableColumn("Start Point");
        TableColumn<OrderWrapper, String> c5 = new TableColumn("Finish Point");
        TableColumn<OrderWrapper, String> c6 = new TableColumn("Distance");
        TableColumn<OrderWrapper, String> c7 = new TableColumn("Time");
        TableColumn<OrderWrapper, String> c8 = new TableColumn("Approved");
        c1.setCellValueFactory(new PropertyValueFactory("orderid"));
        c3.setCellValueFactory(new PropertyValueFactory("userid"));
        c4.setCellValueFactory(new PropertyValueFactory("startPoint"));
        c5.setCellValueFactory(new PropertyValueFactory("finishPoint"));
        c6.setCellValueFactory(new PropertyValueFactory("distance"));
        c7.setCellValueFactory(new PropertyValueFactory("time"));
        c8.setCellValueFactory(new PropertyValueFactory("approved"));
        orderHistoryTable.getColumns().add(c1);
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
                exec.submit(new UIUpdateThread(orderQueueTable , new ClientThread(1221, Integer.toString(Account.id)), "OrderWrapper"));
                orderQueueTableItems= null;
                break;
            case 1:
                exec.submit(new UIUpdateThread(orderHistoryTable , new ClientThread(1222, Integer.toString(Account.id)), "OrderWrapper"));
                orderHistoryTableItems= null;
        }
    }

    public void approveButtonClick(ActionEvent actionEvent) {
        new ClientThread(1224, orderQueueTable.getSelectionModel().getSelectedItem().getOrderid()+Global.splitSymbol+"1").start();
        updateTable(0);
        updateTable(1);
        approveButton.setDisable(true);
        rejectButton.setDisable(true);}

    public void rejectButtonClick(ActionEvent actionEvent) {
        new ClientThread(1224, orderQueueTable.getSelectionModel().getSelectedItem().getOrderid()+Global.splitSymbol+"0").start();
        updateTable(0);
        updateTable(1);
        approveButton.setDisable(true);
        rejectButton.setDisable(true);}



    public void updateButtonClick(ActionEvent actionEvent) {
        new UIPreloadThread(this).start();
    }
}
