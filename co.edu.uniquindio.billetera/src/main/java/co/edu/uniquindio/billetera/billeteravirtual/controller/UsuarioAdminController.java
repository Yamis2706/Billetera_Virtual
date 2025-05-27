package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.UUID;

public class UsuarioAdminController {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colId, colNombre, colCorreo, colTipo;
    @FXML private TextField txtNombre, txtCorreo;
    @FXML private ComboBox<String> cbTipo;
    @FXML private Label lblMensaje;

    private ObservableList<Usuario> listaUsuarios;

    @FXML
    public void initialize() {
        listaUsuarios = FXCollections.observableArrayList(DataUtil.cargarUsuarios());
        tablaUsuarios.setItems(listaUsuarios);

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCedula()));        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCorreo()));
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo()));

        cbTipo.setItems(FXCollections.observableArrayList("Admin", "Cliente"));
    }

    @FXML
    private void crearUsuario() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String tipo = cbTipo.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || tipo == null) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        Usuario usuario = new Usuario(UUID.randomUUID().toString(), nombre, correo, tipo);
        listaUsuarios.add(usuario);
        DataUtil.guardarUsuarios(listaUsuarios);
        tablaUsuarios.refresh();
        lblMensaje.setText("Usuario creado correctamente.");

        if ("Cliente".equals(tipo)) {
            BilleteravirtualAppController.agregarPestanaPerfilCliente(usuario);
        }
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
        String tipo = cbTipo.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || tipo == null) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        seleccionado.setNombre(nombre);
        seleccionado.setCorreo(correo);
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
        txtNombre.clear();
        txtCorreo.clear();
        cbTipo.getSelectionModel().clearSelection();
    }
}