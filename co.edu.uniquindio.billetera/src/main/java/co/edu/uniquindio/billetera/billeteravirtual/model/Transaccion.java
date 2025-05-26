package co.edu.uniquindio.billetera.billeteravirtual.model;

import co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy.TransaccionStrategy;
import java.time.LocalDate;

public class Transaccion {
    private String idTransaccion;
    private LocalDate fecha;
    private String tipo; // Depósito, Retiro, Transferencia
    private double monto;
    private String descripcion;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private Categoria categoria;
    private TransaccionStrategy estrategia;

    public Transaccion(String idTransaccion, LocalDate fecha, String tipo, double monto, String descripcion,
                       Cuenta cuentaOrigen, Cuenta cuentaDestino, Categoria categoria, TransaccionStrategy estrategia) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.categoria = categoria;
        this.estrategia = estrategia;
    }

    // Getters y setters
    public String getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(String idTransaccion) { this.idTransaccion = idTransaccion; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Cuenta getCuentaOrigen() { return cuentaOrigen; }
    public void setCuentaOrigen(Cuenta cuentaOrigen) { this.cuentaOrigen = cuentaOrigen; }
    public Cuenta getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(Cuenta cuentaDestino) { this.cuentaDestino = cuentaDestino; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public TransaccionStrategy getEstrategia() { return estrategia; }
    public void setEstrategia(TransaccionStrategy estrategia) { this.estrategia = estrategia; }

    public void ejecutar() {
        if (estrategia != null) {
            estrategia.ejecutarTransaccion(monto);
        }
    }
}