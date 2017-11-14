package com.fireyao;

import com.fireyao.repository.BaseDtoRepositoryExecutor;
import com.fireyao.repository.impl.BaseDtoRepositoryExecutorImpl;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Set;

/**
 * @author liuliyuan
 * @date 2017/11/13 10:08
 * @Description:
 */
public class BaseRepositoryFactoryBean<R extends JpaRepository<ENTITY, ID>, ENTITY,
        ID extends Serializable> extends JpaRepositoryFactoryBean<R, ENTITY, ID> {

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new BaseRepositoryFactory(em);
    }

    private static class BaseRepositoryFactory<DTO, ENTITY, ID extends Serializable>
            extends JpaRepositoryFactory {

        private final EntityManager em;

        public BaseRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }

        //设置具体的实现类是BaseRepositoryImple
        @Override
        protected Object getTargetRepository(RepositoryInformation information) {

            Class<?> domainType = information.getDomainType();
            System.out.println(domainType.getName());

            return new BaseDtoRepositoryExecutorImpl<DTO, ENTITY, ID>((Class<ENTITY>) information.getDomainType(), em);
        }

        //设置具体的实现类的class
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseDtoRepositoryExecutorImpl.class;
        }
    }

}
