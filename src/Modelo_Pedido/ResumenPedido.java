package Modelo_Pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ResumenPedido {
    public static void mostrarResumen(Pedido pedido){
        System.out.println("======================================");
        System.out.println("Resumen del Pedido");
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Tipo: " + pedido.getCliente().tipoDeCliente());
        System.out.println("--------------------------------------");

        BigDecimal subtotal = BigDecimal.ZERO;

        for (LineaPedido linea : pedido.getLineas()) {
            double sub = linea.calcularSubtotal();
            subtotal = subtotal.add(BigDecimal.valueOf(sub));
            System.out.println("- " + linea.getProducto().getNombre() + " x" + linea.getCantidad() + ": S/. " +
                    String.format("%.2f", sub));
        }

        BigDecimal descuento = BigDecimal.valueOf(pedido.getCliente().getDescuento());
        BigDecimal montoDescuento = subtotal.multiply(descuento).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = BigDecimal.valueOf(pedido.calcularTotal()).setScale(2, RoundingMode.HALF_UP);

        System.out.println("--------------------------------------");
        System.out.println("Subtotal: S/. " + String.format("%.2f", subtotal));
        if (descuento.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("Descuento (" + (int)(descuento.doubleValue() * 100) + "%): -S/. " +
                    String.format("%.2f", montoDescuento));
        }
        System.out.println("Total a pagar: S/. " + String.format("%.2f", total));
        System.out.println("======================================");
    }
    
}
