package com.appgro.model.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReporteFiltroRequest implements Serializable {

	private static final long serialVersionUID = -2818821087814103853L;

	private String campo;

	private String valorIgualA;
	private List<Integer> valorEntreIds;
	private Double numeroMayorA;
	private Double numeroMenorA;
	private Date fechaMayorA;
	private Date fechaMenorA;

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValorIgualA() {
		return valorIgualA;
	}

	public void setValorIgualA(String valorIgualA) {
		this.valorIgualA = valorIgualA;
	}

	public List<Integer> getValorEntreIds() {
		return valorEntreIds;
	}

	public void setValorEntreIds(List<Integer> valorEntreIds) {
		this.valorEntreIds = valorEntreIds;
	}

	public Double getNumeroMayorA() {
		return numeroMayorA;
	}

	public void setNumeroMayorA(Double numeroMayorA) {
		this.numeroMayorA = numeroMayorA;
	}

	public Double getNumeroMenorA() {
		return numeroMenorA;
	}

	public void setNumeroMenorA(Double numeroMenorA) {
		this.numeroMenorA = numeroMenorA;
	}

	public Date getFechaMayorA() {
		return fechaMayorA;
	}

	public void setFechaMayorA(Date fechaMayorA) {
		this.fechaMayorA = fechaMayorA;
	}

	public Date getFechaMenorA() {
		return fechaMenorA;
	}

	public void setFechaMenorA(Date fechaMenorA) {
		this.fechaMenorA = fechaMenorA;
	}

}
