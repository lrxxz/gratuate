package org.lrx.controller;

import org.lrx.entity.DeviceStrator;
import org.lrx.service.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceServiceImpl deviceService;

    @ResponseBody
    @RequestMapping("/addDevice")
    public Map<String ,Object> addDevice(DeviceStrator deviceStrator){
        return deviceService.addDevice(deviceStrator);
    }

    @RequestMapping("/deviceAdd")
    public String deviceAdd(){
        return "repairadd";
    }

    @RequestMapping("/updateDevice")
    public String updateDevice(Integer id, Map<String,Object> map){
        DeviceStrator oneDevice = deviceService.getOneDevice(id);
        map.put("oneDevice",oneDevice);
        return "repairupdate";
    }

    @ResponseBody
    @RequestMapping("/updateDevice2")
    public Map<String, Object> updateDevice2(DeviceStrator deviceStrator){
        return deviceService.updateDevice(deviceStrator);
    }

    @ResponseBody
    @RequestMapping("/deleteDevice")
    public Map<String,Object> deleteDevice(Integer id){
        return deviceService.deleteDevice(id);
    }

    @ResponseBody
    @RequestMapping("/deleteDevices")
    public Map<String,Object> deleteDevices(@RequestParam(value = "idArray[]") Integer[] idArray){
        return deviceService.deleteDevices(idArray);
    }

    @RequestMapping("/deviceList/{page}/{rows}")
    public String deviceList(@PathVariable("page") Integer page, @PathVariable("rows") Integer rows , Map<String,Object> map){
        Map<String, Object> map1 = deviceService.selectAllDevices(page, rows);
        map.put("data",map1.get("data"));
        map.put("page",map1.get("page"));  //当前页
        map.put("pages",map1.get("pages"));    //总页数
        map.put("total",map1.get("total"));    //总数据量
        return "repairlist";
    }
}
