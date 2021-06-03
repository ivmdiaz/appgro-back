package com.appgro.controller;

import com.appgro.model.entity.GenPerfil;
import com.appgro.model.request.AutenticacionRequest;
import com.appgro.model.request.CorreoRecuperarContrasenaRequest;
import com.appgro.model.request.RegistroUsuarioRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.LoginResponse;
import com.appgro.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class LoginController {

    private final LoginService mLoginService;

    public LoginController(LoginService mLoginService) {
        this.mLoginService = mLoginService;
    }

    @PostMapping("/login")
    public EntidadResponse<LoginResponse> autenticarUsuario(@RequestBody AutenticacionRequest peticion) {
        return mLoginService.autenticarUsuario(peticion);
    }

    @PutMapping("/registro/comprador")
    public EstadoResponse registrarUsuarioComprador(@RequestBody RegistroUsuarioRequest peticion) {
        return mLoginService.registrarUsuario(peticion, GenPerfil.ID_PERFIL_COMPRADOR);
    }

    @PutMapping("/registro/vendedor")
    public EstadoResponse registrarUsuarioVendedor(@RequestBody RegistroUsuarioRequest peticion) {
        return mLoginService.registrarUsuario(peticion, GenPerfil.ID_PERFIL_VENDEDOR);
    }

    @PostMapping("/send-password-recovery")
    public EstadoResponse enviarCorreoRecuperacion(@RequestBody CorreoRecuperarContrasenaRequest peticion) {
        return mLoginService.enviarCorreoRecuperacionContrasena(peticion.getCorreo());
    }
}
