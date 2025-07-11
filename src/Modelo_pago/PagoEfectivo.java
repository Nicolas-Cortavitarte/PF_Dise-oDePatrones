package Modelo_pago;

public class PagoEfectivo implements MetodoPago{

    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago en efectivo recibido. Total: S/ " + String.format("%.2f", monto));
        System.out.println("Por favor entregar el dinero al repartidor.");
    }
    
}
