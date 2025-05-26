package co.edu.uniquindio.billetera.billeteravirtual.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Presupuesto implements Serializable {
    private String nombre;
    private double monto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Presupuesto(String nombre, double monto, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}