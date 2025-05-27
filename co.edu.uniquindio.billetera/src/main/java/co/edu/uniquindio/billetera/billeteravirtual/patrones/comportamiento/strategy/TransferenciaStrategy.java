package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy;

import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;

public class TransferenciaStrategy implements TransaccionStrategy {
    @Override
    public void ejecutarTransaccion(Transaccion transaccion) {
        System.out.println("Transferencia realizada por: " + transaccion.getMonto());
        // Aquí puedes agregar la lógica de transferencia usando transaccion.getCuenta(), transaccion.getCuentaDestino(), etc.
    }
}