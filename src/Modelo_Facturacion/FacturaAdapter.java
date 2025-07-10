package Modelo_Facturacion;

public class FacturaAdapter implements Facturador{
    
    private SistemaFactura Factura; 

    public FacturaAdapter(SistemaFactura Factura) {
        this.Factura = Factura;
    }
    
    @Override
    public void generarComprobante(String datosFactura) {
        Factura.emitirFactura(datosFactura);
    }
    
}
