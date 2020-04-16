package sample.Methods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserSQLMethods extends SQLMethods {
    private static Statement statement = SQLMethods.getStatement();
    public UserSQLMethods(){
        super();
    }
    static public ArrayList<String[]> getOrdersInQueue(String userId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {

            int columnCount=  getColumnCount("orderlist");
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

    static public ArrayList<String[]> getOrdersHistory(String userId){
        ArrayList<String[]> value  = new ArrayList<>();
        try {

            int columnCount=  getColumnCount("orderlist");
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
    static public boolean addOrder( String driverid, String userid, String startpoint, String finishpoint,  String time, String distance){
        try {

            statement.execute(String.format("insert into orderlist(driverid, userid,startpoint, finishpoint, time, distance, approved, inqueue) values (%s, %s, '%s','%s','%s',%s,0,1);",
                    driverid,  userid,  startpoint,  finishpoint,  time,  distance));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public boolean deleteOrder(String orderId){
        try {
            statement.execute(String.format("Delete from orderlist where id = %s; ", orderId));
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
