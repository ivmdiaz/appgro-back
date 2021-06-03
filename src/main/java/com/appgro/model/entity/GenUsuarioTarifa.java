package com.appgro.model.entity;

import javax.persistence.*;

import com.appgro.model.response.TarifasResponse;

import java.io.Serializable;

@Entity
@Table(name = "gen_usuario_tarifas")
public class GenUsuarioTarifa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tarifa")
	private Long idTarifa;
	
	@Column(name = "id_usuario")
	private Long idUsuario;

	@Column(name = "tarifa")
	private Double tarifa;

	@Column(name = "rango_inicial")
	private Double rangoInicial;

	@Column(name = "rango_final")
	private Double rangoFinal;
	
	
	public GenUsuarioTarifa() {
		
	}
	
	public GenUsuarioTarifa(TarifasResponse tarifa, Long idUsuario) {
		this.setIdTarifa(tarifa.getIdTarifa());
		this.setIdUsuario(idUsuario);
		this.setTarifa(tarifa.getTarifa());
		this.setRangoInicial(tarifa.getRangoInicial());
		this.setRangoFinal(tarifa.getRangoFinal());
	}

	public Long getIdTarifa() {
		return idTarifa;
	}

	public void setIdTarifa(Long idTarifa) {
		this.idTarifa = idTarifa;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
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

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idTarifa != null ? idTarifa.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof GenUsuarioTarifa)) {
			return false;
		}
		GenUsuarioTarifa other = (GenUsuarioTarifa) object;
		if ((this.idTarifa == null && other.idTarifa != null)
				|| (this.idTarifa != null && !this.idTarifa.equals(other.idTarifa))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GenUsuarioTarifa[ idTarifa=" + idTarifa + " ]";
	}
}
