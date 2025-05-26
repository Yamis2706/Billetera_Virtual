package co.edu.uniquindio.billetera.billeteravirtual.patrones.estructurales.facade;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;
import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;
import java.util.ArrayList;
import java.util.List;

public class BilleteraFacade {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Transaccion> transacciones = new ArrayList<>();

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario registrado: " + usuario.getNombre());
    }

    public void agregarCuentaAUsuario(Usuario usuario, Cuenta cuenta) {
        usuario.getCuentas().add(cuenta);
        System.out.println("Cuenta agregada a usuario: " + usuario.getNombre());
    }

    public void ejecutarTransaccion(Transaccion transaccion) {
        transaccion.ejecutar();
        transacciones.add(transaccion);
        System.out.println("Transacción ejecutada: " + transaccion.getIdTransaccion());
    }

    public void depositar(Cuenta cuenta, double monto) {
        cuenta.depositarDinero(monto);
    }

    public void retirar(Cuenta cuenta, double monto) {
        cuenta.retirarDinero(monto);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }
}