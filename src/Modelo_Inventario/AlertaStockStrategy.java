package Modelo_Inventario;

import Modelo_Pedido.Producto;

public interface AlertaStockStrategy {
    boolean debeAlertar(Producto producto, int stockActual, int stockkMinimo);
}
