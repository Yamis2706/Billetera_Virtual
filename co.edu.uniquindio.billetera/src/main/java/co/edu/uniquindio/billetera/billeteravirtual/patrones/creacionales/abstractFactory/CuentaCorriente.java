package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory;

public class CuentaCorriente implements Cuenta {
    @Override
    public void mostrarTipoCuenta() {
        System.out.println("Cuenta Corriente");
    }
}
