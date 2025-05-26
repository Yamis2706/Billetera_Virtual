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

public class RegistroUsuarioViewController {

    @FXML private TextField txtNombre, txtCedula, txtCorreo, txtTelefono, txtDireccion;
    @FXML private PasswordField txtClave;
    @FXML private Label lblMensaje;

    @FXML
    private void onRegistrar(ActionEvent event) {
        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        String clave = txtClave.getText();

        if (nombre.isEmpty() || cedula.isEmpty() || correo.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || clave.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios");
            return;
        }

        List<Usuario> usuarios = DataUtil.cargarUsuarios();
        boolean existe = usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo));
        if (existe) {
            lblMensaje.setText("El correo ya está registrado");
            return;
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(nombre)
                .cedula(cedula)
                .correo(correo)
                .telefono(telefono)
                .direccion(direccion)
                .clave(clave)
                .build();
        usuarios.add(nuevoUsuario);
        DataUtil.guardarUsuarios(usuarios);
        lblMensaje.setText("Usuario registrado correctamente");

        // Volver al login
        try {
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billetera/billeteravirtual/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 350);
            stage.setScene(scene);
            stage.setTitle("Login - Billetera Virtual");
        } catch (IOException e) {
            lblMensaje.setText("Error al volver al login");
        }
    }
}