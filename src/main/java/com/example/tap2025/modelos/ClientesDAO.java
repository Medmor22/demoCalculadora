package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ClientesDAO {
    private int idCte;
    private String nomCte;
    private String telCte;
    private String direccion;
    private String emailCte;

    public int getIdCte() {
        return idCte;
    }

    public void setIdCte(int idCte) {
        this.idCte = idCte;
    }

    public String getNomCte() {
        return nomCte;
    }

    public void setNomCte(String nomCte) {
        this.nomCte = nomCte;
    }

    public String getTelCte() {
        return telCte;
    }

    public void setTelCte(String telCte) {
        this.telCte = telCte;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmailCte() {
        return emailCte;
    }

    public void setEmailCte(String emailCte) {
        this.emailCte = emailCte;
    }

    public void INSERT(){
        //String query = "INSERT INTO clientes VALUES(?,?,?,?,?)";
        //String sql = "insert into clientes values(?,?,?,?,?,?)";
        String query = "INSERT INTO clientes(nomCte, telCte, direccion, emailCte) " +
                "VALUES('"+nomCte+"','"+telCte+"','"+direccion+"','"+emailCte+"')";
        try{
            Statement stmt = conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            //trasa de ejecución
            e.printStackTrace();
        }
    }

    public void UPDATE(){
        String query = "UPDATE clientes SET nomCte = '"+nomCte+"', " +
                "telCte = '"+telCte+"', direccion = '"+direccion+"', " +
                "emailCte = ' "+emailCte+"' WHERE idCliente = "+idCte+";";
        try{
            Statement stmt = conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM clientes WHERE idCliente = "+idCte+";";

        try{
            Statement stmt = conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Pasar los registros (elementos) de la BD a Java a un conjunto de objetos para que pueda entenderlos
    //ObservableList tipo de objeto que requiere el componente que usaremos para mostrarlo en pantalla

    public ObservableList<ClientesDAO> SELECT(){
        String query = "SELECT * FROM clientes"; //Consulta que te trae todos los resultados de la tabla
        ObservableList<ClientesDAO> ListaC = FXCollections.observableArrayList();
        ClientesDAO objC;

        try{
            Statement stmt = conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                objC = new ClientesDAO();
                objC.setIdCte(rs.getInt("idCliente"));
                objC.setNomCte(rs.getString("nomCte"));
                objC.setTelCte(rs.getString("telCte"));
                objC.setDireccion(rs.getString("direccion"));
                objC.setEmailCte(rs.getString("emailCte"));
                ListaC.add(objC);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ListaC;
    }
}
