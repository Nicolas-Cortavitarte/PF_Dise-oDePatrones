package Modelo_Facturacion;

import Modelo_Pedido.LineaPedido;
import Modelo_Pedido.Pedido;

public class SistemaBoleta {

    public String emitirBoleta(Pedido pedido) {
        StringBuilder sb = new StringBuilder();
        sb.append("========= BOLETA DE VENTA =========\n");
        sb.append("Cliente: ").append(pedido.getCliente().getNombre()).append("\n");
        sb.append("Productos:\n");
        for (LineaPedido linea : pedido.getLineas()) {
            sb.append("- ").append(linea.getProducto().getNombre()).append(" x").append(linea.getCantidad())
                    .append(": S/. ").append(String.format("%.2f", linea.calcularSubtotal())).append("\n");
        }
        sb.append("Total a pagar: S/. ").append(String.format("%.2f", pedido.calcularTotal())).append("\n");
        sb.append("Gracias por su compra.");
        return sb.toString();
    }
}
