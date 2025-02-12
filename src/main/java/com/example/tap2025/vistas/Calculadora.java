package com.example.tap2025.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {

    private Scene escena;
    //Para la tipo pantalla de la calculadora.
    private TextField txtDisplay;
    //Para las dos partes de la calculadora, pantalla y botones.
    private VBox vBox;
    //El cuadrito donde estarán los botones, pues este permite que se divida en cuadritos.
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    String strTeclas[] = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "=", "0", ".", "-"};

    public void CrearUI(){
        CrearBtnTeclado();
        txtDisplay = new TextField("0");
        //txtDisplay.setPromptText("Teclea tu operación");
        //Esto es para que ya no pueda escribir en la tipo pantalla.
        txtDisplay.setEditable(false);
        //Alineado hacia la derecha.
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);
        vBox = new VBox(txtDisplay, gdpTeclado);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 20));
        escena = new Scene(vBox, 200, 200);
    }

    //throws = aventar los errores a donde se manda llamar.
    //Jerarquía de Excepciones primero cuestiones especificas y después las generales.
    public void CrearBtnTeclado(){
        arBtnTeclado = new Button[4][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(5);
        gdpTeclado.setVgap(5);
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBtnTeclado[i][j] = new Button(strTeclas[pos]);
                int finalPos = pos;
                arBtnTeclado[i][j].setOnAction(event -> EventoTeclado(strTeclas[finalPos]));
                arBtnTeclado[i][j].setPrefSize(50, 50);
                gdpTeclado.add(arBtnTeclado[i][j], j, i);
                pos++;
            }
        }
    }

    //appendtxt, setText, promtText
    private void EventoTeclado(String strTecla){
        txtDisplay.appendText(strTecla);
    }

    public Calculadora(){
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }
}
