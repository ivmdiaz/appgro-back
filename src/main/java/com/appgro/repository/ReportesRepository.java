package com.appgro.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appgro.config.ReadOnlyRepository;
import com.appgro.model.entity.ViewVentasTodas;
import com.appgro.model.response.ReporteProductoResponse;
import com.appgro.model.response.ReporteTiendaResponse;

public interface ReportesRepository extends ReadOnlyRepository<ViewVentasTodas> {

	@Query("SELECT v FROM ViewVentasTodas v WHERE v.idVendedor =:idVendedor " 
	+ " AND (COALESCE (:idsTienda) IS NULL OR (v.idTienda in :idsTienda) ) "
	+ " AND (COALESCE (:idsProducto) IS NULL OR (v.idProducto in :idsProducto) ) "
	+ " AND (COALESCE (:idsCategoriaProducto) IS NULL OR (v.idCategoriaProducto in :idsCategoriaProducto ) ) "
	+ " AND (:totalVentaMayor IS NULL OR v.totalVenta >= :totalVentaMayor) "
	+ " AND (:totalVentaMenor IS NULL OR v.totalVenta <= :totalVentaMenor) "
	+ " AND (COALESCE (:idsDepartamentoVenta) IS NULL OR (v.idDepartamentoVenta in :idsDepartamentoVenta) ) "
	+ " AND (COALESCE (:idsCiudadVenta) IS NULL OR (v.idCiudadVenta in :idsCiudadVenta) ) "
	+ " AND (CAST(:fechaVentaMayor AS date) IS NULL OR v.fechaVenta >= :fechaVentaMayor) "
	+ " AND (:estado IS NULL OR v.estado = :estado) "
	)
	List<ViewVentasTodas> getReporteTodasVentas(
			@Param("idVendedor") Long idVendedor,
			@Param("idsTienda") List<Integer> idsTienda, 
			@Param("idsProducto") List<Integer> idsProducto,
			@Param("idsCategoriaProducto") List<Integer> idsCategoriaProducto,
			@Param("totalVentaMayor") Double totalVentaMayor, 
			@Param("totalVentaMenor") Double totalVentaMenor,
			@Param("idsDepartamentoVenta") List<Integer> idDepartamentoVenta,
			@Param("idsCiudadVenta") List<Integer> idCiudadVenta,
			@Param("fechaVentaMayor") Date fechaVentaMayor,
			@Param("estado") String estado
	);
	
