package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Categoria;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.UUID;

public class CategoriaController {

    @FXML private TableView<Categoria> tablaCategorias;
    @FXML private TableColumn<Categoria, String> colId;
    @FXML private TableColumn<Categoria, String> colNombre;
    @FXML private TableColumn<Categoria, String> colDescripcion;
    @FXML private TextField txtId, txtNombre, txtDescripcion;
    @FXML private Label lblMensaje;

    private ObservableList<Categoria> listaCategorias;

    @FXML
    public void initialize() {
        listaCategorias = FXCollections.observableArrayList(DataUtil.listarCategorias());
        tablaCategorias.setItems(listaCategorias);

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colDescripcion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescripcion()));
    }

    @FXML
    private void crearCategoria() {
        String id = UUID.randomUUID().toString().substring(0, 5);
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        Categoria nueva = new Categoria(id, nombre, descripcion);
        DataUtil.agregarCategoria(nueva);
        listaCategorias.add(nueva);
        lblMensaje.setText("Categoría creada.");
    }

    @FXML
    private void actualizarCategoria() {
        Categoria seleccionada = tablaCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            seleccionada.setNombre(txtNombre.getText());
            seleccionada.setDescripcion(txtDescripcion.getText());
            DataUtil.actualizarCategoria(seleccionada);
            tablaCategorias.refresh();
            lblMensaje.setText("Categoría actualizada.");
        }
    }

    @FXML
    private void eliminarCategoria() {
        Categoria seleccionada = tablaCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            DataUtil.eliminarCategoria(seleccionada.getId());
            listaCategorias.remove(seleccionada);
            lblMensaje.setText("Categoría eliminada.");
        }
    }
}