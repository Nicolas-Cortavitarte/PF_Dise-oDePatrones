package Modelo_Pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LineaPedido {

    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        BigDecimal precio = BigDecimal.valueOf(producto.getPrecio());
        BigDecimal cantidadBD = BigDecimal.valueOf(cantidad);
        BigDecimal subtotal = precio.multiply(cantidadBD).setScale(2, RoundingMode.HALF_UP);
        return subtotal.doubleValue();
    }

    @Override
    public String toString() {
        return cantidad + " x " + producto.getPrecio() + " = S/." + calcularSubtotal();
    }
    
}
