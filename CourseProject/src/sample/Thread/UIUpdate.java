package sample.Thread;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.DataWrapper.DriverWrapper;
import sample.DataWrapper.OrderWrapper;
import sample.DataWrapper.UserWrapper;
import sample.DataWrapper.VehicleWrapper;

import java.util.ArrayList;
import java.util.Random;

public class UIUpdate {
    TableView table;
    ClientThread thread;
    String wrapperType;
    ObservableList obsList;
    public UIUpdate(TableView tableView, ClientThread thread, String WrapperType){
        table = tableView; this.thread = thread; wrapperType = WrapperType;
    }
    public void start(){
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch (wrapperType){
            case "DriverWrapper":
                obsList = DriverWrapper.convertArrayList((ArrayList)thread.result);
                break;
            case ("OrderWrapper"):
                obsList = OrderWrapper.convertArrayList((ArrayList)thread.result);
                break;
            case ("VehicleWrapper"):
                obsList = VehicleWrapper.convertArrayList((ArrayList)thread.result);
                break;
            case "UserWrapper":
                obsList = UserWrapper.convertArrayList((ArrayList)thread.result);
                break;


        }
        Platform.runLater(()->table.setItems(obsList));


    }
}
