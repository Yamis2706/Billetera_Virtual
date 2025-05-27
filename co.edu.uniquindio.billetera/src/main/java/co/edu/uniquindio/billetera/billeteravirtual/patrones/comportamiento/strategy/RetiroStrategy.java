package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy;

import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;

public class RetiroStrategy implements TransaccionStrategy {
    @Override
    public void ejecutarTransaccion(Transaccion transaccion) {
        System.out.println("Retiro realizado por: " + transaccion.getMonto());
        // Aquí puedes agregar la lógica de retiro usando transaccion.getCuenta(), etc.
    }
}