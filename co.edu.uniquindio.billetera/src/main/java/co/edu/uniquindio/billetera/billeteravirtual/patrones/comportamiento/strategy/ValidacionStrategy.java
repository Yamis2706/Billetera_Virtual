package co.edu.uniquindio.billetera.billeteravirtual.patrones.comportamiento.strategy;

// Archivo: co/edu/uniquindio/billeteravirtual/billetera_virtual/strategy/ValidacionStrategy.java
package co.edu.uniquindio.billeteravirtual.billetera_virtual.strategy;

public interface ValidacionStrategy {
    boolean validar(String valor);
}