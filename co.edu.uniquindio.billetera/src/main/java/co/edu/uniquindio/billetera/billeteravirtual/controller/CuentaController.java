package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;
import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class CuentaController {

    @FXML private TableView<Cuenta> tablaCuentas;
    @FXML private TableColumn<Cuenta, String> colIdCuenta;
    @FXML private TableColumn<Cuenta, String> colBanco;
    @FXML private TableColumn<Cuenta, String> colNumero;
    @FXML private TableColumn<Cuenta, String> colTipo;
    @FXML private TableColumn<Cuenta, String> colSaldo;
    @FXML private TextField txtBanco;
    @FXML private TextField txtNumero;
    @FXML private TextField txtTipo;
    @FXML private Button btnCrear;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnDepositar;
    @FXML private Button btnRetirar;
    @FXML private Button btnTransferir;
    @FXML private TextField txtConsulta;
    @FXML private Button btnConsultar;
    @FXML private TextArea txtMovimientos;
    @FXML private Label lblMensaje;

    private ObservableList<Cuenta> listaCuentas;

    @FXML
    public void initialize() {
        listaCuentas = FXCollections.observableArrayList(DataUtil.cargarCuentas());
        tablaCuentas.setItems(listaCuentas);

        colIdCuenta.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdCuenta()));
        colBanco.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBanco()));
        colNumero.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNumero()));
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo()));
        colSaldo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getSaldo())));

        tablaCuentas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> mostrarCuenta(newSel));
    }

    @FXML
    private void crearCuenta() {
        String banco = txtBanco.getText();
        String numero = txtNumero.getText();
        String tipo = txtTipo.getText();

        if (banco.isEmpty() || numero.isEmpty() || tipo.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        Cuenta cuenta = new Cuenta(banco, numero, tipo);
        listaCuentas.add(cuenta);
        DataUtil.guardarCuentas(listaCuentas);
        limpiarCampos(); // Esta línea limpia los campos
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
        String numero = txtNumero.getText();
        String tipo = txtTipo.getText();

        if (banco.isEmpty() || numero.isEmpty() || tipo.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        seleccionada.setBanco(banco);
        seleccionada.setNumero(numero);
        seleccionada.setTipo(tipo);
        DataUtil.guardarCuentas(listaCuentas);
        limpiarCampos();
        lblMensaje.setText("Cuenta modificada correctamente.");
        tablaCuentas.refresh();
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
                tablaCuentas.refresh();
                DataUtil.guardarCuentas(listaCuentas);
                lblMensaje.setText("Depósito realizado.");
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
                tablaCuentas.refresh();
                DataUtil.guardarCuentas(listaCuentas);
                lblMensaje.setText("Retiro realizado.");
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
                tablaCuentas.refresh();
                DataUtil.guardarCuentas(listaCuentas);
                lblMensaje.setText("Transferencia realizada.");
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
        }
    }

    private void limpiarCampos() {
        txtBanco.clear();
        txtNumero.clear();
        txtTipo.clear();
        tablaCuentas.getSelectionModel().clearSelection();
    }
}