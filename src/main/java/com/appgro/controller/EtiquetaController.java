package com.appgro.controller;

import com.appgro.model.entity.GenEtiqueta;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.service.EtiquetaService;
import com.appgro.util.AppgroUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etiqueta")
public class EtiquetaController {

    private final EtiquetaService mEtiquetaService;

    public EtiquetaController(EtiquetaService mEtiquetaService) {
        this.mEtiquetaService = mEtiquetaService;
    }

    @PreAuthorize(AppgroUtil.SS_PRODUCTO_ADMINISTRAR)
    @PutMapping("/guardar")
    public EstadoResponse guardarEtiqueta(@RequestBody IdNombreResponse request ) {
        return mEtiquetaService.guardarEtiqueta(request);
    }

    @PreAuthorize(AppgroUtil.SS_PRODUCTO_ADMINISTRAR)
    @GetMapping("/obtenerEtiquetas")
    public EntidadResponse<List<GenEtiqueta>> obtenerEtiquetas() {
        return mEtiquetaService.listarEtiquetas();
    }
    
    @PreAuthorize(AppgroUtil.SS_TIENDA_ADMINISTRAR)
    @DeleteMapping("/eliminar/{idEtiqueta}")
    public EstadoResponse eliminarEtiqueta( @PathVariable Integer idEtiqueta) {
        return mEtiquetaService.eliminarEtiqueta(idEtiqueta);
    }

}
