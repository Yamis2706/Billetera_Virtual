package co.edu.uniquindio.billetera.billeteravirtual.service;

import co.edu.uniquindio.billeteravirtual.billetera_virtual.mapping.dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.model.Usuario;
import java.util.List;

public interface IBilleteraVirtualMapping {
    List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios);
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);
    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);
}