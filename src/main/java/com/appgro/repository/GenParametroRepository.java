package com.appgro.repository;

import com.appgro.model.entity.GenParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenParametroRepository extends JpaRepository<GenParametro, Long>,
        JpaSpecificationExecutor<GenParametro> {

    GenParametro findFirstByIdParametro(Integer idParametro);
}
