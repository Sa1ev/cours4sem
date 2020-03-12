package sample.WindowController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.regex.Pattern;


public class AuthorizationController {
    private String returningValue = null;
    @FXML
    Button loginButton;
    @FXML
    Button rejectButton;

    @FXML
    TextField numberField;
    @FXML
    TextField passwordField;

    @FXML
    public void initialize(){
        Pattern validIntegerText = Pattern.compile("^\\d{1,11}$");

        TextFormatter<String> textFormatter = new TextFormatter<String>(new DefaultStringConverter(), "8",
                change -> {
                    String newText = change.getControlNewText() ;

                    if (validIntegerText.matcher(newText).matches()) {
                        return change ;
                    } else return null;
                });
        numberField.setTextFormatter(textFormatter);


        Pattern validPasswordText = Pattern.compile("\\S{0,20}");
        TextFormatter<String> textFormatter2 = new TextFormatter<String>(new DefaultStringConverter(), null,
                change -> {
                    String newText = change.getControlNewText() ;
                    if (validPasswordText.matcher(newText).matches()) {
                        return change ;
                    } else return null ;
                });
        passwordField.setTextFormatter(textFormatter2);
    }
    Pattern phone = Pattern.compile("\\d{11}");
    Pattern password = Pattern.compile("\\S{5,20}");
    @FXML
    public void loginClick(){
        if (phone.matcher(numberField.getText()).matches() & password.matcher(passwordField.getText()).matches()){
            returningValue = numberField.getText()+" "+passwordField.getText();
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

}
