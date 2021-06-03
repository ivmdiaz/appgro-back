package com.appgro.util;

import com.appgro.model.response.EstadoResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

public class AppgroUtil {


    private AppgroUtil() {
        throw new UnsupportedOperationException();
    }
    
    public static final EncriptadorAES AES = new EncriptadorAES();

    /**
     * Permisos para cada pantalla
     */
    public static final String SS_TIENDA_ADMINISTRAR = "hasAnyRole('ROLE_1')";
    public static final String SS_PRODUCTO_ADMINISTRAR = "hasAnyRole('ROLE_2')";

    /**
     * Estados MercadoPago
     */
    public static final String ESTADO_FALLIDO_MP = "FALLIDO";
    public static final String ESTADO_PENDIENTE_MP = "PENDIENTE";
    public static final String ESTADO_EXITOSO_MP = "EXITOSO";

    /**
     * Servicios LOGIN
     */
    public static final EstadoResponse R0001 = respuestaErr("Usuario y/o contraseña incorrecta.");
    public static final EstadoResponse R00A1 = respuestaErr("El usuario está inhabilitado, por favor contacte con administración para que se reactive.");
    public static final EstadoResponse R0002 = respuestaOk("Usuario logueado exitosamente.");

    
    public static final EstadoResponse R0003 = respuestaOk("Usuario registrado exitosamente.");
    public static final EstadoResponse R00A3 = respuestaErr("El usuario ya existe, por favor contacte el administrador para reactivarlo.");

    public static final EstadoResponse R0004 = respuestaErr("No existe una cuenta para la dirección de correo electrónico.");
    public static final EstadoResponse R0005 = respuestaOk("Se ha enviado correo electrónico para restaurar su contraseña.");

    public static final EstadoResponse R0006 = respuestaErr("El token de cambio de contraseña no existe.");
    public static final EstadoResponse R0007 = respuestaErr("El token de cambio de contraseña está vencido.");
    public static final EstadoResponse R0008 = respuestaErr("El token de cambio de contraseña ya fue utilizado.");
    public static final EstadoResponse R0009 = respuestaOk("El token de cambio de contraseña es válido.");

    public static final EstadoResponse R0010 = respuestaErr("La contraseña y confirmación no coinciden.");
    public static final EstadoResponse R0011 = respuestaOk("La contraseña se ha actualizado satisfactoriamente.");

    public static final EstadoResponse R0012 = respuestaOk("Se ha asignado el perfil al usuario exitosamente.");

    public static final EstadoResponse R0013 = respuestaOk("La tienda se ha registrado exitosamente.");


    public static final EstadoResponse R0014 = respuestaOk("La tienda se ha actualizado exitosamente.");
    public static final EstadoResponse R0015 = respuestaErr("La tienda no está registrada en el sistema.");

    public static final EstadoResponse R0016 = respuestaOk("Se ha consultado exitosamente las tiendas.");

    public static final EstadoResponse R0017 = respuestaOk("El producto se ha guardado exitosamente.");
    public static final EstadoResponse R0018 = respuestaErr("No se puede guardar el producto. [SS]");

    public static final EstadoResponse R0019 = respuestaOk("El producto se ha eliminado exitosamente.");
    public static final EstadoResponse R0020 = respuestaErr("No se puede eliminar el producto, por favor verifique que no tenga ventas asociadas");

    public static final EstadoResponse R0021 = respuestaOk("Se ha consultado exitosamente los productos");

    public static final EstadoResponse R0023 = respuestaOk("Se ha consultado exitosamente las etiquetas");

    public static final EstadoResponse R0024 = respuestaOk("Se ha consultado exitosamente las categorias");

    public static final EstadoResponse R0025 = respuestaOk("Se ha consultado exitosamente los domicilios del usuario");
    public static final EstadoResponse R0026 = respuestaErr("No se puede consultar los domicilios del usuario. [SS]");

    public static final EstadoResponse R0027 = respuestaOk("El domicilio del usuario se ha guardado exitosamente.");
    public static final EstadoResponse R0028 = respuestaErr("No se puede guardar el domicilio del usuario. [SS]");

    public static final EstadoResponse R0029 = respuestaOk("El domicilio del usuario se ha eliminado exitosamente.");
    public static final EstadoResponse R0030 = respuestaErr("No se puede eliminar el domicilio del usuario. [SS]");

    public static final EstadoResponse R0031 = respuestaOk("Se ha guardado el producto en el carrito de compras exitosamente.");
    public static final EstadoResponse R0032 = respuestaErr("No existen unidades disponibles para comprar este producto.");
    public static final EstadoResponse R0033 = respuestaErr("No existe el producto para agregar en el carrito.");
    public static final EstadoResponse R0034 = respuestaErr("No se puede actualizar el producto del carrito de compras [NE].");

    public static final EstadoResponse R0035 = respuestaOk("Se ha consultado exitosamente el carrito de compras");

    public static final EstadoResponse R0036 = respuestaOk("Se ha eliminado el producto del carrito de compras exitosamente.");
    public static final EstadoResponse R0037 = respuestaErr("No se puede eliminar el producto del carrito de compras [NE].");

    public static final EstadoResponse R0038 = respuestaOk("Se ha seleccionado el domicilio predeterminado exitosamente.");
    public static final EstadoResponse R0039 = respuestaErr("No se puede seleccionar el domicilio predeterminado del usuario. [SS]");

    public static final EstadoResponse R0040 = respuestaOk("Se ha consultado exitosamente el domicilio por su identificador");

