package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory;

import co.edu.uniquindio.billetera.billeteravirtual.mapping.dto.UsuarioDto;
import java.util.Collections;
import java.util.List;

public class ModelFactory {
    private static final ModelFactory instancia = new ModelFactory();

    private ModelFactory() {}

    public static ModelFactory getInstancia() {
        return instancia;
    }

    public List<UsuarioDto> obtenerUsuarios() {
        // Retorna una lista vacía como ejemplo
        return Collections.emptyList();
    }
}