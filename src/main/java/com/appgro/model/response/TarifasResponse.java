package com.appgro.model.response;

import com.appgro.model.entity.GenUsuarioTarifa;


public class TarifasResponse {

	private Long idTarifa;
	private Double rangoInicial;
	private Double rangoFinal;
	private Double tarifa;
	
	public TarifasResponse() {
		
	}

	public TarifasResponse(GenUsuarioTarifa bd) {
		this.setIdTarifa(bd.getIdTarifa());
		this.setRangoInicial(bd.getRangoInicial());
		this.setRangoFinal(bd.getRangoFinal());
		this.setTarifa(bd.getTarifa());
	}

	public Long getIdTarifa() {
		return idTarifa;
	}

	public void setIdTarifa(Long idTarifa) {
		this.idTarifa = idTarifa;
	}

	public Double getRangoInicial() {
		return rangoInicial;
	}

	public void setRangoInicial(Double rangoInicial) {
		this.rangoInicial = rangoInicial;
	}

	public Double getRangoFinal() {
		return rangoFinal;
	}

	public void setRangoFinal(Double rangoFinal) {
		this.rangoFinal = rangoFinal;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}

}
