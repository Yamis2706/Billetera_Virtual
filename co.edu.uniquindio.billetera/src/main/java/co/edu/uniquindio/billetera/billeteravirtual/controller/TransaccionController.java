package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;
import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class TransaccionController {

    private static int contadorTransacciones = 1;

    private String generarIdTransaccion() {
        return String.format("%04d", contadorTransacciones++);
    }

    // Constantes de tipos de transacción
    private static final String TIPO_DEPOSITO = "Depósito";
    private static final String TIPO_TRANSFERENCIA = "Transferencia";
    private static final String TIPO_RETIRO = "Retiro";

    // Constantes de mensajes
    private static final String MSG_CAMPOS_REQUERIDOS = "Por favor complete todos los campos requeridos para realizar la transacción.";
    private static final String MSG_MONTO_INVALIDO = "Monto inválido. Ingrese solo números.";
    private static final String MSG_MONTO_MAYOR_CERO = "El monto debe ser mayor a cero. Ingrese un valor válido.";
    private static final String MSG_MISMA_CUENTA = "No puede transferir entre la misma cuenta. Seleccione cuentas diferentes.";
    private static final String MSG_MONTO_SUPERA_SALDO = "No puede %s un monto mayor al saldo disponible (%s).";
    private static final String MSG_ERROR_UNEXPECTED = "Ocurrió un error inesperado: ";
    private static final String MSG_ERROR_CARGA = "Error al cargar los datos: ";
    private static final String MSG_ERROR_SINCRONIZAR = "Error al sincronizar cuentas: ";

    @FXML
    private ComboBox<Cuenta> cbCuentaOrigen, cbCuentaDestino;
    @FXML
    private ComboBox<String> cbTipoTransaccion;
    @FXML
    private TextField txtMonto;
    @FXML
    private TableView<Transaccion> tablaTransacciones;
    @FXML
    private TableColumn<Transaccion, String> colId, colTipo, colOrigen, colDestino, colMonto, colDescripcion, colFecha;
    @FXML
    private Label lblMensaje;

    private ObservableList<Cuenta> listaCuentas;
    private ObservableList<Transaccion> listaTransacciones;

    @FXML
    public void initialize() {
        try {
            cargarCuentasEnCombos();
            cbTipoTransaccion.setItems(FXCollections.observableArrayList(TIPO_DEPOSITO, TIPO_TRANSFERENCIA, TIPO_RETIRO));

            cbTipoTransaccion.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (TIPO_TRANSFERENCIA.equals(newVal)) {
                    cbCuentaDestino.setDisable(false);
                } else {
                    cbCuentaDestino.setDisable(true);
                    cbCuentaDestino.getSelectionModel().clearSelection();
                }
            });
            cbCuentaDestino.setDisable(true);

            colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
            colTipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipo()));
            colOrigen.setCellValueFactory(data -> new SimpleStringProperty(
                    data.getValue().getCuentaOrigen() != null ? data.getValue().getCuentaOrigen().getNumero() : ""));
            colDestino.setCellValueFactory(data -> new SimpleStringProperty(
                    data.getValue().getCuentaDestino() != null ? data.getValue().getCuentaDestino().getNumero() : ""));
            colMonto.setCellValueFactory(data -> new SimpleStringProperty(
                    String.valueOf(data.getValue().getMonto())));
            colDescripcion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));
            colFecha.setCellValueFactory(data -> new SimpleStringProperty(
                    data.getValue().getFecha().toString()));

            // Cambios aquí: la tabla inicia vacía
            listaTransacciones = FXCollections.observableArrayList();
            tablaTransacciones.setItems(listaTransacciones);
            tablaTransacciones.setEditable(false);

            limpiarMensaje();
        } catch (Exception e) {
            mostrarMensaje(MSG_ERROR_CARGA + e.getMessage(), false);
        }
    }

    @FXML
    private void realizarTransaccion() {
        limpiarMensaje();

        String tipo = cbTipoTransaccion.getValue();
        Cuenta origen = cbCuentaOrigen.getValue();
        Cuenta destino = cbCuentaDestino.getValue();
        String montoStr = txtMonto.getText();

        if (!validarCampos(tipo, origen, destino, montoStr)) return;

        try {
            double monto = Double.parseDouble(montoStr);
            if (!validarMonto(monto, tipo, origen, destino)) return;

            switch (tipo) {
                case TIPO_DEPOSITO -> {
                    origen.depositarDinero(monto);
                    registrarTransaccion(TIPO_DEPOSITO, origen, null, monto);
                    mostrarMensaje("Depósito realizado exitosamente en la cuenta " + origen.getNumero() + ".", true);
                }
                case TIPO_RETIRO -> {
                    origen.retirarDinero(monto);
                    registrarTransaccion(TIPO_RETIRO, origen, null, monto);
                    mostrarMensaje("Retiro realizado exitosamente de la cuenta " + origen.getNumero() + ".", true);
                }
                case TIPO_TRANSFERENCIA -> {
                    origen.transferirDinero(destino, monto);
                    registrarTransaccion(TIPO_TRANSFERENCIA, origen, destino, monto);
                    mostrarMensaje("Transferencia realizada exitosamente de la cuenta " + origen.getNumero() +
                            " a la cuenta " + destino.getNumero() + ".", true);
                }
            }
            DataUtil.guardarCuentas(listaCuentas);
            sincronizarCuentas();
            CuentaController.refrescarTablaCuentas();
            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarMensaje(MSG_MONTO_INVALIDO, false);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage(), false);
        } catch (Exception e) {
            mostrarMensaje(MSG_ERROR_UNEXPECTED + e.getMessage(), false);
        }
    }

    // --- Métodos privados de validación y mensajes ---

    private boolean validarCampos(String tipo, Cuenta origen, Cuenta destino, String montoStr) {
        if (tipo == null || montoStr.isEmpty() || origen == null || (TIPO_TRANSFERENCIA.equals(tipo) && destino == null)) {
            mostrarMensaje(MSG_CAMPOS_REQUERIDOS, false);
            return false;
        }
        return true;
    }

    private boolean validarMonto(double monto, String tipo, Cuenta origen, Cuenta destino) {
        if (monto <= 0) {
            mostrarMensaje(MSG_MONTO_MAYOR_CERO, false);
            return false;
        }
        if (TIPO_RETIRO.equals(tipo) && monto > origen.getSaldo()) {
            mostrarMensaje(String.format(MSG_MONTO_SUPERA_SALDO, "retirar", origen.getSaldo()), false);
            return false;
        }
        if (TIPO_TRANSFERENCIA.equals(tipo)) {
            if (origen.getNumero().equals(destino.getNumero())) {
                mostrarMensaje(MSG_MISMA_CUENTA, false);
                return false;
            }
            if (monto > origen.getSaldo()) {
                mostrarMensaje(String.format(MSG_MONTO_SUPERA_SALDO, "transferir", origen.getSaldo()), false);
                return false;
            }
        }
        return true;
    }

    private void mostrarMensaje(String mensaje, boolean exito) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(exito
                ? "-fx-text-fill: green; -fx-font-weight: bold;"
                : "-fx-text-fill: red; -fx-font-weight: bold;");
    }

    // --- Métodos auxiliares de lógica de UI y sincronización ---

    private void registrarTransaccion(String tipo, Cuenta origen, Cuenta destino, double monto) {
        String descripcion = switch (tipo) {
            case TIPO_DEPOSITO -> "Depósito de $" + monto + " a la cuenta " + origen.getNumero();
            case TIPO_RETIRO -> "Retiro de $" + monto + " de la cuenta " + origen.getNumero();
            case TIPO_TRANSFERENCIA -> "Transferencia de $" + monto + " de la cuenta " + origen.getNumero() +
                    " a la cuenta " + (destino != null ? destino.getNumero() : "");
            default -> "";
        };
        Transaccion transaccion = new Transaccion(
                generarIdTransaccion(),
                LocalDate.now(),
                tipo,
                monto,
                descripcion,
                origen,
                destino,
                null,
                null
        );
        DataUtil.agregarTransaccion(transaccion);
        listaTransacciones.add(transaccion);
        ordenarTransaccionesPorId();
        tablaTransacciones.refresh();
    }

    private void ordenarTransaccionesPorId() {
        listaTransacciones.sort(Comparator.comparing(Transaccion::getId));
    }

    private void limpiarCampos() {
        cbTipoTransaccion.getSelectionModel().clearSelection();
        cbCuentaOrigen.getSelectionModel().clearSelection();
        cbCuentaDestino.getSelectionModel().clearSelection();
        txtMonto.clear();
        cbCuentaDestino.setDisable(true);
    }

    private void limpiarMensaje() {
        lblMensaje.setText("");
        lblMensaje.setStyle("");
    }

    private void sincronizarCuentas() {
        try {
            List<Cuenta> cuentasActualizadas = DataUtil.cargarCuentas();
            listaCuentas.setAll(cuentasActualizadas);
            cbCuentaOrigen.setItems(listaCuentas);
            cbCuentaDestino.setItems(listaCuentas);
        } catch (Exception e) {
            mostrarMensaje(MSG_ERROR_SINCRONIZAR + e.getMessage(), false);
        }
    }

    private void cargarCuentasEnCombos() {
        List<Cuenta> cuentas = DataUtil.cargarCuentas();
        listaCuentas = FXCollections.observableArrayList(cuentas);
        cbCuentaOrigen.setItems(listaCuentas);
        cbCuentaDestino.setItems(listaCuentas);

        cbCuentaOrigen.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Cuenta item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNumero());
            }
        });
        cbCuentaOrigen.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Cuenta item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNumero());
            }
        });
        cbCuentaDestino.setCellFactory(cbCuentaOrigen.getCellFactory());
        cbCuentaDestino.setButtonCell(cbCuentaOrigen.getButtonCell());
    }
}