package com.example.tap2025.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginAdministrador {
    public void mostrar(Stage primaryStage){
        Stage stgLogin = new Stage();
        stgLogin.setTitle("Login Administrador :)");
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(10);
        gp.setVgap(10);

        Label lblUsuario = new Label("Usuario: ");
        TextField txtFieldUsuario = new TextField();
        Label lblPassword = new Label("Contraseña: ");
        PasswordField pssField = new PasswordField();
        Button btnLogin = new Button("Iniciar Sesión");
        Label lblTexto = new Label();

        gp.add(lblUsuario, 0, 0);
        gp.add(txtFieldUsuario, 1, 0);
        gp.add(lblPassword, 0, 1);
        gp.add(pssField, 1, 1);
        gp.add(btnLogin, 1, 2);
        gp.add(lblTexto, 1,3);

        btnLogin.setOnAction(event -> {
            String usuario = txtFieldUsuario.getText();
            String password = pssField.getText();

            if (usuario.equals("Javi") && password.equals("1234")){
                stgLogin.close();
                new CrudProductos().mostrar(primaryStage); //Así se abre el CRUD
            } else {
                lblTexto.setText("Datos incorrectos.");
            }
        });

        Scene escena = new Scene(gp, 300, 200);
        stgLogin.setScene(escena);
        stgLogin.show();
    }
}
