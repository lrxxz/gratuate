package org.lrx.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.lrx.dao.UserMapper;
import org.lrx.entity.UserStrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl {

    @Autowired
    UserMapper userMapper;

    /**
     * 用户注册
     * @param userStrator
     * @return
     */
    public Map<String,Object> registerUser(UserStrator userStrator){
        Map<String,Object> map = new HashMap<>();
        if (userStrator==null){
            map.put("code",0);
            map.put("message","用户实体为空");
            return map;
        }
        Integer count = userMapper.selectUserByPhoneOrCard(userStrator.getUserPhone());
        if (count>0){
            map.put("code",0);
            map.put("message","该用户已存在");
            return map;
        }
        Boolean boo = userMapper.insertUser(userStrator);
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
     * 用户登录
     * @param userPhoneOrCard
     * @param userPassword
     * @return
     */
    public Map<String,Object> loginUser(String userPhoneOrCard,String userPassword){
        Map<String,Object> map = new HashMap<>();
        if (userPhoneOrCard == null || userPassword == null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        UserStrator userStrator = userMapper.selectUserByPhoneAndPassword(userPhoneOrCard, userPassword);
        if (userStrator == null){
            map.put("code",0);
            map.put("message","账号或密码错误");
            return map;
        }
        map.put("code",1);
        map.put("message","登录成功");
        map.put("userStrator",userStrator);
        return map;
    }

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    public UserStrator getOneUser(Integer id){
        return userMapper.selectUserById(id);
    }

    /**
     * 修改用户信息
     * @param userStrator
     * @return
     */
    public Map<String,Object> updateUser(UserStrator userStrator){
        Map<String,Object> map = new HashMap<>();
        if (userStrator==null){
            map.put("code",0);
            map.put("message","用户实体为空");
            return map;
        }
        List<UserStrator> userStrators = userMapper.selectUserByPhoneOrCard2(userStrator.getUserPhone(), userStrator.getUserCard());
        if (!CollectionUtils.isEmpty(userStrators)){
            for (UserStrator userStrator1:userStrators) {
                if (!userStrator1.getId().equals(userStrator.getId())) {
                    map.put("code", 0);
                    map.put("message", "该用户已存在");
                    return map;
                }
            }
        }
        Boolean boo = userMapper.updateUserById(userStrator);
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
     * @param userPhone
     * @param userCard
     * @return
     */
    public Map<String,Object> selectUserByPhoneAndCard(String userPhone, String userCard){
        Map<String,Object> map = new HashMap<>();
        if (userPhone == null || userPhone == null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Integer num = userMapper.selectUserByPhoneAndCard(userPhone, userCard);
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
     * 用户重置密码
     * @param UserPassword
     * @param id
     * @return
     */
    public Map<String ,Object> resetUserPassword(String UserPassword,Integer id){
        Map<String ,Object> map = new HashMap<>();
        if(UserPassword==null || id==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = userMapper.updateUserPassword(UserPassword, id);
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
     * 删除用户
     * @param id
     * @return
     */
    public Map<String,Object> deleteUser(Integer id){
        Map<String,Object> map = new HashMap<>();
        if(id==null){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = userMapper.deleteUserById(id);
        if (!boo){
            map.put("code",0);
            map.put("message","删除用户失败");
            return map;
        }
        map.put("code",1);
        map.put("message","删除用户成功");
        return map;
    }

    /**
     * 批量删除用户
     * @param idArray
     * @return
     */
    public Map<String,Object> deleteUsers(Integer[] idArray){
        List<Integer> ids = (List<Integer>)CollectionUtils.arrayToList(idArray);
        Map<String,Object> map = new HashMap<>();
        if(CollectionUtils.isEmpty(ids)){
            map.put("code",0);
            map.put("message","数据为空");
            return map;
        }
        Boolean boo = userMapper.deleteUsers(ids);
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
     * 分页查询所有用户
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> selectAllUser(Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
        Page<UserStrator> pages = PageHelper.startPage(page, rows).doSelectPage(() -> userMapper.selectAllUser());
        List<UserStrator> result = pages.getResult();
        map.put("data",result);
        map.put("page", pages.getPageNum());  //当前页
        map.put("pages",pages.getPages());    //总页数
        map.put("total",pages.getTotal());    //总数据量
        return map;
    }

    /**
     * 统计用户数量
     * @return
     */
    public Map<String,Object> selectUserSum(){
        Map<String,Object> map = new HashMap<>();
        Integer sum = userMapper.selectUserSum();
        Integer ownerSum = userMapper.selectUserByKind("业主");
        Integer lesseeSum = userMapper.selectUserByKind("租户");
        map.put("sum",sum);
        map.put("ownerSum",ownerSum);
        map.put("lesseeSum",lesseeSum);
        return map;
    }
}
