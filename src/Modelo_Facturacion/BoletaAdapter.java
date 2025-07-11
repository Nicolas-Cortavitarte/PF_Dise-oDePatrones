package Modelo_Facturacion;

import Modelo_Pedido.Pedido;

public class BoletaAdapter implements Facturador{
    
    private SistemaBoleta Boleta;

    public BoletaAdapter(SistemaBoleta Boleta) {
        this.Boleta = Boleta;
    }
    
    @Override
    public void generarComprobante(Pedido pedido) {
        Boleta.emitirBoleta(pedido);
    }
    
}
