package Modelo_Inventario;

import Modelo_Pedido.Producto;

public class AlertarPorCantidadMinima implements AlertaStockStrategy{

    @Override
    public boolean debeAlertar(Producto producto, int stockActual, int stockkMinimo) {
        return stockActual <= stockkMinimo;
    }
    
}
