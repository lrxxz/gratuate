package org.lrx.entity;


import lombok.Data;

@Data
public class AdminStrator {
   private Integer id;
   //管理员姓名
   private String adminName;
   //管理员性别
   private String adminSex;
   //管理员电话
   private String adminPhone;
   //管理员身份证信息
   private String adminCard;
   //管理员住址
   private String adminAddress;
   //登录密码
   private String adminPassword;
}
