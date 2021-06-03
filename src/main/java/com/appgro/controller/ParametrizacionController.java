package com.appgro.controller;

import com.appgro.model.entity.GenUsuarioUbicacion;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.service.administracion.ParametrizacionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parametrizacion")
public class ParametrizacionController {

    private final ParametrizacionService mParametrizacionService;

    public ParametrizacionController(ParametrizacionService mParametrizacionService) {
        this.mParametrizacionService = mParametrizacionService;
    }

    @GetMapping("/domicilioPorId/{idDomicilio}")
    public EntidadResponse<GenUsuarioUbicacion> domicilioPorId(
            @PathVariable final Long idDomicilio) {
        return mParametrizacionService.domicilioPorId(idDomicilio);
    }

    @GetMapping("/obtenerPaises")
    public EntidadResponse<List<IdNombreResponse>> obtenerPaises() {
        return mParametrizacionService.obtenerPaises();
    }

    @GetMapping("/obtenerDepartamentosPorPais/{idPais}")
    public EntidadResponse<List<IdNombreResponse>> obtenerDepartamentosPorPais(
            @PathVariable final Integer idPais) {
        return mParametrizacionService.obtenerDepartamentosPorPais(idPais);
    }

    @GetMapping("/obtenerCiudadesPorDepartamento/{idDepartamento}")
    public EntidadResponse<List<IdNombreResponse>> obtenerCiudadesPorDepartamento(
            @PathVariable final Integer idDepartamento) {
        return mParametrizacionService.obtenerCiudadesPorDepartamento(idDepartamento);
    }
}
