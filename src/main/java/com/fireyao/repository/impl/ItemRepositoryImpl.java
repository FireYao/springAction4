package com.fireyao.repository.impl;

import com.fireyao.domain.Item;
import com.fireyao.repository.ItemRepository;
import com.fireyao.repository.ItemRepositoryExpand;
import com.fireyao.repository.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author liuliyuan
 * @date 2017/11/7 14:19
 * @see ItemRepository 的扩展实现，该实现类名前缀必须和被扩展的接口一样，否则调用扩展方法时会出错
 */
@Component
public class ItemRepositoryImpl extends BaseDtoRepository<ItemDTO, Item> implements ItemRepositoryExpand {


    @Override
    public Page<ItemDTO> findItemDTOs(Specification<Item> spec, Pageable page) {
        return super.findDtosByCondition(spec, page);
    }

}
