package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.WindowController.AuthorizationController;
import sample.WindowController.CreateDriverController;
import sample.WindowController.CreateUserController;
import sample.WindowController.CreateVehicleController;

import java.io.IOException;

public class WindowsCreator {
    public static String createAuthorizationWindow(){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/authorizationwindow.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
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
    public static String createUserCreationWindow(){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/createuserwindow.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Окно создания пользователя");
            stage.setScene(new Scene(root, 400, 300));
            stage.setWidth(400);
            stage.setHeight(300);
            stage.setMinWidth(400);
            stage.setMinHeight(300);
            stage.setMaxWidth(400);
            stage.setMaxHeight(300);
            stage.showAndWait();
            return loader.<CreateUserController>getController().getReturningValue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String createDriverCreationWindow(){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/createdriverwindow.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Окно создания водителя");
            stage.setScene(new Scene(root, 400, 300));
            stage.setWidth(400);
            stage.setHeight(300);
            stage.setMinWidth(400);
            stage.setMinHeight(300);
            stage.setMaxWidth(400);
            stage.setMaxHeight(300);
            stage.showAndWait();
            return loader.<CreateDriverController>getController().getReturningValue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String createVehicleWindow(){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/createvehiclewindow.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Окно создания транспорта");
            stage.setScene(new Scene(root, 400, 300));
            stage.setWidth(400);
            stage.setHeight(300);
            stage.setMinWidth(400);
            stage.setMinHeight(300);
            stage.setMaxWidth(400);
            stage.setMaxHeight(300);
            stage.showAndWait();
            return loader.<CreateVehicleController>getController().getReturningValue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
