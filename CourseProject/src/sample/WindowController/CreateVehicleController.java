package sample.WindowController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import sample.DataWrapper.DriverWrapper;
import sample.DataWrapper.VehicleWrapper;
import sample.Global;

import java.util.regex.Pattern;


public class CreateVehicleController {
    private String returningValue = null;
    @FXML
    Button createButton;
    @FXML
    Button rejectButton;

    @FXML
    TextField modelField;
    @FXML
    TextField licenceField;


    @FXML
    public void initialize(){

        Pattern validModelText = Pattern.compile("^[A-Za-zА-Яа-я0-9\\-]{0,45}$");
        setupPattern(validModelText, modelField, null);

        Pattern validLicenceText = Pattern.compile("\\d{0,15}");
        setupPattern(validLicenceText, licenceField, null);


    }
    @FXML
    public void createButtonClick(){
        if (modelField.getText() != "" & licenceField.getText() != ""){
            returningValue = modelField.getText()+ Global.splitSymbol+licenceField.getText();
        }
        ((Stage)modelField.getScene().getWindow()).close();

    }
    @FXML
    public void rejectClick(){
        ((Stage)modelField.getScene().getWindow()).close();

    }

    public String getReturningValue(){
        return returningValue;
    }
    public void setValues(VehicleWrapper values){
         modelField.setText(values.getModel());
         licenceField.setText(values.getLicenceNumber());

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
}
