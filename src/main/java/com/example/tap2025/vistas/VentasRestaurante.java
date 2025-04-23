package com.example.tap2025.vistas;

import com.example.tap2025.modelos.Producto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

/*
En esta clase se realiza: la pantalla sin necesidad del teclado, botones grandes con las categorías, al
presionar una categoría se muestran los productos que contiene, cada producto es un botón que al ser presionado
se agrega a la bolsa de pedidos, se muestra el pedido en una tabla con cantidad y total del producto elegido.
 */

public class VentasRestaurante {
    private Map<String, List<Producto>> categoriaProductos = new HashMap<>();
    private List<Producto> bolsaPedidos = new ArrayList<>();
    private VBox vBoxProductos = new VBox(10);
    private TableView<Producto> tblVPedidos = new TableView<>();
    private Label lblTotal = new Label("Total: $0.00");

    public void mostrarPantallaProd(Stage stage){
        inicializarProductos();

        //Este es el layout principal.
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        //Las categorías.
        HBox hBoxCategorias = new HBox(10);
        hBoxCategorias.setAlignment(Pos.CENTER);
        hBoxCategorias.setPadding(new Insets(15));
        for (String categoria : categoriaProductos.keySet()){
            Button btn = new Button(categoria);
            btn.setStyle("-fx-font-size: 18px; -fx-padding: 10, 20;");
            btn.setOnAction(event -> mostrarCategoriaProd(categoria));
            hBoxCategorias.getChildren().add(btn);
        }
        root.setTop(hBoxCategorias);

        //Productos.
        vBoxProductos.setPadding(new Insets(10));
        vBoxProductos.setAlignment(Pos.CENTER_LEFT);
        root.setCenter(vBoxProductos);

        //Pedidos.
        VBox vBoxPedido = new VBox(10);
        vBoxPedido.setPadding(new Insets(10));
        vBoxPedido.setAlignment(Pos.TOP_CENTER);

        tblVPedidos = new TableView<>();
        TableColumn<Producto, String> tblColNombre = new TableColumn<>("Producto");
        tblColNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Producto, Integer> tblColCantidad = new TableColumn<>("Cantidad");
        tblColCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        TableColumn<Producto, Double> tblColTotal = new TableColumn<>("Total");
        tblColTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblVPedidos.getColumns().addAll(tblColNombre, tblColCantidad, tblColTotal);
        tblVPedidos.setPrefHeight(300);

        Button btnFinalizar = new Button("Finalizar Pedido");
        btnFinalizar.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        btnFinalizar.setOnAction(event -> finalizarPedido());
        Button btnAdministrador = new Button("Administrar productos");
        btnAdministrador.setOnAction(e -> {
            LoginAdministrador loginAdmin = new LoginAdministrador();
            loginAdmin.mostrar(stage);
        });


        vBoxPedido.getChildren().addAll(new Label("Pedido Actual:"), tblVPedidos, lblTotal, btnFinalizar);
        root.setRight(vBoxPedido);

        //De esta forma se mostrará la escena.
        Scene escena = new Scene(root, 1000, 600);
        stage.setTitle("Sistema de ventas");
        stage.setScene(escena);
        stage.show();
    }

    private void mostrarCategoriaProd(String categoria){
        vBoxProductos.getChildren().clear();
        List<Producto> productos = categoriaProductos.getOrDefault(categoria, new ArrayList<>());
        for (Producto producto : productos){
            Button btn = new Button(producto.getNombre() + "\n$" + producto.getPrecio());
            btn.setPrefSize(150, 100);
            btn.setStyle("-fx-font-size: 14px;");
            btn.setOnAction(event -> agregarCompra(producto));
            vBoxProductos.getChildren().add(btn);
        }
    }

