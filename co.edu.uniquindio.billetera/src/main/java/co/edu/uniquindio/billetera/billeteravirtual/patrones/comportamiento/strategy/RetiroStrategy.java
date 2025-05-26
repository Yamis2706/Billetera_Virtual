package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy;

import co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy.TransaccionStrategy;

public class RetiroStrategy implements TransaccionStrategy {
    @Override
    public void ejecutarTransaccion(double monto) {
        System.out.println("Retiro realizado por: " + monto);
    }
}