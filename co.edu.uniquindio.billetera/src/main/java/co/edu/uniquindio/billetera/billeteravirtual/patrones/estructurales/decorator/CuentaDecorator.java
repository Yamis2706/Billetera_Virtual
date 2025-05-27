package co.edu.uniquindio.billetera.billeteravirtual.patrones.estructurales.decorator;

import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;

import java.io.Serializable;

public abstract class CuentaDecorator extends Cuenta implements Serializable {
    protected final Cuenta cuenta;

    public CuentaDecorator(Cuenta cuenta) {
        super();
        this.cuenta = cuenta;
    }
    @Override
    public String toString() {
        return cuenta.toString();
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

    @Override
    public String getNumero() {
        return cuenta.getNumero();
    }

    @Override
    public String getBanco() {
        return cuenta.getBanco();
    }

    @Override
    public String getTipo() {
        return cuenta.getTipo();
    }

    @Override
    public String getIdUsuario() {
        return cuenta.getIdUsuario();
    }

    @Override
    public double getSaldoInicial() {
        return cuenta.getSaldoInicial();
    }
}