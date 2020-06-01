package sample.WindowController.PopupWindows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import sample.DataWrapper.UserWrapper;

import java.util.regex.Pattern;


public class CreateUserController {
    private String[] returningValue = null;
    @FXML
    Button createButton;
    @FXML
    Button rejectButton;

    @FXML
    TextField numberField;
    @FXML
    TextField userNameField;
    @FXML
    TextField passwordField;

    @FXML
    public void initialize(){
        Pattern validIntegerText = Pattern.compile("^\\d{1,11}$");
        setupPattern(validIntegerText, numberField, "8");

        Pattern validPasswordText = Pattern.compile("\\S{0,20}");
        setupPattern(validPasswordText, passwordField, null);

        Pattern validNameText = Pattern.compile("[A-Яа-яA-Za-z\\s]{0,30}");
        setupPattern(validNameText, userNameField, null);

    }
    Pattern phone = Pattern.compile("\\d{11}");
    Pattern password = Pattern.compile("\\S{5,20}");
    @FXML
    public void createButtonClick(){
        if (phone.matcher(numberField.getText()).matches() & password.matcher(passwordField.getText()).matches() & !userNameField.getText().isEmpty()){
            returningValue = new String[3];
            returningValue[0] =  numberField.getText();
            returningValue[1] =  passwordField.getText();
            returningValue[2] =  userNameField.getText();
        }
        ((Stage)passwordField.getScene().getWindow()).close();

    }
    @FXML
    public void rejectClick(){
        ((Stage)passwordField.getScene().getWindow()).close();

    }

    public String[] getReturningValue(){
        return returningValue;
    }

    public void setValues(UserWrapper values){


        numberField.setText(values.getPhoneNumber());
        passwordField.setText(values.getPassword());
        userNameField.setText((values.getName()));

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
