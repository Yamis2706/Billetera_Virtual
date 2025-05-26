package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory;

public class CorrienteFactory implements BilleteraAbstractFactory {
    @Override
    public Cuenta crearCuenta() {
        return new CuentaCorriente();
    }

    @Override
    public Tarjeta crearTarjeta() {
        return new TarjetaCredito();
    }
}
