package Modelo_Inventario;

import java.util.*;

public class SubjectObserver {
    private List<Observer> observadores = new ArrayList<>();
    
    public void agregarObservador(Observer o){
        observadores.add(o);
    }
    
    public void eliminarObservador(Observer o){
        observadores.remove(o);
    }
    
    public void notificarObservadores(String mensaje){
        for (Observer o : observadores) {
            o.update(mensaje);
        }
    }
    
}
