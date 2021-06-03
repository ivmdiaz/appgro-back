package com.appgro.controller;

import com.appgro.model.request.CategoriaRequest;
import com.appgro.model.response.CategoriaResponse;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.service.CategoriaService;
import com.appgro.util.AppgroUtil;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

	private final CategoriaService mCategoriaService;

	public CategoriaController(CategoriaService mCategoriaService) {
		this.mCategoriaService = mCategoriaService;
	}

	@GetMapping("/obtenerCategorias")
	public EntidadResponse<List<CategoriaResponse>> obtenerCategorias() {
		return mCategoriaService.listarCategorias();
	}

	@PreAuthorize(AppgroUtil.SS_PRODUCTO_ADMINISTRAR)
	@PutMapping("/guardar")
	public EstadoResponse guardarCategoria(@RequestBody CategoriaRequest request) {
		return mCategoriaService.guardarCategoria(request);
	}

	@PreAuthorize(AppgroUtil.SS_TIENDA_ADMINISTRAR)
	@DeleteMapping("/eliminar/{idCategoria}")
	public EstadoResponse eliminarCategoria(@PathVariable Integer idCategoria) {
		return mCategoriaService.eliminarCategoria(idCategoria);
	}

}
