package sample.WindowController.PopupWindows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import sample.Methods.ConnectionDB;
import sample.Methods.ConnectionParams;

import java.util.regex.Pattern;

public class ConnectionController {
    @FXML
    Button createButton;

    @FXML
    TextField ipField;
    @FXML
    TextField portField;
    @FXML
    public void initialize(){
        String zeroTo255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5]){0,3}";
        Pattern validIntegerText = Pattern.compile("^\\d{0,5}$");
        setupPattern(validIntegerText, portField, null);
        Pattern validNameText = Pattern.compile("[\\d\\.]{0,15}");
        setupPattern(validNameText, ipField, null);

    }
    Pattern ipPattern = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
    public void createButtonClick(ActionEvent actionEvent) {
        if (portField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Данные введены некорректно");

            alert.showAndWait();
        }
        else if (new Integer(portField.getText())<65535 & ipPattern.matcher(ipField.getText()).matches()){
            ConnectionParams.setIp(ipField.getText());
            ConnectionParams.setport(portField.getText());
            ConnectionDB.asyncReconnect();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Данные введены некорректно");

            alert.showAndWait();
        }
        ((Stage)ipField.getScene().getWindow()).close();
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
