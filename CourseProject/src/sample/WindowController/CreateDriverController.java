package sample.WindowController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import sample.DataWrapper.DriverWrapper;
import sample.DataWrapper.VehicleWrapper;
import sample.Global;

import java.util.regex.Pattern;


public class CreateDriverController {
    private String returningValue = null;
    @FXML
    Button createButton;
    @FXML
    Button rejectButton;

    @FXML
    TextField numberField;
    @FXML
    TextField passwordField;
    @FXML
    TextField nameField;
    @FXML
    ChoiceBox vehicleBox;
    @FXML
    TextField licenceIdField;

    @FXML
    public void initialize(){
        Pattern validIntegerText = Pattern.compile("^\\d{1,11}$");
        setupPattern(validIntegerText, numberField, "8");
        Pattern validPasswordText = Pattern.compile("^\\S{0,20}$");
        setupPattern(validPasswordText, passwordField, null);
        Pattern validNameText = Pattern.compile("^[A-Яа-яA-Za-z\\s]{0,30}$");
        setupPattern(validNameText, nameField, null);
        Pattern validLicenceText = Pattern.compile("^\\d{0,9}$");
        setupPattern(validLicenceText, licenceIdField, null);


    }
    Pattern phone = Pattern.compile("\\d{11}");
    Pattern password = Pattern.compile("\\S{5,20}");
    @FXML
    public void createButtonClick(){
        if (phone.matcher(numberField.getText()).matches() & password.matcher(passwordField.getText()).matches() & nameField.getText()!=""& licenceIdField.getText()!=""){
            returningValue = numberField.getText()+ Global.splitSymbol+
                    passwordField.getText()+Global.splitSymbol+
                    nameField.getText()+Global.splitSymbol+
                    vehicleBox.getSelectionModel().getSelectedItem().toString()+Global.splitSymbol+licenceIdField.getText();
        }
        ((Stage)passwordField.getScene().getWindow()).close();

    }
    @FXML
    public void rejectClick(){
        ((Stage)passwordField.getScene().getWindow()).close();

    }

    public String getReturningValue(){
        return returningValue;
    }

    public void setValues(DriverWrapper values){
        numberField.setText(values.getPhoneNumber());
        passwordField.setText(values.getPassword());
        nameField.setText(values.getName());
        licenceIdField.setText(values.getLicenceId());

    }

    private void setupPattern(Pattern pattern, TextField field, String defaultValue){
        TextFormatter<String> textFormatter = new TextFormatter<String>(new DefaultStringConverter(), defaultValue,
                change -> {
                    String newText = change.getControlNewText() ;

                    if (pattern.matcher(newText).matches()) {
                        return change ;
                    } else return null;
                });
        field.setTextFormatter(textFormatter);
    }

    public void fillChoiceBox(ObservableList<VehicleWrapper> list){
        ObservableList myList = FXCollections.observableArrayList();

        for (int i = 0; i < list.size(); i++) {
            myList.add(list.get(i).getId());
        }
        vehicleBox.setItems(myList);
        vehicleBox.getSelectionModel().select(0);
    }
}
