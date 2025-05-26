package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;
import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class CuentaController {

    @FXML
    private TableView<Cuenta> tablaCuentas;
    @FXML
    private TableColumn<Cuenta, String> colNumero;
    @FXML
    private TableColumn<Cuenta, String> colBanco;
    @FXML
    private TableColumn<Cuenta, String> colTipo;
    @FXML
    private TableColumn<Cuenta, Double> colSaldo;
    @FXML
    private Label lblMensaje;

    private ObservableList<Cuenta> listaCuentas;
    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        usuarioActual = DataUtil.obtenerUsuarioActual();
        if (usuarioActual != null) {
            listaCuentas = FXCollections.observableArrayList(usuarioActual.getCuentas());
            tablaCuentas.setItems(listaCuentas);
            colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
            colBanco.setCellValueFactory(new PropertyValueFactory<>("banco"));
            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        }
    }

    @FXML
    public void agregarDinero() {
        Cuenta seleccionada = tablaCuentas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Agregar Dinero");
            dialog.setHeaderText("Ingrese el monto a agregar:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(montoStr -> {
                try {
                    double monto = Double.parseDouble(montoStr);
                    if (monto > 0) {
                        seleccionada.depositarDinero(monto);
                        DataUtil.guardarUsuarios(DataUtil.cargarUsuarios());
                        tablaCuentas.refresh();
                        lblMensaje.setText("Dinero agregado.");
                    } else {
                        lblMensaje.setText("Monto inválido.");
                    }
                } catch (NumberFormatException e) {
                    lblMensaje.setText("Ingrese un número válido.");
                }
            });
        } else {
            lblMensaje.setText("Seleccione una cuenta.");
        }
    }

    @FXML
    public void retirarDinero() {
        Cuenta seleccionada = tablaCuentas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Retirar Dinero");
            dialog.setHeaderText("Ingrese el monto a retirar:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(montoStr -> {
                try {
                    double monto = Double.parseDouble(montoStr);
                    if (monto > 0 && seleccionada.getSaldo() >= monto) {
                        seleccionada.retirarDinero(monto);
                        DataUtil.guardarUsuarios(DataUtil.cargarUsuarios());
                        tablaCuentas.refresh();
                        lblMensaje.setText("Dinero retirado.");
                    } else {
                        lblMensaje.setText("Monto inválido o saldo insuficiente.");
                    }
                } catch (NumberFormatException e) {
                    lblMensaje.setText("Ingrese un número válido.");
                }
            });
        } else {
            lblMensaje.setText("Seleccione una cuenta.");
        }
    }

    @FXML
    public void transferirDinero() {
        Cuenta origen = tablaCuentas.getSelectionModel().getSelectedItem();
        if (origen != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Transferir Dinero");
            dialog.setHeaderText("Ingrese el número de cuenta destino:");
            Optional<String> cuentaDestinoOpt = dialog.showAndWait();
            if (cuentaDestinoOpt.isPresent()) {
                String numeroDestino = cuentaDestinoOpt.get();
                Cuenta destino = DataUtil.buscarCuentaPorNumero(numeroDestino);
                if (destino != null && !destino.getNumero().equals(origen.getNumero())) {
                    TextInputDialog montoDialog = new TextInputDialog();
                    montoDialog.setTitle("Transferir Dinero");
                    montoDialog.setHeaderText("Ingrese el monto a transferir:");
                    Optional<String> montoOpt = montoDialog.showAndWait();
                    montoOpt.ifPresent(montoStr -> {
                        try {
                            double monto = Double.parseDouble(montoStr);
                            if (monto > 0 && origen.getSaldo() >= monto) {
                                origen.retirarDinero(monto);
                                destino.depositarDinero(monto);
                                DataUtil.guardarUsuarios(DataUtil.cargarUsuarios());
                                tablaCuentas.refresh();
                                lblMensaje.setText("Transferencia realizada.");
                            } else {
                                lblMensaje.setText("Monto inválido o saldo insuficiente.");
                            }
                        } catch (NumberFormatException e) {
                            lblMensaje.setText("Ingrese un número válido.");
                        }
                    });
                } else {
                    lblMensaje.setText("Cuenta destino inválida.");
                }
            }
        } else {
            lblMensaje.setText("Seleccione una cuenta de origen.");
        }
    }
}