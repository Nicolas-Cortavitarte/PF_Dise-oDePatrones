package Modelo_pago;

public class PagoTarjeta implements MetodoPago {

    @Override
    public String procesarPago(double monto) {
        return "✅ Pago con tarjeta por S/ " + String.format("%.2f", monto)
                + "\nSe ha enviado un recibo a su correo.";
    }
}
