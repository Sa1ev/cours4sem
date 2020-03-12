package sample.WindowController;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DataWrapper.OrderWrapper;
import sample.ScreenController;

public class DriverController {

    @FXML
    public void changeToAdminClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("admin");
    }
    @FXML
    public void changeToUserClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("user");
    }
    @FXML
    public void changeToGuestClick(ActionEvent actionEvent) {
        ScreenController.getINSTANCE().activate("guest");
    }

    @FXML
    TableView<OrderWrapper> orderQueueTable;
    @FXML
    TableView<OrderWrapper> orderHistoryTable;

    @FXML
    public void initialize() {
        initializeOrderQueueTable();
        initializeOrderHistoryTable();
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
}
