package com.java.informationstatistic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 默认admin用户登录
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020728
 */
@Controller
public class LoginController {
    /**
     * 默认用户名和密码
     */
    private static final String NAME = "admin";
    private static final String PASS = "admin";

    /**
     * 默认登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("/login/check")
    @ResponseBody
    public String loginIndex(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        if (NAME.equals(userName) && PASS.equals(password)) {
            return "1";
        }
        return "0";
    }

    /**
     * 进入主页面
     *
     * @return
     */
    @GetMapping("/login")
    public String intoIndex() {
        return "login";
    }

    @GetMapping("/index")
    public String goToIndex() {
        return "index";
    }
}
