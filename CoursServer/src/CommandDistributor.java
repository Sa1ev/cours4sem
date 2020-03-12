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
                adminSQLMethods.addVehicle(command[1], new Integer(command[2]));
                return true;
            case 3:
                return true;
            case 4:
                adminSQLMethods.deleteItemById(command[1], new Integer(command[2]));
                return true;
            case 5:
                adminSQLMethods.clearTable(command[1]);
                return true;
            case 6:
                adminSQLMethods.getLineByPhoneAndPassword(command[1], new Integer(command[2]), command[3]);
                return true;
            default:
                return null;
        }
    }
    private static Object driverRequest(String[]command){
        switch (new Integer(command[0])%100){
            case 1:
                return driverSQLMethods.getTable(command[1]);
            default:
                return null;
        }
    }
    private static Object userRequest(String[] command){
        switch (new Integer(command[0])%100){
            case 1:
                return userSQLMethods.getTable(command[1]);
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
}
