package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import sample.DataWrapper.DriverWrapper;
import sample.DataWrapper.OrderWrapper;
import sample.DataWrapper.UserWrapper;
import sample.DataWrapper.VehicleWrapper;

import java.util.ArrayList;

public class UIUpdateThread extends Thread {
    TableView table;
    ClientThread thread;
    String wrapperType;
    public UIUpdateThread(TableView tableView, ClientThread thread, String WrapperType){
        table = tableView; this.thread = thread; wrapperType = WrapperType;
    }
    @Override
    public void run(){

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ObservableList obsList = null;
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
        table.setItems(obsList);


    }
}
