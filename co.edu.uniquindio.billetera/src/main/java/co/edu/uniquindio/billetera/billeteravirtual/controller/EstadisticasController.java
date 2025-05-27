package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;
import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.util.*;

public class EstadisticasController {

    @FXML private PieChart pieGastos;
    @FXML private BarChart<String, Number> barUsuarios;
    @FXML private BarChart<String, Number> barSaldoPromedio;
    @FXML private Label lblSaldoPromedio;

    private Timer timer;

    @FXML
    public void initialize() {
        cargarEstadisticas();
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> cargarEstadisticas());
            }
        }, 2000, 2000); // espera 2s y luego actualiza cada 2s
    }

    // Ahora es público para que otros controladores puedan llamarlo
    public void cargarEstadisticas() {
        List<Usuario> usuarios = DataUtil.cargarUsuarios();

        // 1. Gastos más comunes (por categoría)
        Map<String, Double> gastosPorCategoria = new HashMap<>();
        for (Usuario usuario : usuarios) {
            for (Transaccion t : usuario.getTransacciones()) {
                if ("Gasto".equalsIgnoreCase(t.getTipo())) {
                    String categoria = "Otro";
                    if (t.getCategoria() != null && t.getCategoria().getNombre() != null) {
                        categoria = t.getCategoria().getNombre();
                    }
                    gastosPorCategoria.put(categoria, gastosPorCategoria.getOrDefault(categoria, 0.0) + t.getMonto());
                }
            }
        }
        List<PieChart.Data> pieData = new ArrayList<>();
        for (Map.Entry<String, Double> entry : gastosPorCategoria.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieGastos.setData(javafx.collections.FXCollections.observableArrayList(pieData));

        // 2. Usuarios con más transacciones
        XYChart.Series<String, Number> serieUsuarios = new XYChart.Series<>();
        usuarios.stream()
                .sorted((u1, u2) -> Integer.compare(
                        u2.getTransacciones().size(),
                        u1.getTransacciones().size()))
                .limit(10)
                .forEach(u -> serieUsuarios.getData().add(
                        new XYChart.Data<>(u.getNombre(), u.getTransacciones().size())
                ));
        barUsuarios.getData().setAll(serieUsuarios);

        // 3. Saldo promedio de usuarios (y gráfica)
        XYChart.Series<String, Number> serieSaldo = new XYChart.Series<>();
        double sumaSaldos = 0;
        int totalUsuarios = usuarios.size();
        for (Usuario usuario : usuarios) {
            double saldoUsuario = usuario.getCuentas().stream().mapToDouble(Cuenta::getSaldo).sum();
            sumaSaldos += saldoUsuario;
            serieSaldo.getData().add(new XYChart.Data<>(usuario.getNombre(), saldoUsuario));
        }
        barSaldoPromedio.getData().setAll(serieSaldo);

        double saldoPromedio = totalUsuarios > 0 ? sumaSaldos / totalUsuarios : 0;
        lblSaldoPromedio.setText(String.format("$ %.2f", saldoPromedio));
    }

    // Llama este método al cerrar la ventana para evitar fugas de memoria
    public void detenerTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}