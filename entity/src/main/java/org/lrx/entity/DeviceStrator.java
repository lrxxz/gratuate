package org.lrx.entity;

import lombok.Data;

@Data
public class DeviceStrator {

    //id
    private Integer id;
    //设备名称
    private String deviceName;
    //用户名称
    private String userName;
    //联系电话
    private String userPhone;
    //设备状态
    private String deviceStatus;
    //报修时间
    private String deviceTime;
    //报修描述
    private String deviceMark;
    //用户id
    private Integer userId;
}
