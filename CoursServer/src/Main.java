import Methods.AdminSQLMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    static ExecutorService executeIt = Executors.newFixedThreadPool(2);
            public static void main(String[] args) {
                System.out.println(System.getProperty("user.dir"));
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
