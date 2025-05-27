package co.edu.uniquindio.billetera.billeteravirtual.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaccion implements Serializable {
    private String id;
    private LocalDate fecha;
    private String tipo;
    private double monto;
    private String descripcion;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private Categoria categoria;
    private Object otro; // Si tienes otro campo, ajústalo según tu modelo

    public Transaccion(String id, LocalDate fecha, String tipo, double monto, String descripcion,
                       Cuenta cuentaOrigen, Cuenta cuentaDestino, Categoria categoria, Object otro) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.categoria = categoria;
        this.otro = otro;
    }

    // Getters y setters
    public String getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public String getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public String getDescripcion() { return descripcion; }
    public Cuenta getCuentaOrigen() { return cuentaOrigen; }
    public Cuenta getCuentaDestino() { return cuentaDestino; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Object getOtro() { return otro; }
    public void setOtro(Object otro) { this.otro = otro; }

    // Método requerido por el facade
    public void ejecutar() {
        switch (tipo) {
            case "Depósito" -> {
                if (cuentaOrigen != null) {
                    cuentaOrigen.depositarDinero(monto);
                }
            }
            case "Retiro" -> {
                if (cuentaOrigen != null) {
                    cuentaOrigen.retirarDinero(monto);
                }
            }
            case "Transferencia" -> {
                if (cuentaOrigen != null && cuentaDestino != null) {
                    cuentaOrigen.transferirDinero(cuentaDestino, monto);
                }
            }
            default -> throw new UnsupportedOperationException("Tipo de transacción no soportado: " + tipo);
        }
    }

    // Método requerido por el facade
    public String getIdTransaccion() {
        return id;
    }
}
