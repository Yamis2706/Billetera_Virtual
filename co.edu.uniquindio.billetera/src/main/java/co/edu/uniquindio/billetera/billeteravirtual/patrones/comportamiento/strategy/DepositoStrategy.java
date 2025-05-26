package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy;

import co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy.TransaccionStrategy;

public class DepositoStrategy implements TransaccionStrategy {
    @Override
    public void ejecutarTransaccion(double monto) {
        System.out.println("Depósito realizado por: " + monto);
    }
}