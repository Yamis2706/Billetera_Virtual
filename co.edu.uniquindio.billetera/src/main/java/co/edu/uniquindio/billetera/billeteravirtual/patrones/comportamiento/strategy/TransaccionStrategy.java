package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy;

import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;

public interface TransaccionStrategy {
    void ejecutarTransaccion(Transaccion transaccion);
}