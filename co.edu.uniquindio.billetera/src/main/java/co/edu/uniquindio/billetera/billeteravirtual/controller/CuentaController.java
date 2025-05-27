package co.edu.uniquindio.billetera.billeteravirtual.controller;

import javafx.beans.property.SimpleStringProperty;
import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;
import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class CuentaController {

    @FXML
    private TableView<Cuenta> tablaCuentas;
    @FXML
    private TextField txtBanco, txtNumero, txtTipo, txtConsulta, txtSaldoInicial;
    @FXML
    private TextArea txtMovimientos;
    @FXML
    private Label lblMensaje;

    private ObservableList<Cuenta> listaCuentas;

    @FXML
    private TableView<Transaccion> tablaTransacciones;
    @FXML private TableColumn<Cuenta, String> colIdCuenta;
    @FXML private TableColumn<Cuenta, String> colBanco;
    @FXML private TableColumn<Cuenta, String> colNumero;
    @FXML private TableColumn<Cuenta, String> colTipo;
    @FXML private TableColumn<Cuenta, String> colSaldo;

    private static TableView<Cuenta> tablaCuentasStatic;

    @FXML
    public void initialize() {
        listaCuentas = FXCollections.observableArrayList(DataUtil.cargarCuentas());
        tablaCuentas.setItems(listaCuentas);

        tablaCuentasStatic = tablaCuentas;

        colIdCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdCuenta()));
        colBanco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBanco()));
        colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumero()));
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        colSaldo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSaldo())));

        // Listener para cargar datos al seleccionar una cuenta
        tablaCuentas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                mostrarCuenta(newSel);
                txtNumero.setDisable(true); // No permitir cambiar el número
            }
        });
    }

    public static void refrescarTablaCuentas() {
        if (tablaCuentasStatic != null) {
            ObservableList<Cuenta> cuentasActualizadas = FXCollections.observableArrayList(DataUtil.cargarCuentas());
            tablaCuentasStatic.setItems(cuentasActualizadas);
            tablaCuentasStatic.refresh();
        }
    }

    @FXML
    private void crearCuenta() {
        String banco = txtBanco.getText();
        String numero = txtNumero.getText();
        String tipo = txtTipo.getText();
        String saldoStr = txtSaldoInicial.getText();

        if (banco.isEmpty() || numero.isEmpty() || tipo.isEmpty() || saldoStr.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        if (listaCuentas.stream().anyMatch(c -> c.getNumero().equals(numero))) {
            lblMensaje.setText("Ya existe una cuenta con ese número.");
            return;
        }

        double saldoInicial;
        try {
            saldoInicial = Double.parseDouble(saldoStr);
            if (saldoInicial < 0) {
                lblMensaje.setText("El saldo inicial no puede ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            lblMensaje.setText("Saldo inicial inválido.");
            return;
        }

        Cuenta cuenta = new Cuenta(banco, numero, tipo, saldoInicial);
        listaCuentas.add(cuenta);
        DataUtil.guardarCuentas(listaCuentas);
        limpiarCampos();
        lblMensaje.setText("Cuenta creada correctamente.");
        tablaCuentas.refresh();
    }

    @FXML
    private void modificarCuenta() {
        Cuenta seleccionada = tablaCuentas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            lblMensaje.setText("Seleccione una cuenta para modificar.");
            return;
        }
        String banco = txtBanco.getText();
        String tipo = txtTipo.getText();
        String saldoStr = txtSaldoInicial.getText();

        if (banco.isEmpty() || tipo.isEmpty() || saldoStr.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        double saldo;
        try {
            saldo = Double.parseDouble(saldoStr);
            if (saldo < 0) {
                lblMensaje.setText("El saldo no puede ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            lblMensaje.setText("Saldo inválido.");
            return;
        }

        seleccionada.setBanco(banco);
        seleccionada.setTipo(tipo);
        seleccionada.setSaldo(saldo);
        DataUtil.guardarCuentas(listaCuentas);
        limpiarCampos();
        lblMensaje.setText("Cuenta modificada correctamente.");
        tablaCuentas.refresh();
        actualizarTransaccionesEnVista();
    }

    @FXML
    private void eliminarCuenta() {
        Cuenta seleccionada = tablaCuentas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            lblMensaje.setText("Seleccione una cuenta para eliminar.");
            return;
        }
        listaCuentas.remove(seleccionada);
        DataUtil.guardarCuentas(listaCuentas);
        limpiarCampos();
        lblMensaje.setText("Cuenta eliminada correctamente.");
        tablaCuentas.refresh();
        actualizarTransaccionesEnVista();
    }

    @FXML
    private void agregarDinero() {
        Cuenta seleccionada = tablaCuentas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            lblMensaje.setText("Seleccione una cuenta para depositar.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Depositar Dinero");
        dialog.setHeaderText("Ingrese el monto a depositar:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(montoStr -> {
            try {
                double monto = Double.parseDouble(montoStr);
                if (monto <= 0) {
                    lblMensaje.setText("El monto debe ser mayor a cero.");
                    return;
                }
                seleccionada.depositarDinero(monto);
                DataUtil.guardarCuentas(listaCuentas);

                Transaccion transaccion = new Transaccion(
                        UUID.randomUUID().toString(),
                        LocalDate.now(),
                        "Ingreso",
                        monto,
                        "Depósito en cuenta " + seleccionada.getNumero(),
                        seleccionada,
                        null,
                        null,
                        null
                );
                DataUtil.agregarTransaccion(transaccion);

                tablaCuentas.refresh();
                lblMensaje.setText("Depósito realizado.");
                actualizarTransaccionesEnVista();
            } catch (NumberFormatException e) {
                lblMensaje.setText("Monto inválido.");
            } catch (IllegalArgumentException e) {
                lblMensaje.setText(e.getMessage());
            }
        });
    }

    @FXML
    private void retirarDinero() {
        Cuenta seleccionada = tablaCuentas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            lblMensaje.setText("Seleccione una cuenta para retirar.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retirar Dinero");
        dialog.setHeaderText("Ingrese el monto a retirar:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(montoStr -> {
            try {
                double monto = Double.parseDouble(montoStr);
                if (monto <= 0) {
                    lblMensaje.setText("El monto debe ser mayor a cero.");
                    return;
                }
                seleccionada.retirarDinero(monto);
                DataUtil.guardarCuentas(listaCuentas);

                Transaccion transaccion = new Transaccion(
                        UUID.randomUUID().toString(),
                        LocalDate.now(),
                        "Gasto",
                        monto,
                        "Retiro de cuenta " + seleccionada.getNumero(),
                        seleccionada,
                        null,
                        null,
                        null
                );
                DataUtil.agregarTransaccion(transaccion);

                tablaCuentas.refresh();
                lblMensaje.setText("Retiro realizado.");
                actualizarTransaccionesEnVista();
            } catch (NumberFormatException e) {
                lblMensaje.setText("Monto inválido.");
            } catch (IllegalArgumentException e) {
                lblMensaje.setText(e.getMessage());
            }
        });
    }

    @FXML
    private void transferirDinero() {
        Cuenta origen = tablaCuentas.getSelectionModel().getSelectedItem();
        if (origen == null) {
            lblMensaje.setText("Seleccione la cuenta de origen.");
            return;
        }
        ChoiceDialog<Cuenta> dialog = new ChoiceDialog<>(null, listaCuentas.filtered(c -> c != origen));
        dialog.setTitle("Transferir Dinero");
        dialog.setHeaderText("Seleccione la cuenta destino:");
        Optional<Cuenta> destinoOpt = dialog.showAndWait();
        if (destinoOpt.isEmpty()) return;
        Cuenta destino = destinoOpt.get();

        TextInputDialog montoDialog = new TextInputDialog();
        montoDialog.setTitle("Transferir Dinero");
        montoDialog.setHeaderText("Ingrese el monto a transferir:");
        Optional<String> montoStrOpt = montoDialog.showAndWait();
        montoStrOpt.ifPresent(montoStr -> {
            try {
                double monto = Double.parseDouble(montoStr);
                if (monto <= 0) {
                    lblMensaje.setText("El monto debe ser mayor a cero.");
                    return;
                }
                origen.transferirDinero(destino, monto);
                DataUtil.guardarCuentas(listaCuentas);

                Transaccion transaccionSalida = new Transaccion(
                        UUID.randomUUID().toString(),
                        LocalDate.now(),
                        "Gasto",
                        monto,
                        "Transferencia a cuenta " + destino.getNumero(),
                        origen,
                        destino,
                        null,
                        null
                );
                DataUtil.agregarTransaccion(transaccionSalida);

                Transaccion transaccionEntrada = new Transaccion(
                        UUID.randomUUID().toString(),
                        LocalDate.now(),
                        "Ingreso",
                        monto,
                        "Transferencia desde cuenta " + origen.getNumero(),
                        destino,
                        origen,
                        null,
                        null
                );
                DataUtil.agregarTransaccion(transaccionEntrada);

                tablaCuentas.refresh();
                lblMensaje.setText("Transferencia realizada.");
                actualizarTransaccionesEnVista();
            } catch (NumberFormatException e) {
                lblMensaje.setText("Monto inválido.");
            } catch (IllegalArgumentException e) {
                lblMensaje.setText(e.getMessage());
            }
        });
    }

    @FXML
    private void consultarCuenta() {
        String consulta = txtConsulta.getText().trim();
        if (consulta.isEmpty()) {
            lblMensaje.setText("Ingrese un ID o número de cuenta.");
            return;
        }
        Cuenta encontrada = listaCuentas.stream()
                .filter(c -> c.getIdCuenta().equalsIgnoreCase(consulta) || c.getNumero().equalsIgnoreCase(consulta))
                .findFirst().orElse(null);

        if (encontrada == null) {
            lblMensaje.setText("Cuenta no encontrada.");
            txtMovimientos.clear();
            return;
        }

        tablaCuentas.getSelectionModel().select(encontrada);
        mostrarCuenta(encontrada);

        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(encontrada.getIdCuenta()).append("\n");
        sb.append("Banco: ").append(encontrada.getBanco()).append("\n");
        sb.append("Número: ").append(encontrada.getNumero()).append("\n");
        sb.append("Tipo: ").append(encontrada.getTipo()).append("\n");
        sb.append("Saldo: ").append(encontrada.getSaldo()).append("\n");
        sb.append("Movimientos:\n");
        for (String mov : encontrada.getMovimientos()) {
            sb.append(" - ").append(mov).append("\n");
        }
        txtMovimientos.setText(sb.toString());
        lblMensaje.setText("Consulta realizada.");
    }

    private void mostrarCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            txtBanco.setText(cuenta.getBanco());
            txtNumero.setText(cuenta.getNumero());
            txtTipo.setText(cuenta.getTipo());
            txtSaldoInicial.setText(String.valueOf(cuenta.getSaldo()));
        }
    }

    private void limpiarCampos() {
        txtBanco.clear();
        txtNumero.clear();
        txtTipo.clear();
        txtSaldoInicial.clear();
        txtNumero.setDisable(false);
        tablaCuentas.getSelectionModel().clearSelection();
    }

    private void actualizarTransaccionesEnVista() {
        if (tablaTransacciones != null) {
            tablaTransacciones.setItems(DataUtil.cargarTransaccionesObservable());
            tablaTransacciones.refresh();
        }
    }
}