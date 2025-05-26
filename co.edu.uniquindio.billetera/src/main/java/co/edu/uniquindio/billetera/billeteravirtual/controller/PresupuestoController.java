package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Presupuesto;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class PresupuestoController {

    @FXML private TableView<Presupuesto> tablaPresupuestos;
    @FXML private TableColumn<Presupuesto, String> colNombre;
    @FXML private TableColumn<Presupuesto, Double> colMonto;
    @FXML private TableColumn<Presupuesto, LocalDate> colInicio;
    @FXML private TableColumn<Presupuesto, LocalDate> colFin;
    @FXML private TextField txtNombre, txtMonto;
    @FXML private DatePicker dpInicio, dpFin;
    @FXML private Button btnCrear, btnModificar, btnEliminar;
    @FXML private Label lblMensaje;

    private ObservableList<Presupuesto> listaPresupuestos;

    @FXML
    public void initialize() {
        listaPresupuestos = FXCollections.observableArrayList(DataUtil.cargarPresupuestos());
        tablaPresupuestos.setItems(listaPresupuestos);

        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colMonto.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getMonto()));
        colInicio.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFechaInicio()));
        colFin.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFechaFin()));

        tablaPresupuestos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> mostrarPresupuesto(newSel));
    }

    private void mostrarPresupuesto(Presupuesto p) {
        if (p != null) {
            txtNombre.setText(p.getNombre());
            txtMonto.setText(String.valueOf(p.getMonto()));
            dpInicio.setValue(p.getFechaInicio());
            dpFin.setValue(p.getFechaFin());
        }
    }

    @FXML
    private void crearPresupuesto() {
        try {
            String nombre = txtNombre.getText();
            double monto = Double.parseDouble(txtMonto.getText());
            LocalDate inicio = dpInicio.getValue();
            LocalDate fin = dpFin.getValue();

            Presupuesto p = new Presupuesto(nombre, monto, inicio, fin);
            listaPresupuestos.add(p);
            DataUtil.guardarPresupuestos(listaPresupuestos);
            limpiarCampos();
            lblMensaje.setText("Presupuesto creado.");
        } catch (Exception e) {
            lblMensaje.setText("Datos inválidos.");
        }
    }

    @FXML
    private void modificarPresupuesto() {
        Presupuesto seleccionado = tablaPresupuestos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                seleccionado.setNombre(txtNombre.getText());
                seleccionado.setMonto(Double.parseDouble(txtMonto.getText()));
                seleccionado.setFechaInicio(dpInicio.getValue());
                seleccionado.setFechaFin(dpFin.getValue());
                tablaPresupuestos.refresh();
                DataUtil.guardarPresupuestos(listaPresupuestos);
                limpiarCampos();
                lblMensaje.setText("Presupuesto modificado.");
            } catch (Exception e) {
                lblMensaje.setText("Datos inválidos.");
            }
        }
    }

    @FXML
    private void eliminarPresupuesto() {
        Presupuesto seleccionado = tablaPresupuestos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaPresupuestos.remove(seleccionado);
            DataUtil.guardarPresupuestos(listaPresupuestos);
            limpiarCampos();
            lblMensaje.setText("Presupuesto eliminado.");
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtMonto.clear();
        dpInicio.setValue(null);
        dpFin.setValue(null);
        tablaPresupuestos.getSelectionModel().clearSelection();
    }
}