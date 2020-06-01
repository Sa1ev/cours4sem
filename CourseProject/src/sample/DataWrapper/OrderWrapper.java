package sample.DataWrapper;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Utils.Util;

import java.util.ArrayList;

public class OrderWrapper {
    private SimpleIntegerProperty orderid;
    private SimpleIntegerProperty driverid;
    private SimpleIntegerProperty userid;
    private SimpleStringProperty startPoint;
    private SimpleStringProperty finishPoint;
    private SimpleIntegerProperty distance;
    private SimpleStringProperty time;
    private SimpleStringProperty approved;
    private SimpleStringProperty inqueue;
    private SimpleStringProperty datatime;
    private SimpleStringProperty vehicleid;



    public OrderWrapper(String[] properties){
        orderid = new SimpleIntegerProperty(new Integer(properties[0]));
        driverid = new SimpleIntegerProperty(new Integer(properties[1]));
        userid = new SimpleIntegerProperty(new Integer(properties[2]));
        startPoint = new SimpleStringProperty(properties[3]);
        finishPoint = new SimpleStringProperty(properties[4]);
        distance = new SimpleIntegerProperty(new Integer(properties[5]));
        time = new SimpleStringProperty(properties[6]);
        approved = new SimpleStringProperty(properties[7]);
        inqueue = new SimpleStringProperty(properties[8]);
        datatime = new SimpleStringProperty(properties[9]);
        vehicleid = new SimpleStringProperty(properties[10]);
    }

    public boolean isMatching(String text){
        return orderid.getValue().toString().contains(text) | driverid.getValue().toString().contains(text) |
                userid.getValue().toString().contains(text) | startPoint.get().contains(text) | finishPoint.get().contains(text);
    }
    public boolean isMatchingByDriver(String text){
        return orderid.getValue().toString().contains(text) | driverid.getValue().toString().contains(text)| startPoint.get().contains(text) | finishPoint.get().contains(text) ;
    }
    public boolean isMatchingByUser(String text){
        return orderid.getValue().toString().contains(text)  | userid.getValue().toString().contains(text)| startPoint.get().contains(text) | finishPoint.get().contains(text);
    }

    public static ObservableList convertArrayList(ArrayList<String[]> list){
        if (!(list == null)){
            ObservableList newList = FXCollections.observableArrayList();
            for (int i = 0; i < list.size(); i++) {
                newList.add(new OrderWrapper(list.get(i)));
            }
            return newList;
        }
        return null;
    }

    public String getStartPoint() {
        return startPoint.get();
    }

    public SimpleStringProperty startPointProperty() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint.set(startPoint);
    }

    public String getFinishPoint() {
        return finishPoint.get();
    }

    public SimpleStringProperty finishPointProperty() {
        return finishPoint;
    }

    public void setFinishPoint(String finishPoint) {
        this.finishPoint.set(finishPoint);
    }

    public String getOrderid() {
        return orderid.getValue().toString();
    }

    public SimpleIntegerProperty orderidProperty() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid.set(new Integer(orderid));
    }

    public String getDriverid() {
        return driverid.getValue().toString();
    }

    public SimpleIntegerProperty driveridProperty() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid.set(new Integer(driverid));
    }

    public String getUserid() {
        return userid.getValue().toString();
    }

    public SimpleIntegerProperty useridProperty() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid.set(new Integer(userid));
    }

    public String getDistance() {
        return distance.getValue().toString();
    }

    public SimpleIntegerProperty distanceProperty() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance.set(new Integer(distance));
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getApproved() {
        return approved.get();
    }

    public SimpleStringProperty approvedProperty() {
        return new SimpleStringProperty(Util.intToYesOrNo(approved.get()));
    }

    public void setApproved(String approved) {
        this.approved.set(approved);
    }

    public String getInqueue() {
        return inqueue.get();
    }

    public SimpleStringProperty inqueueProperty() {
        return new SimpleStringProperty(Util.intToYesOrNo(inqueue.get()));
    }

    public void setInqueue(String inqueue) {
        this.inqueue.set(inqueue);
    }


    public String getDatatime() {
        return datatime.get();
    }

    public SimpleStringProperty datatimeProperty() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime.set(datatime);
    }


    public String getVehicleid() {
        return vehicleid.get();
    }

    public SimpleStringProperty vehicleidProperty() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid.set(vehicleid);
    }
}
