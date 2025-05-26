package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.builder;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;

public class UsuarioBuilder {
    protected String nombre;
    protected String cedula;
    protected String correo;
    protected String telefono;
    protected String direccion;

    public UsuarioBuilder nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public UsuarioBuilder cedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public UsuarioBuilder correo(String correo) {
        this.correo = correo;
        return this;
    }

    public UsuarioBuilder telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public UsuarioBuilder direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public Usuario build() {
        return Usuario.builder()
                .nombre(nombre)
                .cedula(cedula)
                .correo(correo)
                .telefono(telefono)
                .direccion(direccion)
                .build();
    }
}