	@Query("SELECT NEW com.appgro.model.response.ReporteTiendaResponse(t.nombre, t.direccion, SUM(c.precio), "
			+ "SUM(c.cantidad) ) "
			
			+ " FROM GenTienda t, GenProducto p, AppCarritoCompra c, GenUsuarioUbicacion ub, GenCiudad uc "
			
			+ " WHERE t.idTienda = p.idTienda AND c.idProducto = p.idProducto AND c.idUbicacion = ub.idUbicacion "
			+ " AND ub.ciudad = uc.idCiudad AND t.idUsuario =:idVendedor AND c.estado <> 'PROCESO' "
			
			+ " AND (COALESCE (:idsTienda) IS NULL OR (t.idTienda in :idsTienda) ) "
			+ " AND (COALESCE (:idsDepartamentoVenta) IS NULL OR (uc.idDepartamento in :idsDepartamentoVenta) ) "
			+ " AND (COALESCE (:idsCiudadVenta) IS NULL OR (uc.idCiudad in :idsCiudadVenta) ) "
			+ " AND (CAST(:fechaVentaMayor AS date) IS NULL OR c.regDate >= :fechaVentaMayor) "
			+ " AND (:estado IS NULL OR c.estado = :estado) "
			+ " GROUP BY (t.idTienda) "
	)
	List<ReporteTiendaResponse> getReporteTiendas(@Param("idVendedor") Long idVendedor,
			@Param("idsTienda") List<Integer> idsTienda,
			@Param("idsDepartamentoVenta") List<Integer> idDepartamentoVenta,
			@Param("idsCiudadVenta") List<Integer> idCiudadVenta,
			@Param("fechaVentaMayor") Date fechaVentaMayor,
			@Param("estado") String estado
	);
	
	
	@Query("SELECT NEW com.appgro.model.response.ReporteProductoResponse(t.nombre, p.nombre, "
			+ " pc.categoria, p.precio, p.disponibilidad, p.unidadMedida, SUM(c.precio), SUM(c.cantidad) ) "
			
			+ " FROM GenTienda t, GenProducto p, AppCarritoCompra c, GenUsuarioUbicacion ub, GenCiudad uc,"
			+ " GenCategoriaProducto pc "
			
			+ " WHERE t.idTienda = p.idTienda AND c.idProducto = p.idProducto AND c.idUbicacion = ub.idUbicacion "
			+ " AND ub.ciudad = uc.idCiudad AND pc.idCategoria = p.categoria.idCategoria  AND t.idUsuario =:idVendedor "
			+ " AND c.estado <> 'PROCESO' "
			
			+ " AND (COALESCE (:idsTienda) IS NULL OR (t.idTienda in :idsTienda) ) "
			+ " AND (COALESCE (:idsProducto) IS NULL OR (p.idProducto in :idsProducto) ) "
			+ " AND (COALESCE (:idsCategoriaProducto) IS NULL OR (pc.idCategoria in :idsCategoriaProducto ) ) "
			+ " AND (COALESCE (:idsDepartamentoVenta) IS NULL OR (uc.idDepartamento in :idsDepartamentoVenta) ) "
			+ " AND (COALESCE (:idsCiudadVenta) IS NULL OR (uc.idCiudad in :idsCiudadVenta) ) "
			+ " AND (CAST(:fechaVentaMayor AS date) IS NULL OR c.regDate >= :fechaVentaMayor) "
			+ " AND (:estado IS NULL OR c.estado = :estado) "
			+ " GROUP BY (p.idProducto, pc.idCategoria, t.idTienda) "
	)
	List<ReporteProductoResponse> getReporteProductos(@Param("idVendedor") Long idVendedor,
			@Param("idsTienda") List<Integer> idsTienda, 
			@Param("idsProducto") List<Integer> idsProducto,
			@Param("idsCategoriaProducto") List<Integer> idsCategoriaProducto,
			@Param("idsDepartamentoVenta") List<Integer> idDepartamentoVenta,
			@Param("idsCiudadVenta") List<Integer> idCiudadVenta,
			@Param("fechaVentaMayor") Date fechaVentaMayor,
			@Param("estado") String estado
	);

	@Query("SELECT NEW com.appgro.model.response.ReporteTiendaResponse(t, u, SUM(c.precio), SUM(c.cantidad) ) "
			
			+ " FROM GenTienda t, GenUsuario u, GenProducto p, AppCarritoCompra c, GenUsuarioUbicacion ub, GenCiudad uc "
			
			+ " WHERE t.idTienda = p.idTienda AND c.idProducto = p.idProducto AND c.idUbicacion = ub.idUbicacion "
			+ " AND ub.ciudad = uc.idCiudad AND c.estado <> 'PROCESO' AND t.idUsuario = u.idUsuario"
			
			+ " AND (COALESCE (:idsTienda) IS NULL OR (t.idTienda in :idsTienda) ) "
			+ " AND (COALESCE (:idsDepartamentoVenta) IS NULL OR (uc.idDepartamento in :idsDepartamentoVenta) ) "
			+ " AND (COALESCE (:idsCiudadVenta) IS NULL OR (uc.idCiudad in :idsCiudadVenta) ) "
			+ " AND (CAST(:fechaVentaMayor AS date) IS NULL OR c.regDate >= :fechaVentaMayor) "
			+ " AND (CAST(:fechaVentaMenor AS date) IS NULL OR c.regDate <= :fechaVentaMenor) "
			+ " AND (:estado IS NULL OR c.estado = :estado) "
			+ " GROUP BY (t.idTienda, u.idUsuario) "
	)
	List<ReporteTiendaResponse> getReporteAdmTiendas(
			@Param("idsTienda") List<Integer> idsTienda,
			@Param("idsDepartamentoVenta") List<Integer> idDepartamentoVenta,
			@Param("idsCiudadVenta") List<Integer> idCiudadVenta,
			@Param("fechaVentaMayor") Date fechaVentaMayor,
			@Param("fechaVentaMenor") Date fechaVentaMenor,
			@Param("estado") String estado
	);
}
