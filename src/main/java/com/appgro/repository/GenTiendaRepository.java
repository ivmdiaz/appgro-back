package com.appgro.repository;

import com.appgro.model.entity.GenTienda;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.model.response.TiendaResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenTiendaRepository extends JpaRepository<GenTienda, Integer>,
        JpaSpecificationExecutor<GenTienda> {

    /**
     * Obtiene una tienda según su identificador y usuario
     *
     * @param idTienda  identificador de la tienda en base de datos
     * @param idUsuario objeto idUsuario identificador del usuario asociado a la tienda
     * @return GenTienda con un identificador de tienda y usuario.
     */
    GenTienda findFirstByIdTiendaAndIdUsuario(Integer idTienda, Long idUsuario);

    @Query("select count(t) > 0 from GenTienda t where t.idTienda = ?1 AND t.idUsuario = ?2")
    boolean existByIdTiendaAndIdUsuario(Integer idTienda, Long idUsuario);

    /**
     * Listar las tiendas en modo lazy (carga diferida)
     *
     * @param pageable objeto para realizar consulta diferida en base de datos
     * @return una lista de tiendas con carga diferida
     */
    @Query("SELECT new com.appgro.model.response.TiendaResponse(t) FROM GenTienda t " +
            " WHERE t.estado = 'HABILITADA' ")
    List<TiendaResponse> findAllLazy(Pageable pageable);

    /**
     * Listar las tiendas por categoria
     *
     * @param idCategoria identificador de categoria a filtrar
     * @return una lista de tiendas segun el filtro indicado.
     */
    @Query("SELECT new com.appgro.model.response.TiendaResponse(t) "
            + " FROM GenTienda t, GenProducto p "
            + " WHERE t.idTienda = p.idTienda"
            + " AND t.estado = 'HABILITADA' AND p.categoria.idCategoria = ?1 ")
    List<TiendaResponse> findAllByCategoria(Integer idCategoria);
    
    
    /**
     * Listar las tiendas por categoria
     *
     * @param idCategoria identificador de categoria a filtrar
     * @return una lista de tiendas segun el filtro indicado.
     */
    @Query("SELECT new com.appgro.model.response.TiendaResponse(t) "
            + " FROM GenTienda t WHERE t.idUsuario = ?1 "
            + " AND t.estado = 'HABILITADA' ")
    List<TiendaResponse> findAllByProveedor(Long idUsuario);
    
    /**
     * Listar las tiendas para un select
     *
     * @param idCategoria identificador de categoria a filtrar
     * @return una lista de tiendas segun el filtro indicado.
     */
    @Query("SELECT new com.appgro.model.response.IdNombreResponse(t.idTienda, t.nombre) "
            + " FROM GenTienda t WHERE t.idUsuario = ?1 "
            + " AND t.estado = 'HABILITADA' ")
    List<IdNombreResponse> findAllByProveedorSelect(Long idUsuario);
    
    /**
     * Listar las tiendas para un select
     *
     * @param idCategoria identificador de categoria a filtrar
     * @return una lista de tiendas segun el filtro indicado.
     */
    @Query("SELECT new com.appgro.model.response.IdNombreResponse(t.idTienda, t.nombre) "
            + " FROM GenTienda t WHERE t.estado = 'HABILITADA' ")
    List<IdNombreResponse> findAllSelect();
}
