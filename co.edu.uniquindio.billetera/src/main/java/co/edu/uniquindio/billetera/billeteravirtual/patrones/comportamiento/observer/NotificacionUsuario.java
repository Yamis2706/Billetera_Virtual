package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.observer;

public class NotificacionUsuario implements Observer {
    private String nombre;

    public NotificacionUsuario(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println("[" + nombre + "] Notificación: " + mensaje);
    }
}
