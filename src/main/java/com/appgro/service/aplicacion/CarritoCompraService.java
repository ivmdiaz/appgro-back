package com.appgro.service.aplicacion;

import com.appgro.model.request.RegistroCarritoRequest;
import com.appgro.model.response.CarritoResponse;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;

import java.util.List;

public interface CarritoCompraService {

    /**
     * Función encargada de preparar la compra de un producto.
     *
     * @param peticion petición recibida por parámetro.
     * @return
     */
    EstadoResponse guardarEnCarritoCompras(RegistroCarritoRequest peticion);

    /**
     * Función encargada de obtener las compras en proceso de un usuario logueado.
     *
     * @return
     */
    EntidadResponse<List<CarritoResponse>> obtenerCarritoComprasEnProceso();

    /**
     * Función encargada de eliminar la compra de un producto.
     *
     * @param idCarrito id del carrito recibido por parámetro.
     * @return
     */
    EstadoResponse eliminarDelCarritoCompras(Long idCarrito);

    /**
     * Función encargada de eliminar la compra de productos en proceso del usuario.
     *
     * @return
     */
    EstadoResponse eliminarCarritoComprasEnProceso();

    /**
     * Función encargada de obtener el historico de compras de un usuario logueado.
     *
     * @return lista de compras diferentes a PROCESO
     */
    EntidadResponse<List<CarritoResponse>> obtenerHistoricoCarrito();
}
