package co.edu.uniquindio.billetera.billeteravirtual.patrones.estructurales.decorator;

import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;

public abstract class CuentaDecorator extends Cuenta {
    protected final Cuenta cuenta;

    public CuentaDecorator(Cuenta cuenta) {
        super(cuenta.getIdCuenta(), cuenta.getBanco(), cuenta.getNumero(), cuenta.getTipo());
        this.cuenta = cuenta;
    }

    @Override
    public void depositarDinero(double monto) {
        cuenta.depositarDinero(monto);
    }

    @Override
    public void retirarDinero(double monto) {
        cuenta.retirarDinero(monto);
    }

    @Override
    public double getSaldo() {
        return cuenta.getSaldo();
    }
}