package co.edu.uniquindio.billetera.billeteravirtual.model;

import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idCuenta;
    private String banco;
    private String numero;
    private String tipo;
    private double saldo;
    private double saldoInicial;
    private String idUsuario;
    private String idPresupuesto;
    private List<String> movimientos = new ArrayList<>();

    // Método para generar un ID aleatorio de 5 caracteres alfanuméricos
    private static String generarIdAleatorio() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int idx = (int) (Math.random() * caracteres.length());
            sb.append(caracteres.charAt(idx));
        }
        return sb.toString();
    }

    // Constructor principal
    public Cuenta(String banco, String numero, String tipo, double saldoInicial, String idUsuario) {
        this.idCuenta = generarIdAleatorio();
        this.banco = banco;
        this.numero = numero;
        this.tipo = tipo;
        this.saldoInicial = saldoInicial;
        this.saldo = saldoInicial;
        this.idUsuario = idUsuario;
        // Agrega y guarda la cuenta automáticamente al crearla
        List<Cuenta> cuentas = DataUtil.cargarCuentas();
        cuentas.add(this);
        DataUtil.guardarCuentas(cuentas);
    }

    // Constructor vacío (opcional, útil para serialización)
    public Cuenta() {}

    // Método para actualizar los datos de la cuenta desde otra instancia
    public void actualizarDesde(Cuenta otra) {
        this.banco = otra.banco;
        this.numero = otra.numero;
        this.tipo = otra.tipo;
        this.saldo = otra.saldo;
        this.saldoInicial = otra.saldoInicial;
        this.idUsuario = otra.idUsuario;
        this.idPresupuesto = otra.idPresupuesto;
        this.movimientos = new ArrayList<>(otra.movimientos);
    }

    // Método estático para actualizar una cuenta en la lista y persistir los cambios
    public static void actualizarCuentaEnLista(Cuenta cuentaActualizada) {
        List<Cuenta> cuentas = DataUtil.cargarCuentas();
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getNumero().equals(cuentaActualizada.getNumero())) {
                cuentas.set(i, cuentaActualizada);
                break;
            }
        }
        DataUtil.guardarCuentas(cuentas);
    }

    // Getters y setters
    public String getIdCuenta() { return idCuenta; }
    public void setIdCuenta(String idCuenta) { this.idCuenta = idCuenta; }

    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public double getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(double saldoInicial) { this.saldoInicial = saldoInicial; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getIdPresupuesto() { return idPresupuesto; }
    public void setIdPresupuesto(String idPresupuesto) { this.idPresupuesto = idPresupuesto; }

    public List<String> getMovimientos() { return movimientos; }
    public void setMovimientos(List<String> movimientos) { this.movimientos = movimientos; }

    // Métodos de operación
    public void depositarDinero(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        this.saldo += monto;
        movimientos.add("Depósito: +" + monto);
        actualizarCuentaEnLista(this);
    }

    public void retirarDinero(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        if (this.saldo < monto) throw new IllegalArgumentException("Saldo insuficiente");
        this.saldo -= monto;
        movimientos.add("Retiro: -" + monto);
        actualizarCuentaEnLista(this);
    }

    public void transferirDinero(Cuenta destino, double monto) {
        if (destino == null) throw new IllegalArgumentException("Cuenta destino inválida");
        this.retirarDinero(monto);
        destino.depositarDinero(monto);
        movimientos.add("Transferencia a " + destino.getNumero() + ": -" + monto);
        destino.getMovimientos().add("Transferencia desde " + this.getNumero() + ": +" + monto);
        actualizarCuentaEnLista(this);
        actualizarCuentaEnLista(destino);
    }
}