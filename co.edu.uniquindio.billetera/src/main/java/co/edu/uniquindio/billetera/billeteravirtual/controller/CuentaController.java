package co.edu.uniquindio.billetera.billeteravirtual.controller;

import javafx.beans.property.SimpleStringProperty;
import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;
import co.edu.uniquindio.billetera.billeteravirtual.model.Presupuesto;
import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CuentaController {

    @FXML
    private TableView<Cuenta> tablaCuentas;
    @FXML
    private TextField txtBanco, txtNumero, txtTipo, txtConsulta, txtSaldoInicial, txtIdUsuario;
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
    @FXML private TableColumn<Cuenta, String> colIdUsuario;
    @FXML private TableColumn<Cuenta, String> colIdPresupuesto;

    @FXML
    private ComboBox<Cuenta> comboCuentaOrigen;
    @FXML
    private ComboBox<Cuenta> comboCuentaDestino;

    @FXML
    private ComboBox<String> cbPresupuesto;

    private ObservableList<Cuenta> cuentasUsuario = FXCollections.observableArrayList();

    private static TableView<Cuenta> tablaCuentasStatic;

    // --- CAMPOS PARA GRÁFICAS ---
    @FXML
    private PieChart graficaGastosComunes;
    @FXML
    private BarChart<String, Number> graficaUsuariosTransacciones;
    @FXML
    private BarChart<String, Number> graficaSaldoPromedioUsuarios;

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
        colIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUsuario()));
        if (colIdPresupuesto != null) {
            colIdPresupuesto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdPresupuesto() != null ? cellData.getValue().getIdPresupuesto() : ""));
        }

        tablaCuentas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                mostrarCuenta(newSel);
                txtNumero.setDisable(true);
            }
        });

        if (comboCuentaOrigen != null && comboCuentaDestino != null) {
            cuentasUsuario.setAll(listaCuentas);
            comboCuentaOrigen.setItems(cuentasUsuario);
            comboCuentaDestino.setItems(cuentasUsuario);
        }

        if (cbPresupuesto != null) {
            cargarPresupuestosDisponibles();
        }

        actualizarSaldosPorTransacciones();

        // Inicializar gráficas si existen en la vista
        actualizarGraficas();
        if (graficaSaldoPromedioUsuarios != null) {
            mostrarSaldoPromedioUsuarios();
        }
    }

    private void cargarPresupuestosDisponibles() {
        List<Presupuesto> presupuestos = DataUtil.cargarPresupuestos();
        List<Cuenta> cuentas = DataUtil.cargarCuentas();
        List<String> idsAsociados = cuentas.stream()
                .map(Cuenta::getIdPresupuesto)
                .filter(id -> id != null && !id.isEmpty())
                .collect(Collectors.toList());
        List<String> idsDisponibles = presupuestos.stream()
                .map(Presupuesto::getIdPresupuesto)
                .filter(id -> !idsAsociados.contains(id))
                .collect(Collectors.toList());
        cbPresupuesto.setItems(FXCollections.observableArrayList(idsDisponibles));
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
        String idUsuario = txtIdUsuario.getText();
        String idPresupuesto = cbPresupuesto != null ? cbPresupuesto.getValue() : null;

        if (banco.isEmpty() || numero.isEmpty() || tipo.isEmpty() || saldoStr.isEmpty() || idUsuario.isEmpty() || idPresupuesto == null || idPresupuesto.isEmpty()) {
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

        Cuenta cuenta = new Cuenta(banco, numero, tipo, saldoInicial, idUsuario);
        cuenta.setSaldoInicial(saldoInicial);
        cuenta.setSaldo(saldoInicial);
        cuenta.setIdPresupuesto(idPresupuesto);
        listaCuentas.add(cuenta);
        DataUtil.guardarCuentas(listaCuentas);

        if (comboCuentaOrigen != null && comboCuentaDestino != null) {
            cuentasUsuario.add(cuenta);
        }

        limpiarCampos();
        if (cbPresupuesto != null) {
            cargarPresupuestosDisponibles();
        }
        lblMensaje.setText("Cuenta creada correctamente.");
        tablaCuentas.refresh();

        actualizarSaldosPorTransacciones();
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
        String idUsuario = txtIdUsuario.getText();
        String idPresupuesto = cbPresupuesto != null ? cbPresupuesto.getValue() : null;

        if (banco.isEmpty() || tipo.isEmpty() || saldoStr.isEmpty() || idUsuario.isEmpty() || idPresupuesto == null || idPresupuesto.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        double saldoInicial;
        try {
            saldoInicial = Double.parseDouble(saldoStr);
            if (saldoInicial < 0) {
                lblMensaje.setText("El saldo no puede ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            lblMensaje.setText("Saldo inválido.");
            return;
        }

        boolean tieneTransacciones = DataUtil.cargarTransacciones().stream()
                .anyMatch(t -> (t.getCuentaOrigen() != null && t.getCuentaOrigen().getNumero().equals(seleccionada.getNumero()))
                        || (t.getCuentaDestino() != null && t.getCuentaDestino().getNumero().equals(seleccionada.getNumero())));

        seleccionada.setBanco(banco);
        seleccionada.setTipo(tipo);
        seleccionada.setIdUsuario(idUsuario);
        seleccionada.setIdPresupuesto(idPresupuesto);

        if (!tieneTransacciones) {
            seleccionada.setSaldoInicial(saldoInicial);
            seleccionada.setSaldo(saldoInicial);
        }

        DataUtil.guardarCuentas(listaCuentas);

        if (comboCuentaOrigen != null && comboCuentaDestino != null) {
            cuentasUsuario.setAll(listaCuentas);
        }

        limpiarCampos();
        if (cbPresupuesto != null) {
            cargarPresupuestosDisponibles();
        }
        lblMensaje.setText("Cuenta modificada correctamente.");
        tablaCuentas.refresh();
        actualizarTransaccionesEnVista();

        actualizarSaldosPorTransacciones();
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

        if (comboCuentaOrigen != null && comboCuentaDestino != null) {
            cuentasUsuario.remove(seleccionada);
        }

        limpiarCampos();
        if (cbPresupuesto != null) {
            cargarPresupuestosDisponibles();
        }
        lblMensaje.setText("Cuenta eliminada correctamente.");
        tablaCuentas.refresh();
        actualizarTransaccionesEnVista();

        actualizarSaldosPorTransacciones();
    }

    // --- Cambios aquí: Llamada a actualizarGraficas() ---
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

                actualizarSaldosPorTransacciones();
                actualizarGraficas();
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

                actualizarSaldosPorTransacciones();
                actualizarGraficas();
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

                actualizarSaldosPorTransacciones();
                actualizarGraficas();
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
        sb.append("ID Usuario: ").append(encontrada.getIdUsuario()).append("\n");
        sb.append("ID Presupuesto: ").append(encontrada.getIdPresupuesto() != null ? encontrada.getIdPresupuesto() : "").append("\n");
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
            txtSaldoInicial.setText(String.valueOf(cuenta.getSaldoInicial()));
            txtIdUsuario.setText(cuenta.getIdUsuario());
            if (cbPresupuesto != null) {
                cbPresupuesto.setValue(cuenta.getIdPresupuesto());
            }
        }
    }

    private void limpiarCampos() {
        txtBanco.clear();
        txtNumero.clear();
        txtTipo.clear();
        txtSaldoInicial.clear();
        txtIdUsuario.clear();
        if (cbPresupuesto != null) {
            cbPresupuesto.getSelectionModel().clearSelection();
        }
        txtNumero.setDisable(false);
        tablaCuentas.getSelectionModel().clearSelection();
    }

    private void actualizarTransaccionesEnVista() {
        if (tablaTransacciones != null) {
            tablaTransacciones.setItems(DataUtil.cargarTransaccionesObservable());
            tablaTransacciones.refresh();
        }
    }

    public void actualizarSaldosPorTransacciones() {
        List<Cuenta> cuentas = DataUtil.cargarCuentas();
        List<Transaccion> transacciones = DataUtil.cargarTransacciones();

        for (Cuenta cuenta : cuentas) {
            cuenta.setSaldo(cuenta.getSaldoInicial());
        }

        for (Transaccion t : transacciones) {
            if (t.getCuentaOrigen() != null) {
                Cuenta origen = cuentas.stream()
                        .filter(c -> c.getNumero().equals(t.getCuentaOrigen().getNumero()))
                        .findFirst().orElse(null);
                if (origen != null) {
                    if ("Gasto".equalsIgnoreCase(t.getTipo())) {
                        origen.setSaldo(origen.getSaldo() - t.getMonto());
                    }
                }
            }
            if (t.getCuentaDestino() != null && "Ingreso".equalsIgnoreCase(t.getTipo())) {
                Cuenta destino = cuentas.stream()
                        .filter(c -> c.getNumero().equals(t.getCuentaDestino().getNumero()))
                        .findFirst().orElse(null);
                if (destino != null) {
                    destino.setSaldo(destino.getSaldo() + t.getMonto());
                }
            }
            if (t.getCuentaOrigen() != null && "Ingreso".equalsIgnoreCase(t.getTipo()) && t.getCuentaDestino() == null) {
                Cuenta origen = cuentas.stream()
                        .filter(c -> c.getNumero().equals(t.getCuentaOrigen().getNumero()))
                        .findFirst().orElse(null);
                if (origen != null) {
                    origen.setSaldo(origen.getSaldo() + t.getMonto());
                }
            }
        }

        DataUtil.guardarCuentas(cuentas);

        if (tablaCuentas != null) {
            listaCuentas.setAll(cuentas);
            tablaCuentas.refresh();
        }
    }

    // --- NUEVO: Método para actualizar gráficas ---
    private void actualizarGraficas() {
        if (graficaUsuariosTransacciones != null) {
            mostrarUsuariosConMasTransacciones();
        }
        if (graficaGastosComunes != null) {
            mostrarGastosMasComunes();
        }
    }

    // --- MÉTODOS PARA GRÁFICAS ---

    // Gráfica de categorías más utilizadas (por descripción)
    public void mostrarGastosMasComunes() {
        List<Transaccion> transacciones = DataUtil.cargarTransacciones();
        Map<String, Double> gastosPorCategoria = new HashMap<>();

        for (Transaccion t : transacciones) {
            if ("Gasto".equalsIgnoreCase(t.getTipo())) {
                String categoria = t.getDescripcion() != null ? t.getDescripcion() : "Otro";
                gastosPorCategoria.put(categoria, gastosPorCategoria.getOrDefault(categoria, 0.0) + t.getMonto());
            }
        }

        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
        gastosPorCategoria.forEach((cat, monto) -> datos.add(new PieChart.Data(cat, monto)));
        graficaGastosComunes.setData(datos);
    }

    // Gráfica de usuarios con más transacciones (solo cuenta origen)
    public void mostrarUsuariosConMasTransacciones() {
        List<Transaccion> transacciones = DataUtil.cargarTransacciones();
        Map<String, Integer> transaccionesPorUsuario = new HashMap<>();

        for (Transaccion t : transacciones) {
            if (t.getCuentaOrigen() != null) {
                String usuario = t.getCuentaOrigen().getIdUsuario();
                transaccionesPorUsuario.put(usuario, transaccionesPorUsuario.getOrDefault(usuario, 0) + 1);
            }
        }

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        transaccionesPorUsuario.forEach((usuario, cantidad) ->
                serie.getData().add(new XYChart.Data<>(usuario, cantidad))
        );
        graficaUsuariosTransacciones.getData().setAll(serie);
    }

    public void mostrarSaldoPromedioUsuarios() {
        List<Cuenta> cuentas = DataUtil.cargarCuentas();
        Map<String, List<Double>> saldosPorUsuario = new HashMap<>();

        for (Cuenta c : cuentas) {
            saldosPorUsuario.computeIfAbsent(c.getIdUsuario(), k -> new ArrayList<>()).add(c.getSaldo());
        }

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        saldosPorUsuario.forEach((usuario, saldos) -> {
            double promedio = saldos.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            serie.getData().add(new XYChart.Data<>(usuario, promedio));
        });
        graficaSaldoPromedioUsuarios.getData().setAll(serie);
    }
}