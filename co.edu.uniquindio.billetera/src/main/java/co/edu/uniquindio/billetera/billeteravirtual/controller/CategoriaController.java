package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Categoria;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Comparator;
import java.util.List;

public class CategoriaController {

    @FXML
    private TableView<Categoria> tablaCategorias;
    @FXML
    private TableColumn<Categoria, String> colId;
    @FXML
    private TableColumn<Categoria, String> colNombre;
    @FXML
    private TableColumn<Categoria, String> colDescripcion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private Label lblMensaje;

    private final ObservableList<Categoria> listaCategorias = FXCollections.observableArrayList();
    private Categoria categoriaSeleccionada = null;
    private int contadorId = 1;

    @FXML
    public void initialize() {
        // Cargar categorías desde DataUtil
        List<Categoria> categorias = DataUtil.listarCategorias();
        listaCategorias.setAll(categorias);

        // Ajustar contadorId para evitar duplicados
        categorias.stream()
                .mapToInt(c -> {
                    try { return Integer.parseInt(c.getId()); } catch (Exception e) { return 0; }
                })
                .max().ifPresent(maxId -> contadorId = maxId + 1);

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colDescripcion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescripcion()));
        tablaCategorias.setItems(listaCategorias);

        tablaCategorias.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                categoriaSeleccionada = newSel;
                txtNombre.setText(newSel.getNombre());
                txtDescripcion.setText(newSel.getDescripcion());
            }
        });
    }

    private String obtenerSiguienteIdCategoria() {
        return String.valueOf(
                listaCategorias.stream()
                        .mapToInt(c -> {
                            try { return Integer.parseInt(c.getId()); } catch (Exception e) { return 0; }
                        })
                        .max()
                        .orElse(0) + 1
        );
    }

    @FXML
    private void crearCategoria() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            mostrarMensaje("Debe ingresar nombre y descripción.", false);
            return;
        }
        Categoria nueva = new Categoria("0", nombre, descripcion); // ID temporal
        DataUtil.agregarCategoria(nueva);
        reasignarIdsCategorias();
        limpiarCampos();
        mostrarMensaje("Categoría creada exitosamente.", true);
        TransaccionController.recargarCategoriasGlobal();
    }

    @FXML
    private void actualizarCategoria() {
        if (categoriaSeleccionada == null) {
            mostrarMensaje("Seleccione una categoría para actualizar.", false);
            return;
        }
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            mostrarMensaje("Debe ingresar nombre y descripción.", false);
            return;
        }
        Categoria actualizada = new Categoria(categoriaSeleccionada.getId(), nombre, descripcion);
        DataUtil.actualizarCategoria(actualizada);
        reasignarIdsCategorias();
        limpiarCampos();
        mostrarMensaje("Categoría actualizada con éxito.", true);
        TransaccionController.recargarCategoriasGlobal();
    }

    @FXML
    private void eliminarCategoria() {
        if (categoriaSeleccionada == null) {
            mostrarMensaje("Seleccione una categoría para eliminar.", false);
            return;
        }
        DataUtil.eliminarCategoria(categoriaSeleccionada.getId());
        reasignarIdsCategorias();
        limpiarCampos();
        mostrarMensaje("Categoría eliminada.", true);
        TransaccionController.recargarCategoriasGlobal();
    }

    // Agrega este método en CategoriaController
    private void reasignarIdsCategorias() {
        List<Categoria> categorias = DataUtil.listarCategorias();
        for (int i = 0; i < categorias.size(); i++) {
            categorias.get(i).setId(String.valueOf(i + 1));
        }
        DataUtil.guardarCategorias(categorias);
        listaCategorias.setAll(categorias);
    }



    private void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        categoriaSeleccionada = null;
        tablaCategorias.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String mensaje, boolean exito) {
        lblMensaje.setText(mensaje);
        lblMensaje.setTextFill(exito ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
    }
}