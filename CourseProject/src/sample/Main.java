package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

         //new ClientThread().run();

        FXMLLoader adminLoader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/adminwindow.fxml"));
        FXMLLoader driverLoader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/driverwindow.fxml"));
        FXMLLoader userLoader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/userwindow.fxml"));
        FXMLLoader guestLoader = new FXMLLoader(WindowsCreator.class.getResource("WindowsStorage/guestwindow.fxml"));
        Pane admin = adminLoader.load();
        Pane driver = driverLoader.load();
        Pane user = userLoader.load();
        Pane guest = guestLoader.load();


        Scene scene = new Scene(guest);

        ScreenController screenController = ScreenController.getINSTANCE();
        screenController.setScene(scene);
        screenController.addLoader("admin", adminLoader);
        screenController.addLoader("driver", driverLoader);
        screenController.addLoader("user", userLoader);
        screenController.addLoader("guest", guestLoader);

        screenController.addScreen("admin", admin);
        screenController.addScreen("driver", driver);
        screenController.addScreen("user", user);
        screenController.addScreen("guest", guest);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Course");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}

