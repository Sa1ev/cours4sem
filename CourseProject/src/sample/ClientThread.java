package sample;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class ClientThread extends Thread{
    static Socket socket = new Socket();
    public enum Role {
        ADMIN(11), DRIVER(12), USER(13), GUEST(14);
        private int value;
        private Role(int value) {
            this.value = value;
        }
        public static boolean inEnum(int code){
            for (Role role : Role.values()){
                if (role.value==code){
                    return true;
                }
            }
            return false;
        }
    };
    public enum Commands{
        GET_TABLE(1), ADD_VALUE(2), EDIT_VALUE(3), DELETE_ONE_ELEMENT(4), CLEAR_TABLE(5), GET_PROFILE(6),
        GET_ORDER_QUEUE(21), GET_ORDER_HISTORY(22), GET_AVG_TIME_AND_DISTANCE(23), APPROVE_OR_DICLINE(24);
        private int value;
        private Commands(int value) {
            this.value = value;
        }
        public static boolean inEnum(int code){
            for (Commands commands : Commands.values()){
                if (commands.value==code){
                    return true;
                }
            }
            return false;
        }
    };


    public Object result = null;
    private int code = 1401;
    private String command = "Vehicle";

    public ClientThread(int code, String command) {
        this.code = code;
        this.command = command;

    }

    @Override
    public void run() {
        synchronized(socket){
            try {
                socket = new Socket("localhost", 3345);
                System.out.println("Client connected to socket");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!(Role.inEnum(code/100)&Commands.inEnum(code%100))){
                return;
            }
            try {
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String request = code+Global.splitSymbol+command;
                oos.writeUTF(request);
                System.out.println("reading object on thread: "+Thread.currentThread());
                while (result == null){

                    result = ois.readObject();
                }
                ois.close();
                oos.flush();

            }
            catch (IOException ex) {
                System.out.println(Thread.currentThread());
                ex.printStackTrace();
            }
            catch(ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}