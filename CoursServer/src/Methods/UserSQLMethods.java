package Methods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserSQLMethods extends SQLMethods {
    public UserSQLMethods(String url, String user, String  password){
        super(url, user, password);
    }
    public ArrayList<String[]> getOrdersInQueue(String userId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {


            ResultSet tableCount = statement.executeQuery(String.format("SELECT COUNT(*)\n" +
                    "  FROM INFORMATION_SCHEMA.COLUMNS\n"+
                    "  where TABLE_SCHEMA = 'coursedb' "+
                    "   AND table_name = 'orderList'"));
            tableCount.next();
            int columnCount=  tableCount.getInt(1);
            ResultSet set = statement.executeQuery(String.format("Select * from orderList where userid = %s and inqueue = 1", userId));
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

    public ArrayList<String[]> getOrdersHistory(String userId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {


            ResultSet tableCount = statement.executeQuery(String.format("SELECT COUNT(*)\n" +
                    "  FROM INFORMATION_SCHEMA.COLUMNS\n"+
                    "  where TABLE_SCHEMA = 'coursedb' "+
                    "   AND table_name = 'orderList'"));
            tableCount.next();
            int columnCount=  tableCount.getInt(1);
            ResultSet set = statement.executeQuery(String.format("Select * from orderList where userid = %s and inqueue = 0", userId));
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
    public void addOrder( String driverid, String userid, String startpoint, String finishpoint, String distance, String time){
        try {

            statement.execute(String.format("insert into orderlist(driverid, userid,startpoint, finishpoint, time, distance, approved, inqueue) values (%s, %s, '%s','%s','%s',%s,0,1);",
                    driverid,  userid,  startpoint,  finishpoint,  time,  distance));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(String orderId){
        try {
            statement.execute(String.format("Delete from orderlist where id = %s; ", orderId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
