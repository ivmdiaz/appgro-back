package com.appgro.controller;

import com.appgro.model.request.RegistroTiendaRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.TiendaResponse;
import com.appgro.service.TiendaService;
import com.appgro.util.AppgroUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tienda")
public class TiendaController {

    private final TiendaService mTiendaService;

    public TiendaController(TiendaService mTiendaService) {
        this.mTiendaService = mTiendaService;
    }

    @PreAuthorize(AppgroUtil.SS_TIENDA_ADMINISTRAR)
    @PutMapping("/guardar")
    public EstadoResponse guardarTienda(@RequestBody RegistroTiendaRequest peticion) {
        return mTiendaService.guardarTienda(peticion);
    }
    
    @PreAuthorize(AppgroUtil.SS_TIENDA_ADMINISTRAR)
    @DeleteMapping("/eliminar/{idTienda}")
    public EstadoResponse eliminarTienda( @PathVariable Integer idTienda) {
        return mTiendaService.eliminarTienda(idTienda);
    }

    @GetMapping("/obtenerTiendasByPaginador")
    public EntidadResponse<List<TiendaResponse>> obtenerTiendas(@RequestParam Integer page,
                                                                @RequestParam Integer limit) {
        return mTiendaService.listarTiendas(page, limit);
    }

    @GetMapping("/obtenerTiendasByCategoria/{categoria}")
    public EntidadResponse<List<TiendaResponse>> obtenerTiendasByCategoria(
            @PathVariable Integer categoria) {
        return mTiendaService.obtenerTiendasByCategoria(categoria);
    }

    @GetMapping("/obtenerTiendas")
    public EntidadResponse<List<TiendaResponse>> obtenerTiendas() {
        return mTiendaService.listarTiendas();
    }

    @GetMapping("/obtenerTiendasCercanas")
    public EntidadResponse<List<TiendaResponse>> obtenerTiendasCercanas(){
        return mTiendaService.listarTiendasCercanas();
    }

}
