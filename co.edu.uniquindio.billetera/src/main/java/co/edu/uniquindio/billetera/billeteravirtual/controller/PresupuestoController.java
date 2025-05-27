package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Categoria;
import co.edu.uniquindio.billetera.billeteravirtual.model.Presupuesto;
import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;
import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;
import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;
import java.util.stream.Collectors;

public class PresupuestoController {

    @FXML private TableView<Presupuesto> tablaPresupuestos;
    @FXML private TableColumn<Presupuesto, String> colIdPresupuesto;
    @FXML private TableColumn<Presupuesto, String> colIdCuenta;
    @FXML private TableColumn<Presupuesto, String> colNombre;
    @FXML private TableColumn<Presupuesto, String> colCategoria;
    @FXML private TableColumn<Presupuesto, Double> colMonto;
    @FXML private TableColumn<Presupuesto, Double> colGastado;
    @FXML private TableColumn<Presupuesto, Double> colSaldo;
    @FXML private TableColumn<Presupuesto, String> colEstado;
    @FXML private TextField txtNombre, txtMonto, txtGastado;
    @FXML private ComboBox<Categoria> cbCategoria;
    @FXML private Button btnCrear, btnModificar, btnEliminar;
    @FXML private Label lblMensaje, lblEstado, lblGastado;

    private ObservableList<Presupuesto> listaPresupuestos;

