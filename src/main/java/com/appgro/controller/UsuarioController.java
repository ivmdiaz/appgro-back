package com.appgro.controller;

import com.appgro.model.entity.GenUsuarioUbicacion;
import com.appgro.model.request.ActualizarUsuarioRequest;
import com.appgro.model.request.IdentificadorRequest;
import com.appgro.model.request.RegistroDomicilioRequest;
import com.appgro.model.request.RegistroUsuarioRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService mUsuarioService;

    public UsuarioController(UsuarioService mUsuarioService) {
        this.mUsuarioService = mUsuarioService;
    }

    @PutMapping("/guardar")
    public EstadoResponse guardarUsuario(@RequestBody RegistroUsuarioRequest peticion) {
        return mUsuarioService.guardarUsuario(peticion);
    }

    @GetMapping("/obtenerDomicilios")
    public EntidadResponse<List<GenUsuarioUbicacion>> obtenerDomicilios() {
        return mUsuarioService.obtenerDomicilios();
    }

    @PutMapping("/guardarDomicilio")
    public EstadoResponse guardarDomicilio(@RequestBody RegistroDomicilioRequest peticion) {
        return mUsuarioService.guardarDomicilio(peticion);
    }

    @DeleteMapping("/eliminarDomicilio/{idDomicilio}")
    public EstadoResponse guardarDomicilio(@PathVariable Long idDomicilio) {
        return mUsuarioService.eliminarDomicilio(idDomicilio);
    }

    @PutMapping("/seleccionarDomicilio")
    public EstadoResponse seleccionarDomicilio(@RequestBody IdentificadorRequest peticion) {
        return mUsuarioService.seleccionarDomicilio(peticion);
    }

    @PutMapping("/actualizarDatos")
    public EstadoResponse actualizarDatosUsuario(@RequestBody ActualizarUsuarioRequest peticion) {
        return mUsuarioService.actualizarUsuario(peticion);
    }
}
