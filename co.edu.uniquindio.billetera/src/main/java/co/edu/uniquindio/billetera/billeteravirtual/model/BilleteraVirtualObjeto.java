package co.edu.uniquindio.billetera.billeteravirtual.model;

import java.util.ArrayList;
import java.util.List;

public class BilleteraVirtualObjeto {
    private List<Usuario> listaUsuarios = new ArrayList<>();

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
}
