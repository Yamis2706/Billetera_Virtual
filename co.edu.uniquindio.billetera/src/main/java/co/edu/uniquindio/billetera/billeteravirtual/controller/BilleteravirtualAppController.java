package co.edu.uniquindio.billetera.billeteravirtual.controller;

import co.edu.uniquindio.billetera.billeteravirtual.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class BilleteravirtualAppController {

    @FXML
    private TabPane tabPanePrincipal;

    public static BilleteravirtualAppController instancia;

    @FXML
    public void initialize() {
        instancia = this;
    }

    public static void agregarPestanaPerfilCliente(Usuario usuario) {
        if (instancia != null && usuario != null) {
            Tab tab = new Tab(usuario.getNombre());
            VBox contenido = new VBox(new Label("Perfil de " + usuario.getNombre() + "\nCorreo: " + usuario.getCorreo()));
            tab.setContent(contenido);
            instancia.tabPanePrincipal.getTabs().add(tab);
        }
    }
}