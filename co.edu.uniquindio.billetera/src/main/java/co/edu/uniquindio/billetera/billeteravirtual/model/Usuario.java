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

    // Lista de observadores
    private final List<Observer> observadores = new ArrayList<>();

    private List<Cuenta> cuentas = new ArrayList<>();

    // Getter para cuentas
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    // Implementación del método de Sujeto
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
        // Implementación pendiente
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
}