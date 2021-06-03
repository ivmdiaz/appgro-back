package com.appgro.model.request;

import java.util.List;

import com.appgro.model.response.TarifasResponse;

public class TarifasRequest {

	private List<TarifasResponse> tarifas;

	public List<TarifasResponse> getTarifas() {
		return tarifas;
	}

	public void setTarifas(List<TarifasResponse> tarifas) {
		this.tarifas = tarifas;
	}

}
