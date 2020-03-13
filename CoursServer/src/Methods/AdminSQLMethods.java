package Methods;

import java.sql.*;
import java.util.ArrayList;

public class AdminSQLMethods extends SQLMethods {

    public AdminSQLMethods(String url, String user, String  password){
        super(url, user, password);
    }

    public void addVehicle(String model, String licenceNumber, String driverid){
        try {

            statement.execute(String.format("insert into vehicle( Model,LicenceNumber, DriverId) values('%s', %s, %s);", model, licenceNumber, driverid));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void addUser(String phoneNumber, String password, String name ){
        try {

            statement.execute(String.format("insert into user( Name, Password, PhoneNumber) values('%s', '%s', %s);", name, password, phoneNumber));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addDriver( String phoneNumber, String password, String name, String vehicleId, String licenceId ){
        try {

            statement.execute(String.format("insert into Driver( Name, Password, PhoneNumber, vehicleid, licenceid) values('%s', '%s', %s,%s,%s);",
                    name, password,phoneNumber,vehicleId,licenceId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void editVehicle(String id, String model, String licenceNumber, String driverid){
        try {

            statement.execute(String.format("update vehicle set  Model='%s', LicenceNumber=%s, DriverId=%s where id = %s;",  model, licenceNumber, driverid,id));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void editUser(String id,String phoneNumber, String password, String name ){
        try {

            statement.execute(String.format("update user set Name='%s', Password='%s', PhoneNumber=%s where id = %s;", name, password, phoneNumber,id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editDriver(String id,String phoneNumber, String password, String name, String vehicleId, String licenceId ){
        try {

            statement.execute(String.format("update Driver set Name='%s', Password='%s', PhoneNumber=%s, vehicleid=%s, licenceid=%s where id = %s;",
                    name, password,phoneNumber,vehicleId,licenceId, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAvgTimeAndDistance(String role, String id){
        ArrayList<String> result = new ArrayList<>();
        ResultSet set = null;
        try {
            set = statement.executeQuery(String.format("select SEC_TO_TIME(AVG(TIME_TO_SEC(time))) from orderlist where %sid = %s and approved = 1", role, id));
            while (set.next()){
                result.add(set.getString(1));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            set = statement.executeQuery(String.format("select AVG(distance) from orderlist where %sid = %s and approved = 1", role, id));
            while (set.next()){
                result.add(set.getString(1));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

    public void deleteItemById(String tableName, String id){
        try {
            statement.execute(String.format("Delete from %s where id = %s; ", tableName, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public  void clearTable ( String tableName){

        try {
            statement.execute("Delete from "+tableName+";");
            statement.execute("ALTER TABLE "+tableName+" AUTO_INCREMENT = 1;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String[]> getLineByPhoneAndPassword(String tableName, Long phone, String password){
        ArrayList<String[]> value  = new ArrayList<>();
        try {
            ResultSet tableCount = statement.executeQuery(String.format("SELECT COUNT(*)\n" +
                    "  FROM INFORMATION_SCHEMA.COLUMNS\n"+
                    "  where TABLE_SCHEMA = 'coursedb' "+
                    "   AND table_name = '%s'", tableName));
            tableCount.next();
            int columnCount =  tableCount.getInt(1);
            ResultSet set = statement.executeQuery(String.format("Select * from  %s where phonenumber = %s and password  = '%s'", tableName, phone, password));
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
