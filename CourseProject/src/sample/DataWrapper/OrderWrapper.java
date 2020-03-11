package sample.DataWrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class OrderWrapper {
    private SimpleStringProperty orderid;
    private SimpleStringProperty driverid;
    private SimpleStringProperty userid;
    private SimpleStringProperty distance;
    private SimpleStringProperty time;
    private SimpleStringProperty approved;
    private SimpleStringProperty inqueue;
    public OrderWrapper(String[] properties){
        orderid = new SimpleStringProperty(properties[0]);
        driverid = new SimpleStringProperty(properties[1]);
        userid = new SimpleStringProperty(properties[2]);
        distance = new SimpleStringProperty(properties[3]);
        time = new SimpleStringProperty(properties[4]);
        approved = new SimpleStringProperty(properties[5]);
        inqueue = new SimpleStringProperty(properties[6]);
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

    public String getOrderid() {
        return orderid.get();
    }

    public SimpleStringProperty orderidProperty() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid.set(orderid);
    }

    public String getDriverid() {
        return driverid.get();
    }

    public SimpleStringProperty driveridProperty() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid.set(driverid);
    }

    public String getUserid() {
        return userid.get();
    }

    public SimpleStringProperty useridProperty() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid.set(userid);
    }

    public String getDistance() {
        return distance.get();
    }

    public SimpleStringProperty distanceProperty() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
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
        return approved;
    }

    public void setApproved(String approved) {
        this.approved.set(approved);
    }

    public String getInqueue() {
        return inqueue.get();
    }

    public SimpleStringProperty inqueueProperty() {
        return inqueue;
    }

    public void setInqueue(String inqueue) {
        this.inqueue.set(inqueue);
    }


}
