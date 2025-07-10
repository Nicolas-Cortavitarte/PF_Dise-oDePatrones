package Modelo_Pedido;

import Modelo_Cliente.Cliente;
import java.util.*;

public class GestorPedidos {
    List<Pedido> pedidos = new ArrayList<>();
    
    public Pedido crearPedido(Cliente cliente){
        Pedido pedido = new Pedido(cliente);
        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

}
