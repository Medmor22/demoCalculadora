package com.example.demo.vistas;

import com.example.demo.componentes.ButtonCell;
import com.example.demo.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.concurrent.Callable;

public class ListaClientes extends Stage {

    private ToolBar tblMenu;
    private TableView<ClientesDAO> tbvClientes;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaClientes() {
        CrearUI();
        this.setTitle("Lista de Clientes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvClientes = new TableView<>();
        btnAgregar = new Button(); //agrega una imagen, iconfinder
        btnAgregar.setOnAction(event -> new Cliente(tbvClientes));
        ImageView imv = new ImageView(getClass().getResource("/images/211872_person_add_icon.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);
        tblMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tblMenu, tbvClientes);
        escena = new Scene(vBox, 800, 600);

    }

    private void CreateTable() {
        ClientesDAO objC = new ClientesDAO();
        TableColumn<ClientesDAO,String> tbcNomCte = new TableColumn<>("Nombre");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        TableColumn<ClientesDAO,String> tbcTelefono = new TableColumn<>("Telefono");
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telCte"));

        TableColumn<ClientesDAO,String> tbcDireccion = new TableColumn<>("Dirección");
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<ClientesDAO,String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("emailCte"));


        TableColumn<ClientesDAO,String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
            @Override
            public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> param) {
                return new ButtonCell("Editar");
            }
        });
        TableColumn<ClientesDAO,String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
            @Override
            public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> param) {
                return new ButtonCell("Eliminar");
            }
        });

        tbvClientes.getColumns().addAll(tbcNomCte,tbcDireccion,tbcTelefono,tbcEmail);
        tbvClientes.setItems(objC.SELECT());
    }

}
