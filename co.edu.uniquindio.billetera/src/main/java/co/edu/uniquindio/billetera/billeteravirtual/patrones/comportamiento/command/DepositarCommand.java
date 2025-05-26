package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.command;

import co.edu.uniquindio.billetera.billeteravirtual.model.Cuenta;

public class DepositarCommand implements Command {
    private final Cuenta cuenta;
    private final double monto;

    public DepositarCommand(Cuenta cuenta, double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() {
        cuenta.depositar(monto);
    }
}
