module co.edu.uniquindio.billetera.billeteravirtual {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.billetera.billeteravirtual to javafx.fxml;
    exports co.edu.uniquindio.billetera.billeteravirtual;
}