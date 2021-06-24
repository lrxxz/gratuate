package org.lrx.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lrx.entity.CommodityKind;

import java.util.List;

@Mapper
public interface KindMapper {
    //新增商品种类
    Boolean insertKind(CommodityKind kind);

    //删除商品种类
    Boolean deleteKind(@Param("id") Integer id);

    //批量删除商品种类
    Boolean deleteKinds(List<Integer> ids);

    //查询所有商品种类
    List<CommodityKind> selectAllKind();

    //查询单个商品种类
    CommodityKind selectOneKind(Integer id);


}
