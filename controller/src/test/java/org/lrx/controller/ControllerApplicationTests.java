package org.lrx.controller;

import org.junit.jupiter.api.Test;
import org.lrx.entity.UserStrator;
import org.lrx.service.AdminServiceImpl;
import org.lrx.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SpringBootTest
class ControllerApplicationTests {

    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    UserServiceImpl userService;

    @Test
    void contextLoads() {
        System.out.println();
    }

}
