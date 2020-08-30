package com.java.informationstatistic.controller;

import com.java.informationstatistic.service.ConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 视图控制层
 *
 * @author luyu
 * @since 20200807
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
@Controller
public class ViewController {
    @Resource
    private ConfigService configService;

    @GetMapping("/dataInit")
    public String initData(Model model){
        List<String> pts = configService.findPlatformFlag();
        model.addAttribute("pts",pts);
        return "dataInit";
    }

    @GetMapping("/dataCount")
    public String dataCount(Model model){
        List<String> pts = configService.findPlatformFlag();
        model.addAttribute("pts",pts);
        return "dataCount";
    }

    @GetMapping("/systemConfig")
    public String systemConfig(Model model){
        List<String> pts = configService.findPlatformFlag();
        model.addAttribute("pts",pts);
        return "systemConfig";
    }

    @GetMapping("/platformManage")
    public String platformManage(){
        return "platformManage";
    }
}
