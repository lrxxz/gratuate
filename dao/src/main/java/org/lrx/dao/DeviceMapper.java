package org.lrx.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lrx.entity.DeviceStrator;

import java.util.List;

@Mapper
public interface DeviceMapper {

    //新增设备
    Boolean insertDevice(DeviceStrator deviceStrator);

    //新增设备(用户)
    Boolean insertDevice2(DeviceStrator deviceStrator);

    //删除设备
    Boolean deleteDevice(@Param("id") Integer id);

    //批量删除
    Boolean deleteDevices(List<Integer> ids);

    //通过id修改设备
    Boolean updateDeviceById(DeviceStrator deviceStrator);

    //查询所有的设备
    List<DeviceStrator> selectAllDevice();

    List<DeviceStrator> selectAllDevice2(@Param("userId") Integer userId);

    DeviceStrator selectOneDevice(@Param("id") Integer id);

    Integer selectDeviceSum();
}
