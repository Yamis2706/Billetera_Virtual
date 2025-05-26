package co.edu.uniquindio.billetera.billeteravirtual.mapping.mappers;

import co.edu.uniquindio.billetera.billeteravirtual.mapping.dto.UsuarioDto;
import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import java.util.List;

public interface IBilleteraVirtualMapping {
    List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios);
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);
    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);
}