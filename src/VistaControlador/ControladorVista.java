package VistaControlador;

import Modelo_Cliente.Cliente;
import Modelo_Cliente.ClienteExclusivo;
import Modelo_Cliente.ClienteFactory;
import Modelo_Cliente.ClienteNormal;
import Vista.FormVista;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class ControladorVista {

    private FormVista vista;
    private List<Cliente> listaClientes;

    public ControladorVista(FormVista vista) {
        this.vista = vista;
        this.listaClientes = new ArrayList<>();

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

}
