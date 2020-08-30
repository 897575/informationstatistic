package com.java.informationstatistic.controller;

import com.java.informationstatistic.model.PlatformInfo;
import com.java.informationstatistic.model.PlatformTableInfo;
import com.java.informationstatistic.model.RelateTableInfo;
import com.java.informationstatistic.service.CarPostService;
import com.java.informationstatistic.service.ConfigService;
import com.java.informationstatistic.service.TagPostService;
import com.java.informationstatistic.tools.DataUtil;
import com.java.informationstatistic.tools.StringInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 配置控制层
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 20200822
 */
@RestController
public class PlatformConfigController {

    @Resource
    private ConfigService configService;
    @Resource
    private CarPostService carPostService;
    @Resource
    private TagPostService tagPostService;

    @PostMapping("/config/query")
    public Map<String, Object> queryPlaformConfig(Integer pageSize, Integer pageNumber) {
        if (pageSize == null || pageNumber == null || pageSize == 0 || pageNumber == 0) {
            return new HashMap<>();
        }
        Map<String, Object> resultMap = new HashMap<>();
        int total = configService.findPlatformCount();
        if (total < 1) {
            return new HashMap<>();
        }
        int firstIndex = (pageNumber - 1) * pageSize;
        Map<String, String> param = new HashMap<>();
        param.put("firstIndex", firstIndex + "");
        param.put("pageSize", pageSize + "");
        resultMap.put("total", total);
        resultMap.put("rows", configService.findPlatformInfo(param));
        return resultMap;
    }

    @PostMapping("/config/delete")
    public String deletePlaformConfig(String[] ids) {
        if (ids.length == 0 || ids == null) {
            return StringInfo.TWO + "";
        }
        for (int i = 0; i < ids.length; i++) {
            ids[i] = ids[i].replace("[", "").replace("]", "");
        }
        int result = configService.deletePlatformInfos(Arrays.asList(ids));
        if (result > 0) {
            return StringInfo.ONE + "";
        } else {
            return StringInfo.TWO + "";
        }
    }

