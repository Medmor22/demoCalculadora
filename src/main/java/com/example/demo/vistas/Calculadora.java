package com.example.demo.vistas;

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
    private TextField txtDisplay;
    private VBox vbox;
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    String strTeclas[] = {"M+","M-","MR","C","7","8","9","*","4","5","6","/","1","2","3","+","0",".","=","-"};

    private String operador = "";
    private double operando1 = 0;
    private boolean nuevoNumero = true;
    private boolean errorFlag = false;
    private boolean puntoUsado = false;
    private boolean resultadoMostrado = false; //bandera para permitir seguir operando sobre el resultado


    public void CrearUI(){
        CrearBtnTeclado();
        txtDisplay = new TextField("0");
       // txtDisplay.setPromptText("Teclea tu operación");
        txtDisplay.setEditable(false);
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);
        vbox = new VBox(txtDisplay, gdpTeclado);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 30, 30, 30));
        escena = new Scene(vbox,300,300);
        escena.getStylesheets().add(getClass().getResource("/styles/calcu.css").toString());
    }

//throws = aventar los errores a donde se manda llamar
    //Jerarquia de Exepciones primero cuestiones especificas y despues las generales
    public void CrearBtnTeclado(){
        arBtnTeclado = new Button[5][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(5);
        gdpTeclado.setVgap(5);
        int pos = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                arBtnTeclado[i][j] = new Button(strTeclas[pos]);

                // Si es "C", asignamos la acción de limpiar
                if (strTeclas[pos].equals("C")) {
                    arBtnTeclado[i][j].setOnAction(e -> LimpiarCalculadora());
                } else {
                    int finalPos = pos;
                    arBtnTeclado[i][j].setOnAction(e -> EventoTeclado(strTeclas[finalPos]));
                }

               // if(strTeclas[pos].equals("*"))
               //     arBtnTeclado[i][j].setId("fontButton");
                //asignación directa
                //    arBtnTeclado[i][j].setStyle("-fx-background-color: rgba(0,0,0,0.5);");

                //int finalPos = pos;
                //arBtnTeclado[i][j].setOnAction(e -> EventoTeclado(strTeclas[finalPos]));
                arBtnTeclado[i][j].setPrefSize(60,60);
                gdpTeclado.add(arBtnTeclado[i][j],j,i);
                pos++;
            }
        }
    }
//appendtxt, setText, promtText
private void EventoTeclado(String strTecla) {
    // Si hubo un error previo, limpiar antes de continuar
    if (errorFlag) {
        txtDisplay.setText("0");
        errorFlag = false;
        puntoUsado = false;
        resultadoMostrado = false;
    }

    if ("0123456789".contains(strTecla)) {
        if (nuevoNumero || txtDisplay.getText().equals("Syntax Error")) {
            txtDisplay.setText(strTecla);
            nuevoNumero = false;
        } else {
            txtDisplay.appendText(strTecla);
        }
        resultadoMostrado = false; // Se vuelve a escribir un número, por lo que se puede seguir operando normalmente
    }
    else if (".".equals(strTecla)) {
        if (puntoUsado) return; // Evita múltiples puntos en un número
        if (nuevoNumero) {
            txtDisplay.setText("0."); // Si es un nuevo número, empieza con "0."
            nuevoNumero = false;
        } else {
            txtDisplay.appendText(".");
        }
        puntoUsado = true;
        resultadoMostrado = false;
    }
    else if ("+-*/".contains(strTecla)) {
        // Permitir continuar con operaciones sobre el resultado
        if (resultadoMostrado) {
            operador = strTecla;
            nuevoNumero = true;
            resultadoMostrado = false;
            puntoUsado = false;
            return;
        }

        if (nuevoNumero) { // Evita operadores sin número previo
            txtDisplay.setText("Syntax Error");
            errorFlag = true;
            return;
        }

        operando1 = Double.parseDouble(txtDisplay.getText());
        operador = strTecla;
        nuevoNumero = true;
        puntoUsado = false;
    }
    else if ("=".equals(strTecla)) {
        if (nuevoNumero) { // Evita cálculos sin segundo operando
            txtDisplay.setText("Syntax Error");
            errorFlag = true;
            return;
        }

        double operando2 = Double.parseDouble(txtDisplay.getText());

        // Verificar división por cero
        if (operador.equals("/") && operando2 == 0) {
            txtDisplay.setText("Syntax Error");
            errorFlag = true;
            return;
        }

        double resultado = realizarOperacion(operando1, operando2, operador);
        txtDisplay.setText(String.valueOf(resultado));

        //Ahora se puede seguir operando sobre el resultado
        operando1 = resultado;
        nuevoNumero = true;
        puntoUsado = false;
        resultadoMostrado = true; // Indica que se acaba de mostrar un resultado
    }
}

    private double realizarOperacion(double operando1, double operando2, String operador){
        switch (operador){
            case "+": return operando1 + operando2;
            case "-": return operando1 - operando2;
            case "*": return operando1 * operando2;
            case "/": return operando1 / operando2;
            default: return operando2;
        }
    }

    private void LimpiarCalculadora(){
        txtDisplay.setText("0");
        operador = "";
        operando1 = 0;
        nuevoNumero = true;
        errorFlag = false;
        puntoUsado = false;
        resultadoMostrado = false;
    }

    public Calculadora() {
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }
}
