package com.example.tap2025.vistas;

import com.example.tap2025.modelos.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class CrudProductos {
    private final ObservableList<Producto> productos = FXCollections.observableArrayList();

     public void mostrar (Stage stage){
         //Secciones.
         TextField txtFieldNombre = new TextField();
         TextField txtFieldPrecio = new TextField();
         ComboBox<String> comboBoxCategoria = new ComboBox<>();
         comboBoxCategoria.getItems().addAll("Entradas", "Platillos", "Bebidas", "Postres");
         Button btnImagen = new Button("Elegir Imagen");
         Label lblImagen = new Label("Sin imagen");
         Button btnAgregar = new Button("Agregar Producto");

         //Tabla.
         TableView<Producto> tableView = new TableView<>();
         TableColumn<Producto, String> tblColNombre = new TableColumn<>("Nombre");
         TableColumn<Producto, Double> tblColPrecio = new TableColumn<>("Precio");
         TableColumn<Producto, String> tblColCategoria = new TableColumn<>("Categoría");
         TableColumn<Producto, String> tblColImagen = new TableColumn<>("Imagen");

         tblColNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
         tblColPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
         tblColCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
         tblColImagen.setCellValueFactory(new PropertyValueFactory<>("imagen"));

         tableView.getColumns().addAll(tblColNombre, tblColPrecio, tblColCategoria, tblColImagen);
         tableView.setItems(productos);

         //Imágenes.
         btnImagen.setOnAction(event -> {
             FileChooser fileChooser = new FileChooser();
             fileChooser.setTitle("Elegir Imagen");
             fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*jpeg"));
             File fileImagen = fileChooser.showOpenDialog(stage);
             if (fileImagen != null){
                 lblImagen.setText(fileImagen.getAbsolutePath());
             }
         });

         //Agregar datos.
         btnAgregar.setOnAction(event -> {
             try{
                 String nombre = txtFieldNombre.getText();
                 double precio = Double.parseDouble(txtFieldPrecio.getText());
                 String categoria = comboBoxCategoria.getValue();
                 String imagen = lblImagen.getText();

                 if (nombre.isEmpty() || categoria == null || imagen.equals("Sin imagen")){
                     throw new IllegalArgumentException("Faltan datos");
                 }

                 productos.add(new Producto(nombre, precio, categoria, imagen));
                 txtFieldNombre.clear();
                 txtFieldPrecio.clear();
                 comboBoxCategoria.setValue(null);
                 lblImagen.setText("Sin imagen");
             } catch (Exception e) {
                 Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al agregar producto: " + e.getMessage());
                 alerta.showAndWait();
             }
         });

         //Editar y eliminar.
         Button btnEditar = new Button("Editar");
         Button btnEliminar = new Button("Eliminar");

         btnEditar.setOnAction(event -> {
             Producto elegido = tableView.getSelectionModel().getSelectedItem();
             if(elegido != null){
                 txtFieldNombre.setText(elegido.getNombre());
                 txtFieldPrecio.setText(String.valueOf(elegido.getPrecio()));
                 comboBoxCategoria.setValue(elegido.getCategoria());
                 lblImagen.setText(elegido.getImagen());
                 productos.remove(elegido);
             }
         });

         btnEliminar.setOnAction(event -> {
             Producto elegido = tableView.getSelectionModel().getSelectedItem();
             if (elegido != null) {
                 productos.remove(elegido);
             }
         });

         //Layout.
         VBox vBoxContenido = new VBox(10,
                 new Label("Nombre"), txtFieldNombre,
                 new Label("Precio"), txtFieldPrecio,
                 new Label("Categoría"), comboBoxCategoria,
                 btnImagen, lblImagen,
                 btnAgregar, btnEditar, btnEliminar
         );

         vBoxContenido.setPadding(new Insets(10));

         VBox vBoxTabla = new VBox(tableView);
         vBoxTabla.setPadding(new Insets(10));

         HBox root = new HBox(20, vBoxContenido, vBoxTabla);
         root.setPadding(new Insets(15));

         Scene escena = new Scene(root, 900, 400);
         stage.setScene(escena);
         stage.setTitle("Gestionar Productos");
         stage.show();
     }
}
