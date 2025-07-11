package Modelo_Pedido;

import Modelo_Cliente.Cliente;
import Modelo_Facturacion.BoletaAdapter;
import Modelo_Facturacion.FacturaAdapter;
import Modelo_Facturacion.Facturador;
import Modelo_Facturacion.SistemaBoleta;
import Modelo_Facturacion.SistemaFactura;
import Modelo_pago.MetodoPago;
import java.util.Scanner;

public class ConfirmarPedido {

    public void confirmarPedido(Pedido pedido, MetodoPago metodoPago) {

        ResumenPedido.mostrarResumen(pedido);

        double total = pedido.calcularTotal();
        metodoPago.procesarPago(total);
        Cliente cliente = pedido.getCliente();
        boolean empresa = cliente.tipoDeCliente().equalsIgnoreCase("Exclusivo");

        Facturador facturador;
        if (empresa) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Ingrese RUC del cliente: ");
            String ruc = sc.nextLine();
            facturador = new FacturaAdapter(ruc);
        } else {
            facturador = new BoletaAdapter(new SistemaBoleta());
        }

        facturador.generarComprobante(pedido);
    }
}
