package Modelo_Facturacion;

public class BoletaAdapter implements Facturador{
    
    private SistemaBoleta Boleta;

    public BoletaAdapter(SistemaBoleta Boleta) {
        this.Boleta = Boleta;
    }
    
    @Override
    public void generarComprobante(String datosFactura) {
        Boleta.emitirBoleta(datosFactura);
    }
    
}
