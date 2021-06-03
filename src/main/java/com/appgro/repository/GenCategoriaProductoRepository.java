package com.appgro.repository;

import com.appgro.model.entity.GenCategoriaProducto;
import com.appgro.model.response.CategoriaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenCategoriaProductoRepository extends JpaRepository<GenCategoriaProducto, Integer>,
        JpaSpecificationExecutor<GenCategoriaProducto> {

    /**
     * Obtiene una lista de categorias segun el formato de respuesta.
     * @return
     */
    @Query("SELECT new com.appgro.model.response.CategoriaResponse(c) FROM GenCategoriaProducto c "
    		+ " order by c.idCategoria ")
    List<CategoriaResponse> findAllResponse();

}
