package co.edu.uniquindio.billetera.billeteravirtual.patrones.estructurales.adapter;

public class SistemaPagoExternoAdapter implements PasarelaPago {
    private SistemaPagoExterno sistemaPagoExterno;

    public SistemaPagoExternoAdapter(SistemaPagoExterno sistemaPagoExterno) {
        this.sistemaPagoExterno = sistemaPagoExterno;
    }

    @Override
    public boolean procesarPago(double monto) {
        sistemaPagoExterno.realizarPagoExterno(monto);
        return true;
    }
}
