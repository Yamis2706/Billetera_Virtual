package co.edu.uniquindio.billetera.billeteravirtual.patrones.estructurales.decorator;

import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;

public abstract class CuentaDecorator extends Cuenta {
    protected final Cuenta cuenta;

    public CuentaDecorator(Cuenta cuenta) {
        super(cuenta.getIdCuenta(), cuenta.getBanco(), cuenta.getNumeroCuenta(), cuenta.getTipoCuenta());
        this.cuenta = cuenta;
    }

    @Override
    public void depositar(double monto) {
        cuenta.depositar(monto);
    }

    @Override
    public void retirar(double monto) {
        cuenta.retirar(monto);
    }

    @Override
    public double getSaldo() {
        return cuenta.getSaldo();
    }
}
