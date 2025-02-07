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
        CrearKeyboard();
        txtDisplay = new TextField("0");
        //Esto es para que ya no pueda escribir en la tipo pantalla.
        txtDisplay.setEditable(false);
        //Alineado hacia la derecha.
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);
        vBox = new VBox(txtDisplay, gdpTeclado);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        escena = new Scene(vBox, 200, 200);
    }

    public void CrearKeyboard(){
        arBtnTeclado = new Button[4][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(5);
        gdpTeclado.setVgap(5);
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBtnTeclado[i][j] = new Button(strTeclas[pos]);
                arBtnTeclado[i][j].setPrefSize(50, 50);
                gdpTeclado.add(arBtnTeclado[i][j], j, i);
                pos++;
            }
        }
    }

    public Calculadora(){
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }
}
