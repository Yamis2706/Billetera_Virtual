package co.edu.uniquindio.billetera.billeteravirtual.patrones.estructurales.decorator;

import co.edu.uniquindio.billetera.billeteravirtual.model.Transaccion;

public class NotificacionTransaccionDecorator extends TransaccionDecorator {

    public NotificacionTransaccionDecorator(Transaccion transaccion) {
        super(transaccion);
    }

    @Override
    public void ejecutar() {
        super.ejecutar();
        enviarNotificacion();
    }

    private void enviarNotificacion() {
        System.out.println("Notificación: Se ejecutó la transacción " + getIdTransaccion());
    }
}