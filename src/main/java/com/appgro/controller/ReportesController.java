package com.appgro.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appgro.model.request.ReporteRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {
	
	private final ReporteService mReporteService;
	
	public ReportesController(final ReporteService mReporteService) {
		this.mReporteService = mReporteService;
	}

	@PostMapping("/obtenerReporte")
	public EntidadResponse<String> obtenerReporte(@RequestBody ReporteRequest peticion) {
        return mReporteService.obtenerReporte(peticion);
    }
	
	@PostMapping("/obtenerReporteAdministrador")
	public EntidadResponse<String> obtenerReporteAdministrador(@RequestBody ReporteRequest peticion) {
        return mReporteService.obtenerReporteAdministrador(peticion);
    }
}
