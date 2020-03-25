package Methods;

import java.sql.*;
import java.util.ArrayList;

public class AdminSQLMethods extends SQLMethods {

    public AdminSQLMethods(String url, String user, String  password){
        super(url, user, password);
    }

    public void addVehicle(String model, String licenceNumber){
        try {

            statement.execute(String.format("insert into vehicle(Model,LicenceNumber) values('%s', %s);", model, licenceNumber));
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
            statement.execute(String.format("update Driver set vehicleid=null where vehicleid = %s;", vehicleId));
            statement.execute(String.format("insert into Driver( Name, Password, PhoneNumber, vehicleid, licenceid) values('%s', '%s', %s,%s,%s);",
                    name, password,phoneNumber,vehicleId,licenceId));
            ResultSet set = statement.executeQuery(String.format("Select max(id) from vehicle;"));
            String driverId = "-1";
            while (set.next()){
                driverId = set.getString(1);
            }
            statement.execute(String.format("update vehicle set driverid=%s where id = %s;", driverId, vehicleId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void editVehicle(String id, String model, String licenceNumber){
        try {

            statement.execute(String.format("update vehicle set  Model='%s', LicenceNumber=%s where id = %s;",  model, licenceNumber, id));
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
            statement.execute(String.format("update Driver set vehicleid=null where vehicleid = %s;", vehicleId));
            statement.execute(String.format("update vehicle set driverid=null where driverid = %s;", id));
            statement.execute(String.format("update Driver set Name='%s', Password='%s', PhoneNumber=%s, vehicleid=%s, licenceid=%s where id = %s;",
                    name, password,phoneNumber,vehicleId,licenceId, id));
            statement.execute(String.format("update vehicle set driverid=%s where id = %s;", id, vehicleId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAvgTimeAndDistanceOfCertainId(String role, String id){
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


    public ArrayList<String[]> getDriverStatistics(){
        ArrayList<String[]> statistics = new ArrayList<>();
        statistics.add(new String[]{getAvgTime(), getAvgDistance()});
        statistics.add(getMaxDriverValues());
        statistics.add(getMinDriverValues());
        statistics.add(getMaxSumDriverValues());
        statistics.add(getMaxSumFromLastMonthDriverValues());
        return statistics;
    }

    public ArrayList<String[]> getUserStatistics(){
        ArrayList<String[]> statistics = new ArrayList<>();
        statistics.add(new String[]{getAvgTime(), getAvgDistance()});
        statistics.add(getMaxUserValues());
        statistics.add(getMinUserValues());
        statistics.add(getMaxSumUserValues());
        statistics.add(getMaxSumFromLastMonthUserValues());
        return statistics;
    }

    public ArrayList<String[]> getVehicleStatistics(){
        ArrayList<String[]> statistics = new ArrayList<>();
        statistics.add(new String[]{getAvgTime(), getAvgDistance()});
        statistics.add(new String[]{getCountOfVehicleWithDriver(), getCountOfVehicleWithoutDriver()});
        return statistics;
    }
    

    private String getAvgDistance(){
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
    private String getAvgTime(){
        try{
            ResultSet set = statement.executeQuery("select SEC_TO_TIME(AVG(TIME_TO_SEC(time))) from orderlist where approved = 1;");
            while (set.next()){
                return set.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String[] getMaxDriverValues(){
        try{
            ResultSet set = statement.executeQuery("select d.id, d.name,  max(o.time),max(o.distance) from orderlist o join driver d on d.id = o.driverid where o.approved = 1;");
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
    private String[] getMinDriverValues(){
        try{
            ResultSet set = statement.executeQuery("select d.id, d.name, min(o.time),min(o.distance) from orderlist o join driver d on d.id = o.driverid where o.approved = 1;");
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
    private String[] getMaxSumDriverValues(){
        try{
            ResultSet set = statement.executeQuery("select t.driverid, t.name, max(t.newtime), max(t.newDist)  from (select SEC_TO_TIME(sum(TIME_TO_SEC(o.time))) as newtime,  " +
                    "(select sum(distance)) as newDist, o.driverid, d.name from orderlist o join driver d on d.id = o.driverid where approved = 1 group by driverid) t;");
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
    private String[] getMaxSumFromLastMonthDriverValues(){
        try{
            ResultSet set = statement.executeQuery("select t.driverid, t.name, max(t.newtime), max(t.newDist)  from (select SEC_TO_TIME(sum(TIME_TO_SEC(o.time)))" +
                    " as newtime,  (select sum(distance)) as newDist, o.driverid, d.name from orderlist o join driver d on d.id = o.driverid " +
                    "where approved = 1 and dateandtime > DATE_SUB(now(), INTERVAL 1 MONTH) and dateandtime < now() group by driverid) t;");
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

    private String[] getMaxUserValues(){
        try{
            ResultSet set = statement.executeQuery("select u.id, u.name, max(o.time),max(o.distance) from orderlist o join user u on u.id = o.userid where o.approved = 1;");
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
    private String[] getMinUserValues(){
        try{
            ResultSet set = statement.executeQuery("select u.id, u.name,min(o.time),min(o.distance) from orderlist o join user u on u.id = o.userid where o.approved = 1;");
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
    private String[] getMaxSumUserValues(){
        try{
            ResultSet set = statement.executeQuery("select  t.userid  , t.name,max(t.newtime), max(newDist) from (select SEC_TO_TIME(sum(TIME_TO_SEC(o.time))) as " +
                    "newtime, (select sum(distance)) as newDist, o.userid, u.name from orderlist o join user u on u.id = o.userid where approved = 1 group by userid) t;");
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
    private String[] getMaxSumFromLastMonthUserValues(){
        try{
            ResultSet set = statement.executeQuery("select  t.userid  , t.name,max(t.newtime), max(newDist) from (select SEC_TO_TIME(sum(TIME_TO_SEC(o.time))) as newtime, " +
                    " (select sum(distance)) as newDist, o.userid, u.name from orderlist o join user u on u.id = o.userid " +
                    "where approved = 1 and dateandtime > DATE_SUB(now(), INTERVAL 1 MONTH) and dateandtime < now() group by userid) t;");
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

    private String getCountOfVehicleWithoutDriver(){
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

    private String getCountOfVehicleWithDriver(){
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
}
