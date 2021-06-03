package com.appgro.repository;

import com.appgro.model.entity.AppCarritoCompra;
import com.appgro.model.response.CarritoResponse;
import com.appgro.model.response.CompraResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppCarritoCompraRepository extends JpaRepository<AppCarritoCompra, Long>,
        JpaSpecificationExecutor<AppCarritoCompra> {

    @Query(" SELECT new com.appgro.model.response.CarritoResponse(c, p, ub) FROM AppCarritoCompra c, "
            + " GenProducto p, GenUsuarioUbicacion ub WHERE c.idProducto = p.idProducto "
            + " AND ub.idUbicacion = c.idUbicacion AND c.idComprador = ?1 "
            + " AND c.estado = ?2 ORDER BY c.regDate DESC"
    )
    List<CarritoResponse> consultarCarritoComprasByEstadoOrderByRegDateDesc(Long idComprador, String estado);
    
    @Query(" SELECT new com.appgro.model.response.CarritoResponse(c, p, ub) FROM AppCarritoCompra c, "
            + " GenProducto p, GenUsuarioUbicacion ub WHERE c.idProducto = p.idProducto "
            + " AND ub.idUbicacion = c.idUbicacion AND c.idComprador = ?1 "
            + " AND c.estado <> 'PROCESO' ORDER BY c.regDate DESC"
    )
    List<CarritoResponse> consultarComprasByCompradorOrderByRegDateDesc(Long idComprador);

    List<AppCarritoCompra> findAllByIdCompradorAndEstado(Long idComprador,String estado);
    
    
    @Query(" SELECT new com.appgro.model.response.CompraResponse(c, p, u, t, ub) FROM AppCarritoCompra c, "
            + " GenProducto p, GenUsuarioUbicacion ub, GenTienda t, GenUsuario u "
            + " WHERE c.idProducto = p.idProducto AND u.idUsuario = c.idComprador"
            + " AND ub.idUbicacion = c.idUbicacion AND t.idTienda = p.idTienda "
            + " AND t.idUsuario = ?1 AND c.estado = 'COMPRADO' ORDER BY c.regDate ASC"
    )
    List<CompraResponse> consultarComprasByProveedorOrderByRegDateDesc(Long idProveedor);
    
	@Query("select count(c) > 0 from AppCarritoCompra c where c.idProducto = ?1 ")
	boolean existByIdProducto(Integer idProducto);
	
	@Query("SELECT COUNT(c) > 0 FROM AppCarritoCompra c, GenProducto p, GenTienda t "
			+ " WHERE c.idCarritoCompra = ?1 AND c.idProducto = p.idProducto "
			+ " AND p.idTienda = t.idTienda AND t.idUsuario = ?2 ")
	boolean existByIdProveedor(Long idCarritoCompra, Long idUsuario);
	
	@Modifying
    @Query("UPDATE AppCarritoCompra c SET c.estado = 'ENTREGADO' where c.idCarritoCompra = ?1")
    void actualizarEstadoAEntregado(Long idCarritoCompra);
	
	@Query(" SELECT new com.appgro.model.response.CompraResponse(c, p, u, t, ub) FROM AppCarritoCompra c, "
            + " GenProducto p, GenUsuarioUbicacion ub, GenTienda t, GenUsuario u "
            + " WHERE c.idProducto = p.idProducto AND u.idUsuario = c.idComprador"
            + " AND ub.idUbicacion = c.idUbicacion AND t.idTienda = p.idTienda "
            + " AND c.idCarritoCompra IN :ids ORDER BY c.regDate DESC"
    )
    List<CompraResponse> consultarComprasByIdCompra(@Param("ids") List<Long> ids);

}
