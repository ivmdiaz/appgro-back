package com.appgro.model.request;

import java.io.Serializable;
import java.util.List;

public class ReporteRequest implements Serializable {

	private static final long serialVersionUID = -1078384910082579015L;

	private String reporte;

	private List<ReporteFiltroRequest> filtros;

	private List<ReporteCampoRequest> campos;

	public String getReporte() {
		return reporte;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}

	public List<ReporteFiltroRequest> getFiltros() {
		return filtros;
	}

	public void setFiltros(List<ReporteFiltroRequest> filtros) {
		this.filtros = filtros;
	}

	public List<ReporteCampoRequest> getCampos() {
		return campos;
	}

	public void setCampos(List<ReporteCampoRequest> campos) {
		this.campos = campos;
	}

}
