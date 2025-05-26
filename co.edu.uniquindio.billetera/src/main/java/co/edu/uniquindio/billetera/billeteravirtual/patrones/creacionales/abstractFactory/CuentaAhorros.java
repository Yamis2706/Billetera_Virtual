package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory;

public class CuentaAhorros implements Cuenta {
    @Override
    public void mostrarTipoCuenta() {
        System.out.println("Cuenta de Ahorros");
    }
}