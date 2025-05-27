package co.edu.uniquindio.billetera.billeteravirtual.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class TransaccionFX {
    private final StringProperty idTransaccion;
    private final StringProperty descripcion;
    private final DoubleProperty monto;
    private final ObjectProperty<LocalDate> fecha;
    private final StringProperty tipo;
    private final StringProperty categoria;

    public TransaccionFX(Transaccion t) {
        this.idTransaccion = new SimpleStringProperty(t.getIdTransaccion());
        this.descripcion = new SimpleStringProperty(t.getDescripcion());
        this.monto = new SimpleDoubleProperty(t.getMonto());
        this.fecha = new SimpleObjectProperty<>(t.getFecha());
        this.tipo = new SimpleStringProperty(t.getTipo());
        this.categoria = new SimpleStringProperty(
                t.getCategoria() != null ? t.getCategoria().getNombre() : ""
        );
    }

    public String getIdTransaccion() { return idTransaccion.get(); }
    public StringProperty idTransaccionProperty() { return idTransaccion; }

    public String getDescripcion() { return descripcion.get(); }
    public StringProperty descripcionProperty() { return descripcion; }

    public double getMonto() { return monto.get(); }
    public DoubleProperty montoProperty() { return monto; }

    public LocalDate getFecha() { return fecha.get(); }
    public ObjectProperty<LocalDate> fechaProperty() { return fecha; }

    public String getTipo() { return tipo.get(); }
    public StringProperty tipoProperty() { return tipo; }

    public String getCategoria() { return categoria.get(); }
    public StringProperty categoriaProperty() { return categoria; }
}
