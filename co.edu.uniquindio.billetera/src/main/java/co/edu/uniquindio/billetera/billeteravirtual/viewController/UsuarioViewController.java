package co.edu.uniquindio.billetera.billeteravirtual.viewController;

import co.edu.uniquindio.billeteravirtual.billetera_virtual.controller.UsuarioController;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.mapping.dto.UsuarioDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioViewController {
    UsuarioController usuarioController;
    ObservableList<UsuarioDto> listaUsuarios = FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<UsuarioDto> tableUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcNombre;

    @FXML
    private TableColumn<UsuarioDto, String> tcCedula;

    @FXML
    private TableColumn<UsuarioDto, String> tcCorreo;

    @FXML
    private TableColumn<UsuarioDto, String> tcTelefono;

    @FXML
    private TableColumn<UsuarioDto, String> tcDireccion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;


    @FXML
    void initialize() {
        usuarioController = new UsuarioController();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        tableUsuario.getItems().clear();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        listaUsuarios.addAll(usuarioController.obtenerUsuarios());
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().cedula()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().telefono()));
        tcDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccion()));
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(UsuarioDto usuarioSeleccionado) {
        if(usuarioSeleccionado != null){
            txtNombre.setText(usuarioSeleccionado.nombre());
            txtCedula.setText(usuarioSeleccionado.cedula());
            txtCorreo.setText(usuarioSeleccionado.correo());
            txtTelefono.setText(usuarioSeleccionado.telefono());
            txtDireccion.setText(usuarioSeleccionado.direccion());
        }
    }

    @FXML
    void onActualizarUsuario(ActionEvent event) {
    }

    @FXML
    void onAgregarUsuario(ActionEvent event) {
    }

    @FXML
    void onEliminarUsuario(ActionEvent event) {
    }

    @FXML
    void onNuevoUsuario(ActionEvent event) {
    }
}