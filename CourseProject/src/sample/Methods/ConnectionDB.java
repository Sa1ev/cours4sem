package sample.Methods;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import sample.Thread.ClientThread;
import sample.Thread.DBListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
    static Statement statement;
    private ConnectionDB(){
        reconnect();
    }
    public static Statement getStatement() {
        if (statement==null){
            new ConnectionDB();
        }
        return statement;
    }
    public static void reconnect(){
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(ConnectionParams.getUrl(), ConnectionParams.USER, ConnectionParams.PASSWORD);
            new DBListener(connection).start();
            statement =  connection.createStatement();
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
            Platform.runLater(()->createErrorMessage());
            return;
        }
//        AdminSQLMethods.updateStatement();
//        UserSQLMethods.updateStatement();
//        DriverSQLMethods.updateStatement();
//        SQLMethods.updateStatement();

    }
    public static void asyncReconnect(){
        new ClientThread(new ClientThread.CommandHandler() {
            @Override
            public Object execute() {
                reconnect();
                return null;
            }
        }).start();
    }

    public static void createErrorMessage(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка подключения");
        alert.setHeaderText("Невозможно подключиться по адресу "+ConnectionParams.getUrl());
        alert.showAndWait();
    }
}
