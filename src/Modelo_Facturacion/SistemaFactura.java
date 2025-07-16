package Modelo_Facturacion;

import Modelo_Pedido.LineaPedido;
import Modelo_Pedido.Pedido;

public class SistemaFactura {

    public String emitirFactura(Pedido pedido, String ruc) {
        StringBuilder sb = new StringBuilder();
        sb.append("========= FACTURA ELECTRÓNICA =========\n");
        sb.append("RUC: ").append(ruc).append("\n");
        sb.append("Razón Social: ").append(pedido.getCliente().getNombre()).append("\n");
        sb.append("Detalle de Productos:\n");
        
        for (LineaPedido linea : pedido.getLineas()) {
            sb.append("* ").append(linea.getProducto().getNombre()).append(" x").append(linea.getCantidad())
                    .append(": S/. ").append(String.format("%.2f", linea.calcularSubtotal())).append("\n");
        }

        sb.append("Total facturado: S/. ").append(String.format("%.2f", pedido.calcularTotal())).append("\n");
        sb.append("Factura emitida correctamente.");
        return sb.toString();
    }
}
