package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AdministradorController {

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colCorreo;
    @FXML
    private TableColumn<Usuario, String> colCedula;
    @FXML
    private TableColumn<Usuario, String> colTelefono;
    @FXML
    private TableColumn<Usuario, String> colDireccion;
    @FXML
    private Label lblMensaje;
    @FXML
    private Label lblTotalUsuarios;
    @FXML
    private Label lblUsuariosInstitucionales;
    @FXML
    private Label lblTelefonosUnicos;

    private ObservableList<Usuario> listaUsuarios;

    @FXML
    public void initialize() {
        if (tablaUsuarios != null) {
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
            colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            cargarUsuarios();
        }
    }

    public void cargarUsuarios() {
        List<Usuario> usuarios = DataUtil.cargarUsuarios();
        listaUsuarios = FXCollections.observableArrayList(usuarios);
        tablaUsuarios.setItems(listaUsuarios);
        mostrarEstadisticas();
    }

    @FXML
    public void agregarUsuario() {
        Usuario nuevo = Usuario.builder()
                .nombre("Nuevo")
                .correo("nuevo@mail.com")
                .cedula("0000")
                .telefono("0000000000")
                .direccion("Dirección")
                .build();
        listaUsuarios.add(nuevo);
        DataUtil.guardarUsuarios(listaUsuarios);
        cargarUsuarios();
        lblMensaje.setText("Usuario agregado.");
    }

    @FXML
    public void editarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            TextInputDialog dialog = new TextInputDialog(seleccionado.getNombre());
            dialog.setTitle("Editar Usuario");
            dialog.setHeaderText("Editar nombre del usuario");
            dialog.setContentText("Nombre:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(nombre -> {
                seleccionado.setNombre(nombre);
                DataUtil.guardarUsuarios(listaUsuarios);
                cargarUsuarios();
                lblMensaje.setText("Usuario editado.");
            });
        } else {
            lblMensaje.setText("Selecciona un usuario para editar.");
        }
    }

    @FXML
    public void eliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaUsuarios.remove(seleccionado);
            DataUtil.guardarUsuarios(listaUsuarios);
            cargarUsuarios();
            lblMensaje.setText("Usuario eliminado.");
        } else {
            lblMensaje.setText("Selecciona un usuario para eliminar.");
        }
    }

    public void mostrarEstadisticas() {
        if (lblTotalUsuarios != null) {
            lblTotalUsuarios.setText(String.valueOf(listaUsuarios.size()));
        }
        if (lblUsuariosInstitucionales != null) {
            long institucionales = listaUsuarios.stream()
                    .filter(u -> u.getCorreo() != null && u.getCorreo().endsWith("@uniquindio.edu.co"))
                    .count();
            lblUsuariosInstitucionales.setText(String.valueOf(institucionales));
        }
        if (lblTelefonosUnicos != null) {
            Set<String> telefonos = listaUsuarios.stream()
                    .map(Usuario::getTelefono)
                    .filter(t -> t != null && !t.isEmpty())
                    .collect(Collectors.toSet());
            lblTelefonosUnicos.setText(String.valueOf(telefonos.size()));
        }
    }
}