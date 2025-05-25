package co.edu.uniquindio.billetera.billeteravirtual.mapping.mappers;

import co.edu.uniquindio.billeteravirtual.billetera_virtual.mapping.dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.model.Usuario;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.service.IBilleteraVirtualMapping;
import java.util.ArrayList;
import java.util.List;

public class BilleteraVirtualMappingImpl implements IBilleteraVirtualMapping {
    @Override
    public List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios) {
        if(listaUsuarios == null){
            return null;
        }
        List<UsuarioDto> listaUsuariosDto = new ArrayList<UsuarioDto>(listaUsuarios.size());
        for (Usuario usuario : listaUsuarios) {
            listaUsuariosDto.add(usuarioToUsuarioDto(usuario));
        }

        return listaUsuariosDto;
    }

    @Override
    public UsuarioDto clienteToClienteDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getNombre(),
                usuario.getCedula(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getDireccion());
    }

    @Override
    public Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .nombre(usuarioDto.nombre())
                .cedula(usuarioDto.cedula())
                .correo(usuarioDto.correo())
                .telefono(usuarioDto.telefono())
                .direccion(usuarioDto.direccion())
                .build();
    }
}