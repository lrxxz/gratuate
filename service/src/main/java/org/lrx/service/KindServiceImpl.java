package org.lrx.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.lrx.dao.KindMapper;
import org.lrx.entity.CommodityKind;
import org.lrx.entity.DeviceStrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KindServiceImpl {

    @Autowired
    KindMapper kindMapper;

    /**
     * 新增种类
     * @param kind
     * @return
     */
    public Map<String,Object> addKind(CommodityKind kind){
        Map<String,Object> map = new HashMap<>();
        if (kind==null){
            map.put("code",0);
            map.put("message","种类实体为空");
            return map;
        }
        Boolean boo = kindMapper.insertKind(kind);
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
     * 删除种类
     * @param id
     * @return
     */
    public Map<String,Object> deleteKind(Integer id){
        Map<String,Object> map = new HashMap<>();
        if(id==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = kindMapper.deleteKind(id);
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
     * 批量删除种类
     * @param idArray
     * @return
     */
    public Map<String,Object> deleteKinds(Integer[] idArray){
        List<Integer> ids = (List<Integer>) CollectionUtils.arrayToList(idArray);
        Map<String,Object> map = new HashMap<>();
        if(CollectionUtils.isEmpty(ids)){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = kindMapper.deleteKinds(ids);
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
     * 分页查询所有种类
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> selectAllKinds(Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
        Page<CommodityKind> pages = PageHelper.startPage(page, rows).doSelectPage(() -> kindMapper.selectAllKind());
        List<CommodityKind> result = pages.getResult();
        map.put("data",result);
        map.put("page", pages.getPageNum());  //当前页
        map.put("pages",pages.getPages());    //总页数
        map.put("total",pages.getTotal());    //总数据量
        return map;
    }

    /**
     * 查询全部的种类
     * @return
     */
    public List<CommodityKind> getAllKinds(){
        return kindMapper.selectAllKind();
    }

    /**
     * 查询单个商品种类
     * @param id
     * @return
     */
    public CommodityKind getOneKind(Integer id){
        return kindMapper.selectOneKind(id);
    }
}
