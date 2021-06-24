package org.lrx.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lrx.entity.CommodityKind;
import org.lrx.entity.CommodityPicture;
import org.lrx.entity.CommodityStrator;

import java.util.List;

@Mapper
public interface CommodityMapper {

    /**
     *商品管理
     */

    //新增商品信息
    Boolean insertCommodity(CommodityStrator commodityStrator);

    //查询全部商品信息
    List<CommodityStrator> selectAllCommodity();

    //通过种类查询商品信息
    List<CommodityStrator> selectAllCommodityByKind(@Param("kind") Integer kind);

    //修改商品信息通过id
    Boolean updateCommodity(CommodityStrator commodityStrator);

    //查询单个商品信息
    CommodityStrator selectCommodityOne(@Param("id") Integer id);

    //删除单个商品信息
    Boolean deleteCommodity(@Param("id") Integer id);

    //批量删除商品信息
    Boolean deleteCommodities(List<Integer> ids);

    /**
     *图片管理
     */

    //新增图片
    Boolean insertPicture(List<CommodityPicture> pictures);

    //删除图片通过商品id
    Boolean deletePicture(@Param("commodityId") Integer commodityId);

    //批量删除照片
    Boolean deletePictures(List<Integer> ids);

    //查询图片通过商品id
    List<CommodityPicture> selectPicture(@Param("commodityId") Integer commodityId);

}
