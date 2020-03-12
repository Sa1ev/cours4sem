package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import sample.WindowController.*;

import java.util.HashMap;

public class ScreenController {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private HashMap<String, FXMLLoader> loaderMap = new HashMap<>();
    private Scene main;
    static ScreenController INSTANCE = new ScreenController();

    private ScreenController() {

    }
    public static ScreenController getINSTANCE(){
        return INSTANCE;
    }

    public void setScene(Scene main){
        this.main = main;
    }

    public void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    public void addLoader(String name, FXMLLoader loader){
        loaderMap.put(name, loader);
    }

    public void removeScreen(String name){
        screenMap.remove(name);
    }

    public void activate(String name){
        main.setRoot( screenMap.get(name));
        switch (name){
            case("admin"):
                new UIPreloadThread(loaderMap.get("admin").<AdminController>getController()).start();
                break;
            case("driver"):
                new UIPreloadThread(loaderMap.get("driver").<DriverController>getController()).start();
                break;
            case("user"):
                new UIPreloadThread(loaderMap.get("user").<UserController>getController()).start();
                break;
            case("guest"):
                new UIPreloadThread( loaderMap.get("guest").<GuestController>getController());
                break;

        }
    }
}