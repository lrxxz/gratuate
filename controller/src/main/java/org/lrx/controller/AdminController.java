package org.lrx.controller;

import org.lrx.entity.AdminStrator;
import org.lrx.entity.UserStrator;
import org.lrx.service.AdminServiceImpl;
import org.lrx.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    UserServiceImpl userService;

    @ResponseBody
    @RequestMapping("/login")
    public Map<String ,Object> login(String adminPhoneOrCard, String adminPassword, HttpServletRequest request){
        Map<String, Object> map = adminService.loginAdmin(adminPhoneOrCard, adminPassword);
        AdminStrator adminStrator = (AdminStrator)map.get("adminStrator");
        request.getSession().setAttribute("admin",adminStrator);
        return map;
    }

    @ResponseBody
    @RequestMapping("/register")
    public Map<String ,Object> register(AdminStrator adminStrator){
        return adminService.registerAdmin(adminStrator);
    }

    @ResponseBody
    @RequestMapping("/approve")
    public Map<String ,Object> approve(String adminPhone, String adminCard){
        return adminService.selectAdminByPhoneAndCard(adminPhone,adminCard);
    }

    @ResponseBody
    @RequestMapping("/resetPassword")
    public Map<String ,Object> resetPassword(Integer id, String adminPassword){
        return adminService.resetAdminPassword(adminPassword,id);
    }


    @RequestMapping("/userList/{page}/{rows}")
    public String userList(@PathVariable("page") Integer page,@PathVariable("rows") Integer rows , Map<String,Object> map){
        Map<String, Object> map1 = userService.selectAllUser(page, rows);
        map.put("data",map1.get("data"));
        map.put("page",map1.get("page"));  //当前页
        map.put("pages",map1.get("pages"));    //总页数
        map.put("total",map1.get("total"));    //总数据量
        return "personnel_list";
    }

    @ResponseBody
    @RequestMapping("/addUser")
    public Map<String,Object> addUser(UserStrator userStrator){
        return userService.registerUser(userStrator);
    }

    @ResponseBody
    @RequestMapping("/deleteUser")
    public Map<String,Object> deleteUser(Integer id){
        return userService.deleteUser(id);
    }

    @ResponseBody
    @RequestMapping("/deleteUsers")
    public Map<String,Object> deleteUsers(@RequestParam(value = "idArray[]") Integer[] idArray){
        return userService.deleteUsers(idArray);
    }

    @RequestMapping("/updateUser")
    public String updateUser(Integer id, Map<String,Object> map){
        UserStrator oneUser = userService.getOneUser(id);
        map.put("oneUser",oneUser);
        return "personnel_update";
    }

    @ResponseBody
    @RequestMapping("/updateUser2")
    public Map<String, Object> updateUser2(UserStrator userStrator){
       return userService.updateUser(userStrator);
    }

    @RequestMapping("/oneSet/{id}")
    public String oneSet(@PathVariable("id") Integer id, Map<String,Object> map){
        AdminStrator oneAdmin = adminService.getOneAdmin(id);
        map.put("oneAdmin",oneAdmin);
        return "one_set";
    }

    @ResponseBody
    @RequestMapping("/updateAdmin")
    public Map<String, Object> updateAdmin(AdminStrator adminStrator){
        return adminService.updateAdmin(adminStrator);
    }

    @RequestMapping("/adminList/{page}/{rows}")
    public String adminList(@PathVariable("page") Integer page,@PathVariable("rows") Integer rows , Map<String,Object> map){
        Map<String, Object> map1 = adminService.selectAllAdmin(page, rows);
        map.put("data",map1.get("data"));
        map.put("page",map1.get("page"));  //当前页
        map.put("pages",map1.get("pages"));    //总页数
        map.put("total",map1.get("total"));    //总数据量
        return "adminlist";
    }

}
