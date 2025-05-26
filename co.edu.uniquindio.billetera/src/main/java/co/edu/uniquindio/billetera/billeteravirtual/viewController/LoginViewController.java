package co.edu.uniquindio.billetera.billeteravirtual.viewController;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoginViewController {

    @FXML private RadioButton rbUsuario, rbAdmin;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private Button btnIngresar, btnRegistrar;
    @FXML private Label lblMensaje;

    private ToggleGroup tipoIngreso;

    @FXML
    public void initialize() {
        tipoIngreso = new ToggleGroup();
        rbUsuario.setToggleGroup(tipoIngreso);
        rbAdmin.setToggleGroup(tipoIngreso);
    }

    @FXML
    private void onSeleccionarTipo(ActionEvent event) {
        boolean esUsuario = rbUsuario.isSelected();
        txtCorreo.setVisible(true);
        txtClave.setVisible(true);
        btnIngresar.setVisible(true);
        btnRegistrar.setVisible(esUsuario);
        txtCorreo.setPromptText(esUsuario ? "Correo" : "Usuario");
        lblMensaje.setText("");
    }

    @FXML
    private void onLogin(ActionEvent event) {
        String correo = txtCorreo.getText();
        String clave = txtClave.getText();

        if (rbAdmin.isSelected()) {
            if (correo.equals(DataUtil.ADMIN_CORREO) && clave.equals(DataUtil.ADMIN_CLAVE)) {
                abrirVentanaAdministrador();
            } else {
                lblMensaje.setText("Correo o clave de administrador incorrectos");
            }
        } else if (rbUsuario.isSelected()) {
            List<Usuario> usuarios = DataUtil.cargarUsuarios();
            boolean encontrado = usuarios.stream()
                    .anyMatch(u -> u.getCorreo().equals(correo) && u.getClave().equals(clave));
            if (encontrado) {
                abrirVentanaUsuario();
            } else {
                lblMensaje.setText("Correo o clave incorrectos");
            }
        }
    }

    @FXML
    private void onMostrarRegistro(ActionEvent event) {
        try {
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billetera/billeteravirtual/RegistroUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            stage.setScene(scene);
            stage.setTitle("Registro de Usuario");
        } catch (IOException e) {
            lblMensaje.setText("Error al cargar el registro");
        }
    }

    private void abrirVentanaUsuario() {
        try {
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billetera/billeteravirtual/Usuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 824, 589);
            stage.setScene(scene);
            stage.setTitle("Usuario - Billetera Virtual");
        } catch (IOException e) {
            lblMensaje.setText("Error al cargar la ventana de usuario");
        }
    }

    private void abrirVentanaAdministrador() {
        try {
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billetera/billeteravirtual/BilleteraVirtualApp.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Billetera Virtual - Administrador");
        } catch (IOException e) {
            lblMensaje.setText("Error al cargar la ventana de administrador");
        }
    }
}