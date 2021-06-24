package org.lrx.controller;

import org.lrx.entity.CommodityKind;
import org.lrx.entity.CommodityStrator;
import org.lrx.service.CommodityServiceImpl;
import org.lrx.service.ImageUpload;
import org.lrx.service.KindServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    ImageUpload imageUpload;

    @Autowired
    KindServiceImpl kindService;

    @Autowired
    CommodityServiceImpl commodityService;

    @RequestMapping("/commodityList/{page}/{rows}")
    public String commodityList(@PathVariable("page") Integer page, @PathVariable("rows") Integer rows , Map<String,Object> map){
        Map<String, Object> map1 = commodityService.getAllCommodity(page, rows);
        map.put("data",map1.get("data"));
        map.put("page",map1.get("page"));  //当前页
        map.put("pages",map1.get("pages"));    //总页数
        map.put("total",map1.get("total"));    //总数据量
        return "commodity_list";
    }

    @RequestMapping("/commodityAdd")
    public String homeAdd(Map<String,Object> map){
        List<CommodityKind> allKinds = kindService.getAllKinds();
        map.put("allKinds",allKinds);
        return "commodity_add";
    }

    @ResponseBody
    @RequestMapping("/uploadImg")
    public Map<String, Object> insertUniversityFile(@RequestParam("images") MultipartFile[] images) {
        Map<String, Object> map = new HashMap<>();
        String[] pathArray = new String[images.length];
        for (int i=0;i<images.length;i++){
            String uploadPathDB = imageUpload.uploadImg(images[i]);
            pathArray[i] = uploadPathDB;
        }
        if (pathArray.length==0) {
            map.put("code", 0);
            map.put("message", "文件上传出错");
            return map;
        }
        map.put("code", 1);
        map.put("message", "文件上传成功");
        map.put("pathArray",pathArray);
        return map;
    }

    @ResponseBody
    @RequestMapping("/addCommodity")
    public Map<String, Object> addCommodity(CommodityStrator commodityStrator){
        return commodityService.insertCommodity(commodityStrator);
    }

    @ResponseBody
    @RequestMapping("/deleteCommodity")
    public Map<String,Object> deleteCommodity(Integer id){
        return commodityService.deleteCommodity(id);
    }

    @ResponseBody
    @RequestMapping("/deleteCommodities")
    public Map<String,Object> deleteCommodities(@RequestParam(value = "idArray[]") Integer[] idArray){
        return commodityService.deleteCommodities(idArray);
    }
}
