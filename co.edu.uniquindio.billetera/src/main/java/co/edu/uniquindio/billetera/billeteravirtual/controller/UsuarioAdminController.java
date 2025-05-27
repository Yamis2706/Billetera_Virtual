package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UsuarioAdminController {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colId, colNombre, colCorreo, colTelefono, colTipo;
    @FXML private TextField txtCedula, txtNombre, txtCorreo, txtTelefono;
    @FXML private ComboBox<String> cbTipo;
    @FXML private Label lblMensaje;

    private ObservableList<Usuario> listaUsuarios;

    @FXML
    public void initialize() {
        listaUsuarios = FXCollections.observableArrayList(DataUtil.cargarUsuarios());
        tablaUsuarios.setItems(listaUsuarios);

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCedula()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCorreo()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo()));

        cbTipo.setItems(FXCollections.observableArrayList("Admin", "Usuario"));

        // Listener para cargar datos al seleccionar un usuario
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>() {
            @Override
            public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
                if (newValue != null) {
                    txtCedula.setText(newValue.getCedula());
                    txtNombre.setText(newValue.getNombre());
                    txtCorreo.setText(newValue.getCorreo());
                    txtTelefono.setText(newValue.getTelefono());
                    cbTipo.setValue(newValue.getTipo());
                    txtCedula.setDisable(true); // No permitir cambiar el ID
                }
            }
        });
    }

    @FXML
    private void crearUsuario() {
        String cedula = txtCedula.getText();
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();
        String tipo = cbTipo.getValue();

        if (cedula.isEmpty() || nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || tipo == null) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }
        if (cedula.length() != 4 || !cedula.matches("\\d{4}")) {
            lblMensaje.setText("El ID debe tener 4 dígitos numéricos.");
            return;
        }
        if (listaUsuarios.stream().anyMatch(u -> u.getCedula().equals(cedula))) {
            lblMensaje.setText("Ya existe un usuario con ese ID.");
            return;
        }

        Usuario usuario = new Usuario(cedula, nombre, correo, telefono);
        usuario.setTipo(tipo);
        listaUsuarios.add(usuario);
        DataUtil.guardarUsuarios(listaUsuarios);
        tablaUsuarios.refresh();
        lblMensaje.setText("Usuario creado correctamente.");

        crearPerfilFXML(nombre);

        limpiarCampos();
    }

    @FXML
    private void actualizarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblMensaje.setText("Seleccione un usuario.");
            return;
        }
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();
        String tipo = cbTipo.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || tipo == null) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        seleccionado.setNombre(nombre);
        seleccionado.setCorreo(correo);
        seleccionado.setTelefono(telefono);
        seleccionado.setTipo(tipo);
        DataUtil.guardarUsuarios(listaUsuarios);
        tablaUsuarios.refresh();
        lblMensaje.setText("Usuario actualizado.");
        limpiarCampos();
    }

    @FXML
    private void eliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblMensaje.setText("Seleccione un usuario.");
            return;
        }
        listaUsuarios.remove(seleccionado);
        DataUtil.guardarUsuarios(listaUsuarios);
        tablaUsuarios.refresh();
        lblMensaje.setText("Usuario eliminado.");
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCedula.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        cbTipo.getSelectionModel().clearSelection();
        txtCedula.setDisable(false);
        tablaUsuarios.getSelectionModel().clearSelection();
    }

    private void crearPerfilFXML(String nombreUsuario) {
        String contenidoFXML =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<?import javafx.scene.layout.AnchorPane?>\n" +
                        "<?import javafx.scene.control.Label?>\n" +
                        "<AnchorPane xmlns:fx=\"http://javafx.com/fxml\" prefWidth=\"400\" prefHeight=\"200\">\n" +
                        "    <children>\n" +
                        "        <Label text=\"Perfil de " + nombreUsuario.replace("\"", "") + "\" layoutX=\"100\" layoutY=\"80\" style=\"-fx-font-size: 18px;\"/>\n" +
                        "    </children>\n" +
                        "</AnchorPane>";

        try {
            String nombreArchivo = "src/main/resources/co/edu/uniquindio/billetera/billeteravirtual/Perfil_" + nombreUsuario.replaceAll("\\s+", "") + ".fxml";
            Files.write(Paths.get(nombreArchivo), contenidoFXML.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            lblMensaje.setText("No se pudo crear el perfil FXML.");
        }
    }
}