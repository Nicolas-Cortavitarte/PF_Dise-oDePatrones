package Modelo_pago;

public class PagoYape implements MetodoPago{

    @Override
    public void procesarPago(double monto) {
        System.out.println("Escanea el QR para completar el pago de S/ " + String.format("%.2f", monto));
        System.out.println("Esperando confirmación de Yape...");
        System.out.println("Pago confirmado. ¡Gracias por tu compra!");
    }
    
}
