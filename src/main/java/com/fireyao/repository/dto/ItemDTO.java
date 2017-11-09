package com.fireyao.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuliyuan
 * @date 2017/11/7 14:16
 * @Description: item 数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {


    private Integer itemId;
    private String itemName;

}
