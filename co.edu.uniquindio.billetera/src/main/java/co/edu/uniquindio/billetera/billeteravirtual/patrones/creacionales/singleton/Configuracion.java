package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.singleton;

public class Configuracion {
    private static volatile Configuracion instancia;
    private String parametroGlobal;

    private Configuracion() {
        parametroGlobal = "valorPorDefecto";
    }

    public static Configuracion getInstancia() {
        if (instancia == null) {
            synchronized (Configuracion.class) {
                if (instancia == null) {
                    instancia = new Configuracion();
                }
            }
        }
        return instancia;
    }

    public String getParametroGlobal() {
        return parametroGlobal;
    }

    public void setParametroGlobal(String parametroGlobal) {
        this.parametroGlobal = parametroGlobal;
    }
}