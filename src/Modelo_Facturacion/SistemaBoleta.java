package Modelo_Facturacion;

import Modelo_Pedido.LineaPedido;
import Modelo_Pedido.Pedido;

public class SistemaBoleta {
    public void emitirBoleta(Pedido pedido){
        System.out.println("========= BOLETA DE VENTA =========");
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Productos:");

        for (LineaPedido linea : pedido.getLineas()) {
            System.out.println("- " + linea.getProducto().getNombre() + " x" + linea.getCantidad()
                    + ": S/. " + String.format("%.2f", linea.calcularSubtotal()));
        }

        System.out.println("Total a pagar: S/. " + String.format("%.2f", pedido.calcularTotal()));
        System.out.println("Gracias por su compra.");
    }
}
