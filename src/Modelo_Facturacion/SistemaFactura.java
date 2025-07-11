package Modelo_Facturacion;

import Modelo_Pedido.LineaPedido;
import Modelo_Pedido.Pedido;

public class SistemaFactura {
    public void emitirFactura(Pedido pedido, String ruc){
        System.out.println("========= FACTURA ELECTRÓNICA =========");
        System.out.println("RUC: " + ruc);
        System.out.println("Razón Social: " + pedido.getCliente().getNombre());
        System.out.println("Detalle de Productos:");

        for (LineaPedido linea : pedido.getLineas()) {
            System.out.println("* " + linea.getProducto().getNombre() + " x" + linea.getCantidad()
                    + ": S/. " + String.format("%.2f", linea.calcularSubtotal()));
        }

        System.out.println("Total facturado: S/. " + String.format("%.2f", pedido.calcularTotal()));
        System.out.println("Factura emitida correctamente.");
    }
}
