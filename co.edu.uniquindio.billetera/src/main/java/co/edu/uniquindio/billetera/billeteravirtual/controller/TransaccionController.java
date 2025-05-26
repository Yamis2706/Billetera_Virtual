
package co.edu.uniquindio.billetera.billeteravirtual.controller;

import javafx.scene.control.cell.PropertyValueFactory;
import co.edu.uniquindio.billetera.billeteravirtual.model.Categoria;
import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransaccionController {

    @FXML private TableView<Transaccion> tablaTransacciones;
    @FXML private TableColumn<Transaccion, String> colIdTransaccion;
    @FXML private TableColumn<Transaccion, String> colDescripcion;
    @FXML private TableColumn<Transaccion, Double> colMonto;
    @FXML private TableColumn<Transaccion, LocalDate> colFecha;
    @FXML private TableColumn<Transaccion, String> colTipo;
    @FXML private TableColumn<Transaccion, String> colCategoria;
    @FXML private TextField txtIdTransaccion;
    @FXML private TextField txtDescripcion, txtMonto;
    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<String> cbTipo;
    @FXML private ComboBox<Categoria> cbCategoria;
    @FXML private Button btnCrear, btnModificar, btnEliminar, btnFiltrar, btnBuscarId;
    @FXML private Label lblMensaje;
    @FXML private ComboBox<Categoria> cbFiltroCategoria;

    private ObservableList<Transaccion> listaTransacciones;

    private static final String[] TIPOS = {"Depósito", "Retiro", "Transferencia"};

    @FXML
    public void initialize() {
        listaTransacciones = DataUtil.cargarTransaccionesObservable();
        tablaTransacciones.setItems(listaTransacciones);

        colIdTransaccion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdTransaccion()));
        colDescripcion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescripcion()));
        colMonto.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getMonto()));
        colFecha.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFecha()));
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo()));
        colCategoria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCategoria() != null ? data.getValue().getCategoria().getNombre() : ""
        ));

        cbTipo.getItems().setAll(TIPOS);

        List<Categoria> categorias = DataUtil.cargarCategorias();
        cbCategoria.getItems().setAll(categorias);
        cbFiltroCategoria.getItems().setAll(categorias);

        StringConverter<Categoria> categoriaStringConverter = new StringConverter<>() {
            @Override
            public String toString(Categoria object) {
                return object != null ? object.getNombre() : "";
            }
            @Override
            public Categoria fromString(String string) {
                return categorias.stream().filter(c -> c.getNombre().equals(string)).findFirst().orElse(null);
            }
        };
        cbCategoria.setConverter(categoriaStringConverter);
        cbFiltroCategoria.setConverter(categoriaStringConverter);

        tablaTransacciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> mostrarTransaccion(newSel));
    }

    // En TransaccionController.java, crea un método:
    private void recargarCategorias() {
        List<Categoria> categorias = DataUtil.cargarCategorias();
        cbCategoria.getItems().setAll(categorias);
        cbFiltroCategoria.getItems().setAll(categorias);
    }

    private void mostrarTransaccion(Transaccion t) {
        if (t != null) {
            txtIdTransaccion.setText(t.getIdTransaccion());
            txtDescripcion.setText(t.getDescripcion());
            txtMonto.setText(String.valueOf(t.getMonto()));
            dpFecha.setValue(t.getFecha());
            cbTipo.setValue(t.getTipo());
            cbCategoria.setValue(t.getCategoria());
        }
    }

    @FXML
    private void crearTransaccion() {
        try {
            String descripcion = txtDescripcion.getText();
            double monto = Double.parseDouble(txtMonto.getText());
            LocalDate fecha = dpFecha.getValue();
            String tipo = cbTipo.getValue();
            Categoria categoria = cbCategoria.getValue();

            if (categoria == null) {
                lblMensaje.setText("Seleccione una categoría.");
                return;
            }

            String idTransaccion = UUID.randomUUID().toString().substring(0, 5);

            Transaccion t = new Transaccion(
                    idTransaccion,
                    fecha,
                    tipo,
                    monto,
                    descripcion,
                    null,
                    null,
                    categoria,
                    null
            );
            listaTransacciones.add(t);
            DataUtil.guardarTransacciones(listaTransacciones);
            limpiarCampos();
            lblMensaje.setText("Transacción creada.");
        } catch (Exception e) {
            lblMensaje.setText("Datos inválidos.");
        }
    }

    @FXML
    private void modificarTransaccion() {
        Transaccion seleccionado = tablaTransacciones.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                seleccionado.setDescripcion(txtDescripcion.getText());
                seleccionado.setMonto(Double.parseDouble(txtMonto.getText()));
                seleccionado.setFecha(dpFecha.getValue());
                seleccionado.setTipo(cbTipo.getValue());
                Categoria categoria = cbCategoria.getValue();
                seleccionado.setCategoria(categoria);
                tablaTransacciones.refresh();
                DataUtil.guardarTransacciones(listaTransacciones);
                limpiarCampos();
                lblMensaje.setText("Transacción modificada.");
            } catch (Exception e) {
                lblMensaje.setText("Datos inválidos.");
            }
        }
    }

    @FXML
    private void eliminarTransaccion() {
        Transaccion seleccionado = tablaTransacciones.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaTransacciones.remove(seleccionado);
            DataUtil.guardarTransacciones(listaTransacciones);
            limpiarCampos();
            lblMensaje.setText("Transacción eliminada.");
        }
    }

    @FXML
    private void filtrarPorCategoria() {
        Categoria filtro = cbFiltroCategoria.getValue();
        if (filtro != null) {
            List<Transaccion> filtradas = listaTransacciones.stream()
                    .filter(t -> t.getCategoria() != null && filtro.getNombre().equals(t.getCategoria().getNombre()))
                    .collect(Collectors.toList());
            tablaTransacciones.setItems(FXCollections.observableArrayList(filtradas));
        } else {
            tablaTransacciones.setItems(listaTransacciones);
        }
    }

    @FXML
    private void buscarTransaccionPorId() {
        String id = txtIdTransaccion.getText().trim();
        if (id.isEmpty()) {
            tablaTransacciones.setItems(listaTransacciones);
            lblMensaje.setText("");
            return;
        }
        Transaccion t = DataUtil.buscarTransaccionPorId(id);
        if (t != null) {
            tablaTransacciones.setItems(FXCollections.observableArrayList(t));
            lblMensaje.setText("Transacción encontrada.");
        } else {
            tablaTransacciones.setItems(FXCollections.observableArrayList());
            lblMensaje.setText("No se encontró la transacción con ese ID.");
        }
    }

    @FXML
    private void limpiarBusqueda() {
        txtIdTransaccion.clear();
        tablaTransacciones.setItems(listaTransacciones);
        lblMensaje.setText("");
    }

    private void limpiarCampos() {
        txtIdTransaccion.clear();
        txtDescripcion.clear();
        txtMonto.clear();
        dpFecha.setValue(null);
        cbTipo.setValue(null);
        cbCategoria.getSelectionModel().clearSelection();
        tablaTransacciones.getSelectionModel().clearSelection();
    }
}
