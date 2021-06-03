package com.appgro.service.pago;

import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.MercadoPagoResponse;

import java.util.Date;

public interface MercadoPagoService {
    /**
     * Metodo que hace el proceso de registro del pedido en mercadopago para que la app lo reciba
     *
     * @return Datos necesarios para continuar el pa
     */
    EntidadResponse<MercadoPagoResponse> realizarPago();

    /**
     * Metodo que recibe los argumentos del backurl cuando haya finalizado la transacción de pago
     *
     * @param fechaEntrega fecha de entrega del pedido
     */
    void recibirPago(Date fechaEntrega);
}
