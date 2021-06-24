package org.lrx.entity;

import lombok.Data;

@Data
public class ActivityStrator {

    //ID
    private Integer id;
    //活动名称
    private String activityName;
    //活动地址
    private String activityAddress;
    //活动主办方单位
    private String activityUnit;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //活动状态
    private String activityStatus;
    //活动描述
    private String activityMessage;
    //参加活动的人数
    private Integer activitySum;
}
