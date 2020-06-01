package sample.DataWrapper;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class UserWrapper {
    private SimpleIntegerProperty id;
    private SimpleStringProperty Name;
    private SimpleStringProperty PhoneNumber;
    private SimpleStringProperty Password;
    public UserWrapper(String[] properties){
        id = new SimpleIntegerProperty(new Integer(properties[0]));
        Name = new SimpleStringProperty(properties[1]);
        PhoneNumber = new SimpleStringProperty(properties[2]);
        Password = new SimpleStringProperty(properties[3]);

    }
    public boolean isMatching(String text){
        return id.getValue().toString().contains(text) | Name.get().contains(text) | PhoneNumber.get().contains(text);
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
        return id.getValue().toString();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(new Integer(id));
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


}
