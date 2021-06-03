package com.appgro.service;

import com.appgro.model.request.ReporteRequest;
import com.appgro.model.response.EntidadResponse;

public interface ReporteService {
	
	public EntidadResponse<String> obtenerReporte(final ReporteRequest reporte);
	
	public EntidadResponse<String> obtenerReporteAdministrador(final ReporteRequest reporte);

}
