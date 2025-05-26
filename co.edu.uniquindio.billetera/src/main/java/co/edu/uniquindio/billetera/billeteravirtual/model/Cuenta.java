package co.edu.uniquindio.billetera.billeteravirtual.model;

import java.io.Serializable;

public class Cuenta implements Serializable {
    private String idCuenta;
    private String banco;
    private String numero; // Cambiado para coincidir con el TableView
    private String tipo;   // Cambiado para coincidir con el TableView
    private double saldo;

    public Cuenta() {
        // Constructor vacío necesario para JavaFX y serialización
    }

    public Cuenta(String idCuenta, String banco, String numero, String tipo) {
        this.idCuenta = idCuenta;
        this.banco = banco;
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = 0.0;
    }

    // Métodos para depositar, retirar y transferir
    // En Cuenta.java
    public void depositarDinero(double monto) {
        if (monto > 0) {
            this.saldo += monto;
        }
    }

    public void retirarDinero(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        if (monto > saldo) throw new IllegalArgumentException("Saldo insuficiente");
        this.saldo -= monto;
    }

    public void transferirDinero(Cuenta destino, double monto) {
        this.retirarDinero(monto);
        destino.depositarDinero(monto);
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
}