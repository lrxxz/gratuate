package org.lrx.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.lrx.dao.CommodityMapper;
import org.lrx.dao.ShopMapper;
import org.lrx.dao.UserMapper;
import org.lrx.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ShopServiceImpl {

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    CommodityMapper commodityMapper;

    @Autowired
    UserMapper userMapper;


    /**
     * 加入购物车
     * @param commodityId
     * @param userId
     * @return
     */
    public Map<String,Object> insertShop(Integer commodityId,Integer userId){
        Map<String,Object> map = new HashMap<>();
        if (commodityId==null || userId == null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Integer sum = shopMapper.selectShop(commodityId, userId);
        if (sum>0){
            map.put("code",0);
            map.put("message","已加入购物车，快去购物车瞧瞧!");
            return map;
        }
        Shop shop = new Shop();
        shop.setCommodityId(commodityId);
        shop.setUserId(userId);
        shop.setShopNum(1);
        Boolean aBoolean = shopMapper.insertShop(shop);
        if (!aBoolean){
            map.put("code",0);
            map.put("message","数据库写入出现问题");
            return map;
        }
        map.put("code",1);
        map.put("message","加入购物车成功");
        return map;
    }

    /**
     * 查询某个用户的购物车数量
     * @param userId
     * @return
     */
    public Integer getShopByUser(Integer userId){
        return shopMapper.selectCountShop(userId);
    }


    /**
     * 查询用户的购物车列表
     * @param userId
     * @return
     */
    public List<Shop> getAllShopBuyUser(Integer userId){
        List<Shop> shops = shopMapper.selectByUserShops(userId);
        Iterator<Shop> shopIterator = shops.iterator();
        while (shopIterator.hasNext()){
            Shop shop = shopIterator.next();
            CommodityStrator commodityStrator = commodityMapper.selectCommodityOne(shop.getCommodityId());
            List<CommodityPicture> pictures = commodityMapper.selectPicture(commodityStrator.getId());
            commodityStrator.setCommodityPictures(pictures);
            shop.setCommodityStrator(commodityStrator);
        }
        return shops;
    }


    /**
     * 查询用户全部的订单
     * @param userId
     * @return
     */
    public List<Order> getAllOrderByUser(Integer userId){
        List<Order> orders = shopMapper.selectByUserOrder(userId);
        Iterator<Order> orderIterator = orders.iterator();
        while(orderIterator.hasNext()){
            Order order = orderIterator.next();
            CommodityStrator commodityStrator = commodityMapper.selectCommodityOne(order.getCommodityId());
            List<CommodityPicture> pictures = commodityMapper.selectPicture(commodityStrator.getId());
            commodityStrator.setCommodityPictures(pictures);
            order.setCommodityStrator(commodityStrator);
        }
        return orders;
    }

    /**
     * 删除用户的订单
     * @param id
     * @return
     */
    public Map<String,Object> deleteOrder(Integer id){
        Map<String,Object> map = new HashMap<>();
        if (id == null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean aBoolean = shopMapper.deleteOrder(id);
        if (!aBoolean){
            map.put("code",0);
            map.put("message","数据库删除出现问题");
            return map;
        }
        map.put("code",1);
        map.put("message","删除订单成功");
        return map;
    }

    /**
     * 查询某个用户的订单数量
     * @param userId
     * @return
     */
    public Integer getOrderByUser(Integer userId){
        return shopMapper.selectCountOrder(userId);
    }


    /**
     * 购物车结算
     * @param id
     * @return
     */
    public Map<String,Object> shopBalance(Integer id){
        Map<String,Object> map = new HashMap<>();
        if (id == null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Shop shop = shopMapper.selectOneShop(id);
        if (shop == null){
            map.put("code",0);
            map.put("message","物品不在购物车");
            return map;
        }
        CommodityStrator commodityStrator = commodityMapper.selectCommodityOne(shop.getCommodityId());
        if (commodityStrator == null){
            map.put("code",0);
            map.put("message","商品已不存在");
            return map;
        }
        Order order = new Order();
        order.setUserId(shop.getUserId());
        order.setCommodityId(shop.getCommodityId());
        order.setOrderStatus(0);
        order.setOrderPrice(shop.getShopNum() * commodityStrator.getCommodityPrice());
        order.setOrderNum(shop.getShopNum());
        order.setOrderDate(LocalDate.now().toString());
        Boolean aBoolean = shopMapper.insertOrder(order);
        if (!aBoolean){
            map.put("code",0);
            map.put("message","订单生成失败");
            return map;
        }
        shopMapper.deleteShop(id);
        map.put("code",1);
        map.put("message","订单结算成功");
        return map;
    }


    /**
     * 批量生成订单
     * @param idArray
     * @return
     */
    public Map<String,Object> shopBalances(Integer[] idArray){
        Map<String,Object> map = new HashMap<>();
        if (idArray == null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        List<Integer> ids = (List<Integer>) CollectionUtils.arrayToList(idArray);
        List<Shop> shops = shopMapper.selectShopsByIds(ids);
        if (shops == null){
            map.put("code",0);
            map.put("message","该数据不在购物车");
            return map;
        }
        for (Shop shop : shops){
            CommodityStrator commodityStrator = commodityMapper.selectCommodityOne(shop.getCommodityId());
            Order order = new Order();
            order.setUserId(shop.getUserId());
            order.setCommodityId(shop.getCommodityId());
            order.setOrderStatus(0);
            order.setOrderPrice(shop.getShopNum() * commodityStrator.getCommodityPrice());
            order.setOrderNum(shop.getShopNum());
            order.setOrderDate(LocalDate.now().toString());
            shopMapper.insertOrder(order);
        }
        shopMapper.deleteShops(ids);
        map.put("code",1);
        map.put("message","订单结算成功");
        return map;
    }


    /**
     * 确认收货
     * @param id
     * @return
     */
    public Map<String,Object> orderYes(Integer id){
        Map<String,Object> map = new HashMap<>();
        if (id==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean aBoolean = shopMapper.updateOrderStatus(id);
        if (!aBoolean){
            map.put("code",0);
            map.put("message","收货失败");
            return map;
        }
        map.put("code",1);
        map.put("message","收货成功");
        return map;
    }

    /**
     * 获取所有的订单信息
     * @return
     */
    public Map<String, Object> getAllOrders(Integer page, Integer rows){
        Map<String, Object> map = new HashMap<>();
        Page<Order> pages = PageHelper.startPage(page, rows).doSelectPage(() -> shopMapper.selectAllOrder());
        List<Order> orderList = pages.getResult();
        Iterator<Order> orderIterator = orderList.iterator();
        while(orderIterator.hasNext()){
            Order order = orderIterator.next();
            CommodityStrator commodityStrator = commodityMapper.selectCommodityOne(order.getCommodityId());
            List<CommodityPicture> pictures = commodityMapper.selectPicture(commodityStrator.getId());
            UserStrator userStrator = userMapper.selectUserById(order.getUserId());
            commodityStrator.setCommodityPictures(pictures);
            order.setCommodityStrator(commodityStrator);
            order.setUserStrator(userStrator);
        }
        map.put("data",orderList);
        map.put("page", pages.getPageNum());  //当前页
        map.put("pages",pages.getPages());    //总页数
        map.put("total",pages.getTotal());    //总数据量
        return map;
    }


    /**
     * 状态改为派送中
     * @param id
     * @return
     */
    public Map<String,Object> updateOrderStatus(Integer id){
        Map<String,Object> map = new HashMap<>();
        if (id==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean aBoolean = shopMapper.updateOrderStatus2(id);
        if (!aBoolean){
            map.put("code",0);
            map.put("message","派送失败");
            return map;
        }
        map.put("code",1);
        map.put("message","派送成功");
        return map;
    }

    /**
     * 购物商品数量+1
     * @param id
     * @param num
     * @return
     */
    public Map<String,Object> numAdd(Integer id,Integer num){
        Map<String,Object> map = new HashMap<>();
        if (id==null || num==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean aBoolean = shopMapper.numAdd(id, num);
        if (!aBoolean){
            map.put("code",0);
            map.put("message","数据写入错误");
            return map;
        }
        map.put("code",1);
        map.put("message","+1成功");
        return map;
    }

    /**
     * 将商品移出购物车
     * @param id
     * @return
     */
    public Map<String,Object> deleteShop(Integer id){
        Map<String,Object> map = new HashMap<>();
        if (id == null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean aBoolean = shopMapper.deleteShop(id);
        if (!aBoolean){
            map.put("code",0);
            map.put("message","删除购物车失败");
            return map;
        }
        map.put("code",1);
        map.put("message","移出购物车成功");
        return map;
    }
}