    @FXML
    public void initialize() {
        listaPresupuestos = FXCollections.observableArrayList(DataUtil.cargarPresupuestos());
        tablaPresupuestos.setItems(listaPresupuestos);

        colIdPresupuesto.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdPresupuesto()));
        colIdCuenta.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdCuenta()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colCategoria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCategoria() != null ? data.getValue().getCategoria().getNombre() : ""));
        colMonto.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getMontoTotal()));
        colGastado.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getMontoGastado()));
        colEstado.setCellValueFactory(data -> {
            double saldo = data.getValue().getMontoTotal() - data.getValue().getMontoGastado();
            if (saldo > 0) {
                return new javafx.beans.property.SimpleStringProperty("En curso");
            } else if (saldo == 0) {
                return new javafx.beans.property.SimpleStringProperty("Finalizado");
            } else {
                return new javafx.beans.property.SimpleStringProperty("Excedido");
            }
        });
        if (colSaldo != null) {
            colSaldo.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(
                    data.getValue().getMontoTotal() - data.getValue().getMontoGastado()
            ));
        }

        recargarCategorias();

        tablaPresupuestos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> mostrarPresupuesto(newSel));
    }

    private void recargarCategorias() {
        List<Categoria> categorias = DataUtil.cargarCategorias();
        cbCategoria.setItems(FXCollections.observableArrayList(categorias));
        cbCategoria.setConverter(new StringConverter<>() {
            @Override
            public String toString(Categoria object) {
                return object != null ? object.getNombre() : "";
            }
            @Override
            public Categoria fromString(String string) {
                return cbCategoria.getItems().stream().filter(c -> c.getNombre().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void mostrarPresupuesto(Presupuesto p) {
        if (p != null) {
            txtNombre.setText(p.getNombre());
            txtMonto.setText(String.valueOf(p.getMontoTotal()));
            txtGastado.setText(String.valueOf(p.getMontoGastado()));
            cbCategoria.setValue(p.getCategoria());
            double saldo = p.getMontoTotal() - p.getMontoGastado();

            if (lblEstado != null) {
                if (saldo > 0) {
                    lblEstado.setText("En curso");
                    lblMensaje.setText("");
                } else if (saldo == 0) {
                    lblEstado.setText("Finalizado");
                    lblMensaje.setText("");
                } else {
                    lblEstado.setText("Excedido");
                    lblMensaje.setText("No tienes presupuesto suficiente para realizar este gasto");
                }
            }
            if (lblGastado != null) lblGastado.setText("Monto Gastado: " + p.getMontoGastado());
        }
    }

    private String generarIdPresupuesto() {
        int max = listaPresupuestos.stream()
                .map(Presupuesto::getIdPresupuesto)
                .filter(id -> id.matches("\\d{3}"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return String.format("%03d", max + 1);
    }

    // --- Métodos auxiliares para cuentas ---
    private Cuenta buscarCuentaPorNumero(String numero) {
        List<Cuenta> cuentas = DataUtil.cargarCuentas();
        for (Cuenta c : cuentas) {
            if (c.getNumero().equals(numero)) {
                return c;
            }
        }
        return null;
    }

    private void guardarCuentaActualizada(Cuenta cuenta) {
        List<Cuenta> cuentas = DataUtil.cargarCuentas();
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getNumero().equals(cuenta.getNumero())) {
                cuentas.set(i, cuenta);
                break;
            }
        }
        DataUtil.guardarCuentas(cuentas);
    }

    @FXML
    private void crearPresupuesto() {
        try {
            String nombre = txtNombre.getText();
            double monto = Double.parseDouble(txtMonto.getText());
            double gastado = txtGastado.getText().isEmpty() ? 0 : Double.parseDouble(txtGastado.getText());
            Categoria categoria = cbCategoria.getValue();

            if (categoria == null) {
                lblMensaje.setText("Seleccione una categoría.");
                return;
            }

            // No se asocia cuenta aquí porque ya no hay ComboBox de cuenta

            String idPresupuesto = generarIdPresupuesto();

            Presupuesto p = new Presupuesto(
                    idPresupuesto,
                    "", // idCuenta vacío o puedes ajustar según tu modelo
                    nombre,
                    monto,
                    gastado,
                    categoria
            );
            listaPresupuestos.add(p);
            DataUtil.guardarPresupuestos(new java.util.ArrayList<>(listaPresupuestos));
            limpiarCampos();

            double saldo = monto - gastado;
            if (saldo < 0) {
                lblMensaje.setText("No tienes presupuesto suficiente para realizar este gasto");
            } else {
                lblMensaje.setText("Presupuesto creado.");
            }

            recargarCategorias();
            tablaPresupuestos.refresh();
        } catch (Exception e) {
            lblMensaje.setText("Datos inválidos.");
        }
    }

    @FXML
    private void modificarPresupuesto() {
        Presupuesto seleccionado = tablaPresupuestos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                double montoAnterior = seleccionado.getMontoTotal();
                double nuevoMonto = Double.parseDouble(txtMonto.getText());
                double diferencia = nuevoMonto - montoAnterior;

                // No se asocia cuenta aquí porque ya no hay ComboBox de cuenta

                seleccionado.setNombre(txtNombre.getText());
                seleccionado.setMontoTotal(nuevoMonto);
                double gastado = txtGastado.getText().isEmpty() ? 0 : Double.parseDouble(txtGastado.getText());
                seleccionado.setMontoGastado(gastado);
                Categoria categoria = cbCategoria.getValue();
                seleccionado.setCategoria(categoria);

                DataUtil.guardarPresupuestos(new java.util.ArrayList<>(listaPresupuestos));
                limpiarCampos();

                double saldo = seleccionado.getMontoTotal() - seleccionado.getMontoGastado();
                if (saldo < 0) {
                    lblMensaje.setText("No tienes presupuesto suficiente para realizar este gasto");
                } else {
                    lblMensaje.setText("Presupuesto modificado.");
                }

                recargarCategorias();
                tablaPresupuestos.refresh();
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
            DataUtil.guardarPresupuestos(new java.util.ArrayList<>(listaPresupuestos));
            limpiarCampos();
            lblMensaje.setText("Presupuesto eliminado.");
            recargarCategorias();
            tablaPresupuestos.refresh();
        }
    }

    private double calcularMontoGastado(Categoria categoria) {
        if (categoria == null) return 0;
        List<Transaccion> transacciones = DataUtil.cargarTransacciones();
        return transacciones.stream()
                .filter(t -> t.getCategoria() != null && t.getCategoria().getNombre().equals(categoria.getNombre()))
                .mapToDouble(Transaccion::getMonto)
                .sum();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtMonto.clear();
        if (txtGastado != null) txtGastado.clear();
        cbCategoria.getSelectionModel().clearSelection();
        if (lblGastado != null) lblGastado.setText("");
        if (lblEstado != null) lblEstado.setText("");
        tablaPresupuestos.getSelectionModel().clearSelection();
    }

    public void actualizarCategorias() {
        recargarCategorias();
    }
}