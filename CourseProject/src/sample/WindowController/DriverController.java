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
import sample.DataWrapper.OrderWrapper;
import sample.Methods.DriverSQLMethods;
import sample.Thread.ClientThread;
import sample.Thread.UILoadThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DriverController {
    ExecutorService exec = Executors.newFixedThreadPool(1);
    public ObservableList<OrderWrapper> orderQueueTableItems= null;
    public ObservableList<OrderWrapper> orderHistoryTableItems= null;
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
    public TableView<OrderWrapper> orderQueueTable;
    @FXML
    public TableView<OrderWrapper> orderHistoryTable;

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
                        unselectButtons();
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
    void unselectButtons(){
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
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



    public void approveButtonClick(ActionEvent actionEvent) {
        new ClientThread(()->DriverSQLMethods.changeOrder(orderQueueTable.getSelectionModel().getSelectedItem().getOrderid(), "1")).start();
        unselectButtons();
    }

    public void rejectButtonClick(ActionEvent actionEvent) {
        new ClientThread(()->DriverSQLMethods.changeOrder(orderQueueTable.getSelectionModel().getSelectedItem().getOrderid(), "0")).start();
            unselectButtons();
    }





    public void infoClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText("Информация");
        alert.setContentText("Разработчик данного приложения студент\nгруппы ИКБО-08-18 Смирнов Алексей");
        alert.showAndWait();
    }
}
