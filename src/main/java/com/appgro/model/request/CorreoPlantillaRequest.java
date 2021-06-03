package com.appgro.model.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamSource;

public class CorreoPlantillaRequest {

    private String asunto;

    private String plantilla;

    private Map<String, Object> modelo;

    private List<String> para;

    private List<String> copia;

    private List<String> copiaOculta;
    
    private Map<String, InputStreamSource> inLine;

    public CorreoPlantillaRequest(String asunto, String plantilla) {
        this.asunto = asunto;
        this.plantilla = plantilla;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public Map<String, Object> getModelo() {
        if(modelo == null){
            modelo = new HashMap<>();
        }
        return modelo;
    }

    public void setModelo(Map<String, Object> modelo) {
        this.modelo = modelo;
    }

    public List<String> getPara() {
        if(para == null){
            para = new ArrayList<>();
        }
        return para;
    }

    public void setPara(List<String> para) {
        this.para = para;
    }

    public List<String> getCopia() {
        if(copia == null){
            copia = new ArrayList<>();
        }
        return copia;
    }

    public void setCopia(List<String> copia) {
        this.copia = copia;
    }

    public List<String> getCopiaOculta() {
        if(copiaOculta == null){
            copiaOculta = new ArrayList<>();
        }
        return copiaOculta;
    }

    public void setCopiaOculta(List<String> copiaOculta) {
        this.copiaOculta = copiaOculta;
    }

    public void addPara(String para){
        getPara().add(para);
    }

    public void addCopia(String copia){
        getCopia().add(copia);
    }

    public void addCopiaOculta(String copiaOculta){
        getCopiaOculta().add(copiaOculta);
    }

    public void addVariable(String llave, String valor){
        getModelo().put(llave, valor);
    }

	public Map<String, InputStreamSource> getInLine() {
		if(inLine == null) {
			inLine = new HashMap<String, InputStreamSource>();
		}
		return inLine;
	}

	public void setInLine(Map<String, InputStreamSource> inLine) {
		this.inLine = inLine;
	}
    
    
}
