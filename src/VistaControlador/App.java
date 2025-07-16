package VistaControlador;

import Vista.FormVista;

public class App {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            FormVista vista = new FormVista();
            ControladorVista controlador = new ControladorVista(vista);
            vista.setVisible(true);
        }); 
}

}
