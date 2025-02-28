package com.example.tap2025.vistas;

import com.example.tap2025.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        btnAgregar = new Button(); //agrega una imagen, iconfinder
        //btnAgregar.setGraphic(new ImageView(getClass().getResource("/images/agregar.png").toString()));
        ImageView imv = new ImageView(getClass().getResource("/images/211872_person_add_icon.png").toString());
        /*
        ImageView imv = new ImageView(getClass().getResource("/images/....png)
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        * */
        btnAgregar.setGraphic(imv);
        tblMenu = new ToolBar(btnAgregar);
        tbvClientes = new TableView<>();
        CreateTable();

        vBox = new VBox(tblMenu, tbvClientes);
        escena = new Scene(vBox, 800, 600);

    }

    private void CreateTable() {
        ClientesDAO objC = new ClientesDAO();
        TableColumn<ClientesDAO,String> tbcNomCte = new TableColumn<>("Nombre");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        TableColumn<ClientesDAO,String> tbcDireccion = new TableColumn<>("Dirección");
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<ClientesDAO,String> tbcTelefono = new TableColumn<>("Telefono");
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telCte"));

        TableColumn<ClientesDAO,String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("emailCte"));

        tbvClientes.getColumns().addAll(tbcNomCte,tbcDireccion,tbcTelefono,tbcEmail);
        tbvClientes.setItems(objC.SELECT());
    }


}
