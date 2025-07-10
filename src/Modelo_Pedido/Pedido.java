package Modelo_Pedido;

import Modelo_Cliente.Cliente;
import java.math.BigDecimal;
import java.util.*;

public class Pedido {
    private Cliente cliente;
    private List<LineaPedido> lineas;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.lineas = new ArrayList<>();
    }
    
    public void agregarProducto(Producto producto, int cantidad){
        LineaPedido linea = new LineaPedido(producto, cantidad);
        lineas.add(linea);
    }
    
    public double calcularTotal(){
        BigDecimal subTotal = BigDecimal.ZERO;
        
        for (LineaPedido linea : lineas) {
            subTotal = subTotal.add(BigDecimal.valueOf(linea.calcularSubtotal()));
        }
        
        BigDecimal descuento = BigDecimal.valueOf(cliente.getDescuento());
        BigDecimal total = subTotal.multiply(BigDecimal.ONE.subtract(descuento));
        
        return total.doubleValue();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }
    
}
