package sample.WindowController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import sample.DataWrapper.DriverWrapper;
import sample.Global;

import java.util.regex.Pattern;


public class CreateOrderController {
    private String returningValue = null;
    @FXML
    Button createButton;
    @FXML
    Button rejectButton;

    @FXML
    TextField startField;
    @FXML
    TextField finishField;
    @FXML
    TextField distanceField;

    @FXML
    public void initialize(){
        Pattern validIntegerText = Pattern.compile("^\\d{0,6}$");
        setupPattern(validIntegerText, distanceField, null);
        Pattern validNameText = Pattern.compile("^[A-Яа-яA-Za-z\\d.\\s]{0,30}$");
        setupPattern(validNameText, startField, null);
        setupPattern(validNameText, finishField, null);

    }
    Pattern phone = Pattern.compile("\\d{11}");
    Pattern password = Pattern.compile("\\S{5,20}");
    @FXML
    public void createButtonClick(){
        if ( distanceField.getText()!=""& startField.getText()!=""& finishField.getText()!=""){
            returningValue = startField.getText()+ Global.splitSymbol+
                    finishField.getText()+Global.splitSymbol+
                    distanceField.getText();
        }
        ((Stage)startField.getScene().getWindow()).close();

    }
    @FXML
    public void rejectClick(){
        ((Stage)startField.getScene().getWindow()).close();

    }

    public String getReturningValue(){
        return returningValue;
    }

    private void setValues(DriverWrapper values){

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
