package Methods;

import java.sql.*;

public class AdminSQLMethods extends SQLMethods {

    public AdminSQLMethods(String url, String user, String  password){
        super(url, user, password);
    }

    public void addVehicle(String model, int licenceNumber){
        try {

            statement.execute(String.format("insert into vehicle( Model,LicenceNumber, DriverId,Mileage,AvgTime ,AvgMileage) values('%s', %d, 0,0,0,0);", model, licenceNumber));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void addUser(String name, String password, int phoneNumber){
        try {

            statement.execute(String.format("insert into user( Name, Password, PhoneNumber, AvgDriveTime , AvgDriveDistance) values(%s, %s, %d,0,0);", name, password, phoneNumber));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addDriver(String name, int phoneNumber, String password, int vehicleId, int licenceId ){
        try {

            statement.execute(String.format("insert into Driver( Name, Password, PhoneNumber, vehicleid, licenceid, AvgDriveTime , AvgDriveDistance) values(%s, %s, %d,%d,%d,0,0);",
                    name, password,vehicleId,licenceId, phoneNumber));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteItemById(String tableName, int id){
        try {
            statement.execute(String.format("Delete from %s where id = %d; ", tableName, id));
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
}
