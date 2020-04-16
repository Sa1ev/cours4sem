package sample.Methods;


import sample.Thread.DBListener;

import java.sql.*;
import java.util.ArrayList;

public class SQLMethods {
    static Statement statement = getStatement();
    protected SQLMethods(){
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(ConnectionParams.URL, ConnectionParams.USER, ConnectionParams.PASSWORD);
            new DBListener(connection).start();
            statement =  connection.createStatement();
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        }
    }

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
    public static Statement getStatement() {
        if (statement==null){
            new SQLMethods();
        }
        return statement;
    }

    static public ArrayList<String[]> getTable(String tableName){
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
