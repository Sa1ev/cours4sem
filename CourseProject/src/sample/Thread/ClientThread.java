package sample.Thread;
public class ClientThread extends Thread{

    CommandHandler handler;
    public Object result;
    public ClientThread(CommandHandler handler) {
        this.handler = handler;

    }

    @Override
    public void run() {
        result = handler.execute();
    }
    public interface CommandHandler {
        Object execute();
    }


}