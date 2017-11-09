package com.fireyao.repository;

import com.fireyao.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>, JpaSpecificationExecutor<Item>,ItemRepositoryCustom {
    Item findByItemName(String itemName);

    List<Item> findByItemNameLike(String itemName);

    Long deleteByItemId(Integer id);

    List<Item> findByItemNameLikeOrderByItemNameDesc(String itemName);

    @Query("select u from Item u where u.itemId = ?#{[0]}")
    List<Item> findById123(int id);
}
