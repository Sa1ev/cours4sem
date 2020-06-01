package sample.Methods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminSQLMethods extends SQLMethods {
    public AdminSQLMethods(){
        super();
    }

    static public boolean addVehicle(String model, String licenceNumber){
        try {
            statement.execute(String.format("insert into vehicle(Model,LicenceNumber) values('%s', %s);", model, licenceNumber));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    static public boolean addUser(String phoneNumber, String password, String name ){
        synchronized (statement){
            try {

                statement.execute(String.format("insert into users( Name, Password, PhoneNumber) values('%s', '%s', %s);", name, password, phoneNumber));
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    static public boolean addDriver( String phoneNumber, String password, String name, String vehicleId, String licenceId ){
        synchronized (statement){
            try {
                statement.execute(String.format("update Driver set vehicleid=null where vehicleid = %s;", vehicleId));
                statement.execute(String.format("insert into Driver( Name, Password, PhoneNumber, vehicleid, licenceid) values('%s', '%s', %s,%s,%s);",
                        name, password,phoneNumber,vehicleId,licenceId));
                ResultSet set = statement.executeQuery(String.format("Select max(id) from vehicle;"));
                String driverId = "-1";
                while (set.next()){
                    driverId = set.getString(1);
                }
                statement.execute(String.format("update vehicle set driverid=%s where id = %s;", driverId, vehicleId));
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

    }



    static public boolean editVehicle(String id, String model, String licenceNumber){
        synchronized (statement){
        try {

            statement.execute(String.format("update vehicle set  Model='%s', LicenceNumber=%s where id = %s;",  model, licenceNumber, id));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        }

    }
    static public boolean editUser(String id,String phoneNumber, String password, String name ){
        try {

            statement.execute(String.format("update users set Name='%s', Password='%s', PhoneNumber=%s where id = %s;", name, password, phoneNumber,id));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    static public boolean editDriver(String id,String phoneNumber, String password, String name, String vehicleId, String licenceId ){
        try {
            statement.execute(String.format("update Driver set vehicleid=null where vehicleid = %s;", vehicleId));
            statement.execute(String.format("update vehicle set driverid=null where driverid = %s;", id));
            statement.execute(String.format("update Driver set Name='%s', Password='%s', PhoneNumber=%s, vehicleid=%s, licenceid=%s where id = %s;",
                    name, password,phoneNumber,vehicleId,licenceId, id));
            statement.execute(String.format("update vehicle set driverid=%s where id = %s;", id, vehicleId));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public ArrayList<String> getAvgTimeAndDistanceOfCertainId(String role, String id){
        ArrayList<String> result = new ArrayList<>();
        ResultSet set = null;
        try {
            set = statement.executeQuery(String.format("select (AVG((time))) from orderlist where %sid = %s and approved = 1", role, id));
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

    static public boolean deleteItemById(String tableName, String id){
        try {
            statement.execute(String.format("Delete from \"%s\" where id = %s; ", tableName.toLowerCase(), id));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }

    }
    static public  void clearTable ( String tableName){

        try {
            statement.execute("Delete from "+tableName+";");
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    static public ArrayList<String[]> getLineByPhoneAndPassword(String tableName, Long phone, String password){
        ArrayList<String[]> value  = new ArrayList<>();
        try {
            int columnCount =  getColumnCount(tableName);
            ResultSet set = statement.executeQuery(String.format("Select * from  \"%s\" where phonenumber = %s and password  = '%s';", tableName.toLowerCase(), phone, password));
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


    static  public ArrayList<String[]> getDriverStatistics(){
        ArrayList<String[]> statistics = new ArrayList<>();
        statistics.add(new String[]{getAvgTime(), getAvgDistance()});
        statistics.add(getMaxDriverValues());
        statistics.add(getMinDriverValues());
        statistics.add(getMaxSumDriverValues());
        statistics.add(getMaxSumFromLastMonthDriverValues());
        return statistics;
    }

    static public ArrayList<String[]> getUserStatistics(){
        ArrayList<String[]> statistics = new ArrayList<>();
        statistics.add(new String[]{getAvgTime(), getAvgDistance()});
        statistics.add(getMaxUserValues());
        statistics.add(getMinUserValues());
        statistics.add(getMaxSumUserValues());
        statistics.add(getMaxSumFromLastMonthUserValues());
        return statistics;
    }

    static  public ArrayList<String[]> getVehicleStatistics(){
        ArrayList<String[]> statistics = new ArrayList<>();
        statistics.add(new String[]{getAvgTime(), getAvgDistance()});
        statistics.add(new String[]{getCountOfVehicleWithDriver(), getCountOfVehicleWithoutDriver()});
        return statistics;
    }


    static private String getAvgDistance(){
        try{
            ResultSet set = statement.executeQuery("select avg(distance) from orderlist where approved = 1;");
            while (set.next()){
                return set.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static private String getAvgTime(){
        try{
            ResultSet set = statement.executeQuery("select (AVG((time))) from orderlist where approved = 1;");
            while (set.next()){
                return set.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static private String[] getMaxDriverValues(){
        try{
            ResultSet set = statement.executeQuery("select d.id, d.name,  max(o.time) newtime,max(o.distance) from orderlist o " +
                    "join driver d on d.id = o.driverid where o.approved = 1 group by d.id, d.name order by newtime desc; ");
            while (set.next()){
                String[] oneRow = new String[4];
                for (int i = 0; i <4 ; i++) {
                    oneRow[i] = set.getString(i+1);
                }

                return  oneRow;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static private String[] getMinDriverValues(){
        try{
            ResultSet set = statement.executeQuery("select d.id, d.name, min(o.time) newtime,min(o.distance) from orderlist o " +
                    "join driver d on d.id = o.driverid where o.approved = 1 group by d.id, d.name order by newtime;;");
            while (set.next()){
                String[] oneRow = new String[4];
                for (int i = 0; i <4 ; i++) {
                    oneRow[i] = set.getString(i+1);
                }
                return oneRow;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static  private String[] getMaxSumDriverValues(){
        try{
            ResultSet set = statement.executeQuery("\n" +
                    "SELECT driverid,\n" +
                    "sum(distance),\t\n" +
                    "name,\n" +
                    "sum(time)\n" +
                    "FROM orderlist\n" +
                    "join driver on driver.id = orderlist.driverid\n" +
                    "GROUP BY name,driverid\n" +
                    "HAVING sum(time) = (\n" +
                    "SELECT sum(time)\n" +
                    "FROM orderlist\n" +
                    "GROUP BY   driverid\n" +
                    "ORDER BY sum(time) DESC LIMIT 1);");
            while (set.next()){
                String[] oneRow = new String[4];
                for (int i = 0; i <4 ; i++) {
                    oneRow[i] = set.getString(i+1);
                }
                return oneRow;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static private String[] getMaxSumFromLastMonthDriverValues(){
        try{
            ResultSet set = statement.executeQuery("\n" +
                    "SELECT driverid,\n" +
                    "sum(distance),\t\n" +
                    "name,\n" +
                    "sum(time)\n" +
                    "FROM orderlist\n" +
                    "join driver on driver.id = orderlist.driverid\n" +
                    "GROUP BY name,driverid\n" +
                    "HAVING sum(time) = (\n" +
                    "SELECT sum(time)\n" +
                    "FROM orderlist\n" +
                    "where approved = 1 and dateandtime > NOW() - INTERVAL '1 MONTH'\n" +
                    "GROUP BY   driverid\n" +
                    "ORDER BY sum(time) DESC LIMIT 1);");
            while (set.next()){
                String[] oneRow = new String[4];
                for (int i = 0; i <4 ; i++) {
                    oneRow[i] = set.getString(i+1);
                }
                return oneRow;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static  private String[] getMaxUserValues(){
        try{
            ResultSet set = statement.executeQuery("select u.id, u.name, max(o.time) newtime,max(o.distance) from orderlist o " +
                    "join users u on u.id = o.userid where o.approved = 1 group by u.id, u.name order by newtime ;");
            while (set.next()){
                String[] oneRow = new String[4];
                for (int i = 0; i <4 ; i++) {
                    oneRow[i] = set.getString(i+1);
                }

                return  oneRow;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static private String[] getMinUserValues(){
        try{
            ResultSet set = statement.executeQuery("select u.id, u.name,min(o.time) newtime,min(o.distance) from orderlist o " +
                    "join users u on u.id = o.userid where o.approved = 1 group by u.id, u.name order by newtime desc;");
            while (set.next()){
                String[] oneRow = new String[4];
                for (int i = 0; i <4 ; i++) {
                    oneRow[i] = set.getString(i+1);
                }
                return oneRow;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static private String[] getMaxSumUserValues(){
        try{
            ResultSet set = statement.executeQuery("SELECT userid,\n" +
                    "sum(distance),\t\n" +
                    "name,\n" +
                    "sum(time)\n" +
                    "FROM orderlist\n" +
                    "join users on users.id = orderlist.userid\n" +
                    "GROUP BY name,userid\n" +
                    "HAVING sum(time) = (\n" +
                    "SELECT sum(time)\n" +
                    "FROM orderlist\n" +
                    "GROUP BY   userid\n" +
                    "ORDER BY sum(time) DESC LIMIT 1);");
            while (set.next()){
                String[] oneRow = new String[4];
                for (int i = 0; i <4 ; i++) {
                    oneRow[i] = set.getString(i+1);
                }
                return oneRow;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static private String[] getMaxSumFromLastMonthUserValues(){
        try{
            ResultSet set = statement.executeQuery("SELECT userid,\n" +
                    "sum(distance),\t\n" +
                    "name,\n" +
                    "sum(time)\n" +
                    "FROM orderlist\n" +
                    "join users on users.id = orderlist.userid\n" +
                    "GROUP BY name,userid\n" +
                    "HAVING sum(time) = (\n" +
                    "SELECT sum(time)\n" +
                    "FROM orderlist\n" +
                    "where approved = 1 and dateandtime > NOW() - INTERVAL '1 MONTH'\n" +
                    "GROUP BY   userid\n" +
                    "ORDER BY sum(time) DESC LIMIT 1);");
            while (set.next()){
                String[] oneRow = new String[4];
                for (int i = 0; i <4 ; i++) {
                    oneRow[i] = set.getString(i+1);
                }
                return oneRow;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static private String getCountOfVehicleWithoutDriver(){
        try{
            ResultSet set = statement.executeQuery("select count(id) from vehicle where driverid is null;");
            while (set.next()){
                return set.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static private String getCountOfVehicleWithDriver(){
        try{
            ResultSet set = statement.executeQuery("select count(id) from vehicle where driverid is not null;");
            while (set.next()){
                return set.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public String getAvgTimeForVehiclePerDay(String vehicleId){
        try{
            ResultSet set = statement.executeQuery("select avg(distance)/COUNT(DISTINCT CAST(dateandtime AS DATE)) from orderlist where vehicleid = "+vehicleId+" and approved = 1;");
            set.next();
            return set.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
