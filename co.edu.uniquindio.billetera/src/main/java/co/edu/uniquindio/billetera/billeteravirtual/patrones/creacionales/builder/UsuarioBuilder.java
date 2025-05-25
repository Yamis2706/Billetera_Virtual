package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.builder;

import co.edu.uniquindio.billeteravirtual.billetera_virtual.model.Usuario;

public class UsuarioBuilder {
    protected String nombre;
    protected String cedula;
    protected String correo;
    protected String telefono;
    protected String direccion;

    public UsuarioBuilder nombre(String nombre) {
        this.nombre = nombre;
        return this; // Devuelve la instancia actual de UsuarioBuilder
    }

    public UsuarioBuilder cedula(String cedula) {
        this.cedula = cedula;
        return this; // Devuelve la instancia actual de UsuarioBuilder
    }

    public UsuarioBuilder correo(String correo) {
        this.correo = correo;
        return this; // Devuelve la instancia actual de UsuarioBuilder
    }

    public UsuarioBuilder telefono(String telefono) {
        this.telefono = telefono;
        return this; // Devuelve la instancia actual de UsuarioBuilder
    }

    public UsuarioBuilder direccion(String direccion) {
        this.direccion = direccion;
        return this; // Devuelve la instancia actual de UsuarioBuilder
    }

    public Usuario build() {
        return new Usuario(nombre, cedula, correo, telefono, direccion);
    }
}