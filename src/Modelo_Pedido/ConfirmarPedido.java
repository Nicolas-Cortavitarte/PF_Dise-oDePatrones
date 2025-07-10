package Modelo_Pedido;

import Modelo_Cliente.Cliente;
import Modelo_Facturacion.BoletaAdapter;
import Modelo_Facturacion.FacturaAdapter;
import Modelo_Facturacion.Facturador;
import Modelo_Facturacion.SistemaBoleta;
import Modelo_Facturacion.SistemaFactura;
import Modelo_pago.MetodoPago;

public class ConfirmarPedido {
    
    public void confirmarPedido(Pedido pedido, MetodoPago metodoPago){
        
        ResumenPedido.mostrarResumen(pedido);
        
        double total = pedido.calcularTotal();
        metodoPago.procesarPago(total);
        Cliente cliente = pedido.getCliente();
        boolean empresa = cliente.tipoDeCliente().equalsIgnoreCase("Exclusivo");
        
        Facturador facturador;
        if (empresa) {
            facturador = new FacturaAdapter(new SistemaFactura()); 
        } else {
            facturador = new BoletaAdapter(new SistemaBoleta());
        }
        
         String datos = "Cliente: " + cliente.getNombre() +
                       "\nCorreo: " + cliente.getCorreo() +
                       "\nTotal: S/ " + pedido.calcularTotal();

        facturador.generarComprobante(datos);
    }
}
