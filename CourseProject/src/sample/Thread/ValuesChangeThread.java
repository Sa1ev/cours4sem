package sample.Thread;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ValuesChangeThread extends Thread {
    ClientThread thread;
    GenericMethod method;
    public ValuesChangeThread(ClientThread thread, GenericMethod method){
        this.thread = thread;
        this.method = method;
    }
    @Override
    public void run() {
        super.run();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!(boolean)thread.result){
            Platform.runLater(() ->
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Экземпляр с такими параметрами уже присутствует в базе данных!", ButtonType.YES);
                alert.showAndWait();
            });
        }
        else{
            if (method!=null){
                Platform.runLater(() -> method.call());
            }

        }


    }


    public interface GenericMethod {

        public void call();
    }
}
