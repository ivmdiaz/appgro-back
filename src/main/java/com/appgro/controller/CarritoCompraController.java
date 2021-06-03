package com.appgro.controller;

import com.appgro.model.request.RegistroCarritoRequest;
import com.appgro.model.response.CarritoResponse;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.service.aplicacion.CarritoCompraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoCompraController {

    private final CarritoCompraService mCarritoCompraService;


    public CarritoCompraController(CarritoCompraService mCarritoCompraService) {
        this.mCarritoCompraService = mCarritoCompraService;
    }

    @PostMapping("/guardar")
    public EstadoResponse guardarEnCarritoCompras(@RequestBody RegistroCarritoRequest peticion) {
        return mCarritoCompraService.guardarEnCarritoCompras(peticion);
    }

    @GetMapping("/obtenerCarritoComprasEnProceso")
    public EntidadResponse<List<CarritoResponse>> obtenerCarritoComprasEnProceso() {
        return mCarritoCompraService.obtenerCarritoComprasEnProceso();
    }

    @DeleteMapping("/eliminarDelCarritoCompras/{idCarrito}")
    public EstadoResponse eliminarDelCarritoCompras(@PathVariable final Long idCarrito) {
        return mCarritoCompraService.eliminarDelCarritoCompras(idCarrito);
    }

    @DeleteMapping("/eliminarCarritoComprasEnProceso")
    public EstadoResponse eliminarCarritoComprasEnProceso() {
        return mCarritoCompraService.eliminarCarritoComprasEnProceso();
    }

    @GetMapping("/obtenerHistoricoCarrito")
    public EntidadResponse<List<CarritoResponse>> obtenerHistoricoCarrito() {
        return mCarritoCompraService.obtenerHistoricoCarrito();
    }
}
