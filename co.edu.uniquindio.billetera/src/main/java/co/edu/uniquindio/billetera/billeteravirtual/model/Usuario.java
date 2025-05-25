package co.edu.uniquindio.billetera.billeteravirtual.model;

import co.edu.uniquindio.billeteravirtual.billetera_virtual.patrones.creacionales.builder.UsuarioBuilder;

public class Usuario {
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String direccion;

    public Usuario(String nombre, String cedula, String correo, String telefono, String direccion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}