package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.dao.car.ConfigDao;
import com.java.informationstatistic.model.PlatformInfo;
import com.java.informationstatistic.model.PlatformTableInfo;
import com.java.informationstatistic.model.RelateTableInfo;
import com.java.informationstatistic.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigDao configDao;

    @Override
    public List<PlatformTableInfo> findAllInfo() {
        return configDao.findAllInfo();
    }

    @Override
    public List<RelateTableInfo> findRelateTable() {
        return configDao.findRelateTable();
    }

    @Override
    public List<String> findPlatformFlag() {
        return configDao.findPlatformFlag();
    }

    @Override
    public int findPlatformCount() {
        return configDao.findPlatformCount();
    }

    @Override
    public List<PlatformInfo> findPlatformInfo(Map<String, String> param) {
        return configDao.findPlatformInfo(param);
    }

    @Override
    public int deletePlatformInfos(List<String> ids) {
        return configDao.deletePlatformInfos(ids);
    }

    @Override
    public int insertPlatformInfo(PlatformInfo platformInfo) {
        return configDao.insertPlatformInfo(platformInfo);
    }

    @Override
    public int findPlatformByFlag(String flag) {
        return configDao.findPlatformByFlag(flag);
    }

    @Override
    public void editPlatformInfo(PlatformInfo platformInfo) {
        configDao.editPlatformInfo(platformInfo);
    }

    @Override
    public List<Map<String, String>> findPlatformTableInfoLimit(Map<String, String> param) {
        return configDao.findPlatformTableInfoLimit(param);
    }

    @Override
    public int findPlatformTableCount() {
        return configDao.findPlatformTableCount();
    }

    @Override
    public Map<String, String> findPlatRelateTableInfo(String tableName) {
        return configDao.findPlatRelateTableInfo(tableName);
    }

    @Override
    public int findPlatformTableCountbyTableName(String carTableName) {
        return configDao.findPlatformTableCountbyTableName(carTableName);
    }

    @Override
    public int findplatRelateCountByTableName(String tableName) {
        return configDao.findplatRelateCountByTableName(tableName);
    }

    @Override
    public int insertPlatformTbaleInfo(PlatformTableInfo platformTableInfo) {
        return configDao.insertPlatformTbaleInfo(platformTableInfo);
    }

    @Override
    public int insertPlatformRelateTableInfo(RelateTableInfo relateTableInfo) {
        return configDao.insertPlatformRelateTableInfo(relateTableInfo);
    }

    @Override
    public void deletePlatformTableInfo(List<String> tableNames) {
        configDao.deletePlatformTableInfo(tableNames);
    }

    @Override
    public void deletePlatformRelateTableInfo(List<String> tableNames) {
        configDao.deletePlatformRelateTableInfo(tableNames);
    }

    @Override
    public void updatePlatformTableInfo(PlatformTableInfo platformTableInfo) {
        configDao.updatePlatformTableInfo(platformTableInfo);
    }

    @Override
    public void updatePlatformRelateTableInfo(RelateTableInfo relateTableInfo) {
        configDao.updatePlatformRelateTableInfo(relateTableInfo);
    }
}
