package org.lrx.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lrx.entity.AdminStrator;

import java.util.List;

/**
 * 管理员dao
 */
@Mapper
public interface AdminMapper {
    //通过id查询该管理员用户信息
    AdminStrator selectAdminStratorById(@Param("id") Integer id);

    //新增管理员用户
    Boolean insertAdmin(AdminStrator adminStrator);

    //通过电话和密码查询用户
    AdminStrator selectAdminByPhoneAndPassword(@Param("adminPhoneOrCard") String adminPhoneOrCard,@Param("adminPassword") String adminPassword);

    //通过id修改用户信息
    Boolean updateAdminById(AdminStrator adminStrator);

    //修改密码
    Boolean updateAdminPassword(@Param("adminPassword") String adminPassword,@Param("id") Integer id);

    //通过身份证信息或者电话校验管理员是否存在
    Integer selectAdminByPhoneOrCard(@Param("adminPhone") String adminPhone,@Param("adminCard") String adminCard);

    List<AdminStrator> selectAdminByPhoneOrCard2(@Param("adminPhone") String adminPhone, @Param("adminCard") String adminCard);

    Integer selectAdminByPhoneAndCard(@Param("adminPhone") String adminPhone, @Param("adminCard") String adminCard);

    List<AdminStrator> selectAllAdmin();
}
