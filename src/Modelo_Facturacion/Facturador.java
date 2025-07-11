package Modelo_Facturacion;

import Modelo_Pedido.Pedido;

public interface Facturador {
    void generarComprobante(Pedido pedido);
}
