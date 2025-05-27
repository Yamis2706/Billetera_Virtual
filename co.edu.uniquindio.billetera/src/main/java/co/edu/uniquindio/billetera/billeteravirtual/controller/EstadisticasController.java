package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;
import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EstadisticasController {

    @FXML private PieChart pieGastos;
    @FXML private BarChart<String, Number> barUsuarios;
    @FXML private Label lblSaldoPromedio;
    @FXML private ComboBox<String> comboUsuarios;
    @FXML private DatePicker dateDesde, dateHasta;

    private List<Usuario> usuarios;

    @FXML
    public void initialize() {
        usuarios = DataUtil.cargarUsuarios();
        comboUsuarios.getItems().clear();
        comboUsuarios.getItems().add("Todos");
        comboUsuarios.getItems().addAll(
                usuarios.stream().map(Usuario::getNombre).collect(Collectors.toList())
        );
        comboUsuarios.getSelectionModel().selectFirst();
        cargarEstadisticas(null, null, null);
    }

    @FXML
    private void consultarEstadisticas() {
        String usuarioSeleccionado = comboUsuarios.getValue();
        LocalDate desde = dateDesde.getValue();
        LocalDate hasta = dateHasta.getValue();
        cargarEstadisticas(usuarioSeleccionado, desde, hasta);
    }

    private void cargarEstadisticas(String usuarioSeleccionado, LocalDate desde, LocalDate hasta) {
        List<Usuario> usuariosFiltrados = usuarios;
        if (usuarioSeleccionado != null && !"Todos".equals(usuarioSeleccionado)) {
            usuariosFiltrados = usuarios.stream()
                    .filter(u -> u.getNombre().equals(usuarioSeleccionado))
                    .collect(Collectors.toList());
        }

        // 1. Gastos más comunes (por categoría)
        Map<String, Double> gastosPorCategoria = new HashMap<>();
        for (Usuario usuario : usuariosFiltrados) {
            for (Transaccion t : usuario.getTransacciones()) {
                if (t.getTipo().equalsIgnoreCase("Gasto")) {
                    if (filtrarPorFecha(t.getFecha(), desde, hasta)) {
                        String categoria = "Otro";
                        if (t.getCategoria() != null && t.getCategoria().getNombre() != null) {
                            categoria = t.getCategoria().getNombre();
                        }
                        gastosPorCategoria.put(categoria, gastosPorCategoria.getOrDefault(categoria, 0.0) + t.getMonto());
                    }
                }
            }
        }
        List<PieChart.Data> pieData = gastosPorCategoria.entrySet().stream()
                .map(e -> new PieChart.Data(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        pieGastos.setData(FXCollections.observableArrayList(pieData));

        // 2. Usuarios con más transacciones (aplica filtro de fechas)
        XYChart.Series<String, Number> serieUsuarios = new XYChart.Series<>();
        usuarios.stream()
                .sorted((u1, u2) -> Integer.compare(
                        contarTransaccionesPorFecha(u2, desde, hasta),
                        contarTransaccionesPorFecha(u1, desde, hasta)))
                .limit(10)
                .forEach(u -> serieUsuarios.getData().add(
                        new XYChart.Data<>(u.getNombre(), contarTransaccionesPorFecha(u, desde, hasta))
                ));
        barUsuarios.getData().clear();
        barUsuarios.getData().add(serieUsuarios);

        // 3. Saldo promedio de usuarios (sin filtro de fechas)
        double sumaSaldos = 0;
        int totalUsuarios = usuariosFiltrados.size();
        for (Usuario usuario : usuariosFiltrados) {
            double saldoUsuario = 0;
            for (Cuenta cuenta : usuario.getCuentas()) {
                saldoUsuario += cuenta.getSaldo();
            }
            sumaSaldos += saldoUsuario;
        }
        double saldoPromedio = totalUsuarios > 0 ? sumaSaldos / totalUsuarios : 0;
        lblSaldoPromedio.setText(String.format("$ %.2f", saldoPromedio));
    }

    private boolean filtrarPorFecha(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        if (fecha == null) return true;
        if (desde != null && fecha.isBefore(desde)) return false;
        if (hasta != null && fecha.isAfter(hasta)) return false;
        return true;
    }

    private int contarTransaccionesPorFecha(Usuario usuario, LocalDate desde, LocalDate hasta) {
        return (int) usuario.getTransacciones().stream()
                .filter(t -> filtrarPorFecha(t.getFecha(), desde, hasta))
                .count();
    }
}