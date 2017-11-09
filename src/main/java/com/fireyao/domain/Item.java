package com.fireyao.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DynamicUpdate
@DynamicInsert
@Data
public class Item implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Integer itemId;
    @Basic
    @Column(name = "item_name")
    private String itemName;
    @Basic
    @Column(name = "item_price")
    private Integer itemPrice;
    @Basic
    @Column(name = "item_stock")
    private Integer itemStock;


}
