import Methods.AdminSQLMethods;
import Methods.DriverSQLMethods;
import Methods.UserSQLMethods;

public class CommandDistributor {
    private static String url = "jdbc:mysql://localhost:3306/coursedb?useUnicode=true&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "qwerty";
    private static AdminSQLMethods adminSQLMethods= new AdminSQLMethods(url, user, password);;
    private static DriverSQLMethods driverSQLMethods= new DriverSQLMethods(url, user, password);;
    private static UserSQLMethods userSQLMethods= new UserSQLMethods(url, user, password);;


    public static synchronized Object chooseRole(String[] command){
        System.out.println(Thread.currentThread());
        switch (new Integer(command[0])/100){
            case 11:
                return adminRequest(command);
            case  12:
                return driverRequest(command);
            case  13:
                return userRequest(command);
            default:
                return  guestRequest(command);
        }

    }

    private static Object adminRequest(String[] command){
        switch (new Integer(command[0])%100){
            case 1:
                return adminSQLMethods.getTable(command[1]);
            case 2:
                return addRequest(command);
            case 3:
                return editRequest(command);
            case 4:
                adminSQLMethods.deleteItemById(command[1], command[2]);
                return true;
            case 5:
                adminSQLMethods.clearTable(command[1]);
                return true;
            case 6:
                return adminSQLMethods.getLineByPhoneAndPassword(command[1], new Long(command[2]), command[3]);
            case 31:
                return adminSQLMethods.getAvgTimeAndDistanceOfCertainId(command[1], command[2]);
            case 32:
                return getReportRequest(command);
            default:
                return null;
        }
    }
    private static Object driverRequest(String[]command){
        switch (new Integer(command[0])%100){
            case 1:
                return driverSQLMethods.getTable(command[1]);
            case 21:
                return driverSQLMethods.getOrdersInQueue(command[1]);
            case 22:
                return driverSQLMethods.getOrdersHistory((command[1]));
            case 24:
                driverSQLMethods.changeOrder(command[1], command[2]);
                return true;
            default:
                return null;
        }
    }
    private static Object userRequest(String[] command){
        switch (new Integer(command[0])%100){
            case 1:
                return userSQLMethods.getTable(command[1]);
            case 2:
                userSQLMethods.addOrder(command[1], command[2],command[3],command[4],command[5],command[6]);
                return true;
            case 4:
                userSQLMethods.deleteOrder(command[1]);
                return true;
            case 21:
                return userSQLMethods.getOrdersInQueue((command[1]));
            case 22:
                return userSQLMethods.getOrdersHistory((command[1]));
            default:
                return null;

        }
    }
    private static Object guestRequest(String[] command){
        switch (new Integer(command[0])%100){
            case 1:
                return userSQLMethods.getTable(command[1]);
            default:
                return null;
        }
    }

    private static Object addRequest(String[] command){
        switch (command[1]){
            case "User":
                adminSQLMethods.addUser( (command[2]), command[3],command[4]);
                return true;
            case "Vehicle":
                adminSQLMethods.addVehicle(command[2], command[3]);
                return true;
            case "Driver":
                adminSQLMethods.addDriver((command[2]), command[3], command[4], (command[5]),(command[6]));
                return true;
            case "Order":
                return true;
            default:
                return null;
        }
    }
    private static Object editRequest(String[] command){
        switch (command[1]){
            case "User":
                adminSQLMethods.editUser((command[2]), (command[3]), command[4],command[5]);
                return true;
            case "Vehicle":
                adminSQLMethods.editVehicle((command[2]),command[3], command[4]);
                return true;
            case "Driver":
                adminSQLMethods.editDriver((command[2]), (command[3]), command[4], command[5], (command[6]),(command[7]));
                return true;
            case "Order":
                return true;
            default:
                return null;
        }
    }
    private static Object getReportRequest(String[] command){
        switch (command[1]){
            case "User":

                return adminSQLMethods.getUserStatistics();
            case "Vehicle":

                return adminSQLMethods.getVehicleStatistics();
            case "Driver":

                return adminSQLMethods.getDriverStatistics();
            default:
                return null;
        }
    }



}
