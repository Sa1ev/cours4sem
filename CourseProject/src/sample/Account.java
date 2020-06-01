package sample;

import sample.Methods.AdminSQLMethods;
import sample.Thread.ClientThread;

import java.util.ArrayList;

public class Account {
    public static String role = "Guest";
    public static Long phone = -1L;
    public static String password = "";
    public static String name = "";
    public static int id = -1;


    private static Account Instance = new Account();
    private Account(){

    }
    public static Account getAccount(){
        return Instance;
    }

    public static boolean authorization(String role, String number, String password){
        if (role == "Admin"){
            Account.role = "Admin";
            System.out.println(getInfo());
            return true;
        }
        else if (role == "Driver"){
            ClientThread thread = new ClientThread(()-> AdminSQLMethods.getLineByPhoneAndPassword("Driver",new Long(number),password));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<String []> result = (ArrayList)thread.result;
            if (result.size()>0){
                String[] dr = result.get(0);
                Account.role = "Driver";
                Account.phone = new Long(dr[2]);
                Account.password = dr[3];
                Account.name = dr[1];
                Account.id = new Integer(dr[0]);
                System.out.println(getInfo());
                return true;
            }
            return false;
        }
        else if (role == "User"){
            ClientThread thread = new ClientThread(()->AdminSQLMethods.getLineByPhoneAndPassword("Users",new Long(number),password));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<String []> result = (ArrayList)thread.result;
            if (result.size()>0){
                String[] dr = result.get(0);
                Account.role = "User";
                Account.phone = new Long(dr[2]);
                Account.password = dr[3];
                Account.name = dr[1];
                Account.id = new Integer(dr[0]);
                System.out.println(getInfo());
                return true;
            }
            return false;
        }
        else if (role == "Guest"){
            Account.role = "Guest";
            Account.phone = -1L;
            Account.password = "";
            Account.name = "";
            Account.id = -1;
            System.out.println(getInfo());
            return true;
        }
        return false;
    }

    public static String getInfo(){
        return String.format("Current role: %s\nPhoneNumber: %d\nPassword: %s\nId in SQL table: %d", role, phone, password, id);
    }
}
