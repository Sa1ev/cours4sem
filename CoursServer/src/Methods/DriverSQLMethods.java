package Methods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverSQLMethods extends SQLMethods {
    public DriverSQLMethods(String url, String user, String  password){
        super(url, user, password);
    }

    public ArrayList<String[]> getOrdersInQueue(String driverId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {


            ResultSet tableCount = statement.executeQuery(String.format("SELECT COUNT(*)\n" +
                    "  FROM INFORMATION_SCHEMA.COLUMNS\n"+
                    "  where TABLE_SCHEMA = 'coursedb' "+
                    "   AND table_name = 'orderList'"));
            tableCount.next();
            int columnCount=  tableCount.getInt(1);
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

    public ArrayList<String[]> getOrdersHistory(String driverId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {


            ResultSet tableCount = statement.executeQuery(String.format("SELECT COUNT(*)\n" +
                    "  FROM INFORMATION_SCHEMA.COLUMNS\n"+
                    "  where TABLE_SCHEMA = 'coursedb' "+
                    "   AND table_name = 'orderList'"));
            tableCount.next();
            int columnCount=  tableCount.getInt(1);
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

    public void changeOrder(String orderId, String decision){
        try {
            statement.execute(String.format("update orderlist set approved=%s, inqueue=0 where id = %s;",decision,orderId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
