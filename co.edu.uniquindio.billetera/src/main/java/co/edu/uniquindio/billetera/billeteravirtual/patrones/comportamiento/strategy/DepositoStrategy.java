package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy;

import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;

public class DepositoStrategy implements TransaccionStrategy {
    @Override
    public void ejecutarTransaccion(Transaccion transaccion) {
        System.out.println("Depósito realizado por: " + transaccion.getMonto());
        // Aquí puedes agregar la lógica de depósito usando transaccion.getCuenta(), etc.
    }
}