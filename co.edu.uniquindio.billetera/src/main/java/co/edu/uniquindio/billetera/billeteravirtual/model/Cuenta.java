package co.edu.uniquindio.billetera.billeteravirtual.model;

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
    private List<String> movimientos;

    // Método para generar un ID de 5 caracteres alfanuméricos
    private String generarIdCuenta() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int index = (int) (Math.random() * caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        return sb.toString();
    }

    // Constructor original
    public Cuenta(String banco, String numero, String tipo) {
        this.idCuenta = generarIdCuenta();
        this.banco = banco;
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = 0.0;
        this.movimientos = new ArrayList<>();
    }

    // Constructor con saldo inicial
    public Cuenta(String banco, String numero, String tipo, double saldoInicial) {
        this.idCuenta = generarIdCuenta();
        this.banco = banco;
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = saldoInicial;
        this.movimientos = new ArrayList<>();
    }

    public Cuenta(String idCuenta, String banco, String numero, String tipo) {
        this.idCuenta = idCuenta;
        this.banco = banco;
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = 0.0;
        this.movimientos = new ArrayList<>();
    }

    // Getters y Setters
    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<String> getMovimientos() {
        if (movimientos == null) {
            movimientos = new ArrayList<>();
        }
        return movimientos;
    }

    public void setMovimientos(List<String> movimientos) {
        this.movimientos = movimientos;
    }

    // Métodos de negocio
    public void depositarDinero(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        this.saldo += monto;
        this.getMovimientos().add("Depósito: +" + monto);
    }

    public void retirarDinero(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        if (monto > this.saldo) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        this.saldo -= monto;
        this.getMovimientos().add("Retiro: -" + monto);
    }

    public void transferirDinero(Cuenta destino, double monto) {
        if (destino == null) {
            throw new IllegalArgumentException("Cuenta destino no válida.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        if (monto > this.saldo) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        this.saldo -= monto;
        destino.saldo += monto;
        this.getMovimientos().add("Transferencia a " + destino.getNumero() + ": -" + monto);
        destino.getMovimientos().add("Transferencia desde " + this.getNumero() + ": +" + monto);
    }
}