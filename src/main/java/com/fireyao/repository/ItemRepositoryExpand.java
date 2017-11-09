package com.fireyao.repository;

import com.fireyao.domain.Item;
import com.fireyao.repository.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author liuliyuan
 * @date 2017/11/7 14:16
 * @Description: ItemRepository接口的扩展接口，这里自定义一些方法。比如查询实体转换为DTO
 */
public interface ItemRepositoryExpand {

    Page<ItemDTO> findItemDTOs(Specification<Item> spec, Pageable page);

}
