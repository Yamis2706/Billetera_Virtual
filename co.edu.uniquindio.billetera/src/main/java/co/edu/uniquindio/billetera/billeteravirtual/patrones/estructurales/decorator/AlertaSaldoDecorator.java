package co.edu.uniquindio.billetera.billeteravirtual.patrones.estructurales.decorator;

import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;

public class AlertaSaldoDecorator extends CuentaDecorator {
    public AlertaSaldoDecorator(Cuenta cuenta) {
        super(cuenta);
    }

    @Override
    public void retirar(double monto) {
        cuenta.retirar(monto);
        if (cuenta.getSaldo() < 10000) {
            System.out.println("¡Alerta! Saldo bajo en la cuenta.");
        }
    }
}