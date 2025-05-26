package co.edu.uniquindio.billetera.billeteravirtual.mapping.dto;

public class UsuarioDto {
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String direccion;

    public UsuarioDto(String nombre, String cedula, String correo, String telefono, String direccion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
}