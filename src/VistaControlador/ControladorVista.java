package VistaControlador;

import Modelo_Cliente.Cliente;
import Modelo_Cliente.ClienteExclusivo;
import Modelo_Cliente.ClienteNormal;
import Modelo_Inventario.AlertarPorCantidadMinima;
import Modelo_Inventario.GestorInventario;
import Modelo_Pedido.GestorPedidos;
import Modelo_Pedido.Pedido;
import Modelo_Pedido.Producto;
import Vista.FormVista;
import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorVista {

    private FormVista vista;
    private List<Cliente> listaClientes;
    private GestorInventario gestorInventario;
    private List<Producto> listaProductos;
    private Pedido pedidoActual;
    private GestorPedidos gestorPedidos;

    public ControladorVista(FormVista vista) {
        this.vista = vista;
        this.listaClientes = new ArrayList<>();
        this.listaProductos = new ArrayList<>();
        this.gestorInventario = new GestorInventario(new AlertarPorCantidadMinima()); // o tu implementación concreta
        this.gestorInventario.agregarObservador(msg -> {
            vista.getLblAlertaActual().setText(msg);
            vista.getTxtHistorialAlertas().append(msg + "\n");
        });
        this.gestorPedidos = new GestorPedidos();

        vista.getBtnAgregarProducto().addActionListener(e -> agregarProducto());
        vista.getCbbCliente().addActionListener(e -> iniciarPedido());
        vista.getBtnAgregarPedido().addActionListener(e -> agregarAlPedido());

        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnCrearCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCliente();
            }
        });
    }

    private void registrarCliente() {
        String nombre = vista.getTxtNombre().getText();
        String correo = vista.getTxtCorreo().getText();
        String tipo = vista.getRbExclusivo().isSelected() ? "Exclusivo" : "Normal";

        if (nombre.isEmpty() || correo.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(vista, "Completa todos los campos.");
            return;
        }

        Cliente nuevo = null;

        if (tipo.equalsIgnoreCase("Exclusivo")) {
            nuevo = new ClienteExclusivo(nombre, correo);
        } else {
            nuevo = new ClienteNormal(nombre, correo);
        }

        listaClientes.add(nuevo);

        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaClientes().getModel();
        modelo.addRow(new Object[]{nombre, correo, tipo});

        vista.getCbbCliente().addItem(nombre);

        vista.getTxtNombre().setText("");
        vista.getTxtCorreo().setText("");
        vista.getButtonGroup1().clearSelection();
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    private void agregarProducto() {
        String nombre = vista.getTxtNombreProducto().getText();
        String ubicacion = vista.getTxtUbicacionProducto().getText();
        String precioStr = vista.getTxtPrecioProducto().getText();
        String stockStr = vista.getTxtStockProducto().getText();
        String minimoStr = vista.getTxtStockMinimoProducto().getText();

        if (nombre.isEmpty() || ubicacion.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty() || minimoStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Completa todos los campos.");
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);
            int minimo = Integer.parseInt(minimoStr);

            Producto producto = new Producto(nombre, precio, ubicacion);
            gestorInventario.registrarProducto(producto, stock, minimo);
            listaProductos.add(producto);

            DefaultTableModel modelo = (DefaultTableModel) vista.getTablaProductos().getModel();
            modelo.addRow(new Object[]{nombre, precio, stock, ubicacion});

            DefaultTableModel modeloDisp = (DefaultTableModel) vista.getTablaProductosDisponibles().getModel();
            modeloDisp.addRow(new Object[]{nombre, precio, stock});

            limpiarCamposProducto();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Precio, Stock y Stock mínimo deben ser numéricos.");
        }

    }

    private void iniciarPedido() {
        String nombreCliente = (String) vista.getCbbCliente().getSelectedItem();
        if (nombreCliente == null) {
            return;
        }

        Cliente cliente = buscarClientePorNombre(nombreCliente);
        if (cliente == null) {
            return;
        }

        pedidoActual = gestorPedidos.crearPedido(cliente);

        boolean tieneDescuento = cliente.getDescuento() > 0.0;
        vista.getLblDescuento().setText(tieneDescuento ? "Descuento aplicado: SÍ" : "Descuento aplicado: NO");
        vista.getLblTotalPedido().setText("Total: S/ 0.00");

    }

    private Cliente buscarClientePorNombre(String nombre) {
        for (Cliente c : listaClientes) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    private void agregarAlPedido() {
        int fila = vista.getTablaProductosDisponibles().getSelectedRow();
        if (fila == -1 || pedidoActual == null) {
            return;
        }

        String nombreProducto = (String) vista.getTablaProductosDisponibles().getValueAt(fila, 0);
        Producto producto = buscarProductoPorNombre(nombreProducto);
        if (producto == null) {
            return;
        }

        try {
            int cantidad = Integer.parseInt(vista.getTxtCantidadProducto().getText());

            gestorInventario.reducirStock(producto, cantidad);

            pedidoActual.agregarProducto(producto, cantidad);

            JScrollPane scroll = vista.getScrollPedidoActual();
            JTable tabla = (JTable) scroll.getViewport().getView();
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            double subtotal = producto.getPrecio() * cantidad;
            modelo.addRow(new Object[]{producto.getNombre(), cantidad, subtotal});

            double total = pedidoActual.calcularTotal();
            vista.getLblTotalPedido().setText("Total: S/ " + total);

            vista.getTxtCantidadProducto().setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Cantidad inválida.");
        }

    }

    private Producto buscarProductoPorNombre(String nombre) {
        for (Producto p : listaProductos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    private void limpiarCamposProducto() {
        vista.getTxtNombreProducto().setText("");
        vista.getTxtUbicacionProducto().setText("");
        vista.getTxtPrecioProducto().setText("");
        vista.getTxtStockProducto().setText("");
        vista.getTxtStockMinimoProducto().setText("");
    }
}
