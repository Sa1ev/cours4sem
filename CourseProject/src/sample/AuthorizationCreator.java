package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.WindowController.AuthorizationController;

import java.io.IOException;

public class AuthorizationCreator {
    public static String createAuthorizationWindow(){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(AuthorizationCreator.class.getResource("WindowsStorage/authorizationwindow.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Окно авторизации");
            stage.setScene(new Scene(root, 400, 200));
            stage.setWidth(400);
            stage.setHeight(200);
            stage.setMinWidth(400);
            stage.setMinHeight(200);
            stage.setMaxWidth(400);
            stage.setMaxHeight(200);
            stage.showAndWait();
            return loader.<AuthorizationController>getController().getReturningValue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
