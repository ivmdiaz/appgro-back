package com.appgro.controller;

import com.appgro.model.request.EliminarProductoRequest;
import com.appgro.model.request.RegistroProductoRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.ProductoResponse;
import com.appgro.service.ProductoService;
import com.appgro.util.AppgroUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService mProductoService;

    public ProductoController(ProductoService mProductoService) {
        this.mProductoService = mProductoService;
    }

    
    @PreAuthorize(AppgroUtil.SS_PRODUCTO_ADMINISTRAR)
    @PostMapping("/guardar")
    public EstadoResponse registrarProducto(@RequestBody RegistroProductoRequest peticion) {
        return mProductoService.guardarProducto(peticion);
    }

    @PreAuthorize(AppgroUtil.SS_PRODUCTO_ADMINISTRAR)
    @DeleteMapping("/eliminar")
    public EstadoResponse eliminarProducto(@RequestBody EliminarProductoRequest peticion) {
        return mProductoService.eliminarProducto(peticion);
    }

    @GetMapping("/obtenerProductosByTienda")
    public EntidadResponse<List<ProductoResponse>> obtenerProductos(@RequestParam Integer idTienda) {
        return mProductoService.obtenerProductosByTienda(idTienda);
    }
}
