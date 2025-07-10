package Main;

import Modelo_Cliente.Cliente;
import Modelo_Cliente.ExclusivoFactory;
import Modelo_Cliente.NormalFactory;
import Modelo_Inventario.AlertaStockStrategy;
import Modelo_Inventario.AlertarPorCantidadMinima;
import Modelo_Inventario.Compras;
import Modelo_Inventario.GestorInventario;
import Modelo_Pedido.ConfirmarPedido;
import Modelo_Pedido.GestorPedidos;
import Modelo_Pedido.Pedido;
import Modelo_Pedido.Producto;
import Modelo_Pedido.ResumenPedido;
import Modelo_pago.MetodoPago;
import Modelo_pago.PagoEfectivo;

public class Main {

    public static void main(String[] args) {
        Cliente cliNormal = new NormalFactory().crearCliente("Julio", "JulioMM@gmail.com");
        Cliente cliExclusivo = new ExclusivoFactory().crearCliente("Rosario", "RosaLopez@gamil.com");
        
        /*System.out.println("Cliente 1: ");
        System.out.println("Nombre: " + cliNormal.getNombre() + "\nCorreo: " + cliNormal.getCorreo() + "\nTipo: " + cliNormal.tipoDeCliente());*/
        /*System.out.println("Cliente 2: ");
        System.out.println("Nombre: " + cliExclusivo.getNombre() + "\nCorreo: " + cliExclusivo.getCorreo() + "\nTipo: " + cliExclusivo.tipoDeCliente());*/
        
        Producto p1 = new Producto("Laptop Gamer", 5600, "A1");
        Producto p2 = new Producto("Auriculares", 100, "C3");
        
        GestorPedidos gestor = new GestorPedidos();
        Pedido pedido = gestor.crearPedido(cliExclusivo);
        
        pedido.agregarProducto(p1, 1);
        pedido.agregarProducto(p2, 3);

        ResumenPedido.mostrarResumen(pedido);

        /*ConfirmarPedido confirmado = new ConfirmarPedido();
        MetodoPago metodo = new PagoEfectivo();
        confirmado.confirmarPedido(pedido, metodo);
        
        AlertaStockStrategy estrategia = new AlertarPorCantidadMinima();
        GestorInventario inventario = new GestorInventario(estrategia);
        
        Compras compras = new Compras("Ricardo Perez");
        inventario.agregarObservador(compras);
        
        inventario.registrarProducto(p2, 10, 1);
        inventario.reducirStock(p2, 5);
        inventario.reducirStock(p2, 4);
        inventario.reducirStock(p2, 1);*/
    }
    
}
