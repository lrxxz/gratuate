package org.lrx.controller;

import org.lrx.entity.*;
import org.lrx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    CommodityServiceImpl commodityService;

    @Autowired
    KindServiceImpl kindService;

    @Autowired
    ActivityServiceImpl activityService;

    @Autowired
    DeviceServiceImpl deviceService;

    @Autowired
    ShopServiceImpl shopService;

    @ResponseBody
    @RequestMapping("/login")
    public Map<String ,Object> login(String userPhoneOrCard, String userPassword, HttpServletRequest request){
        Map<String, Object> map = userService.loginUser(userPhoneOrCard, userPassword);
        UserStrator userStrator = (UserStrator)map.get("userStrator");
        request.getSession().setAttribute("user",userStrator);
        return map;
    }

    @ResponseBody
    @RequestMapping("/approve")
    public Map<String ,Object> approve(String userPhone, String userCard){
        return userService.selectUserByPhoneAndCard(userPhone,userCard);
    }

    @ResponseBody
    @RequestMapping("/resetPassword")
    public Map<String ,Object> resetPassword(Integer id, String userPassword){
        return userService.resetUserPassword(userPassword,id);
    }

    @RequestMapping("/userIndex")
    public String userIndex(Map<String,Object> map,HttpServletRequest request){
        UserStrator user = (UserStrator)request.getSession().getAttribute("user");
        Integer shopSum = shopService.getShopByUser(user.getId());
        Integer orderSum = shopService.getOrderByUser(user.getId());
        map.put("shopSum",shopSum);
        map.put("orderSum",orderSum);
        return "/user/index";
    }

    @RequestMapping("/userCommodity/{kind}")
    public String userCommodity(@PathVariable("kind") Integer kind,Map<String,Object> map){
        Map<String, Object> map1 = commodityService.getAllCommodityByKind(kind);
        List<CommodityKind> kinds = kindService.getAllKinds();
        map.put("data",map1.get("data"));
        map.put("kinds",kinds);
        return "/user/commodity";
    }

    @RequestMapping("/oneSet/{id}")
    public String oneSet(@PathVariable("id") Integer id, Map<String,Object> map){
        UserStrator oneUser = userService.getOneUser(id);
        map.put("oneUser",oneUser);
        return "/user/one_set";
    }

    @ResponseBody
    @RequestMapping("/updateUser")
    public Map<String, Object> updateUser(UserStrator userStrator){
        return userService.updateUser(userStrator);
    }

    @RequestMapping("/activityList/{page}/{rows}")
    public String activityList(@PathVariable("page") Integer page, @PathVariable("rows") Integer rows , Map<String,Object> map){
        Map<String, Object> map1 = activityService.selectAllActivities(page, rows);
        map.put("data",map1.get("data"));
        map.put("page",map1.get("page"));  //当前页
        map.put("pages",map1.get("pages"));    //总页数
        map.put("total",map1.get("total"));    //总数据量
        return "/user/activity_list";
    }

    @ResponseBody
    @RequestMapping("/addDevice")
    public Map<String ,Object> addDevice(DeviceStrator deviceStrator){
        return deviceService.addDevice2(deviceStrator);
    }

    @RequestMapping("/deviceAdd")
    public String deviceAdd(){
        return "/user/repairadd";
    }

    @RequestMapping("/deviceList/{page}/{rows}/{userId}")
    public String deviceList(@PathVariable("page") Integer page, @PathVariable("rows") Integer rows , @PathVariable("userId") Integer userId , Map<String,Object> map){
        Map<String, Object> map1 = deviceService.selectAllDevices2(page, rows,userId);
        map.put("data",map1.get("data"));
        map.put("page",map1.get("page"));  //当前页
        map.put("pages",map1.get("pages"));    //总页数
        map.put("total",map1.get("total"));    //总数据量
        return "/user/repairlist";
    }

    @ResponseBody
    @RequestMapping("/joinActivity")
    public Map<String ,Object> joinActivity(Integer userId,Integer activityId){
        return activityService.joinActivity(userId,activityId);
    }

    @ResponseBody
    @RequestMapping("/cancelActivity")
    public Map<String ,Object> cancelActivity(Integer userId,Integer activityId){
        return activityService.cancelActivity(userId,activityId);
    }

    @RequestMapping("/shopping")
    public String shopping(Map<String,Object> map,HttpServletRequest request){
        UserStrator user = (UserStrator)request.getSession().getAttribute("user");
        List<Shop> shops = shopService.getAllShopBuyUser(user.getId());
        map.put("shops",shops);
        return "/user/shopping";
    }

    @ResponseBody
    @RequestMapping("/joinShop")
    public Map<String ,Object> joinShop(Integer commodityId,Integer userId){
        return shopService.insertShop(commodityId,userId);
    }

    @ResponseBody
    @RequestMapping("/shopBalance")
    public Map<String ,Object> shopBalance(Integer id){
        return shopService.shopBalance(id);
    }

    @ResponseBody
    @RequestMapping("/shopBalances")
    public Map<String ,Object> shopBalances(@RequestParam(value = "idArray[]") Integer[] idArray){
        return shopService.shopBalances(idArray);
    }

    @ResponseBody
    @RequestMapping("/deleteShop")
    public Map<String,Object> deleteShop(Integer id){
        return shopService.deleteShop(id);
    }

    @RequestMapping("/order")
    public String order(Map<String,Object> map,HttpServletRequest request){
        UserStrator user = (UserStrator)request.getSession().getAttribute("user");
        List<Order> orders = shopService.getAllOrderByUser(user.getId());
        List<Order> orders0 = orders.stream().filter((Order o) -> o.getOrderStatus().equals(0)).collect(Collectors.toList());
        List<Order> orders1 = orders.stream().filter((Order o) -> o.getOrderStatus().equals(1)).collect(Collectors.toList());
        List<Order> orders2 = orders.stream().filter((Order o) -> o.getOrderStatus().equals(2)).collect(Collectors.toList());
        map.put("orders",orders);
        map.put("orders0",orders0);
        map.put("orders1",orders1);
        map.put("orders2",orders2);
        return "/user/order";
    }


    @ResponseBody
    @RequestMapping("/deleteOrder")
    public Map<String ,Object> deleteOrder(Integer id){
        return shopService.deleteOrder(id);
    }

    @ResponseBody
    @RequestMapping("/orderYes")
    public Map<String ,Object> orderYes(Integer id){
        return shopService.orderYes(id);
    }


    @RequestMapping("/commodityOne")
    public String commodityOne(Integer id,Map<String,Object> map){
        CommodityStrator oneCommodity = commodityService.getOneCommodity(id);
        List<CommodityPicture> oneCommodityPicture = commodityService.getOneCommodityPicture(oneCommodity.getId());
        oneCommodity.setCommodityPictures(oneCommodityPicture);
        map.put("oneCommodity",oneCommodity);
        return "/user/commodityOne";
    }


    @ResponseBody
    @RequestMapping("/numAdd")
    public Map<String,Object> numAdd(Integer id,Integer num){
        return shopService.numAdd(id,num);
    }
}
