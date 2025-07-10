package Modelo_Inventario;

import Modelo_Pedido.Producto;
import java.util.*;

public class GestorInventario extends SubjectObserver{
    
    private Map<Producto, Integer> stockActual = new HashMap<>();
    private Map<Producto, Integer> stockMinimo = new HashMap<>();
    private AlertaStockStrategy estrategiaAlerta;

    public GestorInventario(AlertaStockStrategy estrategiaAlerta) {
        this.estrategiaAlerta = estrategiaAlerta;
    }
    
    public void registrarProducto(Producto producto, int cantidadInicial, int minimo){
        stockActual.put(producto, cantidadInicial);
        stockMinimo.put(producto, minimo);
    }
    
    private boolean stockDisponible(Producto producto, int cantidad){
        return stockActual.getOrDefault(producto, 0) >= cantidad;
    }
    
    public void reducirStock(Producto producto, int cantidad){
        if (!stockDisponible(producto, cantidad)) {
            System.out.println("No hay suficiente stock de: " + producto.getNombre());
            return;
        }
        
        int nuevoStock = stockActual.get(producto) - cantidad;
        stockActual.put(producto, nuevoStock);
        System.out.println("Stock actulizado de: " + producto.getNombre() + " -> " + nuevoStock);
        
        int minimo = stockMinimo.getOrDefault(producto, 0);
        if (estrategiaAlerta.debeAlertar(producto, cantidad, minimo)) {
            notificarObservadores("Alerta: stock bajo de: " + producto.getNombre());
        }
    }
    
    public int consultarStock(Producto producto){
        return stockActual.getOrDefault(producto, 0);
    }
}
