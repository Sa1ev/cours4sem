package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.DataWrapper.DriverWrapper;
import sample.DataWrapper.UserWrapper;
import sample.DataWrapper.VehicleWrapper;
import sample.WindowController.*;

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
    public static String createOrderWindow(){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/createorderwindow.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Окно оформления заказа");
            stage.setScene(new Scene(root, 400, 300));
            stage.setWidth(400);
            stage.setHeight(300);
            stage.setMinWidth(400);
            stage.setMinHeight(300);
            stage.setMaxWidth(400);
            stage.setMaxHeight(300);
            stage.showAndWait();
            return loader.<CreateOrderController>getController().getReturningValue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createUserEditWindow(UserWrapper item){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/createuserwindow.fxml"));
            root = loader.load();
            loader.<CreateUserController>getController().setValues(item);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Окно изменения пользователя");
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
    public static String createDriverEditWindow(DriverWrapper item){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/createdriverwindow.fxml"));
            root = loader.load();
            loader.<CreateDriverController>getController().setValues(item);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Окно изменения водителя");
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
    public static String createVehicleEditWindow(VehicleWrapper item){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/createvehiclewindow.fxml"));
            root = loader.load();
            loader.<CreateVehicleController>getController().setValues(item);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Окно изменения транспорта");
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
