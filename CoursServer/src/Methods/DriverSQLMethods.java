package Methods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverSQLMethods extends SQLMethods {
    public DriverSQLMethods(String url, String user, String  password){
        super(url, user, password);
    }

    public ArrayList<String[]> getOrdersInQueue(int driverId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {


            ResultSet tableCount = statement.executeQuery(String.format("SELECT COUNT(*)\n" +
                    "  FROM INFORMATION_SCHEMA.COLUMNS\n"+
                    "  where TABLE_SCHEMA = 'coursedb' "+
                    "   AND table_name = 'orderList'"));
            tableCount.next();
            int columnCount=  tableCount.getInt(1);
            ResultSet set = statement.executeQuery(String.format("Select * from orderList where driverid = %d and inqueue = 1", driverId));
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

    public ArrayList<String[]> getOrdersHistory(int driverId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {


            ResultSet tableCount = statement.executeQuery(String.format("SELECT COUNT(*)\n" +
                    "  FROM INFORMATION_SCHEMA.COLUMNS\n"+
                    "  where TABLE_SCHEMA = 'coursedb' "+
                    "   AND table_name = 'orderList'"));
            tableCount.next();
            int columnCount=  tableCount.getInt(1);
            ResultSet set = statement.executeQuery(String.format("Select * from orderList where driverid = %d and inqueue = 0", driverId));
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

}
