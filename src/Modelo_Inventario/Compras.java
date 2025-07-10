package Modelo_Inventario;

public class Compras implements Observer{

    private String responsable;

    public Compras(String responsable) {
        this.responsable = responsable;
    }
    
    @Override
    public void update(String mensaje) {
        System.out.println("Compras -  " + responsable + " recibe alerta: " + mensaje);
    }
    
}
