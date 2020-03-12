package sample;

import sample.WindowController.AuthorizationController;

public class Account {
    public static String role = "Guest";
    public static int phone = -1;
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
        if (role == "Admin" &number == "8" &  password == "admin"){
            role = "Admin";
            return true;
        }
        else if (role == "Driver"){

        }
        else if (role == "User"){

        }
        else if (role == "Guest"){
            Account.role = "Guest";
            Account.phone = -1;
            Account.password = "";
            Account.name = "";
            Account.id = -1;
            return true;
        }
        return false;
    }
}
