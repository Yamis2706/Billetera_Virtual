package co.edu.uniquindio.billetera.billeteravirtual.utils;

import co.edu.uniquindio.billetera.billeteravirtual.model.*;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataUtil {

    public static final String ADMIN_CLAVE = "1234";
    public static final String ADMIN_CORREO = "@admin";
    private static final String ARCHIVO_USUARIOS = "usuarios.dat";
    private static final String ARCHIVO_TRANSACCIONES = "transacciones.dat";
    private static final String ARCHIVO_CUENTAS = "cuentas.dat";
    private static final String ARCHIVO_PRESUPUESTOS = "presupuestos.dat";
    private static final String ARCHIVO_CATEGORIAS = "categorias.dat";

    private static Usuario usuarioActual;

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario obtenerUsuarioActual() {
        return usuarioActual;
    }

    private static <T> void guardarLista(List<T> lista, String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(new ArrayList<>(lista));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> List<T> cargarLista(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static List<Usuario> cargarUsuarios() {
        return cargarLista(ARCHIVO_USUARIOS);
    }

    public static void guardarUsuarios(List<Usuario> usuarios) {
        guardarLista(usuarios, ARCHIVO_USUARIOS);
    }

    public static List<Transaccion> cargarTransacciones() {
        return cargarLista(ARCHIVO_TRANSACCIONES);
    }

    public static void guardarTransacciones(List<Transaccion> transacciones) {
        guardarLista(transacciones, ARCHIVO_TRANSACCIONES);
    }

    public static List<Cuenta> cargarCuentas() {
        return cargarLista(ARCHIVO_CUENTAS);
    }

    public static void guardarCuentas(List<Cuenta> cuentas) {
        guardarLista(cuentas, ARCHIVO_CUENTAS);
    }

    public static List<Presupuesto> cargarPresupuestos() {
        return cargarLista(ARCHIVO_PRESUPUESTOS);
    }

    public static void guardarPresupuestos(List<Presupuesto> presupuestos) {
        guardarLista(presupuestos, ARCHIVO_PRESUPUESTOS);
    }

    public static void guardarCategorias(List<Categoria> categorias) {
        guardarLista(categorias, ARCHIVO_CATEGORIAS);
    }

    public static List<Categoria> cargarCategorias() {
        List<Categoria> categorias = cargarLista(ARCHIVO_CATEGORIAS);
        if (categorias == null || categorias.isEmpty()) {
            categorias = new ArrayList<>(Arrays.asList(
                    new Categoria("1", "Alimentación", "Gastos de comida"),
                    new Categoria("2", "Transporte", "Gastos de transporte"),
                    new Categoria("3", "Salud", "Gastos médicos"),
                    new Categoria("4", "Entretenimiento", "Ocio y diversión"),
                    new Categoria("5", "Educación", "Gastos educativos")
            ));
            guardarCategorias(categorias);
        } else if (!(categorias instanceof ArrayList)) {
            categorias = new ArrayList<>(categorias);
        }
        return categorias;
    }

    public static void agregarCategoria(Categoria categoria) {
        List<Categoria> categorias = cargarCategorias();
        categorias.add(categoria);
        guardarCategorias(categorias);
    }

    public static void actualizarCategoria(Categoria categoriaActualizada) {
        List<Categoria> categorias = cargarCategorias();
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId().equals(categoriaActualizada.getId())) {
                categorias.set(i, categoriaActualizada);
                break;
            }
        }
        guardarCategorias(categorias);
    }

    public static void eliminarCategoria(String idCategoria) {
        List<Categoria> categorias = cargarCategorias();
        categorias.removeIf(c -> c.getId().equals(idCategoria));
        guardarCategorias(categorias);

        // No se puede limpiar la categoría en transacciones porque Transaccion no tiene categoría
    }

    public static List<Categoria> listarCategorias() {
        return cargarCategorias();
    }

    public static void inicializarDatos() {
        File f = new File(ARCHIVO_USUARIOS);
        if (f.exists()) return;

        List<Categoria> categorias = cargarCategorias();

        List<Usuario> usuarios = new ArrayList<>();
        List<Cuenta> todasCuentas = new ArrayList<>();
        List<Transaccion> todasTransacciones = new ArrayList<>();
        List<Presupuesto> todosPresupuestos = new ArrayList<>();

        String[] nombres = {"Ana Torres", "Carlos Ruiz", "María Gómez", "Juan Pérez", "Luisa Martínez"};
        String[] cedulas = {"1001", "1002", "1003", "1004", "1005"};
        String[] correos = {"ana@mail.com", "carlos@mail.com", "maria@mail.com", "juan@mail.com", "luisa@mail.com"};
        String[] telefonos = {"3101111111", "3102222222", "3103333333", "3104444444", "3105555555"};
        String[] direcciones = {"Calle 1 #10-20", "Carrera 2 #20-30", "Avenida 3 #30-40", "Calle 4 #40-50", "Carrera 5 #50-60"};
        String[] claves = {"ana123", "carlos123", "maria123", "juan123", "luisa123"};

        for (int i = 0; i < 5; i++) {
            List<Cuenta> cuentas = new ArrayList<>();
            List<Transaccion> transacciones = new ArrayList<>();
            List<Presupuesto> presupuestos = new ArrayList<>();

            for (int j = 1; j <= 3; j++) {
                Cuenta cuenta = new Cuenta(
                        "C" + cedulas[i] + j,
                        "Cuenta " + j + " de " + nombres[i],
                        String.valueOf(2000 * j + 500 * i),
                        cedulas[i]
                );
                cuentas.add(cuenta);
            }

            for (int k = 0; k < 5; k++) {
                Cuenta cuenta = cuentas.get(k % cuentas.size());
                Transaccion t = new Transaccion(
                        UUID.randomUUID().toString(),
                        LocalDate.now().minusDays(k + 1),
                        (k % 2 == 0) ? "Ingreso" : "Gasto",
                        (k % 2 == 0) ? 500 * (k + 1) : 300 * (k + 1),
                        ((k % 2 == 0) ? "Ingreso" : "Gasto") + " " + (k + 1) + " de " + nombres[i],
                        cuenta,
                        null,
                        null,
                        null
                );
                transacciones.add(t);
            }

            for (int p = 1; p <= 5; p++) {
                Presupuesto presupuesto = new Presupuesto(
                        UUID.randomUUID().toString(),
                        "Presupuesto " + p + " de " + nombres[i],
                        1000 * p + 200 * i,
                        0,
                        categorias.get((i + p) % categorias.size())
                );
                presupuestos.add(presupuesto);
            }

            Usuario usuario = Usuario.builder()
                    .nombre(nombres[i])
                    .cedula(cedulas[i])
                    .telefono(telefonos[i])
                    .direccion(direcciones[i])
                    .correo(correos[i])
                    .clave(claves[i])
                    .build();

            usuario.setCuentas(cuentas);
            usuario.setTransacciones(transacciones);
            usuario.setPresupuestos(presupuestos);

            usuarios.add(usuario);
            todasCuentas.addAll(cuentas);
            todasTransacciones.addAll(transacciones);
            todosPresupuestos.addAll(presupuestos);
        }

        guardarUsuarios(usuarios);
        guardarCuentas(todasCuentas);
        guardarTransacciones(todasTransacciones);
        guardarPresupuestos(todosPresupuestos);
    }

    public static Cuenta buscarCuentaPorNumero(String numero) {
        List<Cuenta> cuentas = cargarCuentas();
        for (Cuenta c : cuentas) {
            if (c.getNumero().equals(numero)) {
                return c;
            }
        }
        return null;
    }

    public static Transaccion buscarTransaccionPorId(String idTransaccion) {
        List<Transaccion> transacciones = cargarTransacciones();
        for (Transaccion t : transacciones) {
            if (t.getId().equalsIgnoreCase(idTransaccion)) {
                return t;
            }
        }
        return null;
    }

    public static ObservableList<Transaccion> cargarTransaccionesObservable() {
        List<Transaccion> lista = cargarTransacciones();
        return javafx.collections.FXCollections.observableArrayList(lista);
    }

    // --- Métodos para sincronizar cuentas y transacciones ---

    public static void agregarTransaccion(Transaccion transaccion) {
        List<Transaccion> transacciones = cargarTransacciones();
        transacciones.add(transaccion);
        guardarTransacciones(transacciones);

        // Actualiza movimientos en la cuenta origen
        if (transaccion.getCuentaOrigen() != null) {
            Cuenta cuenta = buscarCuentaPorNumero(transaccion.getCuentaOrigen().getNumero());
            if (cuenta != null) {
                if (cuenta.getMovimientos() == null) {
                    cuenta.setMovimientos(new ArrayList<>());
                }
                cuenta.getMovimientos().add(transaccion.getDescripcion());
                // Actualiza la lista de cuentas
                List<Cuenta> cuentas = cargarCuentas();
                for (int i = 0; i < cuentas.size(); i++) {
                    if (cuentas.get(i).getNumero().equals(cuenta.getNumero())) {
                        cuentas.set(i, cuenta);
                        break;
                    }
                }
                guardarCuentas(cuentas);
            }
        }
        // Actualiza movimientos en la cuenta destino si existe
        if (transaccion.getCuentaDestino() != null) {
            Cuenta cuenta = buscarCuentaPorNumero(transaccion.getCuentaDestino().getNumero());
            if (cuenta != null) {
                if (cuenta.getMovimientos() == null) {
                    cuenta.setMovimientos(new ArrayList<>());
                }
                cuenta.getMovimientos().add(transaccion.getDescripcion());
                List<Cuenta> cuentas = cargarCuentas();
                for (int i = 0; i < cuentas.size(); i++) {
                    if (cuentas.get(i).getNumero().equals(cuenta.getNumero())) {
                        cuentas.set(i, cuenta);
                        break;
                    }
                }
                guardarCuentas(cuentas);
            }
        }
    }

    public static void eliminarTransaccion(String idTransaccion) {
        List<Transaccion> transacciones = cargarTransacciones();
        Transaccion t = transacciones.stream()
                .filter(tr -> tr.getId().equals(idTransaccion))
                .findFirst().orElse(null);
        if (t != null) {
            transacciones.remove(t);
            guardarTransacciones(transacciones);

            // Elimina el movimiento de la cuenta origen
            if (t.getCuentaOrigen() != null) {
                Cuenta cuenta = buscarCuentaPorNumero(t.getCuentaOrigen().getNumero());
                if (cuenta != null && cuenta.getMovimientos() != null) {
                    cuenta.getMovimientos().remove(t.getDescripcion());
                    List<Cuenta> cuentas = cargarCuentas();
                    for (int i = 0; i < cuentas.size(); i++) {
                        if (cuentas.get(i).getNumero().equals(cuenta.getNumero())) {
                            cuentas.set(i, cuenta);
                            break;
                        }
                    }
                    guardarCuentas(cuentas);
                }
            }
            // Elimina el movimiento de la cuenta destino si existe
            if (t.getCuentaDestino() != null) {
                Cuenta cuenta = buscarCuentaPorNumero(t.getCuentaDestino().getNumero());
                if (cuenta != null && cuenta.getMovimientos() != null) {
                    cuenta.getMovimientos().remove(t.getDescripcion());
                    List<Cuenta> cuentas = cargarCuentas();
                    for (int i = 0; i < cuentas.size(); i++) {
                        if (cuentas.get(i).getNumero().equals(cuenta.getNumero())) {
                            cuentas.set(i, cuenta);
                            break;
                        }
                    }
                    guardarCuentas(cuentas);
                }
            }
        }
    }
}