    @PostMapping("/config/add")
    public String insertPlatformInfo(PlatformInfo platformInfo) {
        //判断是否有数据
        if (platformInfo == null || platformInfo.getPlatformFlag() == null || platformInfo.getPlatformName() == null ||
                "".equals(platformInfo.getPlatformName()) || "".equals(platformInfo.getPlatformFlag())) {
            return "参数缺失";
        }
        int total = configService.findPlatformByFlag(platformInfo.getPlatformFlag());
        if (total > 0) {
            return "数据已存在";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        platformInfo.setCreateTime(sdf.format(new Date()));
        configService.insertPlatformInfo(platformInfo);
        return StringInfo.ONE + "";
    }

    @PostMapping("/config/edit")
    public String editPlatformInfo(PlatformInfo platformInfo) {
        if (platformInfo == null || platformInfo.getPlatformFlag() == null || platformInfo.getPlatformName() == null ||
                "".equals(platformInfo.getPlatformName()) || "".equals(platformInfo.getPlatformFlag())) {
            return "参数缺失";
        }
        int isExist = configService.findPlatformByFlag(platformInfo.getPlatformFlag());
        if (isExist > 0) {
            return "该平台代码已经存在";
        }
        configService.editPlatformInfo(platformInfo);
        return StringInfo.ONE + "";
    }

    @PostMapping("/config/queryTable")
    public Map<String, Object> queryPlatformTableInfo(Integer pageNumber, Integer pageSize) {
        if (pageSize == null || pageNumber == null || pageSize == 0 || pageNumber == 0) {
            return new HashMap<>();
        }
        Map<String, Object> resultMap = new HashMap<>();
        int total = configService.findPlatformTableCount();
        if (total < 1) {
            return new HashMap<>();
        }
        int firstIndex = (pageNumber - 1) * pageSize;
        Map<String, String> param = new HashMap<>();
        param.put("firstIndex", firstIndex + "");
        param.put("pageSize", pageSize + "");
        List<Map<String, String>> platformTableInfos = configService.findPlatformTableInfoLimit(param);
        if (platformTableInfos == null || platformTableInfos.isEmpty()) {
            return new HashMap<>();
        }
        for (int i = 0; i < platformTableInfos.size(); i++) {
            String tableName = platformTableInfos.get(i).get("carTableName");
            Map<String, String> relateTableInfo = configService.findPlatRelateTableInfo(tableName);
            if (relateTableInfo == null || relateTableInfo.isEmpty()) {
                continue;
            }
            platformTableInfos.get(i).putAll(relateTableInfo);
        }
        resultMap.put("total", total);
        resultMap.put("rows", platformTableInfos);
        return resultMap;
    }

    @PostMapping("/config/addTable")
    public String addPlatformTableInfo(String platformFlags, String carplatformName, String tagPlatformName,
                                       String carRelateTableName, String tagRelateTableName) {
        if (platformFlags == null || carplatformName == null || tagPlatformName == null
                || "".equals(platformFlags) || "".equals(carplatformName) || "".equals(tagPlatformName)) {
            return "参数缺失";
        }
        if(carRelateTableName!=null&&!"".equals(carRelateTableName)&&(tagRelateTableName==null||"".equals(tagRelateTableName))){
            return "关联表的标签表不可以为空";
        }
        platformFlags = platformFlags.replace("，", ",");
        //判断数据是否在表中已经存在
        int isExist = configService.findPlatformTableCountbyTableName(carplatformName);
        if (isExist > 0) {
            return "数据已经存在";
        }
        String[] platforms = platformFlags.split(",");
        String isTableExist = DataUtil.isTableExist(carPostService,tagPostService,platforms,carplatformName,tagPlatformName,carRelateTableName,tagRelateTableName);
        if(!"".equals(isTableExist)){
            return isTableExist;
        }
        PlatformTableInfo platformTableInfo = new PlatformTableInfo();
        platformTableInfo.setPlatformFlags(platformFlags);
        platformTableInfo.setCarTableName(carplatformName);
        platformTableInfo.setTagTableName(tagPlatformName);
        configService.insertPlatformTbaleInfo(platformTableInfo);
        if(carRelateTableName!=null&&tagRelateTableName!=null&&!"".equals(carRelateTableName)&&!"".equals(tagRelateTableName)){
            int isRelateExist = configService.findplatRelateCountByTableName(carplatformName);
            if (isRelateExist < 1) {
                RelateTableInfo relateTableInfo = new RelateTableInfo();
                relateTableInfo.setTableName(carplatformName);
                relateTableInfo.setRelateCarTableName(carRelateTableName);
                relateTableInfo.setRelateTagTableName(tagRelateTableName);
                //插入关联表信息
                configService.insertPlatformRelateTableInfo(relateTableInfo);
            }
        }
        return StringInfo.ONE + "";
    }

    @PostMapping("/config/deleteTable")
    public String deletePlatformInfo(String[] tableNames){
        if(tableNames==null||tableNames.length==0){
            return "参数错误";
        }
        for (int i = 0; i < tableNames.length; i++) {
            tableNames[i] = tableNames[i].replace("[", "").replace("]", "");
        }
        configService.deletePlatformTableInfo(Arrays.asList(tableNames));
        configService.deletePlatformRelateTableInfo(Arrays.asList(tableNames));
        return StringInfo.ONE+"";
    }

    @PostMapping("/config/editTable")
    public String editPlatformInfo(String platformFlags, String carplatformName, String tagPlatformName,
                                   String carRelateTableName, String tagRelateTableName){
        if (platformFlags == null || carplatformName == null || tagPlatformName == null
                || "".equals(platformFlags) || "".equals(carplatformName) || "".equals(tagPlatformName)) {
            return "参数缺失";
        }
        if(carRelateTableName!=null&&!"".equals(carRelateTableName)&&(tagRelateTableName==null||"".equals(tagRelateTableName))){
            return "关联表的标签表不可以为空";
        }
        platformFlags = platformFlags.replace("，", ",");
        String[] platforms = platformFlags.split(",");
        String isTableExsit = DataUtil.isTableExist(carPostService,tagPostService,platforms,carplatformName,tagPlatformName,carRelateTableName,tagRelateTableName);
        if(!"".equals(isTableExsit)){
            return isTableExsit;
        }
        PlatformTableInfo platformTableInfo = new PlatformTableInfo();
        platformTableInfo.setPlatformFlags(platformFlags);
        platformTableInfo.setCarTableName(carplatformName);
        platformTableInfo.setTagTableName(tagPlatformName);
        configService.updatePlatformTableInfo(platformTableInfo);
        int isRelateExist = configService.findplatRelateCountByTableName(carplatformName);
        if(isRelateExist<1&&carRelateTableName!=null&&!"".equals(carplatformName)){
            //原先没有关联表
            RelateTableInfo relateTableInfo = new RelateTableInfo();
            relateTableInfo.setTableName(carplatformName);
            relateTableInfo.setRelateCarTableName(carRelateTableName);
            relateTableInfo.setRelateTagTableName(tagRelateTableName);
            configService.insertPlatformRelateTableInfo(relateTableInfo);
        } else if(isRelateExist>0&&(carRelateTableName==null||"".equals(carRelateTableName))){
            //原先有记录，后来将关联表置空
            List<String> params = new ArrayList<>();
            params.add(carplatformName);
            configService.deletePlatformRelateTableInfo(params);
        }else if(isRelateExist>0&&carRelateTableName==null&&"".equals(carRelateTableName)){
            RelateTableInfo relateTableInfo = new RelateTableInfo();
            relateTableInfo.setTableName(carplatformName);
            relateTableInfo.setRelateCarTableName(carRelateTableName);
            relateTableInfo.setRelateTagTableName(tagRelateTableName);
            configService.updatePlatformRelateTableInfo(relateTableInfo);
        }
        return StringInfo.ONE+"";
    }
}
