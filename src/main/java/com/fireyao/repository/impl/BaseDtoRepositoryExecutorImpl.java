package com.fireyao.repository.impl;

import com.fireyao.repository.BaseDtoRepositoryExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liuliyuan
 * @date 2017/11/10 10:41
 * @Description:
 */
public class BaseDtoRepositoryExecutorImpl<DTO, ENTITY, ID extends Serializable> extends SimpleJpaRepository<ENTITY, ID> implements BaseDtoRepositoryExecutor<DTO, ENTITY, ID> {


    private final EntityManager em;
    private Class<DTO> dtoClass;
    private Class<ENTITY> entityClass;

    public BaseDtoRepositoryExecutorImpl(JpaEntityInformation<ENTITY, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;

        Type[] types = getClass().getGenericInterfaces();

        ParameterizedType type = (ParameterizedType) types[0];
//        Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        //获取DTO类类型
//        dtoClass = (Class<DTO>) (type.getActualTypeArguments()[0]);
        //获取ENTITY类类型
        entityClass = getDomainClass();
    }

    public BaseDtoRepositoryExecutorImpl(Class<ENTITY> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
    }

    @Override
    public Page<DTO> findDTOsPage(Specification<ENTITY> spec, Pageable page) {

        TypedQuery<DTO> query = getEntityQuery(spec, page.getSort());

        return page == null ? new PageImpl<DTO>(query.getResultList()) : getPage(query, page, spec);

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

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DTO> query = cb.createQuery(dtoClass);

        Root<ENTITY> root = query.from(entityClass);
        CriteriaBuilder builder = em.getCriteriaBuilder();
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
        return em.createQuery(query);
    }


    /**
     * 分页查询时，用于计数总的实体数量
     */
    private Long executeCountQuery(Specification<ENTITY> spec) {
        TypedQuery<Long> query = getLongCountQuery(spec);
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
    private TypedQuery<Long> getLongCountQuery(Specification<ENTITY> spec) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
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

        return em.createQuery(query);
    }


    /**
     * 分页
     *
     * @param q
     * @param page
     * @param spec
     * @return
     */
    private Page<DTO> getPage(TypedQuery<DTO> q, Pageable page,
                              Specification<ENTITY> spec) {
        q.setFirstResult(page.getOffset());
        q.setMaxResults(page.getPageSize());

        Long total = executeCountQuery(spec);

        List<DTO> content = total > page.getOffset() ? q.getResultList() : Collections.emptyList();

        return new PageImpl<>(content, page, total);
    }

}
