package com.example.demo.vistas;

import com.example.demo.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cliente extends Stage {

    private Button btnGuardar;
    private TextField txtNomCte, txtDireccion, txtTelCte, txtEmail;
    private VBox vbox;
    private Scene escena;
    private ClientesDAO objC;
    private TableView<ClientesDAO> tbvClientes;

    public Cliente(TableView<ClientesDAO> tbvCte){
        this.tbvClientes=tbvCte;
        objC = new ClientesDAO();
        CrearUI();
        this.setTitle("Registar Cliente");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        txtNomCte = new TextField();
        txtDireccion = new TextField();
        txtTelCte = new TextField();
        txtEmail = new TextField();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(e -> {
            objC.setNomCte(txtNomCte.getText());
            objC.setTelCte(txtTelCte.getText());
            objC.setDireccion(txtDireccion.getText());
            objC.setEmailCte(txtEmail.getText());
            objC.INSERT();
            tbvClientes.setItems(objC.SELECT());
            tbvClientes.refresh();
            this.close();
        });
        vbox = new VBox(txtNomCte, txtTelCte, txtDireccion, txtEmail, btnGuardar);
        escena = new Scene(vbox,120,150);
    }
}
