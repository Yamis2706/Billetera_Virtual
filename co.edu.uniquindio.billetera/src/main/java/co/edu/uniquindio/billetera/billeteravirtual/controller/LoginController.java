package co.edu.uniquindio.billetera.billeteravirtual.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button btnIngresar;

    @FXML
    private void handleIngresar(ActionEvent event) {
        System.out.println("Intentando cargar BilleteraVirtualApp.fxml");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billetera/billeteravirtual/controller/BilleteraVirtualApp.fxml"));
            if (fxmlLoader.getLocation() == null) {
                System.out.println("No se encontró BilleteraVirtualApp.fxml en la ruta especificada.");
                return;
            }
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Administrador");
            stage.setScene(scene);
            stage.show();

            // Cierra la ventana actual de forma segura
            Stage actualStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            actualStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar BilleteraVirtualApp.fxml: " + e.getMessage());
        }
    }
}