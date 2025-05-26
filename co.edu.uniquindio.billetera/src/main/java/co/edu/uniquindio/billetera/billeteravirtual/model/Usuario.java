package co.edu.uniquindio.billetera.billeteravirtual.model;

import co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.observer.Observer;
import co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.observer.Sujeto;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Sujeto {
    private String idUsuario;
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String direccion;
    private double saldo;
    private List<Cuenta> cuentas;
    private List<Observer> observadores = new ArrayList<>();

    // Constructor privado para el builder
    private Usuario(Builder builder) {
        this.idUsuario = builder.idUsuario;
        this.nombre = builder.nombre;
        this.cedula = builder.cedula;
        this.correo = builder.correo;
        this.telefono = builder.telefono;
        this.direccion = builder.direccion;
        this.saldo = builder.saldo;
        this.cuentas = new ArrayList<>();
    }

    // Métodos Observer
    @Override
    public void agregarObservador(Observer observador) {
        observadores.add(observador);
    }

    @Override
    public void eliminarObservador(Observer observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(String mensaje) {
        for (Observer obs : observadores) {
            obs.actualizar(mensaje);
        }
    }

    // Ejemplo de método que notifica
    public void agregarSaldo(double monto) {
        this.saldo += monto;
        notificarObservadores("Se ha agregado saldo: " + monto + ". Nuevo saldo: " + saldo);
    }

    // Getters y setters
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
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
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public List<Cuenta> getCuentas() { return cuentas; }
    public void setCuentas(List<Cuenta> cuentas) { this.cuentas = cuentas; }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String idUsuario;
        private String nombre;
        private String cedula;
        private String correo;
        private String telefono;
        private String direccion;
        private double saldo;

        public Builder idUsuario(String idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder cedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder saldo(double saldo) {
            this.saldo = saldo;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }

    public String getNombreCompleto() {
        return nombre; // O agrega el atributo apellido si lo necesitas
    }
}