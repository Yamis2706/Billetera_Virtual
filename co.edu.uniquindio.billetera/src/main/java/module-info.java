module co.edu.uniquindio.billetera.billeteravirtual {
    requires javafx.controls;
    requires javafx.fxml;

    exports co.edu.uniquindio.billetera.billeteravirtual;
    exports co.edu.uniquindio.billetera.billeteravirtual.controller;
    exports co.edu.uniquindio.billetera.billeteravirtual.viewController;
    // exports co.edu.uniquindio.billetera.billeteravirtual.viewController.usuario.cuentas; // Elimina o comenta si no existe

    opens co.edu.uniquindio.billetera.billeteravirtual to javafx.fxml;
    opens co.edu.uniquindio.billetera.billeteravirtual.controller to javafx.fxml;
    opens co.edu.uniquindio.billetera.billeteravirtual.viewController to javafx.fxml;
    // opens co.edu.uniquindio.billetera.billeteravirtual.viewController.usuario.cuentas to javafx.fxml; // Elimina o comenta si no existe
}