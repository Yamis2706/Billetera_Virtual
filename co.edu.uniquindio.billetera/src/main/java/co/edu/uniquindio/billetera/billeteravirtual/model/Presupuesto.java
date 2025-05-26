package co.edu.uniquindio.billetera.billeteravirtual.model;

public class Presupuesto {
    private String idPresupuesto;
    private String nombre;
    private double montoAsignado;
    private double montoGastado;
    private Categoria categoria;

    public Presupuesto(String idPresupuesto, String nombre, double montoAsignado, double montoGastado, Categoria categoria) {
        this.idPresupuesto = idPresupuesto;
        this.nombre = nombre;
        this.montoAsignado = montoAsignado;
        this.montoGastado = montoGastado;
        this.categoria = categoria;
    }

    // Getters y setters
    public String getIdPresupuesto() { return idPresupuesto; }
    public void setIdPresupuesto(String idPresupuesto) { this.idPresupuesto = idPresupuesto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getMontoAsignado() { return montoAsignado; }
    public void setMontoAsignado(double montoAsignado) { this.montoAsignado = montoAsignado; }
    public double getMontoGastado() { return montoGastado; }
    public void setMontoGastado(double montoGastado) { this.montoGastado = montoGastado; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}