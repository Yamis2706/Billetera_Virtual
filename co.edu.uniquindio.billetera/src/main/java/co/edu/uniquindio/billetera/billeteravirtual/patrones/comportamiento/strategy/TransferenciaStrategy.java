package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy;



public class TransferenciaStrategy implements TransaccionStrategy {
    @Override
    public void ejecutarTransaccion(double monto) {
        System.out.println("Transferencia realizada por: " + monto);
    }
}