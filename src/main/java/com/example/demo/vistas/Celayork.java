package com.example.demo.vistas;

import com.example.demo.componentes.Hilo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Celayork extends Stage {

    private VBox vbox;
    private GridPane gdpCalles;
    private Button btnIniciar;
    private Label[] lblRutas;
    private ProgressBar[] pgbRutas;
    private Scene escena;
    private String[] strRutas={"Ruta Pinos", "Ruta Teneria","San Juan de la Vega","Ruta Monte Blanco","Laureles"};
    private Hilo[] thrRutas;


    public Celayork() {
        CrearUI();
        this.setTitle("Calles de Celaya");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        btnIniciar = new Button("Iniciar");

        pgbRutas = new ProgressBar[5];
        lblRutas = new Label[5];
        thrRutas = new Hilo[5];
        gdpCalles = new GridPane();
        for(int i = 0; i<pgbRutas.length; i++) {
            lblRutas[i] = new Label(strRutas[i]);
            pgbRutas[i] = new ProgressBar(0);
            thrRutas[i] = new Hilo(strRutas[i],pgbRutas[i]);
            gdpCalles.add(lblRutas[i], 0, i);
            gdpCalles.add(pgbRutas[i], 1, i);
            
        }
        btnIniciar.setOnAction(e -> {
            for (int i = 0; i < pgbRutas.length; i++) {
                thrRutas[i].start();
            }
        });
        vbox = new VBox(gdpCalles,btnIniciar);
        escena = new Scene(vbox,300,200);
    }

}
