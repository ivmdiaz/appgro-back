package com.appgro.model.request;

import java.io.Serializable;

public class ReporteCampoRequest implements Serializable {

	private static final long serialVersionUID = -7041732879626898500L;
	
	private String campo;
	private String cabecera;
	
	public ReporteCampoRequest() {
		
	}
	
	public ReporteCampoRequest(String campo, String cabecera) {
		super();
		this.campo = campo;
		this.cabecera = cabecera;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getCabecera() {
		return cabecera;
	}

	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
	}

}
