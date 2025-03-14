package com.example.tap2025.componentes;

import javafx.scene.control.ProgressBar;

import java.util.Random;

public class Hilo extends Thread{

    private ProgressBar pgbRuta;

    //darle nombre a nuestro hilo
    public Hilo(String nombre, ProgressBar pgb){
        super(nombre);
        this.pgbRuta = pgb;
    }

    @Override
    public void run() {
        super.run();
        double avance = 0;
        while (avance < 1) {
            avance += Math.random() * .01;
            this.pgbRuta.setProgress(avance);
            try {
                sleep((long)(Math.random()*500));
            } catch (InterruptedException i) {

            }
        }
    }

        /*
        //lo que yo haga en este metodo va a establecer el funcionamiento del hilo
        for (int i = 1; i <=10 ; i++) {
            //vamos a pedir que se duerma el hilo para poder consultar en cual km va
            //se multiplica por 3000 para que el rango sea de 0 a 3 milisegundos
            try {
                sleep((long)(Math.random()*3000));
                //el InterruptedException es especificamente de hilos
            } catch (InterruptedException e) {
                //esta es una excepción más global, más de ejecución de entorno
                //throw new RuntimeException(e);
            }
            System.out.println("El corredor " + this.getName() + " llegó al KM " + i);
        }
         */
    //}
}
