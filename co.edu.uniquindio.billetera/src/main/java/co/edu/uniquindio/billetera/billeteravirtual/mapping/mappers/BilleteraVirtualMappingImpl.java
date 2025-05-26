package co.edu.uniquindio.billetera.billeteravirtual.mapping.mappers;

import co.edu.uniquindio.billetera.billeteravirtual.mapping.dto.UsuarioDto;
import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class BilleteraVirtualMappingImpl implements IBilleteraVirtualMapping {

    @Override
    public List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios) {
        if (listaUsuarios == null) {
            return null;
        }
        List<UsuarioDto> listaUsuariosDto = new ArrayList<>(listaUsuarios.size());
        for (Usuario usuario : listaUsuarios) {
            listaUsuariosDto.add(usuarioToUsuarioDto(usuario));
        }
        return listaUsuariosDto;
    }

    @Override
    public UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getNombre(),
                usuario.getCedula(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getDireccion()
        );
    }

    @Override
    public Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .nombre(usuarioDto.getNombre())
                .cedula(usuarioDto.getCedula())
                .correo(usuarioDto.getCorreo())
                .telefono(usuarioDto.getTelefono())
                .direccion(usuarioDto.getDireccion())
                .build();
    }
}