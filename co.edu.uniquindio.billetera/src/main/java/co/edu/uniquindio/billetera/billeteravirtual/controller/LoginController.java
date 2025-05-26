package co.edu.uniquindio.billetera.billeteravirtual.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private RadioButton rbUsuario;
    @FXML
    private RadioButton rbAdmin;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtClave;
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Label lblMensaje;

    @FXML
    private void onSeleccionarTipo(ActionEvent event) {
        // Lógica para mostrar campos según el tipo seleccionado
        boolean mostrar = rbUsuario.isSelected() || rbAdmin.isSelected();
        txtCorreo.setVisible(mostrar);
        txtClave.setVisible(mostrar);
        btnIngresar.setVisible(mostrar);
        btnRegistrar.setVisible(rbUsuario.isSelected());
    }

    @FXML
    private void onLogin(ActionEvent event) {
        // Aquí va la lógica de autenticación
        handleIngresar(event);
    }

    @FXML
    private void onMostrarRegistro(ActionEvent event) {
        // Aquí va la lógica para mostrar la ventana de registro
        lblMensaje.setText("Funcionalidad de registro no implementada.");
    }

    @FXML
    private void handleIngresar(ActionEvent event) {
        try {
            var resource = getClass().getResource("/co/edu/uniquindio/billetera/billeteravirtual/BilleteraVirtualApp.fxml");
            if (resource == null) {
                lblMensaje.setText("No se encontró la ventana principal.");
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("BilleteraVirtualApp");
            stage.setScene(scene);
            stage.show();

            Stage actualStage = (Stage) btnIngresar.getScene().getWindow();
            actualStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            lblMensaje.setText("Error al abrir la ventana principal.");
        }
    }
}