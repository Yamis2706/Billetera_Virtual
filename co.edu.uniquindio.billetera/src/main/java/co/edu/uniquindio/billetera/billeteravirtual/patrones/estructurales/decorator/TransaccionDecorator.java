package co.edu.uniquindio.billetera.billeteravirtual.patrones.estructurales.decorator;

import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;

public abstract class TransaccionDecorator extends Transaccion {
    protected Transaccion transaccion;

    public TransaccionDecorator(Transaccion transaccion) {
        super(
                transaccion.getIdTransaccion(),
                transaccion.getFecha(),
                transaccion.getTipo(),
                transaccion.getMonto(),
                transaccion.getDescripcion(),
                transaccion.getCuentaOrigen(),
                transaccion.getCuentaDestino(),
                transaccion.getCategoria(),
                transaccion.getPresupuesto()
        );
        this.transaccion = transaccion;
    }

    @Override
    public void ejecutar() {
        transaccion.ejecutar();
    }
}