package co.edu.uniquindio.billetera.billeteravirtual;

import co.edu.uniquindio.billetera.billeteravirtual.utils.DataUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BilleteraVirtualApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Inicializa datos quemados y persistencia solo una vez
        DataUtil.inicializarDatos();

        FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtualApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 350);
        stage.setTitle("Login - Billetera Virtual");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}