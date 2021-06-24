package org.lrx.entity;

import lombok.Data;

@Data
public class Order {
    private Integer id;
    private Integer userId;
    private Integer commodityId;
    private Integer orderStatus;
    private Integer orderPrice;
    private Integer orderNum;
    private String orderDate;
    private CommodityStrator commodityStrator;
    private UserStrator userStrator;
}
