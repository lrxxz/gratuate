package org.lrx.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.lrx.dao.CommodityMapper;
import org.lrx.entity.CommodityKind;
import org.lrx.entity.CommodityPicture;
import org.lrx.entity.CommodityStrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class CommodityServiceImpl {

    @Autowired
    CommodityMapper commodityMapper;

    @Autowired
    KindServiceImpl kindService;

    /**
     * 新增商品
     * @param commodityStrator
     * @return
     */
    public Map<String,Object> insertCommodity(CommodityStrator commodityStrator){
        Map<String, Object> map = new HashMap<>();
        if (commodityStrator == null){
            map.put("code", 0);
            map.put("message", "实体为空");
            return map;
        }
        if (commodityStrator.getPictureAddress().length == 0){
            map.put("code", 0);
            map.put("message", "请上传图片");
            return map;
        }
        //上传商品，然后拿到商品id
        Boolean boo = commodityMapper.insertCommodity(commodityStrator);
        if (!boo){
            map.put("code", 0);
            map.put("message", "新增商品失败");
            return map;
        }
        Integer commodityId = commodityStrator.getId();
        List<CommodityPicture> pictures = new ArrayList<>();
        for (String path:commodityStrator.getPictureAddress()){
            CommodityPicture picture = new CommodityPicture();
            picture.setCommodityId(commodityId);
            picture.setPictureAddress(path);
            pictures.add(picture);
        }
        Boolean aBoolean = commodityMapper.insertPicture(pictures);
        if (!aBoolean){
            map.put("code", 0);
            map.put("message", "图片存入数据库出错");
            return map;
        }
        map.put("code", 1);
        map.put("message", "新增商品成功");
        return map;
    }

    /**
     * 查询全部的商品信息
     * @param page
     * @param rows
     * @return
     */
    public Map<String,Object> getAllCommodity(Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
        Page<CommodityStrator> pages = PageHelper.startPage(page, rows).doSelectPage(() -> commodityMapper.selectAllCommodity());
        List<CommodityStrator> result = pages.getResult();
        Iterator<CommodityStrator> iterator = result.iterator();
        while (iterator.hasNext()){
            CommodityStrator next = iterator.next();
            List<CommodityPicture> pictures = commodityMapper.selectPicture(next.getId());
            CommodityKind oneKind = kindService.getOneKind(next.getKindId());
            next.setCommodityPictures(pictures);
            next.setCommodityKind(oneKind);
        }
        map.put("data",result);
        map.put("page", pages.getPageNum());  //当前页
        map.put("pages",pages.getPages());    //总页数
        map.put("total",pages.getTotal());    //总数据量
        return map;
    }

    /**
     * 查询全部的商品信息
     * @return
     */
    public Map<String,Object> getAllCommodityByKind(Integer kind){
        Map<String,Object> map = new HashMap<>();
        List<CommodityStrator> result = null;
        if (!kind.equals(1)){
            result = commodityMapper.selectAllCommodityByKind(kind);
        }else {
            result = commodityMapper.selectAllCommodity();
        }
        Iterator<CommodityStrator> iterator = result.iterator();
        while (iterator.hasNext()){
            CommodityStrator next = iterator.next();
            List<CommodityPicture> pictures = commodityMapper.selectPicture(next.getId());
            CommodityKind oneKind = kindService.getOneKind(next.getKindId());
            next.setCommodityPictures(pictures);
            next.setCommodityKind(oneKind);
        }
        map.put("data",result);
        return map;
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    public Map<String,Object> deleteCommodity(Integer id){
        Map<String,Object> map = new HashMap<>();
        if(id==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = commodityMapper.deleteCommodity(id);
        Boolean boo1 = commodityMapper.deletePicture(id);
        if (!boo || !boo1){
            map.put("code",0);
            map.put("message","删除商品失败");
            return map;
        }
        map.put("code",1);
        map.put("message","删除商品成功");
        return map;
    }

    /**
     * 批量删除商品
     * @param idArray
     * @return
     */
    public Map<String,Object> deleteCommodities(Integer[] idArray){
        List<Integer> ids = (List<Integer>) CollectionUtils.arrayToList(idArray);
        Map<String,Object> map = new HashMap<>();
        if(CollectionUtils.isEmpty(ids)){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = commodityMapper.deleteCommodities(ids);
        Boolean boo1 = commodityMapper.deletePictures(ids);
        if (!boo || !boo1){
            map.put("code",0);
            map.put("message","删除失败");
            return map;
        }
        map.put("code",1);
        map.put("message","删除成功");
        return map;
    }

    /**
     * 查询单个商品信息
     * @param id
     * @return
     */
    public CommodityStrator getOneCommodity(Integer id){
        return commodityMapper.selectCommodityOne(id);
    }

    /**
     * 查询某个商品的图片信息
     * @param id
     * @return
     */
    public List<CommodityPicture> getOneCommodityPicture(Integer id){
        return commodityMapper.selectPicture(id);
    }


}
