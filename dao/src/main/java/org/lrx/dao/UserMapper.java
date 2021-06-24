package org.lrx.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lrx.entity.UserStrator;

import java.util.List;

@Mapper
public interface UserMapper {

    //通过id查询用户信息
    UserStrator selectUserById(@Param("id") Integer id);

    //新增用户
    Boolean insertUser(UserStrator userStrator);

    //通过电话和密码查询用户
    UserStrator selectUserByPhoneAndPassword(@Param("userPhoneOrCard") String userPhoneOrCard,@Param("userPassword") String userPassword);

    //通过id修改用户信息
    Boolean updateUserById(UserStrator userStrator);

    //修改密码
    Boolean updateUserPassword(@Param("userPassword") String userPassword,@Param("id") Integer id);

    //查询全部用户
    List<UserStrator> selectAllUser();

    //批量删除用户
    Boolean deleteUsers(List<Integer> ids);

    //通过id删除用户
    Boolean deleteUserById(@Param("id")Integer id);

    //通过身份证信息或者电话校验用户是否存在
    Integer selectUserByPhoneOrCard(@Param("userPhone") String userPhone);

    List<UserStrator> selectUserByPhoneOrCard2(@Param("userPhone") String userPhone, @Param("userCard") String userCard);

    Integer selectUserByPhoneAndCard(@Param("userPhone") String userPhone, @Param("userCard") String userCard);

    Integer selectUserSum();

    Integer selectUserByKind(@Param("userKind") String userKind);

    List<UserStrator> selectUserByIds(List<Integer> ids);
}
