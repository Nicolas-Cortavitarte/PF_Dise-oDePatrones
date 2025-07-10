package Modelo_Cliente;

public class ExclusivoFactory extends ClienteFactory{

    @Override
    public Cliente crearCliente(String nombre, String correo) {
        return new ClienteExclusivo(nombre, correo);
    }
    
}
