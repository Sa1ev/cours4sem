package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

         //new ClientThread().run();

        Pane admin = FXMLLoader.load(getClass().getResource("WindowsStorage/adminwindow.fxml"));
        Pane driver = FXMLLoader.load(getClass().getResource("WindowsStorage/driverwindow.fxml"));
        Pane user = FXMLLoader.load(getClass().getResource("WindowsStorage/userwindow.fxml"));
        Pane guest = FXMLLoader.load(getClass().getResource("WindowsStorage/guestwindow.fxml"));


        Scene scene = new Scene(admin);

        ScreenController screenController = ScreenController.getINSTANCE();
        screenController.setScene(scene);
        screenController.addScreen("admin", admin);
        screenController.addScreen("driver", driver);
        screenController.addScreen("user", user);
        screenController.addScreen("guest", guest);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello JavaFX");
        primaryStage.setWidth(900);
        primaryStage.setHeight(900);

        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}

