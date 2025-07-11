package Modelo_Facturacion;

import Modelo_Pedido.Pedido;

public class FacturaAdapter implements Facturador{
    
    private SistemaFactura Factura;
    private String ruc;

    public FacturaAdapter(String ruc) {
        this.Factura = new SistemaFactura();
        this.ruc = ruc;
    }
    
    @Override
    public void generarComprobante(Pedido pedido) {
        Factura.emitirFactura(pedido, ruc);
    }
    
}
