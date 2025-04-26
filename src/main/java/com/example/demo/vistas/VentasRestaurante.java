package com.example.demo.vistas;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class VentasRestaurante extends Stage {

    private Panel pnlRestaurante;
    private Scene escena;

    public VentasRestaurante() {
        CrearUI();
        this.setTitle("Fondita Doña Lupe");
        this.setScene(escena);
        this.show();
    }

    void CrearUI() {
        pnlRestaurante = new Panel("Tacos el Inge");
        pnlRestaurante.getStyleClass().add("panel-primary");
        escena = new Scene(pnlRestaurante,300,200);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }


}
