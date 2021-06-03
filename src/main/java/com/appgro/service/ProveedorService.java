package com.appgro.service;

import java.util.List;

import com.appgro.model.request.IdentificadorRequest;
import com.appgro.model.request.ReporteRequest;
import com.appgro.model.response.CompraResponse;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.model.response.TarifasResponse;
import com.appgro.model.response.TiendaResponse;

public interface ProveedorService {
	
	 /**
     * Función encargada de obtener el historico de compras de un usuario logueado.
     *
     * @return lista de compras diferentes a PROCESO
     */
    EntidadResponse<List<CompraResponse>> obtenerCompras();

    /**
     * Función encargada de obtener todas las tiendas del proveedor logueado.
     *
     * @return lista de tiendas sin carga diferida
     */
    EntidadResponse<List<TiendaResponse>> obtenerTiendas();
    
    /**
     * Función encargada de obtener todas las tiendas del proveedor logueado.
     *
     * @return lista de tarifas sin carga diferida
     */
    EntidadResponse<List<TarifasResponse>> obtenerTarifas();
    
	/**
	 * Función encargada de guardar las tarifas
	 * @param tarifas
	 * @return
	 */
	EstadoResponse guardarTarifas(List<TarifasResponse> tarifas);
	
	/**
	 * Función encargada de actualizar el estado de un pedido a entregado.
	 * @param tarifas
	 * @return
	 */
	EstadoResponse entregarPedido(IdentificadorRequest id);
	
	/**
     * Función encargada de obtener todas las tiendas del proveedor logueado.
     *
     * @return lista de tiendas
     */
    EntidadResponse<List<IdNombreResponse>> obtenerTiendasSelect();
    
    
    /**
     * Función encargada de obtener todas los productos del proveedor logueado.
     *
     * @return lista de productos
     */
    EntidadResponse<List<IdNombreResponse>> obtenerProductosSelect();
    
    
    /**
     * Función encargada de obtener todas las categorias del proveedor logueado.
     *
     * @return lista de categorias
     */
    EntidadResponse<List<IdNombreResponse>> obtenerCategoriasSelect();
}
