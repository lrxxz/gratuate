package org.lrx.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lrx.entity.Order;
import org.lrx.entity.Shop;

import java.util.List;

@Mapper
public interface ShopMapper {

    Boolean insertShop(Shop shop);

    Integer selectShop(@Param("commodityId") Integer commodityId,@Param("userId") Integer userId);

    Integer selectCountShop(Integer userId);

    List<Shop> selectByUserShops(Integer userId);

    Shop selectOneShop(Integer id);

    Boolean deleteShop(Integer id);

    Boolean deleteShops(List<Integer> ids);

    List<Shop> selectShopsByIds(List<Integer> ids);

    //订单查询

    Boolean insertOrder(Order order);

    Integer selectCountOrder(Integer userId);

    List<Order> selectByUserOrder(Integer userId);

    Boolean deleteOrder(Integer id);

    Boolean updateOrderStatus(Integer id);

    Boolean updateOrderStatus2(Integer id);

    List<Order> selectAllOrder();

    Boolean numAdd(@Param("id") Integer id,@Param("num") Integer num);
}
