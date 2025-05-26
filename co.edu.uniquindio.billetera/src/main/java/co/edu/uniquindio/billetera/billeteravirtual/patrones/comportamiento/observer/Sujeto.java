package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.observer;

public interface Sujeto {
    void agregarObservador(Observer observador);
    void eliminarObservador(Observer observador);
    void notificarObservadores(String mensaje);
}
