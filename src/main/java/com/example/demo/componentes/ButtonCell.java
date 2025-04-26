package com.example.demo.componentes;

import com.example.demo.modelos.ClientesDAO;
import com.example.demo.vistas.Cliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCell extends TableCell<ClientesDAO,String> {

    private Button btnCelda;
    private String strLabelBtn;

    public ButtonCell(String label) {

        strLabelBtn = label;
        btnCelda = new Button(strLabelBtn);
        btnCelda.setOnAction(event -> {
            ClientesDAO objC =this.getTableView().getItems().get(this.getIndex()); //Recuperamos el objeto con todos sus atributos
           if(strLabelBtn.equals("Editar")) {
               new Cliente(this.getTableView(),objC); //Mandar la referencia para refrescar cualquier cambio
           } else{
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Mensaje del sistema");
               alert.setContentText("¿Desea eliminar el registro selccionado?");
               Optional<ButtonType> opcion = alert.showAndWait();
               if(opcion.get() == ButtonType.OK){
                   objC.DELETE();
               }
           }
           this.getTableView().setItems(objC.SELECT());
           this.getTableView().refresh();
        });
    }
    
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) {
            this.setGraphic(btnCelda);
        }
    }
}
