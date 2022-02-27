package com.example.exam01.db;

public class DBQuerys {
    static public String insertQuery(String email, String pwd){
        return "INSERT INTO Users(Email,Pwd) VALUES('" + email + "', '" + pwd + "');";
    }
    static public String countQuery(){
        return "SELECT COUNT(*) FROM Users;";
    }
    static public String validateQuery(String email, String pwd){
        return "SELECT * FROM Users WHERE Email='" + email + "' and Pwd='" +pwd + "';" ;
    }
    static public String existEmailQuery(){
        return "SELECT email * from WHERE ID=1";
    }
}
