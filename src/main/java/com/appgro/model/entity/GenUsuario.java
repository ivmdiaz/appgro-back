package com.appgro.model.entity;

import com.appgro.model.request.ActualizarUsuarioRequest;
import com.appgro.model.request.RegistroUsuarioRequest;
import com.appgro.util.EncriptadorAES;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.codec.digest.DigestUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "gen_usuario")
public class GenUsuario implements Serializable {
	
	public static final String ESTADO_HABILITADO = "HABILITADO";
	public static final String ESTADO_INHABILITADO = "INHABILITADO";
	
	@Transient
	public final EncriptadorAES AES = new EncriptadorAES();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;

    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;

    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "CELULAR")
    private String celular;

    @Column(name = "CONTRASENA")
    private String contrasena;
    
    @Column(name = "ESTADO")
    private String estado;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gen_usuario_perfil",
            joinColumns = {@JoinColumn(name = "ID_USUARIO")},
            inverseJoinColumns = {@JoinColumn(name = "ID_PERFIL")})
    private List<GenPerfil> perfiles;

    public GenUsuario() {
    }

    public GenUsuario(RegistroUsuarioRequest peticion) {
        this.setNombreCompleto(peticion.getNombreCompleto());
        this.setPrimerApellido(peticion.getPrimerApellido());
        this.setSegundoApellido(peticion.getSegundoApellido());
        this.setCorreo(peticion.getCorreo().trim());
        this.setCelular(peticion.getCelular());
        this.setEstado(ESTADO_HABILITADO);
        this.setContrasena(DigestUtils.sha512Hex(peticion.getContrasena().trim()));
    }

    public GenUsuario(ActualizarUsuarioRequest peticionActualizar) {
        this.setIdUsuario(peticionActualizar.getIdUsuario());
        this.setNombreCompleto(peticionActualizar.getNombreCompleto());
        this.setPrimerApellido(peticionActualizar.getPrimerApellido());
        this.setSegundoApellido(peticionActualizar.getSegundoApellido());
        this.setCorreo(peticionActualizar.getCorreo().trim());
        this.setCelular(peticionActualizar.getCelular());
        this.setEstado(ESTADO_HABILITADO);
        this.setContrasena(StringUtils.isEmpty(peticionActualizar.getContrasena().trim())
                ? "generico"
                : DigestUtils.sha512Hex(peticionActualizar.getContrasena()));
    }

    public void actualizar(RegistroUsuarioRequest peticion) {
        this.setNombreCompleto(peticion.getNombreCompleto());
        this.setPrimerApellido(peticion.getPrimerApellido());
        this.setSegundoApellido(peticion.getSegundoApellido());
        this.setCelular(peticion.getCelular());
        this.setEstado(ESTADO_HABILITADO);
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return AES.desencriptar(nombreCompleto);
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = AES.encriptar(nombreCompleto);
    }

    public String getPrimerApellido() {
    	return AES.desencriptar(primerApellido);
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = AES.encriptar(primerApellido);
    }

    public String getSegundoApellido() {
    	return AES.desencriptar(segundoApellido);
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = AES.encriptar(segundoApellido);
    }

    public String getCorreo() {
    	return AES.desencriptar(correo);
    }

    public void setCorreo(String correo) {
        this.correo = AES.encriptar(correo);
    }

    public String getCelular() {
    	return AES.desencriptar(celular);
    }

    public void setCelular(String celular) {
        this.celular = AES.encriptar(celular);
    }

    @JsonIgnore
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<GenPerfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<GenPerfil> perfiles) {
        this.perfiles = perfiles;
    }

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
}
