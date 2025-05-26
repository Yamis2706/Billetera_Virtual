package co.edu.uniquindio.billetera.billeteravirtual.model;


public class Cuenta {
    private String idCuenta;
    private String banco;
    private String numeroCuenta;
    private String tipoCuenta; // Ahorro, Corriente, etc.
    private double saldo;

    public Cuenta(String idCuenta, String banco, String numeroCuenta, String tipoCuenta) {
        this.idCuenta = idCuenta;
        this.banco = banco;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = 0.0;
    }

    // Métodos para depositar y retirar
    public void depositar(double monto) {
        this.saldo += monto;
    }

    public void retirar(double monto) {
        if (monto > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        this.saldo -= monto;
    }

    // Getters y setters
    public String getIdCuenta() { return idCuenta; }
    public void setIdCuenta(String idCuenta) { this.idCuenta = idCuenta; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
}