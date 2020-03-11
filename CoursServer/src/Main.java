import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static String url = "jdbc:mysql://localhost:3306/coursedb?useUnicode=true&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "qwerty";
    static ExecutorService executeIt = Executors.newFixedThreadPool(1);
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (!server.isClosed()) {
                if (br.ready()) {
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        server.close();
                        break;
                    }
                }
                Socket client = server.accept();
                executeIt.execute(new CommandListener(client));
                System.out.println("Connection accepted.");
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
