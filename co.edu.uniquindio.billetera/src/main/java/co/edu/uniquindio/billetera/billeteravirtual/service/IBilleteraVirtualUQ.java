package co.edu.uniquindio.billetera.billeteravirtual.service;

import co.edu.uniquindio.billeteravirtual.billetera_virtual.model.Administrador;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.model.Presupuesto;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.model.Usuario;
import java.util.Date;
import java.util.List;

public interface IBilleteraVirtualUQ {
    boolean crearUsuario(String nombre, String cedula, String correo, String telefono, String direccion);
    void eliminarUsuario(String cedula);
    List<Usuario> obtenerUsuarios();
    Usuario obtenerUsuario(String cedula);
    void mostrarInformacionUsuarios();
    void buscarUsuario(String cedula);
    boolean actualizarUsuario(String cedulaActual, String nombre, String cedula, String correo, String telefono, String direccion);

    boolean crearObjeto(String idObjeto, String descripcion);
    Presupuesto obtenerPresupuesto(String idPresupuesto);
    Administrador obtenerAdministrador(String cedulaAdministrador);
    boolean crearTransaccion(String idTransaccion);
    Date fecha,
    String tipoTransaccion,
    float monto,
    String descripcion,
    String cuentaOrigen,
    String cuentaDestino,
    String categoria);

    boolean eliminarTransaccion(String idTransaccion);

    boolean actualizarTransaccion(String idTransaccionActual, String idTransaccion,  Date fecha,
                                  String tipoTransaccion,
                                  float monto,
                                  String descripcion,
                                  String cuentaOrigen,
                                  String cuentaDestino, String categoria);

    Transaccion obtenerTransaccion(String idTransaccion);
}