package sample.DataWrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class UserWrapper {
    private SimpleStringProperty id;
    private SimpleStringProperty Name;
    private SimpleStringProperty PhoneNumber;
    private SimpleStringProperty Password;
    private SimpleStringProperty AvgDriveTime;
    private SimpleStringProperty AvgDriveDistance;
    public UserWrapper(String[] properties){
        id = new SimpleStringProperty(properties[0]);
        Name = new SimpleStringProperty(properties[1]);
        PhoneNumber = new SimpleStringProperty(properties[2]);
        Password = new SimpleStringProperty(properties[3]);
        AvgDriveTime = new SimpleStringProperty(properties[4]);
        AvgDriveDistance = new SimpleStringProperty(properties[5]);

    }
    public static ObservableList convertArrayList(ArrayList<String[]> list){
        if (!(list == null)){
            ObservableList newList = FXCollections.observableArrayList();
            for (int i = 0; i < list.size(); i++) {
                newList.add(new UserWrapper(list.get(i)));
            }
            return newList;
        }
        return null;
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return Name.get();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getPhoneNumber() {
        return PhoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber.set(phoneNumber);
    }

    public String getPassword() {
        return Password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password.set(password);
    }

    public String getAvgDriveTime() {
        return AvgDriveTime.get();
    }

    public SimpleStringProperty avgDriveTimeProperty() {
        return AvgDriveTime;
    }

    public void setAvgDriveTime(String avgDriveTime) {
        this.AvgDriveTime.set(avgDriveTime);
    }

    public String getAvgDriveDistance() {
        return AvgDriveDistance.get();
    }

    public SimpleStringProperty avgDriveDistanceProperty() {
        return AvgDriveDistance;
    }

    public void setAvgDriveDistance(String avgDriveDistance) {
        this.AvgDriveDistance.set(avgDriveDistance);
    }


}
