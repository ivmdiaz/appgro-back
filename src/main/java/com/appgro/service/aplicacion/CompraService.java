package com.appgro.service.aplicacion;

import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;

public interface CompraService {

    /**
     * Función encargada de obtener el detalle de una compra en proceso
     *
     * @param idCompra identificador de compra a buscar
     * @return
     */
    EntidadResponse obtenerDetalleCompra(Integer idCompra);

    /**
     * Función encargada de comprar un producto.
     *
     * @return
     */
    EstadoResponse comprarProducto();

}
