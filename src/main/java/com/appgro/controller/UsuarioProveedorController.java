package com.appgro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appgro.model.request.IdentificadorRequest;
import com.appgro.model.request.TarifasRequest;
import com.appgro.model.response.CompraResponse;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.model.response.TarifasResponse;
import com.appgro.model.response.TiendaResponse;
import com.appgro.service.ProveedorService;

@RestController
@RequestMapping("/api/proveedor")
public class UsuarioProveedorController {
	
	private final ProveedorService mProveedorService; 
	
	public UsuarioProveedorController(ProveedorService proveedorService) {
		this.mProveedorService = proveedorService;
	}
	
	@GetMapping("/obtenerCompras")
    public EntidadResponse<List<CompraResponse>> obtenerCompras() {
        return mProveedorService.obtenerCompras();
    }
	
	@GetMapping("/obtenerTiendas")
    public EntidadResponse<List<TiendaResponse>> obtenerTiendas() {
        return mProveedorService.obtenerTiendas();
    }
	
	@GetMapping("/obtenerTarifas")
    public EntidadResponse<List<TarifasResponse>> obtenerTarifas() {
        return mProveedorService.obtenerTarifas();
    }
	
	@PostMapping("/guardarTarifas")
    public EstadoResponse guardarTarifas(@RequestBody TarifasRequest tarifas) {
        return mProveedorService.guardarTarifas(tarifas.getTarifas());
    }

	@PutMapping("/entregarPedido")
    public EstadoResponse entregarPedido(@RequestBody IdentificadorRequest peticion) {
        return mProveedorService.entregarPedido(peticion);
    }
	
	@GetMapping("/obtenerTiendasSelect")
    public EntidadResponse<List<IdNombreResponse>> obtenerTiendasSelect() {
        return mProveedorService.obtenerTiendasSelect();
    }
	
	@GetMapping("/obtenerProductosSelect")
    public EntidadResponse<List<IdNombreResponse>> obtenerProductosSelect() {
        return mProveedorService.obtenerProductosSelect();
    }
	
	@GetMapping("/obtenerCategoriasSelect")
    public EntidadResponse<List<IdNombreResponse>> obtenerCategoriasSelect() {
        return mProveedorService.obtenerCategoriasSelect();
    }
	
}
