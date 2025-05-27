package co.edu.uniquindio.billetera.billeteravirtual.model;

import java.io.Serializable;

public class Presupuesto implements Serializable {
    private String idPresupuesto;
    private String idCuenta; // Campo para asociar el presupuesto a una cuenta
    private String nombre;
    private double montoTotal;
    private double montoGastado;
    private Categoria categoria;

    public Presupuesto(String idPresupuesto, String idCuenta, String nombre, double montoTotal, double montoGastado, Categoria categoria) {
        this.idPresupuesto = idPresupuesto;
        this.idCuenta = idCuenta;
        this.nombre = nombre;
        this.montoTotal = montoTotal;
        this.montoGastado = montoGastado;
        this.categoria = categoria;
    }

    public String getIdPresupuesto() { return idPresupuesto; }
    public void setIdPresupuesto(String idPresupuesto) { this.idPresupuesto = idPresupuesto; }

    public String getIdCuenta() { return idCuenta; }
    public void setIdCuenta(String idCuenta) { this.idCuenta = idCuenta; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public double getMontoGastado() { return montoGastado; }
    public void setMontoGastado(double montoGastado) { this.montoGastado = montoGastado; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    // Método para obtener el saldo restante del presupuesto
    public double getSaldoRestante() {
        return montoTotal - montoGastado;
    }
}