package co.edu.uniquindio.billetera.billeteravirtual.service;

public class UsuarioService {
    private static UsuarioService instance;

    private UsuarioService() {
        // Constructor privado
    }

    public static UsuarioService getInstance() {
        if (instance == null) {
            instance = new UsuarioService();
        }
        return instance;
    }

    public void registrarUsuario(String id, String nombre, String correo) {
        // Lógica para registrar un usuario
    }
}
