package com.appgro.service;

import com.appgro.model.request.EliminarProductoRequest;
import com.appgro.model.request.RegistroProductoRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.ProductoResponse;

import java.util.List;

public interface ProductoService {

    /**
     * Función encargada de registrar un producto nuevo.
     *
     * @param peticion petición recibida por el servicio REST
     * @return Respuesta génerica del servicio REST
     */
    EstadoResponse guardarProducto(RegistroProductoRequest peticion);

    /**
     * Función encargada de eliminar un producto.
     *
     * @param peticion petición recibida por el servicio REST
     * @return Respuesta génerica del servicio REST
     */
    EstadoResponse eliminarProducto(EliminarProductoRequest peticion);


    /**
     * Función encargada de listar todos los productos de una tienda
     *
     * @return Respuesta genérica para entidades del servicio REST
     */
    EntidadResponse<List<ProductoResponse>> obtenerProductosByTienda(Integer idTienda);

}
