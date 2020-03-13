package sample.DataWrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class VehicleWrapper {
    private SimpleStringProperty id;
    private SimpleStringProperty Model;
    private SimpleStringProperty LicenceNumber;
    private SimpleStringProperty DriverId;


    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getModel() {
        return Model.get();
    }

    public SimpleStringProperty modelProperty() {
        return Model;
    }

    public void setModel(String model) {
        this.Model.set(model);
    }

    public String getLicenceNumber() {
        return LicenceNumber.get();
    }

    public SimpleStringProperty licenceNumberProperty() {
        return LicenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.LicenceNumber.set(licenceNumber);
    }

    public String getDriverId() {
        return DriverId.get();
    }

    public SimpleStringProperty driverIdProperty() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        this.DriverId.set(driverId);
    }




    public VehicleWrapper(String[] properties){
        id = new SimpleStringProperty(properties[0]);
        Model = new SimpleStringProperty(properties[1]);
        LicenceNumber = new SimpleStringProperty(properties[2]);
        DriverId = new SimpleStringProperty(properties[3]);
    }

    public static ObservableList convertArrayList(ArrayList<String[]> list){
        if (!(list == null)){
            ObservableList newList = FXCollections.observableArrayList();
            for (int i = 0; i < list.size(); i++) {
                newList.add(new VehicleWrapper(list.get(i)));
            }
            return newList;
        }
        return null;
    }

}
