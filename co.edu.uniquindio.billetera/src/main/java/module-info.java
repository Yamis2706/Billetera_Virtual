module co.edu.uniquindio.billeteravirtual.billeteravirtual {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.base;

    requires javafx.graphics;


    requires java.desktop;

    opens co.edu.uniquindio.billetera.billeteravirtual to javafx.fxml;
    exports co.edu.uniquindio.billetera.billeteravirtual;
    exports co.edu.uniquindio.billetera.billeteravirtual.model;
    opens co.edu.uniquindio.billetera.billeteravirtual.viewController.usuario to javafx.fxml;

    opens co.edu.uniquindio.billetera.billeteravirtual.viewController;
    exports co.edu.uniquindio.billetera.billeteravirtual.viewController;

    opens co.edu.uniquindio.billetera.billeteravirtual.controller;
    exports co.edu.uniquindio.billetera.billeteravirtual.controller;

    opens co.edu.uniquindio.billetera.billeteravirtual.viewController.usuario.cuentas to javafx.fxml;
    opens co.edu.uniquindio.billetera.billeteravirtual.model to javafx.base, javafx.fxml;
}