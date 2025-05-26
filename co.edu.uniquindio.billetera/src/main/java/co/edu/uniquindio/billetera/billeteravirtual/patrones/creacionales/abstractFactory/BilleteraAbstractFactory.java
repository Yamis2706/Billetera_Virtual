package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory;

public interface BilleteraAbstractFactory {
    Cuenta crearCuenta();
    Tarjeta crearTarjeta();
}
