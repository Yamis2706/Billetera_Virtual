package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory;

public class AhorrosFactory implements BilleteraAbstractFactory {
    @Override
    public Cuenta crearCuenta() {
        return new CuentaAhorros();
    }

    @Override
    public Tarjeta crearTarjeta() {
        return new TarjetaDebito();
    }
}