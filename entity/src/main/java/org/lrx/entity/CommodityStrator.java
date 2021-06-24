package org.lrx.entity;

import lombok.Data;

import java.util.List;

@Data
public class CommodityStrator {
    //商品id
    private Integer id;
    //商品名称
    private String commodityName;
    //商品描述
    private String commodityMark;
    //商品价格
    private Integer commodityPrice;
    //商品产地
    private String commodityOrigin;

    private Integer kindId;

    private String pictureAddress[];

    private CommodityKind commodityKind;

    private List<CommodityPicture> commodityPictures;
}
