package sample.Utils;

public class Util {
    public static String intToYesOrNo(String value){
        return value.equals("1") ? "Yes" : "No";
    }
    public static String getTime(int speedMS, String orderValues){
        int distance = new Integer(orderValues)/speedMS;
        int hours = distance/3600;
        distance-=hours*3600;
        int minutes  = distance/60;
        distance -= minutes*60;
        return String.format("%d:%d:%d", hours, minutes, distance);

    }
}
