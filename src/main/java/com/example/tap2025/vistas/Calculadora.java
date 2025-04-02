package com.example.tap2025.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Stack;

public class Calculadora extends Stage {

    private Scene escena;
    //Para la tipo pantalla de la calculadora.
    private TextField txtDisplay;
    //Para las dos partes de la calculadora, pantalla y botones.
    private VBox vBox;
    //El cuadrito donde estarán los botones, pues este permite que se divida en cuadritos.
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    private String strTeclas[] = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+", "C"};

    private String op = ""; //Se almacena la operacion ingresada.

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
        escena = new Scene(vBox, 250, 300);
        //El URI es un identificador especifico, es decir, identifica un recurso.
        //Pero estamos usando una URL, que indica la ubicación de un recurso en internet.
        escena.getStylesheets().add(getClass().getResource("/styles/calcu.css").toString());
    }

    //throws = aventar los errores a donde se manda llamar.
    //Jerarquía de Excepciones primero cuestiones especificas y después las generales.
    public void CrearBtnTeclado(){
        arBtnTeclado = new Button[5][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(5);
        gdpTeclado.setVgap(5);
        int pos = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4 && pos < strTeclas.length; j++) {
                arBtnTeclado[i][j] = new Button(strTeclas[pos]);
                //if(strTeclas[pos].equals("*"))
                    //arBtnTeclado[i][j].setId("fontBotton");
                //asignación directa.
                // arBtnTeclado[i][j].setStyle("-fx-background-color: rgba(0,0,0,0.5);");
                int finalPos = pos;
                arBtnTeclado[i][j].setOnAction(event -> EventoTeclado(strTeclas[finalPos]));
                arBtnTeclado[i][j].setPrefSize(50, 50);
                gdpTeclado.add(arBtnTeclado[i][j], j, i);
                pos++;
            }
        }
    }

    //appendtxt, setText, promtText
    private void EventoTeclado(String strTecla) {
        if (strTecla.equals("C")) {
            txtDisplay.setText("0");
            op = "";
            return;
        }

        if (strTecla.equals("=")) {
            if (validacionOp(op)) {
                double resultado = evaluarOperador(op);
                txtDisplay.setText(Double.isInfinite(resultado) ? "Error!: division/0" : String.valueOf(resultado));
                op = String.valueOf(resultado); // Para continuar con más operaciones usando el resultado.
            } else {
                txtDisplay.setText("Error!: sintaxis");
                op = ""; // Reinicia la operación en caso de error.
            }
            return;
        }

        if (txtDisplay.getText().contains("Error!")) {
            txtDisplay.setText(strTecla);
            op = strTecla;
        } else {
            // Manejo especial para permitir el signo "-" como número negativo.
            if (strTecla.equals("-") && (op.isEmpty() || "+-*/".contains("" + op.charAt(op.length() - 1)))) {
                txtDisplay.appendText(strTecla);
                op += strTecla;
                return;
            }
            // Validación para otros casos.
            if (validacionEntrada(op, strTecla)) {
                txtDisplay.appendText(strTecla);
                op += strTecla;
            }
        }
    }

    //Metodo para validar la operacion y saber que está correcta antes de evaluarla.
    private boolean validacionOp(String op){
        return op.matches("^-?[0-9]+(\\.[0-9]+)?([+\\-*/]-?[0-9]+(\\.[0-9]+)?)*$"); //Recuerdalo.
    }

    //Metodo para evitar las operaciones consecutivas o mal escritos.
    private boolean validacionEntrada(String actual, String nuevo){
        if(nuevo.equals(".")){
            //No permitir varios puntos en el mismo número.
            String[] partes = actual.split("[+\\-*/]");
            if(partes.length > 0 && partes[partes.length - 1].contains(".")){
                return false;
            }
        } else if("+-*/".contains(nuevo)){
            //No permitir operaciones consecutivas.
            if(actual.isEmpty() || "+-*/".contains("" + actual.charAt(actual.length() - 1))) {
                return false; //No permite operaciones consecutivas o al principio.
            }
        }
        return true;
    }

    private double evaluarOperador(String expresion){
        Stack<Double> numeros = new Stack<>();
        Stack<Character> operadores = new Stack<>();
        StringBuilder numero = new StringBuilder();
        boolean esNumeroNegativo = true; //Esto permite usar negativos antes y después del operador

        for(int i = 0; i < expresion.length(); i++){
            char caracter = expresion.charAt(i);

            if (Character.isDigit(caracter) || caracter == '.'){
                numero.append(caracter);
                esNumeroNegativo = false; //Ya se espera un número negativo.
            } else {
                if (numero.length() > 0){
                    numeros.push(Double.parseDouble(numero.toString()));
                    numero.setLength(0);
                }
                //Para el uso de números negativos.
                if (caracter == '-' && (i == 0 || "+-*/".contains("" + expresion.charAt(i - 1)))) {
                    numero.append(caracter); //para ser parte deñ número y no un operador.
                } else {
                    //Procesar operadores con jerarquía.
                    while (!operadores.isEmpty() && propiedades(operadores.peek()) >= propiedades(caracter)){
                        aplicarOperaciones(numeros, operadores.pop());
                    }
                    operadores.push(caracter);
                    esNumeroNegativo = true;
                }
            }
        }

        if (numero.length() > 0){
            numeros.push(Double.parseDouble(numero.toString()));
        }

        while (!operadores.isEmpty()){
            aplicarOperaciones(numeros, operadores.pop());
        }

        return numeros.pop();
    }

    private int propiedades(char operador){
        return (operador == '+' || operador == '-') ? 1 : (operador == '*' || operador == '/') ? 2 : 0;
    }

    private void aplicarOperaciones(Stack<Double> numeros, char operador){
        if (numeros.size() < 2) return;
        double b = numeros.pop();
        double a = numeros.pop();

        switch (operador){
            case '+':
                numeros.push(a + b);
                break;
            case '-':
                numeros.push(a - b);
                break;
            case '*':
                numeros.push(a * b);
                break;
            case '/':
                numeros.push(b == 0 ? Double.POSITIVE_INFINITY : a / b);
                break;
        }
    }

    public Calculadora(){
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }
}
