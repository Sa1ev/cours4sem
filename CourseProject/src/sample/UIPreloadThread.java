package sample;

import sample.WindowController.AdminController;
import sample.WindowController.DriverController;
import sample.WindowController.GuestController;
import sample.WindowController.UserController;

public class UIPreloadThread extends  Thread{
    String type;
    Object controller;
    public UIPreloadThread(Object controller){
        this.type = Account.role;
        this.controller = controller;
    }
    @Override
    public void run(){
        switch (type){
            case "Admin":
                ((AdminController)controller).updateTable(0);

                ((AdminController)controller).updateTable(1);

                ((AdminController)controller).updateTable(2);

                ((AdminController)controller).updateTable(3);

                return;
            case "User":
                ((UserController)controller).updateTable(0);

                ((UserController)controller).updateTable(1);

                ((UserController)controller).updateTable(2);

                return;
            case "Driver":
                ((DriverController)controller).updateTable(0);

                ((DriverController)controller).updateTable(1);

                return;
            case "Guest":
                ((GuestController)controller).updateTable(0);

                return;
        }
    }
}
