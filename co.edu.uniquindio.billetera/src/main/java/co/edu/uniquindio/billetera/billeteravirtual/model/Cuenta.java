package co.edu.uniquindio.billetera.billeteravirtual.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cuenta implements Serializable {
    private String idCuenta;
    private String banco;
    private String numero;
    private String tipo;
    private double saldo;
    private List<String> movimientos = new ArrayList<>();

    public Cuenta() {}

    // Constructor de 3 parámetros (genera id aleatorio)
    public Cuenta(String banco, String numero, String tipo) {
        this.idCuenta = generarIdCuenta();
        this.banco = banco;
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = 0.0;
    }

    // Constructor de 4 parámetros (usa id proporcionado)
    public Cuenta(String idCuenta, String banco, String numero, String tipo) {
        this.idCuenta = idCuenta;
        this.banco = banco;
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = 0.0;
    }

    private String generarIdCuenta() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(5);
        Random rnd = new Random();
        for (int i = 0; i < 5; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public void registrarMovimiento(String descripcion) {
        movimientos.add(descripcion);
    }

    public List<String> getMovimientos() {
        return movimientos;
    }

    public void depositarDinero(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        this.saldo += monto;
        registrarMovimiento("Depósito: +" + monto);
    }

    public void retirarDinero(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        if (monto > saldo) throw new IllegalArgumentException("Saldo insuficiente.");
        this.saldo -= monto;
        registrarMovimiento("Retiro: -" + monto);
    }

    public void transferirDinero(Cuenta destino, double monto) {
        this.retirarDinero(monto);
        destino.depositarDinero(monto);
        registrarMovimiento("Transferencia enviada: -" + monto + " a " + destino.getNumero());
        destino.registrarMovimiento("Transferencia recibida: +" + monto + " de " + this.getNumero());
    }

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

    @Override
    public String toString() {
        return banco + " - " + numero + " (" + tipo + ")";
    }
}