package org.lrx.controller;

import org.lrx.entity.CommodityKind;
import org.lrx.service.KindServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/kind")
public class KindController {

    @Autowired
    KindServiceImpl kindService;

    @ResponseBody
    @RequestMapping("/addKind")
    public Map<String ,Object> addKind(CommodityKind kind){
        return kindService.addKind(kind);
    }

    @RequestMapping("/kindAdd")
    public String kindAdd(){
        return "kind_add";
    }


    @ResponseBody
    @RequestMapping("/deleteKind")
    public Map<String,Object> deleteKind(Integer id){
        return kindService.deleteKind(id);
    }

    @ResponseBody
    @RequestMapping("/deleteKinds")
    public Map<String,Object> deleteKinds(@RequestParam(value = "idArray[]") Integer[] idArray){
        return kindService.deleteKinds(idArray);
    }

    @RequestMapping("/kindList/{page}/{rows}")
    public String kindList(@PathVariable("page") Integer page, @PathVariable("rows") Integer rows , Map<String,Object> map){
        Map<String, Object> map1 = kindService.selectAllKinds(page, rows);
        map.put("data",map1.get("data"));
        map.put("page",map1.get("page"));  //当前页
        map.put("pages",map1.get("pages"));    //总页数
        map.put("total",map1.get("total"));    //总数据量
        return "kind_list";
    }
}
