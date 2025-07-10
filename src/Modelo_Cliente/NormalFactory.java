package Modelo_Cliente;

public class NormalFactory extends ClienteFactory{

    @Override
    public Cliente crearCliente(String nombre, String correo) {
        return new ClienteNormal(nombre, correo);
    }
    
}
