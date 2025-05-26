package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.mapping.dto.UsuarioDto;
import co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.abstractFactory.ModelFactory;
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