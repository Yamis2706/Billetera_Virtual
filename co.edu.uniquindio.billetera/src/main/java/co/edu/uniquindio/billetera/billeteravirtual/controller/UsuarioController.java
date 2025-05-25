package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billeteravirtual.billetera_virtual.mapping.dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.patrones.creacionales.factoryMethod.ModelFactory;
import java.util.List;

public class UsuarioController {
    ModelFactory modelFactory;
    public UsuarioController(){
        modelFactory = ModelFactory.getInstancia();
    }

    public List<UsuarioDto> obtenerUsuarios() {
        return modelFactory.obtenerUsuarios();
    }
}