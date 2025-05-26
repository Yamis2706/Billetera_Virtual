package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory;

import co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory.Tarjeta;

public class TarjetaDebito implements Tarjeta {
    @Override
    public void mostrarTipoTarjeta() {
        System.out.println("Tarjeta Débito");
    }
}
