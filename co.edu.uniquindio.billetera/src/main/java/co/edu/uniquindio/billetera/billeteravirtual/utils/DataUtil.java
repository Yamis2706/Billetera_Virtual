package co.edu.uniquindio.billetera.billeteravirtual.utils;

import co.edu.uniquindio.billeteravirtual.billetera_virtual.model.Usuario;

public class DataUtil {

    public static BilleteraVirtualObjeto inicializarDatos() {
        BilleteraVirtualObjeto billeteraVirtualObjeto = new BilleteraVirtualObjeto();
        Usuario usuario1 = Usuario.builder()
                .nombre("juan")
                .cedula("1094")
                .telefono("3175761571")
                .direccion("armenia")
                .build();

        Usuario usuario2 = Usuario.builder()
                .nombre("Ana")
                .cedula("1095")
                .telefono("3108742066")
                .direccion("quimbaya")
                .build();

        Usuario usuario3 = Usuario.builder()
                .nombre("Pedro")
                .cedula("1096")
                .telefono("3144738306")
                .direccion("armenia")
                .build();

        billeteraVirtualObjeto.getListaUsuarios().add(usuario1);
        billeteraVirtualObjeto.getListaUsuarios().add(usuario2);
        billeteraVirtualObjeto.getListaUsuarios().add(usuario3);

        return billeteraVirtualObjeto;
    }
}