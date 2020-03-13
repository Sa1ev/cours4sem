import Methods.AdminSQLMethods;
import Methods.DriverSQLMethods;
import Methods.UserSQLMethods;

import java.io.*;
import java.net.Socket;

public class CommandListener implements Runnable {

    private static Socket clientDialog;

    private static AdminSQLMethods adminSQLMethods;
    private static DriverSQLMethods driverSQLMethods;
    private static UserSQLMethods userSQLMethods;

    public CommandListener(Socket client) {
        CommandListener.clientDialog = client;
    }
    @Override
    public void run() {
        synchronized (clientDialog){
            try {
                ObjectOutputStream out = new ObjectOutputStream(clientDialog.getOutputStream());
                DataInputStream in = new DataInputStream(clientDialog.getInputStream());
                while (!clientDialog.isClosed()) {
                    //"1102 vehicle 12"
                    String str = in.readUTF();
                    System.out.println(str);
                    String[] val = str.split("凸");
                    System.out.println(Thread.currentThread());
                    Object obj = CommandDistributor.chooseRole(val);
                    out.writeObject(obj);
                    out.flush();
                }
                in.close();
                out.close();
                clientDialog.close();

            }
            catch (EOFException e) {
                System.out.println("OK!");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
