package com.example.tap2025;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2;
    private MenuItem mitCalculadora;

    private Scene escena;

    void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora);
        //No recibe parametros.
        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menCompetencia1);
    }

    @Override
    public void start(Stage stage) throws IOException {
        vBox = new VBox();
        stage.setTitle("Hola Mundo de Eventos :)");
        /*Por medio de #new Scene# estoy aplicando lo de un objeto anónimo*/
        stage.setScene(new Scene(vBox));
        stage.show();
        stage.setMaximized(true);


    }

    public static void main(String[] args) {
        launch();

        /*
        void clickEvent(){
            System.out.println("Evento desde un metodo :)");
        }
         */
    }
}

/*
package com.example.tap2025;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    //Declaramos los botones que vamos a utilizar.
private Button btnSaludo, btnSaludo2, btnSaludo3;
private VBox vBox;

@Override
public void start(Stage stage) throws IOException {
    btnSaludo = new Button("Bienvenido Amiguito :)");
    //btnSaludo.setOnAction(event -> clickEvent());
    btnSaludo2 = new Button("Bienvenido Amiguito :)");
    btnSaludo3 = new Button("Bienvenido Amiguito :)");
    vBox = new VBox(btnSaludo, btnSaludo2, btnSaludo3);
    vBox.setSpacing(10);
    //Padding es el margen del padre al objeto y Margin es del hijo al padre.
    vBox.setPadding(new Insets(10, 0, 0, 0));
    stage.setTitle("Hola Mundo de Eventos :)");
    //Por medio de #new Scene# estoy aplicando lo de un objeto anónimo.
    stage.setScene(new Scene(vBox, 200, 200));
    stage.show();
    stage.setMaximized(true);


}

public static void main(String[] args) {
    launch();

        /*
        void clickEvent(){
            System.out.println("Evento desde un metodo :)");
        }

}
 */