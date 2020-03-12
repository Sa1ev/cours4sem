package sample.WindowController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

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
    TextField driverIdField;


    @FXML
    public void initialize(){

        Pattern validModelText = Pattern.compile("^[A-Za-zА-Яа-я0-9\\-]{0,45}$");
        setupPattern(validModelText, modelField, null);

        Pattern validLicenceText = Pattern.compile("\\d{0,15}");
        setupPattern(validLicenceText, licenceField, null);

        Pattern validDriverText = Pattern.compile("\\d{0,9}");
        setupPattern(validDriverText, driverIdField, null);

    }
    @FXML
    public void createButtonClick(){
        if (modelField.getText() != "" & licenceField.getText() != "" & driverIdField.getText() != ""){
            returningValue = modelField.getText()+" "+licenceField.getText()+" "+driverIdField.getText();
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
