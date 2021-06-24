package org.lrx.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lrx.entity.ActivityStrator;

import java.util.List;

@Mapper
public interface ActivityMapper {


    //新增活动
    Boolean insertActivity(ActivityStrator activityStrator);

    //删除活动
    Boolean deleteActivity(@Param("id") Integer id);

    //批量删除
    Boolean deleteActivities(List<Integer> ids);

    //通过id修改活动
    Boolean updateActivityById(ActivityStrator activityStrator);

    //查询所有的活动
    List<ActivityStrator> selectAllActivity();

    ActivityStrator selectOneActivity(@Param("id") Integer id);

    Integer selectActiveSum();

    Integer selectUserActivitySum(@Param("userId") Integer userId,@Param("activityId") Integer activityId);

    Boolean insertUserActivityOne(@Param("userId") Integer userId,@Param("activityId") Integer activityId);

    Boolean activitySumAnd(@Param("activityId") Integer activityId);

    Boolean activitySumMinus(@Param("activityId") Integer activityId);

    Boolean deleteUserActivity(@Param("userId") Integer userId,@Param("activityId") Integer activityId);

    List<Integer> selectActivityUser(@Param("activityId") Integer activityId);
}
