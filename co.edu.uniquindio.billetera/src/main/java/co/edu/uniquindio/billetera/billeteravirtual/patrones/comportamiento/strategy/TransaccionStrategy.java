package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy;

public interface TransaccionStrategy {
    void ejecutarTransaccion(double monto);
}