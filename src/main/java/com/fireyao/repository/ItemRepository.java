package com.fireyao.repository;

import com.fireyao.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>, JpaSpecificationExecutor<Item> {
    Item findByItemName(String itemName);

    List<Item> findByItemNameLike(String itemName);

    Long deleteByItemId(Integer id);

    List<Item> findByItemNameLikeOrderByItemNameDesc(String itemName);


}