    private void agregarCompra(Producto prodElegido){
        Optional<Producto> existente = bolsaPedidos.stream().filter(producto -> producto.getNombre().equals(prodElegido.getNombre())).findFirst();

        if (existente.isPresent()){
            existente.get().incrementarCant();
        } else {
            bolsaPedidos.add(new Producto(prodElegido.getNombre(), prodElegido.getPrecio(), 1));
        }
        actualizarTablaPedidos();
    }

    private void actualizarTablaPedidos(){
        tblVPedidos.getItems().setAll(bolsaPedidos);
        double total = bolsaPedidos.stream().mapToDouble(Producto::getTotal).sum();
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    private void finalizarPedido(){
        if (bolsaPedidos.isEmpty()){
            Alert alerta = new Alert(Alert.AlertType.WARNING, "No hay productos en el pedido", ButtonType.OK);
            alerta.showAndWait();
            return;
        }
        seleccionarMesa();
    }

    private void seleccionarMesa(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Seleccionar mesa");
        dialog.setHeaderText("Asignar el pedido a la mesa");

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(20));

        ToggleGroup tgMesas = new ToggleGroup();
        for (int i = 1; i <= 10; i++) {
            RadioButton rb = new RadioButton("Mesa " + i);
            rb.setToggleGroup(tgMesas);
            gp.add(rb, (i - 1) % 5, (i - 1) / 5);
        }

        dialog.getDialogPane().setContent(gp);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK && tgMesas.getSelectedToggle() != null){
                return ((RadioButton) tgMesas.getSelectedToggle()).getText();
            }
            return null;
        });

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(mesaSeleccionada -> {
            String resumen = "Pedido asignado a " + mesaSeleccionada + "\n" + "Productos:\n";

            for (Producto p : bolsaPedidos){
                resumen += "- " + p.getCantidad() + " x " + p.getNombre() + "\n";
            }

            Alert confirm = new Alert(Alert.AlertType.INFORMATION, resumen, ButtonType.OK);
            confirm.setTitle("Pedido registrado :)");
            confirm.setHeaderText("¡Pedido registrado!");
            confirm.showAndWait();

            bolsaPedidos.clear();
            actualizarTablaPedidos();
        });
    }

    private void inicializarProductos(){
        categoriaProductos.put("Entradas", Arrays.asList(
           new Producto("Alitas 10 piezas", 129, "/images/Entradas/Alitas10.jpg"),
           new Producto("Alitas 15 piezas", 189, "/images/Entradas/Alitas15.jpg"),
           new Producto("Alitas 20 piezas", 249, "/images/Entradas/Alitas20.jpg"),
           new Producto("Boneless 10 piezas", 139, "/images/Entradas/Boneless10.jpg"),
           new Producto("Boneless 15 piezas", 199, "/images/Entradas/Boneless15.jpg"),
           new Producto("Boneless 20 piezas", 269, "/images/Entradas/Boneless20.jpg"),
           new Producto("Ceviche de mariscos", 293, "/images/Entradas/CevicheMariscos.jpg"),
           new Producto("Tártara de atún", 195, "/images/Entradas/TartaraAtun.jpg"),
           new Producto("Costillas BBQ", 279, "/images/Entradas/CostillasBqq.jpg"),
           new Producto("Cecina con guacamole", 253, "/images/Entradas/CecinaGuacamole.jpg"),
           new Producto("Aguachile", 243, "/images/Entradas/Aguachile.jpg"),
           new Producto("Papas a la francesa", 60, "/images/Entradas/PapasFrancesa.jpg"),
           new Producto("Tabla de quesos", 239, "/images/Entradas/TablaQuesos.jpg"),
           new Producto("Queso fundido", 250, "/images/Entradas/QuesoFundido.jpg")
        ));
        categoriaProductos.put("Tostadas", Arrays.asList(
           new Producto("Tostadas de tiritas de pescado", 66, "/images/Tostadas/TiritasPescado.jpg"),
           new Producto("Tostada de atún en tiras", 89, "/images/Tostadas/AtunTiras.jpg"),
           new Producto("Tostada de camarón", 71, "/images/Tostadas/Camaron.jpg"),
           new Producto("Tostada de ceviche de pescado", 71, "/images/Tostadas/CevichePescado.jpg"),
           new Producto("Tostada de ceviche de pulpo", 79, "/images/Tostadas/CevichePulpo.jpg"),
           new Producto("Tostada de marlin guisado", 71, "/images/Tostadas/MarlinGuisado.jpg")
        ));
        categoriaProductos.put("Cocteles", Arrays.asList(
           new Producto("Camarón", 235, "/images/Costeles/Camaron.jpg"),
           new Producto("Pulpo", 235, "/images/Costeles/Pulpo.jpg"),
           new Producto("Camarón y Pulpo", 235, "/images/Costeles/CamaronPulpo.jpg")
        ));
        categoriaProductos.put("Tacos", Arrays.asList(
           new Producto("Tacos de camarón", 106, "/images/Tacos/Camaron.jpg"),
           new Producto("Tacos de pulpo con camarón al ajillo", 109),
           new Producto("Tacos de jicama", 129),
           new Producto("Tacos de lechón", 151),
           new Producto("Tacos mar y tierra", 106)
        ));
        categoriaProductos.put("Pastas y sopas", Arrays.asList(
           new Producto("Fettuccine alfredo", 237),
           new Producto("Fettuccine al burro", 149),
           new Producto("Fettuccine cherry con camarón", 239),
           new Producto("Fettuccine tres quesos", 237),
           new Producto("Fettuccine oriental", 279),
           new Producto("Sopa de fideos", 69),
           new Producto("Sopa de lentejas", 79),
           new Producto("Caldo de camarón", 159),
           new Producto("Clam chowder", 109)
        ));
        categoriaProductos.put("Ensaladas", Arrays.asList(
           new Producto("Ensalada de frutos rojos", 200),
           new Producto("Ensalada de pulpo", 230),
           new Producto("Ensalada frutal con pollo", 245),
           new Producto("Ensalada mar y tierra", 300)
        ));
        categoriaProductos.put("Hamburguesas", Arrays.asList(
           new Producto("Hamburguesa de sirlon", 260),
           new Producto("Hamburguesa de res", 260),
           new Producto("Hamburguesa vegetariana", 240),
           new Producto("Hamburguesa de camarón", 260)
        ));
        categoriaProductos.put("Menú infantil", Arrays.asList(
           new Producto("Pechuga de pollo", 90),
           new Producto("Sabanita de res", 90),
           new Producto("Pizza individual", 195),
           new Producto("Nuggets de pollo", 70)
        ));
        categoriaProductos.put("Pizzas", Arrays.asList(
                new Producto("Pepperoni", 255),
                new Producto("Carnes frías", 275),
                new Producto("3 quesos", 255),
                new Producto("Margarita", 255),
                new Producto("Vegetariana", 255),
                new Producto("Mexicana", 275),
                new Producto("Pastor", 290),
                new Producto("Hawaiana", 275),
                new Producto("Champiñones con tocino", 275),
                new Producto("Camarones con espárragos",290),
                new Producto("Especial", 265),
                new Producto("Arrachera", 290)
        ));
        categoriaProductos.put("Carnes, pescados y mariscos", Arrays.asList(
           new Producto("Trucha almendrada", 309),
           new Producto("Salmón a las brasas", 349),
           new Producto("Salmón teriyaki", 369),
           new Producto("Salmón adobado", 350),
           new Producto("Pulpo a la diabla", 370),
           new Producto("Arrachera", 349),
           new Producto("Rib eye", 489),
           new Producto("Cowboy", 479),
           new Producto("Filete de res al perfil", 360),
           new Producto("Churrasco martin fierro", 340),
           new Producto("Churrasco al grill", 340),
           new Producto("Pechuga de pollo", 275)
        ));
        categoriaProductos.put("Café y postres", Arrays.asList(
           new Producto("Americano", 38),
           new Producto("Expresso", 28),
           new Producto("Cappuccino", 50),
           new Producto("Cappuccino frappé",70),
           new Producto("Café irlandés",150),
           new Producto("Carajillo", 135),
           new Producto("Canija", 135),
           new Producto("Crepas con cajeta", 120),
           new Producto("Helado", 110),
           new Producto("Conejito turin", 120),
           new Producto("Red velvet", 120),
           new Producto("Tartaleta de plátano", 120),
           new Producto("Tartaleta de uvas", 120),
           new Producto("Pan de elote", 100)
        ));
        categoriaProductos.put("Cocteleria", Arrays.asList(
           new Producto("Martini", 129),
           new Producto("Ginebra", 129),
           new Producto("Mojito", 129),
           new Producto("Daiquiri", 129),
           new Producto("Perla negra", 129),
           new Producto("Margarita", 129),
           new Producto("Clericot", 129),
           new Producto("Piña colada", 149)
        ));
        categoriaProductos.put("Cervezas", Arrays.asList(
           new Producto("Corona", 35),
           new Producto("Victoria", 35),
           new Producto("Modelo especial", 35),
           new Producto("Negra modelo", 35),
           new Producto("Pacifico", 35),
           new Producto("Corona light", 35),
           new Producto("Stella", 60),
           new Producto("Michelob ultra", 60),
           new Producto("Corona cero", 40)
        ));
        categoriaProductos.put("Bebidas", Arrays.asList(
           new Producto("Refrescos", 37),
           new Producto("Naranjada", 40),
           new Producto("Limonada", 40),
           new Producto("Agua fresca", 35)
        ));
        categoriaProductos.put("Licores", Arrays.asList(
           new Producto("Baileys", 180),
           new Producto("Jagermeister", 210),
           new Producto("Licor 43", 210),
           new Producto("Amaretto Disaronno", 215),
           new Producto("Sambuca negro", 215),
           new Producto("Cadenas", 210),
           new Producto("Fernet", 180)
        ));
        categoriaProductos.put("Ron", Arrays.asList(
           new Producto("Havana 7 años", 195),
           new Producto("Bacardí añejo", 350),
           new Producto("Bacardí blanco", 350),
           new Producto("Flor de caña 5 años", 170),
           new Producto("Flor de caña 7 años", 185),
           new Producto("Matusalem clásico", 170),
           new Producto("Matusalem platino", 155),
           new Producto("Captain morgan", 120)
        ));
        categoriaProductos.put("Tequila", Arrays.asList(
           new Producto("Don julio 70", 275),
           new Producto("Don julio reposado", 235),
           new Producto("1800 cristalino", 260),
           new Producto("1800 reposado", 200),
           new Producto("Centenario reposado", 150),
           new Producto("Centenario plata", 130),
           new Producto("Tradicional reposado", 140),
           new Producto("Tradicional plata", 140),
           new Producto("Herradura ultra", 260),
           new Producto("Herradura añejo", 260),
           new Producto("Maestro dobel diamante", 260),
           new Producto("7 leguas blanco", 175),
           new Producto("7 leguas reposado", 200)
        ));
        categoriaProductos.put("Whisky", Arrays.asList(
           new Producto("JW black label", 260),
           new Producto("JW red label", 170),
           new Producto("Buchanan's master", 295),
           new Producto("Buchanan's 12 años", 250),
           new Producto("Old parr 12 años", 250),
           new Producto("Jack Daniel's", 190),
           new Producto("Jack Daniel's honey", 190),
           new Producto("Black and white", 130)
        ));
        categoriaProductos.put("Brandy", Arrays.asList(
           new Producto("Magno", 170),
           new Producto("Torres 5 años", 155),
           new Producto("Torres 10 años", 175),
           new Producto("Torres 15 años", 230),
           new Producto("Torres 20 años", 325),
           new Producto("Fundador", 160),
           new Producto("Terry centenario", 165)
        ));
    }
}
