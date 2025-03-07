package com.example.demo;

import com.example.demo.modelos.Conexion;
import com.example.demo.vistas.Calculadora;
import com.example.demo.vistas.ListaClientes;
import com.example.demo.vistas.Rompecabezas;
import com.example.demo.vistas.VentasRestaurante;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1;
    private MenuItem mitCalculadora, mitRestaurante, mitRompecabezas;
    private Scene scene;
    private Scene escena;

    void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(e -> new Calculadora());
        mitRestaurante = new MenuItem("Restaurante");
        mitRestaurante.setOnAction(event -> new ListaClientes());
        mitRompecabezas = new MenuItem("Rompecabezas");
        mitRompecabezas.setOnAction(e -> new Rompecabezas());
        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitRestaurante, mitRompecabezas);
        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menCompetencia1);
        vBox = new VBox(mnbPrincipal);
        escena = new Scene(vBox);
        escena.getStylesheets().add(getClass().getResource("/styles/main.css").toString());
    }

    @Override
    public void start(Stage stage) throws IOException {
        Conexion.createConnection();
        CrearUI();
        stage.setTitle("Hola mundo de eventos");
        stage.setScene(escena);
        stage.show();
        stage.setMaximized(true);
    }


    public static void main(String[] args) {
        launch();
    }
}