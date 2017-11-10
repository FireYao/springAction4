package com.fireyao.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @param <DTO>    DTO
 * @param <ENTITY> ENTITY
 * @author liuliyuan
 * @date 2017/11/9 11:37
 * @Description: DTO对象查询基类
 * 当entity中包含有很多在列表数据中不需要的字段
 * 就需要用到DTO数据传输对象，来将entity与dto进行转换，
 * 来控制数据库访问粒度，减轻数据库压力
 */
public abstract class BaseDtoRepository<DTO, ENTITY> {

    @PersistenceContext
    private EntityManager entityManager;
    private Class<DTO> dtoClass;
    private Class<ENTITY> entityClass;

    public BaseDtoRepository() {
        Type t = getClass().getGenericSuperclass();
        //获取DTO类类型
        dtoClass = (Class<DTO>) ((ParameterizedType) t).getActualTypeArguments()[0];
        //获取ENTITY类类型
        entityClass = (Class<ENTITY>) ((ParameterizedType) t).getActualTypeArguments()[1];
    }

    /**
     * 分页查询
     *
     * @param spec
     * @param page
     * @return
     */
    protected Page<DTO> findDtosByCondition(Specification<ENTITY> spec, Pageable page) {

        TypedQuery<DTO> query = getEntityQuery(spec, page.getSort());

        return page == null ? new PageImpl<DTO>(query.getResultList()) : readPage(query, page, spec);

    }


    /**
     * 获取TypedQuery
     *
     * @param spec
     * @param query
     * @param sort
     * @return
     */
    private TypedQuery<DTO> getEntityQuery(Specification<ENTITY> spec, Sort sort) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DTO> query = cb.createQuery(dtoClass);

        Root<ENTITY> root = query.from(entityClass);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, builder);
            if (predicate != null) {
                query.where(predicate);
            }
        }
        List list = new ArrayList<>();
        for (Field field : dtoClass.getDeclaredFields()) {
            list.add(root.get(field.getName()));
        }
        query.multiselect(list);
        if (sort != null) {
            query.orderBy(QueryUtils.toOrders(sort, root, builder));
        }
        return entityManager.createQuery(query);
    }


    /**
     * 分页查询时，用于计数总的实体数量
     */
    private Long executeCountQuery(Specification<ENTITY> spec) {
        TypedQuery<Long> query = getCountQuery(spec);
        Assert.notNull(query);
        List<Long> totals = query.getResultList();
        Long total = 0L;
        for (Long element : totals) {
            total += element == null ? 0 : element;
        }
        return total;
    }


    /**
     * 获取count查询的TypedQuery
     *
     * @param spec
     * @return
     */
    private TypedQuery<Long> getCountQuery(Specification<ENTITY> spec) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<ENTITY> root = query.from(entityClass);
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, builder);

            if (predicate != null) {
                query.where(predicate);
            }
        }

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        // Remove all Orders the Specifications might have applied
        query.orderBy(Collections.emptyList());

        return entityManager.createQuery(query);
    }


    /**
     * 分页
     *
     * @param q
     * @param page
     * @param spec
     * @return
     */
    private Page<DTO> readPage(TypedQuery<DTO> q, Pageable page,
                               Specification<ENTITY> spec) {
        q.setFirstResult(page.getOffset());
        q.setMaxResults(page.getPageSize());

        Long total = executeCountQuery(spec);

        List<DTO> content = total > page.getOffset() ? q.getResultList() : Collections.emptyList();

        return new PageImpl<>(content, page, total);
    }

}
