package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.command;

public class InvocadorTransaccion {
    private Command comando;

    public void setComando(Command comando) {
        this.comando = comando;
    }

    public void ejecutarComando() {
        if (comando != null) {
            comando.ejecutar();
        }
    }
}