package co.edu.uniquindio.billetera.billeteravirtual.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.observer.Observer;
import co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.observer.Sujeto;

public class Usuario implements Sujeto, Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String direccion;
    private String clave;
    private String tipo; // "Admin" o "Cliente"

    // Lista de observadores
    private final List<Observer> observadores = new ArrayList<>();

    private List<Cuenta> cuentas = new ArrayList<>();
    private List<Transaccion> transacciones = new ArrayList<>();
    private List<Presupuesto> presupuestos = new ArrayList<>();

    // Constructor sin argumentos
    public Usuario() {}

    // Constructor con argumentos principales
    public Usuario(String cedula, String nombre, String correo, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.tipo = "Usuario";
    }

    // Getters y setters para listas
    public List<Cuenta> getCuentas() { return cuentas; }
    public void setCuentas(List<Cuenta> cuentas) { this.cuentas = cuentas; }

    public List<Transaccion> getTransacciones() { return transacciones; }
    public void setTransacciones(List<Transaccion> transacciones) { this.transacciones = transacciones; }

    public List<Presupuesto> getPresupuestos() { return presupuestos; }
    public void setPresupuestos(List<Presupuesto> presupuestos) { this.presupuestos = presupuestos; }

    // Métodos Observer
    @Override
    public void agregarObservador(Observer observer) {
        if (observer != null && !observadores.contains(observer)) {
            observadores.add(observer);
        }
    }

    @Override
    public void eliminarObservador(Observer observador) {
        if (observador != null) {
            observadores.remove(observador);
        }
    }

    @Override
    public void notificarObservadores(String mensaje) {
        for (Observer obs : observadores) {
            obs.actualizar(mensaje);
        }
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Usuario usuario = new Usuario();

        public Builder nombre(String nombre) {
            usuario.nombre = nombre;
            return this;
        }

        public Builder cedula(String cedula) {
            usuario.cedula = cedula;
            return this;
        }

        public Builder correo(String correo) {
            usuario.correo = correo;
            return this;
        }

        public Builder telefono(String telefono) {
            usuario.telefono = telefono;
            return this;
        }

        public Builder direccion(String direccion) {
            usuario.direccion = direccion;
            return this;
        }

        public Builder clave(String clave) {
            usuario.clave = clave;
            return this;
        }

        public Builder tipo(String tipo) {
            usuario.tipo = tipo;
            return this;
        }

        public Usuario build() {
            return usuario;
        }
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}