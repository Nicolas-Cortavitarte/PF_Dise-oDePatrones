package Modelo_pago;

public class PagoTarjeta implements MetodoPago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago con tarjeta...");
        System.out.println("Total cobrado a su tarjeta: S/ " + String.format("%.2f", monto));
        System.out.println("Se ha enviado un recibo a su correo.");
    }

}
