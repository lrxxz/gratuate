package org.lrx.entity;

import lombok.Data;

@Data
public class UserStrator {
    private Integer id;
    //用户姓名
    private String userName;
    //用户电话
    private String userPhone;
    //用户性别
    private String userSex;
    //用户身份证信息
    private String userCard;
    //用户所住单元
    private String userCell;
    //用户居住楼号
    private String userFloor;
    //用户门牌号
    private String userHouse;
    //用户住址
    private String userAddress;
    //用户登录密码
    private String userPassword;
    //用户类别
    private String userKind;
    //用户人口数
    private Integer userSum;
}
