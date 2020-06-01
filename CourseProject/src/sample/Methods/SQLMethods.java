package sample.Methods;


import sample.Thread.DBListener;

import java.sql.*;
import java.util.ArrayList;

public class SQLMethods {
    static Statement statement = ConnectionDB.getStatement();

    protected static int getColumnCount(String tableName){
        ResultSet tableCount = null;
        try {
            tableCount = statement.executeQuery(String.format("SELECT count(column_name) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '%s';",
                    tableName.toLowerCase()));
            tableCount.next();
            return   tableCount.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }


    static public ArrayList<String[]> getTable(String tableName){
        synchronized (statement){
            ArrayList<String[]> value  = new ArrayList<>();
            try {



                int columnCount=  getColumnCount(tableName);
                ResultSet set = statement.executeQuery("Select * from \""+tableName.toLowerCase()+"\";");
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

    static public ArrayList<String[]> getDriversWithVehicleTable(){
        synchronized (statement){
            ArrayList<String[]> value  = new ArrayList<>();
            try {

                int columnCount=  getColumnCount("driver");
                ResultSet set = statement.executeQuery("Select * from driver where vehicleid is not null;");
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
    public static void updateStatement(){
        statement = ConnectionDB.getStatement();
    }



}
