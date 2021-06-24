package org.lrx.controller;

import org.lrx.entity.ActivityStrator;
import org.lrx.entity.UserStrator;
import org.lrx.service.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    ActivityServiceImpl activityService;

    @ResponseBody
    @RequestMapping("/addActivity")
    public Map<String ,Object> addActivity(ActivityStrator activityStrator){
        return activityService.addActivity(activityStrator);
    }

    @RequestMapping("/activityAdd")
    public String activityAdd(){
        return "activity_add";
    }

    @RequestMapping("/updateActivity")
    public String updateActivity(Integer id, Map<String,Object> map){
        ActivityStrator oneActivity = activityService.getOneActivity(id);
        map.put("oneActivity",oneActivity);
        return "activity_update";
    }

    @ResponseBody
    @RequestMapping("/updateActivity2")
    public Map<String, Object> updateActivity2(ActivityStrator activityStrator){
        return activityService.updateActivity(activityStrator);
    }

    @ResponseBody
    @RequestMapping("/deleteActivity")
    public Map<String,Object> deleteActivity(Integer id){
        return activityService.deleteActivity(id);
    }

    @ResponseBody
    @RequestMapping("/deleteActivities")
    public Map<String,Object> deleteActivities(@RequestParam(value = "idArray[]") Integer[] idArray){
        return activityService.deleteActivities(idArray);
    }

    @RequestMapping("/activityList/{page}/{rows}")
    public String activityList(@PathVariable("page") Integer page, @PathVariable("rows") Integer rows , Map<String,Object> map){
        Map<String, Object> map1 = activityService.selectAllActivities(page, rows);
        map.put("data",map1.get("data"));
        map.put("page",map1.get("page"));  //当前页
        map.put("pages",map1.get("pages"));    //总页数
        map.put("total",map1.get("total"));    //总数据量
        return "activity_list";
    }

    @RequestMapping("/activityUsers")
    public String activityUsers(Integer id,Map<String,Object> map){
        List<UserStrator> userStrators = activityService.selectActivityUser(id);
        map.put("userStrators",userStrators);
        return "admin/activityUsers";
    }
}
