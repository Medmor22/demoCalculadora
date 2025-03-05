package com.example.tap2025.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Rompecabezas {
    private Stage stageRomp;
    private GridPane grpRomp;
    private List<ImageView> piezasRomp;
    private long tiempoRomp;

    public Rompecabezas() {
        stageRomp = new Stage();
        stageRomp.setTitle("Rompecabezas Game :)");
        tiempoRomp = System.currentTimeMillis();

        BorderPane root = new BorderPane();
        grpRomp = new GridPane();
        grpRomp.setAlignment(Pos.CENTER);

        imagenesRomp();
        root.setCenter(grpRomp);

        Button botonResolver = new Button("Resolver");
        botonResolver.setOnAction(event -> );
    }

    private void imagenesRomp(){
        piezasRomp = new ArrayList<>();
        String baseRomp = "/images/RompLiloStitch/";
        for (int i = 1; i <= 9; i++) {
            Image imgLiloStitch = new Image(getClass().getResourceAsStream(baseRomp + "pieza" + i + ".png"));
            ImageView imgView = new ImageView(imgLiloStitch);
            imgView.setFitWidth(100);
            imgView.setFitHeight(100);
            piezasRomp.add(imgView);
        }

        Collections.shuffle(piezasRomp);

    }

    private void verificarRomp(){
        long tiempoFinal = System.currentTimeMillis();
        long tiempoTotal = (tiempoFinal - tiempoRomp) / 1000;

        boolean correcto = true;
        int index = 1;
        for (ImageView imgView : piezasRomp){
            if (!imgView.getImage().getUrl().contains("pieza" + index)) {
                correcto = false;
                break;
            }
            index++;
        }
        if (correcto){
            ventanaMensaje("¡Lograste resolver el rompecabezas! En " + tiempoTotal + " segundos.");
        } else {
            ventanaMensaje("Aún no haz terminado de resolver el rompecabezas :(");
        }
    }

    private void ventanaMensaje(String mensaje){
        Stage msj = new Stage();
        VBox vBox = new VBox(new Label(mensaje), new Button("BIEN"));
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 300, 150);
        msj.setScene(scene);
        msj.show();
    }
}

//ImageView imv = new ImageView(getClass().getResource("/images/211872_person_add_icon.png").toString());