package co.edu.uniquindio.billetera.billeteravirtual.utils;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import co.edu.uniquindio.billetera.billeteravirtual.model.BilleteraVirtualObjeto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    // Clave y correo quemados para el administrador
    public static final String ADMIN_CLAVE = "1234";
    public static final String ADMIN_CORREO = "@admin";

    // Archivo para persistencia de usuarios
    private static final String ARCHIVO_USUARIOS = "usuarios.dat";

    // Inicializa datos de ejemplo (puedes usarlo para pruebas)
    public static BilleteraVirtualObjeto inicializarDatos() {
        BilleteraVirtualObjeto billeteraVirtualObjeto = new BilleteraVirtualObjeto();
        Usuario usuario1 = Usuario.builder()
                .nombre("juan")
                .cedula("1094")
                .telefono("3175761571")
                .direccion("armenia")
                .correo("juan@mail.com")
                .clave("clavejuan")
                .build();

        Usuario usuario2 = Usuario.builder()
                .nombre("Ana")
                .cedula("1095")
                .telefono("3108742066")
                .direccion("quimbaya")
                .correo("ana@mail.com")
                .clave("claveana")
                .build();

        Usuario usuario3 = Usuario.builder()
                .nombre("Pedro")
                .cedula("1096")
                .telefono("3144738306")
                .direccion("armenia")
                .correo("pedro@mail.com")
                .clave("clavepedro")
                .build();

        billeteraVirtualObjeto.getListaUsuarios().add(usuario1);
        billeteraVirtualObjeto.getListaUsuarios().add(usuario2);
        billeteraVirtualObjeto.getListaUsuarios().add(usuario3);

        return billeteraVirtualObjeto;
    }

    // Cargar usuarios desde archivo
    public static List<Usuario> cargarUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_USUARIOS))) {
            return (List<Usuario>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Guardar usuarios en archivo
    public static void guardarUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