    public static final EstadoResponse R0041 = respuestaOk("Se ha consultado exitosamente la lista de paises");
    public static final EstadoResponse R0042 = respuestaOk("Se ha consultado exitosamente la lista de departamentos");
    public static final EstadoResponse R0043 = respuestaOk("Se ha consultado exitosamente la lista de ciudades");

    public static final EstadoResponse R0044 = respuestaOk("Los datos del usuario se han guardado exitosamente.");
    public static final EstadoResponse R0045 = respuestaErr("No se puede guardar el usuario. [SS]");

    public static final EstadoResponse R0046 = respuestaOk("Se ha eliminado los productos en proceso del usuario exitosamente.");

    public static final EstadoResponse R0047 = respuestaOk("Usuario actualizado exitosamente.");

    public static final EstadoResponse R0048 = respuestaOk("Se ha consultado exitosamente el histórico de carrito de compras");

    public static final EstadoResponse R0049 = respuestaOk("Se ha contactado con mercadopago exitosamente");
    public static final EstadoResponse R0050 = respuestaOk("Se han obtenido las tiendas ordenadas por cercanía");
    
    public static final EstadoResponse R0051 = respuestaOk("Se ha consultado exitosamente el histórico de compras");
    
    public static final EstadoResponse R0052 = respuestaOk("La tienda se ha eliminado exitosamente.");
    public static final EstadoResponse R0053 = respuestaErr("No se puede eliminar la tienda, por favor verifique que no tenga productos asociados");
    
    public static final EstadoResponse R0054 = respuestaOk("Se ha consultado exitosamente las tarifas.");
    
    public static final EstadoResponse R0055 = respuestaOk("Se ha actualizado la información de compra correctamente.");
    public static final EstadoResponse R0056 = respuestaErr("No se puede actualizar la compra. [SS]");
    
    public static final EstadoResponse R0057 = respuestaOk("La categoria se ha eliminado exitosamente.");
    public static final EstadoResponse R0058 = respuestaErr("No se puede eliminar la categoria, por favor verifique que no tenga productos asociados.");
    
    public static final EstadoResponse R0059 = respuestaOk("Se ha guardado la etiqueta exitosamente.");
    public static final EstadoResponse R0060 = respuestaErr("No se puede guardar la etiqueta. [SS]");
    
    public static final EstadoResponse R0061 = respuestaOk("Se ha guardado la categoria exitosamente.");
    public static final EstadoResponse R0062 = respuestaErr("No se puede guardar la categoria. [SS]");
    
    public static final EstadoResponse R0063 = respuestaOk("Se ha consultado exitosamente los usuarios del sistema");
    
    public static final EstadoResponse R0064 = respuestaErr("El sistema no puede quedarse sin un usuario administrador.");


    public static final EstadoResponse R0065 = respuestaOk("Se ha inhabilitado el usuario correctamente.");    
    public static final EstadoResponse R0066 = respuestaErr("No se puede inhabilitar el usuario. [SS]");

    public static final EstadoResponse R0067 = respuestaOk("Se ha generado el reporte correctamente.");
    public static final EstadoResponse R0068 = respuestaOk("El reporte no contiene información, por favor verifique los filtros.");
    
    public static final EstadoResponse R0069 = respuestaOk("Las tarifas se han actualizado exitosamente.");
    
    public static final EstadoResponse R0070 = respuestaOk("Se ha habilitado el usuario correctamente.");
    public static final EstadoResponse R0071 = respuestaErr("No se puede habilitado el usuario. [SS]");

    
    /**
     * Servicios Correo
     */
    public static final String AS_RECUP_CONTRASENA = "Recuperar contraseña Appgro";
    public static final String AS_COMPRA_FINALIZADA = "Compra finalizada Appgro";
    
    public static final String PL_RECUP_CONTRASNEA = "template-reset-password";
    public static final String PL_ENVIAR_CONTRASENA = "template-send-password.html";
    public static final String PL_COMPRA_FINALIZADA = "template-sucess-payment.html";

    public static final double RADIO_TERRESTRE = 6371.01;


    /**
     * Métodos utilitarios.
     */
    public static EstadoResponse respuestaOk(String mensaje){
        EstadoResponse respuesta = new EstadoResponse();
        respuesta.setExitoso(Boolean.TRUE);
        respuesta.setEstampaTiempo(new Date());
        respuesta.setMensaje(mensaje);
        return respuesta;
    }

    private static EstadoResponse respuestaErr(String mensaje){
        EstadoResponse respuesta = new EstadoResponse();
        respuesta.setExitoso(Boolean.FALSE);
        respuesta.setEstampaTiempo(new Date());
        respuesta.setMensaje(mensaje);
        return respuesta;
    }

    /**
     * Método para obtener el usuario de la sesión.
     *
     * @return
     */
    public static Object getUsuarioLogueado() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Método para obtener el usuario de la sesión.
     *
     * @return
     */
    public static String getNombreUsuarioLogueado() {
        Object principal = getUsuarioLogueado();
        String nombreUsuario;
        if (principal instanceof UserDetails) {
            nombreUsuario = ((UserDetails) principal).getUsername();
        } else {
            nombreUsuario = principal.toString();
        }
        
        return AES.encriptar(nombreUsuario);
    }
    
    /**
     * Calcular distancia entre dos coordenadas geográficas
     *
     * @param lat1 latitud origen
     * @param lon1 longitud origen
     * @param lat2 latitud destino
     * @param lon2 longitud destino
     * @return distancia en kilometros
     */
    public static double distanciaEntreDosCoordenadas(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double valor =  RADIO_TERRESTRE * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
        DecimalFormat df = new DecimalFormat("#,###");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        return Double.parseDouble(df.format(valor));
    }
}
