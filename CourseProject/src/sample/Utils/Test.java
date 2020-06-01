package sample.Utils;

import javafx.collections.ObservableList;
import sample.DataWrapper.UserWrapper;
import sample.Methods.AdminSQLMethods;
import sample.Methods.SQLMethods;
import sample.Methods.UserSQLMethods;
import sample.Thread.ClientThread;
import sample.Thread.ValuesChangeThread;
import sample.Utils.Util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
    private static String generatePhoneNumber(){
        long generatedLong = new Random().nextLong();
        return Long.toString(8_000_000_00_00L + (Math.abs(generatedLong) % 1_000_000_00_00L));
    }

    private static String  generateNumber(){
        return Integer.toString(Math.abs(new SecureRandom().nextInt()));
    }
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static String generateString(int length){
        String returning = "";
        int ran = -1;
        for( int i = 0; i < length; i++ ){
            ran = new Random().nextInt(AB.length());
            returning+=AB.substring(ran, ran+1);
        }
        return returning;
    }
    public static void fillVehicleTable(int loops){
        for (int i = 0; i <loops ; i++) {
            new ValuesChangeThread(new ClientThread(()-> AdminSQLMethods.addVehicle(generateString(10), generateNumber())), null).start();

        }
    }
    public static void fillDriverTable(int loops){
        for (int i = 0; i <loops ; i++) {
            new ValuesChangeThread(new ClientThread(()->
                    AdminSQLMethods.addDriver(generatePhoneNumber(), generateString(15), "DriverName", null, generatePhoneNumber().substring(2))), null).start();

        }
    }
    public static void fillUserTable(int loops){
        for (int i = 0; i <loops ; i++) {
            new ValuesChangeThread(new ClientThread(()->
                    AdminSQLMethods.addUser(generatePhoneNumber(), generateString(15), "UserName")), null).start();

        }
    }
    public static boolean fillOrderTable(int loops, ObservableList<UserWrapper> userTableItems){
        ArrayList<String[]> driverTableItems = SQLMethods.getDriversWithVehicleTable();
        if (userTableItems.isEmpty() || driverTableItems.isEmpty()){
            return false;
        }

        for (int i = 0; i <loops ; i++) {
            String userId = userTableItems.get(ThreadLocalRandom.current().nextInt(0, userTableItems.size())).getId();
            int driverID = ThreadLocalRandom.current().nextInt(0, driverTableItems.size());
            String len = Integer.toString(ThreadLocalRandom.current().nextInt(0, 999999));
            int approved = new Random().nextInt(2);
            UserSQLMethods.addOrderTest(
                    driverTableItems.get(driverID)[0],
                    userId,
                    generateString(15),
                    generateString(15),
                    Util.getTime(12, len),
                    len,
                    approved,
                    1-approved,
                    driverTableItems.get(driverID)[4]);

        }
        return true;
    }

}
