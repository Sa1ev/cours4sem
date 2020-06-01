package sample.Methods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DriverSQLMethods extends SQLMethods {
    public DriverSQLMethods(){
        super();
    }

    static public ArrayList<String[]> getOrdersInQueue(String driverId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {
            int columnCount=  getColumnCount("orderlist");
            ResultSet set = statement.executeQuery(String.format("Select * from orderList where driverid = %s and inqueue = 1", driverId));
            while (set.next()){
                String[] oneRow = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    oneRow[i] = set.getString(i+1);
                }
                value.add(oneRow);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    static public ArrayList<String[]> getOrdersHistory(String driverId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {

            int columnCount=  getColumnCount("orderlist");
            ResultSet set = statement.executeQuery(String.format("Select * from orderList where driverid = %s and inqueue = 0", driverId));
            while (set.next()){
                String[] oneRow = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    oneRow[i] = set.getString(i+1);
                }
                value.add(oneRow);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    static public boolean changeOrder(String orderId, String decision){
        try {
            statement.execute(String.format("update orderlist set approved=%s, inqueue=0 where id = %s;",decision,orderId));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
