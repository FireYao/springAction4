package com.fireyao.repository;

import com.fireyao.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author liuliyuan
 * @date 2017/11/10 10:31
 * @Description:
 */
@NoRepositoryBean
public interface BaseDtoRepositoryExecutor<DTO, ENTITY, ID extends Serializable> extends JpaSpecificationExecutor<ENTITY>,JpaRepository<ENTITY, ID> {
    Page<DTO> findDTOsPage(Specification<ENTITY> spec, Pageable page);
}
