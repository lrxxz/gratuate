package org.lrx.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.lrx.dao.DeviceMapper;
import org.lrx.entity.ActivityStrator;
import org.lrx.entity.DeviceStrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl {

    @Autowired
    DeviceMapper deviceMapper;

    /**
     * 添加保修设备
     * @param deviceStrator
     * @return
     */
    public Map<String,Object> addDevice(DeviceStrator deviceStrator){
        Map<String,Object> map = new HashMap<>();
        if (deviceStrator==null){
            map.put("code",0);
            map.put("message","设备实体为空");
            return map;
        }
        deviceStrator.setDeviceTime(LocalDate.now().toString());
        Boolean boo = deviceMapper.insertDevice(deviceStrator);
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
     * 用户添加保修设备
     * @param deviceStrator
     * @return
     */
    public Map<String,Object> addDevice2(DeviceStrator deviceStrator){
        Map<String,Object> map = new HashMap<>();
        if (deviceStrator==null){
            map.put("code",0);
            map.put("message","设备实体为空");
            return map;
        }
        deviceStrator.setDeviceTime(LocalDate.now().toString());
        Boolean boo = deviceMapper.insertDevice2(deviceStrator);
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
     * 查询设备信息
     * @param id
     * @return
     */
    public DeviceStrator getOneDevice(Integer id){
        return deviceMapper.selectOneDevice(id);
    }

    /**
     * 修改设备信息
     * @param deviceStrator
     * @return
     */
    public Map<String,Object> updateDevice(DeviceStrator deviceStrator){
        Map<String,Object> map = new HashMap<>();
        if (deviceStrator==null){
            map.put("code",0);
            map.put("message","设备实体为空");
            return map;
        }
        deviceStrator.setDeviceTime(LocalDate.now().toString());
        Boolean boo = deviceMapper.updateDeviceById(deviceStrator);
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
     * 删除设备
     * @param id
     * @return
     */
    public Map<String,Object> deleteDevice(Integer id){
        Map<String,Object> map = new HashMap<>();
        if(id==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = deviceMapper.deleteDevice(id);
        if (!boo){
            map.put("code",0);
            map.put("message","删除设备失败");
            return map;
        }
        map.put("code",1);
        map.put("message","删除设备成功");
        return map;
    }

    /**
     * 批量删除设备
     * @param idArray
     * @return
     */
    public Map<String,Object> deleteDevices(Integer[] idArray){
        List<Integer> ids = (List<Integer>) CollectionUtils.arrayToList(idArray);
        Map<String,Object> map = new HashMap<>();
        if(CollectionUtils.isEmpty(ids)){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = deviceMapper.deleteDevices(ids);
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
     * 分页查询所有设备(报修)
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> selectAllDevices(Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
        Page<DeviceStrator> pages = PageHelper.startPage(page, rows).doSelectPage(() -> deviceMapper.selectAllDevice());
        List<DeviceStrator> result = pages.getResult();
        map.put("data",result);
        map.put("page", pages.getPageNum());  //当前页
        map.put("pages",pages.getPages());    //总页数
        map.put("total",pages.getTotal());    //总数据量
        return map;
    }

    /**
     * 分页查询所有设备(用户报修)
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> selectAllDevices2(Integer page,Integer rows,Integer userId){
        Map<String,Object> map = new HashMap<>();
        Page<DeviceStrator> pages = PageHelper.startPage(page, rows).doSelectPage(() -> deviceMapper.selectAllDevice2(userId));
        List<DeviceStrator> result = pages.getResult();
        map.put("data",result);
        map.put("page", pages.getPageNum());  //当前页
        map.put("pages",pages.getPages());    //总页数
        map.put("total",pages.getTotal());    //总数据量
        return map;
    }

    /**
     * 统计报修数量
     * @return
     */
    public Integer selectDeviceSum(){
        return deviceMapper.selectDeviceSum();
    }

    /**
     * 获取所有报修
     * @return
     */
    public List<DeviceStrator> getAllDevice(){
        return deviceMapper.selectAllDevice();
    }
}
