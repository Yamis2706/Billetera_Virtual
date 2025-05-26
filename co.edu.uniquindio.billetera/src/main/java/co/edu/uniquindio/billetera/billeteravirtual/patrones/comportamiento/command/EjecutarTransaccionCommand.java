package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.command;

import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;

public class EjecutarTransaccionCommand implements Command {
    private Transaccion transaccion;

    public EjecutarTransaccionCommand(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    @Override
    public void ejecutar() {
        transaccion.ejecutar();
    }
}
