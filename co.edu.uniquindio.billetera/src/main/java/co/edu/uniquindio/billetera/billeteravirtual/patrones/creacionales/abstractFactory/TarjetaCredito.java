package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory;

public class TarjetaCredito implements Tarjeta {
    @Override
    public void mostrarTipoTarjeta() {
        System.out.println("Tarjeta Crédito");
    }
}
