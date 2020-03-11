package Methods;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

public class SQLMethods {
    Statement statement;
    public SQLMethods(String url, String user, String  password){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager
                    .getConnection(url, user, password);
            statement =  connection.createStatement();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getTable(String tableName){
        ArrayList<String[]> value  = new ArrayList<>();
        try {
            ResultSet tableCount = statement.executeQuery(String.format("SELECT COUNT(*)\n" +
                    "  FROM INFORMATION_SCHEMA.COLUMNS\n"+
                    "  where TABLE_SCHEMA = 'coursedb' "+
                    "   AND table_name = '%s'", tableName));
            tableCount.next();
            int columnCount=  tableCount.getInt(1);
            ResultSet set = statement.executeQuery("Select * from "+tableName);
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
