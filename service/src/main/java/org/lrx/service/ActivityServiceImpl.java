package org.lrx.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.lrx.dao.ActivityMapper;
import org.lrx.dao.UserMapper;
import org.lrx.entity.ActivityStrator;
import org.lrx.entity.UserStrator;
import org.omg.CORBA.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 新增活动
     * @param activityStrator
     * @return
     */
    public Map<String,Object> addActivity(ActivityStrator activityStrator){
        Map<String,Object> map = new HashMap<>();
        if (activityStrator==null){
            map.put("code",0);
            map.put("message","活动实体为空");
            return map;
        }
        activityStrator.setActivitySum(0);
        Boolean boo = activityMapper.insertActivity(activityStrator);
        if (!boo){
            map.put("code",0);
            map.put("message","新增失败");
            return map;
        }
        map.put("code",1);
        map.put("message","新增成功");
        return map;
    }

    /**
     * 查询活动信息
     * @param id
     * @return
     */
    public ActivityStrator getOneActivity(Integer id){
        return activityMapper.selectOneActivity(id);
    }

    /**
     * 修改活动信息
     * @param activityStrator
     * @return
     */
    public Map<String,Object> updateActivity(ActivityStrator activityStrator){
        Map<String,Object> map = new HashMap<>();
        if (activityStrator==null){
            map.put("code",0);
            map.put("message","活动实体为空");
            return map;
        }
        Boolean boo = activityMapper.updateActivityById(activityStrator);
        if (!boo){
            map.put("code",0);
            map.put("message","修改失败");
            return map;
        }
        map.put("code",1);
        map.put("message","修改成功");
        return map;
    }

    /**
     * 删除活动
     * @param id
     * @return
     */
    public Map<String,Object> deleteActivity(Integer id){
        Map<String,Object> map = new HashMap<>();
        if(id==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = activityMapper.deleteActivity(id);
        if (!boo){
            map.put("code",0);
            map.put("message","删除活动失败");
            return map;
        }
        map.put("code",1);
        map.put("message","删除活动成功");
        return map;
    }

    /**
     * 批量删除活动
     * @param idArray
     * @return
     */
    public Map<String,Object> deleteActivities(Integer[] idArray){
        List<Integer> ids = (List<Integer>)CollectionUtils.arrayToList(idArray);
        Map<String,Object> map = new HashMap<>();
        if(CollectionUtils.isEmpty(ids)){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = activityMapper.deleteActivities(ids);
        if (!boo){
            map.put("code",0);
            map.put("message","删除失败");
            return map;
        }
        map.put("code",1);
        map.put("message","删除成功");
        return map;
    }

    /**
     * 分页查询所有活动
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> selectAllActivities(Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
        Page<ActivityStrator> pages = PageHelper.startPage(page, rows).doSelectPage(() -> activityMapper.selectAllActivity());
        List<ActivityStrator> result = pages.getResult();
        map.put("data",result);
        map.put("page", pages.getPageNum());  //当前页
        map.put("pages",pages.getPages());    //总页数
        map.put("total",pages.getTotal());    //总数据量
        return map;
    }

    /**
     * 统计活动数量
     * @return
     */
    public Integer selectActivitySum(){
        return activityMapper.selectActiveSum();
    }

    /**
     * 获取所有活动
     * @return
     */
    public List<ActivityStrator> getAllActivity(){
        return activityMapper.selectAllActivity();
    }

    /**
     * 用户参加活动
     * @param userId
     * @param activityId
     * @return
     */
    public Map<String,Object> joinActivity(Integer userId,Integer activityId){
        Map<String, Object> map = new HashMap<>();
        if (userId == null || activityId == null){
            map.put("code",0);
            map.put("message","传值不能为空");
            return map;
        }
        Integer num = activityMapper.selectUserActivitySum(userId, activityId);
        if (num>0){
            map.put("code",0);
            map.put("message","该活动你已经参加");
            return map;
        }
        Boolean aBoolean = activityMapper.insertUserActivityOne(userId, activityId);
        if (!aBoolean){
            map.put("code",0);
            map.put("message","写入数据库失败");
            return map;
        }
        activityMapper.activitySumAnd(activityId);
        map.put("code",1);
        map.put("message","参加成功");
        return map;
    }

    /**
     * 用户取消参加活动
     * @param userId
     * @param activityId
     * @return
     */
    public Map<String,Object> cancelActivity(Integer userId,Integer activityId){
        Map<String, Object> map = new HashMap<>();
        if (userId == null || activityId == null){
            map.put("code",0);
            map.put("message","传值不能为空");
            return map;
        }
        Integer num = activityMapper.selectUserActivitySum(userId, activityId);
        if (num<=0){
            map.put("code",0);
            map.put("message","您没参加该活动");
            return map;
        }
        Boolean aBoolean = activityMapper.deleteUserActivity(userId, activityId);
        if (!aBoolean){
            map.put("code",0);
            map.put("message","数据库删除失败");
            return map;
        }
        activityMapper.activitySumMinus(activityId);
        map.put("code",1);
        map.put("message","取消参加成功");
        return map;
    }

    /**
     * 查询某个活动的参加人
     * @param activityId
     * @return
     */
    public List<UserStrator> selectActivityUser(Integer activityId){
        if (activityId == null) {
            return null;
        }
        List<Integer> list = activityMapper.selectActivityUser(activityId);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        List<UserStrator> userStrators = userMapper.selectUserByIds(list);
        return userStrators;
    }
}
