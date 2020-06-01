package sample.Thread;

import sample.Account;
import sample.Methods.DriverSQLMethods;
import sample.Methods.SQLMethods;
import sample.Methods.UserSQLMethods;
import sample.WindowController.AdminController;
import sample.WindowController.DriverController;
import sample.WindowController.GuestController;
import sample.WindowController.UserController;

public class UILoadThread extends  Thread{
    static Object lastController;
    String type;
    Object controller;
    public UILoadThread(Object controller){
        this.type = Account.role;
        if (controller!=null){
            this.controller = controller;
            lastController = controller;
        }
        else{
            this.controller = lastController;
        }

    }
    @Override
    public void run(){
        switch (type){
            case "Admin":
                AdminController adminController = (AdminController)controller;
                new UIUpdate(adminController.vehicleTable, new ClientThread(()-> SQLMethods.getTable("Vehicle")), "VehicleWrapper").start();
                adminController.vehicleTableItems = null;
                new UIUpdate(adminController.userTable, new ClientThread(()->SQLMethods.getTable("Users")), "UserWrapper").start();
                adminController.userTableItems = null;
                new UIUpdate(adminController.driverTable, new ClientThread(()->SQLMethods.getTable("Driver")), "DriverWrapper").start();
                adminController.driverTableItems = null;
                new UIUpdate(adminController.orderTable, new ClientThread(()->SQLMethods.getTable( "OrderList")), "OrderWrapper").start();
                adminController.orderTableItems = null;

                return;
            case "User":
                UserController userController = (UserController) controller;
                new UIUpdate(userController.driverTable ,new ClientThread(()->UserSQLMethods.getDriversWithVehicleTable()), "DriverWrapper").start();
                userController.driverTableItems= null;
                new UIUpdate(userController.orderQueueTable , new ClientThread(()->UserSQLMethods.getOrdersInQueue( Integer.toString(Account.id))),
                        "OrderWrapper").start();
                userController.orderQueueTableItems= null;
                new UIUpdate(userController.orderHistoryTable , new ClientThread(()->UserSQLMethods.getOrdersHistory(Integer.toString(Account.id))),
                        "OrderWrapper").start();
                userController.orderHistoryTableItems= null;

                return;
            case "Driver":
                DriverController driverController = (DriverController) controller;
                new UIUpdate(driverController.orderQueueTable , new ClientThread(()-> DriverSQLMethods.getOrdersInQueue( Integer.toString(Account.id))),
                        "OrderWrapper").start();
                driverController.orderQueueTableItems= null;
                new UIUpdate(driverController.orderHistoryTable , new ClientThread(()->DriverSQLMethods.getOrdersHistory(Integer.toString(Account.id))),
                        "OrderWrapper").start();
                driverController.orderHistoryTableItems= null;

                return;
            case "Guest":
                GuestController guestController = (GuestController) controller;
                new UIUpdate(guestController.driverTable ,new ClientThread(()-> SQLMethods.getDriversWithVehicleTable()), "DriverWrapper").start();
                guestController.driverTableItems= null;
                return;
        }
    }
}
