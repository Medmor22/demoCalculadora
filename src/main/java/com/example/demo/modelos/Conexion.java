package com.example.demo.modelos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static String DB = "Restaurante";
    private static String usuario = "root";
    private static String password = "381632150205";
    private static String host = "localhost"; //127.0.0.1
    private static String port = "3306";

    //private String url = "jdbc:mysql://localhost:3306/";

    //Varibale global que apuntará a la BD
    public static Connection connection;

    public static void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Socked: mecanismos de comunicación para intercambiar datos entre 2 aplicaciones del mismo o de diferente tipo
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DB,usuario,password);
            System.out.println("Conectado com sucesso!");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
