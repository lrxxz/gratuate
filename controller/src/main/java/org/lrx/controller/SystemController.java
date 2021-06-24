package org.lrx.controller;


import javafx.beans.binding.ObjectExpression;
import org.lrx.entity.ActivityStrator;
import org.lrx.entity.DeviceStrator;
import org.lrx.entity.UserStrator;
import org.lrx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
public class SystemController {

    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ActivityServiceImpl activityService;

    @Autowired
    DeviceServiceImpl deviceService;

    @Autowired
    ShopServiceImpl shopService;

    //管理员登录
    @GetMapping("/adminLogin")
    public String adminLogin(){
        return "admin/adminLogin";
    }

    //用户登录
    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/logOut2")
    public String logOut2(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "user/login";
    }

    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "admin/adminLogin";
    }

    @GetMapping("/password")
    public String password(){
        return "user/password";
    }

    @GetMapping("/password2/{id}")
    public String password2(@PathVariable("id") Integer id, Map<String,Object> map){
        map.put("id",id);
        return "user/password_2";
    }

    @GetMapping("/password3")
    public String password3(){
        return "user/password_3";
    }


    @GetMapping("/adminPassword")
    public String adminPassword(){
        return "admin/password";
    }

    @GetMapping("/adminPassword2/{id}")
    public String adminPassword2(@PathVariable("id") Integer id, Map<String,Object> map){
        map.put("id",id);
        return "admin/password_2";
    }

    @GetMapping("/adminPassword3")
    public String adminPassword3(){
        return "admin/password_3";
    }

    @GetMapping("/index")
    public String index(Map<String, Object> map){
        Integer activitySum = activityService.selectActivitySum();
        Integer deviceSum = deviceService.selectDeviceSum();
        map.put("activitySum",activitySum);
        map.put("deviceSum",deviceSum);
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(Map<String, Object> map){
        Map<String, Object> map1 = userService.selectUserSum();
        List<ActivityStrator> allActivity = activityService.getAllActivity();
        List<DeviceStrator> allDevice = deviceService.getAllDevice();
        map.put("sum",map1.get("sum"));
        map.put("ownerSum",map1.get("ownerSum"));
        map.put("lesseeSum",map1.get("lesseeSum"));
        map.put("allActivity",allActivity);
        map.put("allDevice",allDevice);
        return "welcome";
    }

    @RequestMapping("/addUser")
    public String addUser(){
        return "personnel_add";
    }

    @RequestMapping("/orderList/{page}/{rows}")
    public String orderList(@PathVariable("page") Integer page,@PathVariable("rows") Integer rows,Map<String,Object> map){
        Map<String, Object> map1 = shopService.getAllOrders(page, rows);
        map.put("data",map1.get("data"));
        map.put("page",map1.get("page"));  //当前页
        map.put("pages",map1.get("pages"));    //总页数
        map.put("total",map1.get("total"));    //总数据量
        return "order_list";
    }

    @RequestMapping("/orderAddress")
    public String orderList(Integer id,Map<String,Object> map){
        UserStrator oneUser = userService.getOneUser(id);
        map.put("oneUser",oneUser);
        return "order_address";
    }

    @ResponseBody
    @RequestMapping("/orderStatus")
    public Map<String, Object> orderStatus(Integer id){
        return shopService.updateOrderStatus(id);
    }
}
