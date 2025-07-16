package VistaControlador;

import Modelo_Cliente.Cliente;
import Modelo_Cliente.ClienteExclusivo;
import Modelo_Cliente.ClienteNormal;
import Modelo_Facturacion.BoletaAdapter;
import Modelo_Facturacion.FacturaAdapter;
import Modelo_Facturacion.Facturador;
import Modelo_Facturacion.SistemaBoleta;
import Modelo_Facturacion.SistemaFactura;
import Modelo_Inventario.AlertarPorCantidadMinima;
import Modelo_Inventario.GestorInventario;
import Modelo_Pedido.GestorPedidos;
import Modelo_Pedido.LineaPedido;
import Modelo_Pedido.Pedido;
import Modelo_Pedido.Producto;
import Modelo_pago.MetodoPago;
import Modelo_pago.PagoEfectivo;
import Modelo_pago.PagoTarjeta;
import Modelo_pago.PagoYape;
import Vista.FormVista;
import java.awt.Color;
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
        this.gestorInventario = new GestorInventario(new AlertarPorCantidadMinima());

        this.gestorInventario.agregarObservador(mensaje -> {
            vista.getLblAlertaActual().setText(mensaje);
            vista.getLblAlertaActual().setForeground(Color.RED);
            vista.getTxtHistorialAlertas().append(mensaje + "\n");
        });

        this.gestorInventario.agregarObservador(msg -> {
            vista.getLblAlertaActual().setText(msg);
            vista.getTxtHistorialAlertas().append(msg + "\n");
        });
        this.gestorPedidos = new GestorPedidos();

        vista.getBtnAgregarProducto().addActionListener(e -> agregarProducto());
        vista.getCbbCliente().addActionListener(e -> iniciarPedido());
        vista.getBtnAgregarPedido().addActionListener(e -> agregarAlPedido());
        vista.getBtnGenerarComprobante().addActionListener(e -> generarComprobante());
        vista.getBtnConfirmarPedido().addActionListener(e -> confirmarPedido());
        vista.getBtnRealizarPago().addActionListener(e -> procesarPago());

        vista.getRbTarjeta().addActionListener(e -> vista.getTxtCodigoTarjeta().setEnabled(true));
        vista.getRbEfectivo().addActionListener(e -> vista.getTxtCodigoTarjeta().setEnabled(false));
        vista.getRbYape().addActionListener(e -> vista.getTxtCodigoTarjeta().setEnabled(false));

        vista.getTxtRUC().setEnabled(false);

        vista.getRbFactura().addActionListener(e -> {
            vista.getTxtRUC().setEnabled(true);
            vista.getTxtRUC().setEditable(true);
        });

        vista.getRbBoleta().addActionListener(e -> {
            vista.getTxtRUC().setEnabled(false);
            vista.getTxtRUC().setText("");
        });

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

    private void confirmarPedido() {
        if (pedidoActual == null) {
            return;
        }

        StringBuilder resumen = new StringBuilder();
        resumen.append("Cliente: ").append(pedidoActual.getCliente().getNombre()).append("\n");
        for (LineaPedido lp : pedidoActual.getLineas()) {
            resumen.append(lp.getCantidad()).append(" x ").append(lp.getProducto().getNombre())
                    .append(" = S/").append(lp.calcularSubtotal()).append("\n");
        }
        resumen.append("Total: S/ ").append(pedidoActual.calcularTotal());

        vista.getTxtResumenPedido().setText(resumen.toString());
    }

    private void generarComprobante() {
        if (pedidoActual == null) {
            JOptionPane.showMessageDialog(vista, "No hay pedido activo.");
            return;
        }

        String texto;
        
        if (vista.getRbFactura().isSelected()) {
            String ruc = vista.getTxtRUC().getText().trim();
            if (ruc.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe ingresar un RUC.");
                return;
            }
            SistemaFactura sistema = new SistemaFactura();
            texto = sistema.emitirFactura(pedidoActual, ruc);

        } else if (vista.getRbBoleta().isSelected()) {
            SistemaBoleta sistema = new SistemaBoleta();
            texto = sistema.emitirBoleta(pedidoActual);

        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione Boleta o Factura.");
            return;
        }
        
        vista.getTxtComprobanteGenerado().setText(texto);
    }

    private String generarResumenTexto(Pedido pedido) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(pedido.getCliente().getNombre()).append("\n");
        for (LineaPedido lp : pedido.getLineas()) {
            sb.append(lp.getCantidad()).append(" x ").append(lp.getProducto().getNombre())
                    .append(" = S/. ").append(lp.calcularSubtotal()).append("\n");
        }
        sb.append("Total: S/. ").append(pedido.calcularTotal());
        return sb.toString();
    }

    private void limpiarCamposProducto() {
        vista.getTxtNombreProducto().setText("");
        vista.getTxtUbicacionProducto().setText("");
        vista.getTxtPrecioProducto().setText("");
        vista.getTxtStockProducto().setText("");
        vista.getTxtStockMinimoProducto().setText("");
    }

    private void actualizarIndicadores() {
        List<Pedido> pedidos = gestorPedidos.getPedidos();

        int totalPedidos = pedidos.size();
        int totalNormal = 0;
        int totalExclusivo = 0;
        Map<String, Integer> contadorProductos = new HashMap<>();

        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getDescuento() > 0.0) {
                totalExclusivo++;
            } else {
                totalNormal++;
            }

            for (LineaPedido linea : pedido.getLineas()) {
                String nombre = linea.getProducto().getNombre();
                contadorProductos.put(nombre,
                        contadorProductos.getOrDefault(nombre, 0) + linea.getCantidad());
            }
        }

        String productoMasVendido = "-";
        int maxVendidos = 0;
        for (Map.Entry<String, Integer> entry : contadorProductos.entrySet()) {
            if (entry.getValue() > maxVendidos) {
                maxVendidos = entry.getValue();
                productoMasVendido = entry.getKey();
            }
        }

        vista.getLblTotalPedidos().setText(String.valueOf(totalPedidos));
        vista.getLblTotalNormal().setText(String.valueOf(totalNormal));
        vista.getLblTotalExclusivo().setText(String.valueOf(totalExclusivo));
        vista.getLblProductoMasVendido().setText(productoMasVendido);
    }

    private void procesarPago() {
        if (pedidoActual == null) {
            JOptionPane.showMessageDialog(vista, "No hay un pedido activo.");
            return;
        }

        double total = pedidoActual.calcularTotal();
        MetodoPago metodo;
        String resultado;

        if (vista.getRbEfectivo().isSelected()) {
            metodo = new PagoEfectivo();
        } else if (vista.getRbTarjeta().isSelected()) {
            metodo = new PagoTarjeta();
        } else if (vista.getRbYape().isSelected()) {
            metodo = new PagoYape();
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un método de pago.");
            return;
        }

        resultado = metodo.procesarPago(total);
        vista.getLblResultadoPago().setText("✓ Pago exitoso");
        vista.getTxtReciboPago().setText(resultado);
    }
}
