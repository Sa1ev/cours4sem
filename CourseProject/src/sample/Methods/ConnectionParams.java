package sample.Methods;

public class ConnectionParams {
    private static String ip = "localhost";
    private static String port = "5432";

    public static String USER = "cou";
    public static String PASSWORD = "root";
    public static void setIp(String ip){
        ConnectionParams.ip=ip;
    }
    public static void setport(String port){
        ConnectionParams.port=port;
    }
    public static String getUrl(){
        return "jdbc:postgresql://"+ip+":"+port+"/coursedb";
    }

}
