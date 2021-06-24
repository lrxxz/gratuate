package org.lrx.entity;

import lombok.Data;

@Data
public class Shop {
    private Integer id;
    private Integer commodityId;
    private Integer userId;
    private Integer shopNum;
    private CommodityStrator commodityStrator;
}
