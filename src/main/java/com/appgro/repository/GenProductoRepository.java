package com.appgro.repository;

import com.appgro.model.entity.GenProducto;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.model.response.ProductoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenProductoRepository
		extends JpaRepository<GenProducto, Integer>, JpaSpecificationExecutor<GenProducto> {

	/**
	 * Busca todos los productos relacionados a una tienda.
	 *
	 * @param idTienda identificador de tienda a buscar
	 * @return lista de productos filtrados
	 */
	@Query(" SELECT new com.appgro.model.response.ProductoResponse(p) "
			+ " FROM GenProducto p WHERE p.idTienda = ?1 order by p.regDate")
	List<ProductoResponse> findAllByIdTienda(Integer idTienda);

	/**
	 * Busca todos los productos relacionados a una tienda.
	 *
	 * @param idProducto identificador del producto a buscar
	 * @return lista de productos filtrados
	 */
	@Query(" SELECT new com.appgro.model.response.ProductoResponse(p)"
			+ " FROM GenProducto p WHERE p.idProducto = ?1 order by p.regDate")
	ProductoResponse findByIdProducto(Integer idProducto);

	/**
	 * Obtiene la disponibilida de un producto.
	 *
	 * @param idProducto identificador del producto a buscar
	 * @return disponibilidad del producto
	 */
	@Query(" SELECT p.disponibilidad FROM GenProducto p WHERE p.idProducto = ?1 order by p.regDate")
	Integer getDisponibilidadById(Integer idProducto);

	/**
	 * Actualizamos la disponibilidad de un producto.
	 * 
	 * @param disponible
	 * @param idProducto
	 */
	@Modifying
	@Query("UPDATE GenProducto p set p.disponibilidad = ?1 WHERE p.idProducto = ?2")
	void setDisponibilidadById(Integer disponible, Integer idProducto);

	GenProducto findFirstByIdProducto(Integer idProducto);
	
	
	@Query("select count(p) > 0 from GenProducto p INNER JOIN p.etiquetas e where e.idEtiqueta = ?1")
	boolean existByIdEtiqueta(Integer idEtiqueta);
	
	@Query("select count(p) > 0 from GenProducto p INNER JOIN p.categoria c where c.idCategoria = ?1")
	boolean existByIdCategoria(Integer idCategoria);
	
	/**
	 * Busca todos los productos relacionados a una tienda.
	 *
	 * @param idTienda identificador de tienda a buscar
	 * @return lista de productos filtrados
	 */
	@Query(" SELECT new com.appgro.model.response.IdNombreResponse(p.idProducto, p.nombre) "
			+ " FROM GenProducto p, GenTienda t WHERE p.idTienda = t.idTienda AND t.idUsuario = ?1 "
			+ " order by p.regDate")
	List<IdNombreResponse> findAllByIdVendedorSelect(Long idVendedor);
	
	
	/**
	 * Busca todos los productos relacionados a una tienda.
	 *
	 * @param idTienda identificador de tienda a buscar
	 * @return lista de productos filtrados
	 */
	@Query(" SELECT new com.appgro.model.response.IdNombreResponse(c.idCategoria, c.categoria) "
			+ " FROM GenProducto p, GenTienda t INNER JOIN p.categoria c "
			+ " WHERE p.idTienda = t.idTienda AND t.idUsuario = ?1 order by p.regDate")
	List<IdNombreResponse> findAllCategoriasByIdVendedorSelect(Long idVendedor);
}
