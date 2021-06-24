package org.lrx.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.lrx.dao.AdminMapper;
import org.lrx.entity.AdminStrator;
import org.lrx.entity.UserStrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AdminServiceImpl {

    @Autowired
    AdminMapper adminMapper;

    /**
     * 管理员注册
     * @param adminStrator
     * @return
     */
    public Map<String,Object> registerAdmin(AdminStrator adminStrator){
        Map<String,Object> map = new HashMap<>();
        if (adminStrator==null){
            map.put("code",0);
            map.put("message","管理员实体为空");
            return map;
        }
        Integer count = adminMapper.selectAdminByPhoneOrCard(adminStrator.getAdminPhone(), adminStrator.getAdminCard());
        if (count>0){
            map.put("code",0);
            map.put("message","该手机号或身份证已注册");
            return map;
        }
        Boolean boo = adminMapper.insertAdmin(adminStrator);
        if (!boo){
            map.put("code",0);
            map.put("message","注册失败");
            return map;
        }
        map.put("code",1);
        map.put("message","注册成功");
        return map;
    }

    /**
     * 管理员登录
     * @param adminPhoneOrCard
     * @param adminPassword
     * @return
     */
    public Map<String,Object> loginAdmin(String adminPhoneOrCard,String adminPassword){
        Map<String,Object> map = new HashMap<>();
        if (adminPassword == null || adminPhoneOrCard == null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        AdminStrator adminStrator = adminMapper.selectAdminByPhoneAndPassword(adminPhoneOrCard, adminPassword);
        if (adminStrator == null){
            map.put("code",0);
            map.put("message","账号或密码错误");
            return map;
        }
        map.put("code",1);
        map.put("message","登录成功");
        map.put("adminStrator",adminStrator);
        return map;
    }

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    public AdminStrator getOneAdmin(Integer id){
        return adminMapper.selectAdminStratorById(id);
    }

    /**
     * 修改用户信息
     * @param adminStrator
     * @return
     */
    public Map<String,Object> updateAdmin(AdminStrator adminStrator){
        Map<String,Object> map = new HashMap<>();
        if (adminStrator==null){
            map.put("code",0);
            map.put("message","管理员实体为空");
            return map;
        }
        List<AdminStrator> adminStratorList = adminMapper.selectAdminByPhoneOrCard2(adminStrator.getAdminPhone(), adminStrator.getAdminCard());
        if (!CollectionUtils.isEmpty(adminStratorList)){
            for (AdminStrator adminStrator1:adminStratorList) {
                if (!adminStrator1.getId().equals(adminStrator.getId())) {
                    map.put("code", 0);
                    map.put("message", "该管理员已存在");
                    return map;
                }
            }
        }
        Boolean boo = adminMapper.updateAdminById(adminStrator);
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
     * 修改密码前认证
     * @param adminPhone
     * @param adminCard
     * @return
     */
    public Map<String,Object> selectAdminByPhoneAndCard(String adminPhone, String adminCard){
        Map<String,Object> map = new HashMap<>();
        if (adminPhone == null || adminCard == null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Integer num = adminMapper.selectAdminByPhoneAndCard(adminPhone, adminCard);
        if (num == null){
            map.put("code",0);
            map.put("message","认证失败");
            return map;
        }
        map.put("code",1);
        map.put("message","认证成功");
        map.put("id",num);
        return map;
    }

    /**
     * 管理员重置密码
     * @param adminPassword
     * @param id
     * @return
     */
    public Map<String ,Object> resetAdminPassword(String adminPassword,Integer id){
        Map<String ,Object> map = new HashMap<>();
        if(adminPassword==null || id==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = adminMapper.updateAdminPassword(adminPassword, id);
        if (!boo){
            map.put("code",0);
            map.put("message","重置密码失败");
            return map;
        }
        map.put("code",1);
        map.put("message","重置密码成功");
        return map;
    }

    /**
     * 分页查询所有管理员
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> selectAllAdmin(Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
        Page<UserStrator> pages = PageHelper.startPage(page, rows).doSelectPage(() -> adminMapper.selectAllAdmin());
        List<UserStrator> result = pages.getResult();
        map.put("data",result);
        map.put("page", pages.getPageNum());  //当前页
        map.put("pages",pages.getPages());    //总页数
        map.put("total",pages.getTotal());    //总数据量
        return map;
    }

}
