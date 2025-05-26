package co.edu.uniquindio.billetera.billeteravirtual.service;

import co.edu.uniquindio.billetera.billeteravirtual.mapping.dto.UsuarioDto;
import java.util.List;

public interface IModelFactoryService {
    List<UsuarioDto> obtenerUsuarios();
}