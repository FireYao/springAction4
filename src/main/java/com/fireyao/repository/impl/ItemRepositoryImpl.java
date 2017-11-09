package com.fireyao.repository.impl;

import com.fireyao.domain.Item;
import com.fireyao.repository.ItemRepositoryCustom;
import com.fireyao.repository.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author liuliyuan
 * @date 2017/11/7 14:19
 * @Description:
 */
@Component
public class ItemRepositoryImpl extends BaseDtoRepository<ItemDTO, Item> implements ItemRepositoryCustom {

    @Override
    @PersistenceContext
    public void setEm(EntityManager em) {
        super.setParam(em, ItemDTO.class, Item.class);
    }

    @Override
    public Page<ItemDTO> findItemDTOs(Specification<Item> spec, Pageable page) {
        return super.findDtosByCondition(spec, page);
    }

}
