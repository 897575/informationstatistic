package com.java.informationstatistic.service;

import com.java.informationstatistic.model.PlatformInfo;
import com.java.informationstatistic.model.PlatformTableInfo;
import com.java.informationstatistic.model.RelateTableInfo;

import java.util.List;
import java.util.Map;

/**
 * 配置表信息
 *
 * @author luyu
 * @version 配置表信息
 * <p>
 * copyright 18994139782@163.com
 * @since 20200814
 */
public interface ConfigService {

    /**
     * 获取配置表信息
     *
     * @return 配置表信息
     */
    List<PlatformTableInfo> findAllInfo();

    /**
     * 获取关联表信息
     *
     * @return 关联表信息
     */
    List<RelateTableInfo> findRelateTable();

    /**
     * 获取平台信息
     *
     * @return 平台标识集合
     */
    List<String> findPlatformFlag();

    /**
     * 获取平台的条数
     * @return 条数
     */
    int findPlatformCount();

    /**
     * 平台信息
     * @return 平台信息
     */
    List<PlatformInfo> findPlatformInfo(Map<String,String> param);

    /**
     * 批量删除
     * @param ids 删除id
     * @return 删除数量
     */
    int deletePlatformInfos(List<String> ids);

    /**
     * 插入平台信息
     * @param platformInfo 平台信息
     * @return 插入结果
     */
    int insertPlatformInfo(PlatformInfo platformInfo);

    /**
     * 查询代码是否已经存在
     * @param flag 平台代码
     * @return 返回结果
     */
    int findPlatformByFlag(String flag);

    /**
     * 修改平台信息
     * @param platformInfo 平台信息
     */
    void editPlatformInfo(PlatformInfo platformInfo);

    /**
     * 分页查询平台与表的联系
     * @param param 分页参数
     * @return 联系信息
     */
    List<Map<String,String>> findPlatformTableInfoLimit(Map<String,String> param);

    /**
     * 获取平台与联系表的数据量
     * @return 数据量
     */
    int findPlatformTableCount();

    /**
     * 获取关联表
     * @param tableName 表名
     * @return 关联表信息
     */
    Map<String,String> findPlatRelateTableInfo(String tableName);

    /**
     * 获取插入数据是否存在
     * @param carTableName 表名
     * @return 结果
     */
    int findPlatformTableCountbyTableName(String carTableName);

    /**
     *  获取插入数据是否存在
     * @param tableName 表名
     * @return 结果
     */
    int findplatRelateCountByTableName(String tableName);

    /**
     * 插入表信息
     * @param platformTableInfo 表信息
     * @return 插入结果
     */
    int insertPlatformTbaleInfo(PlatformTableInfo platformTableInfo);

    /**
     * 插入关联表信息
     * @param relateTableInfo 关联表信息
     * @return 插入结果
     */
    int insertPlatformRelateTableInfo(RelateTableInfo relateTableInfo);


    /**
     * 删除数据
     * @param tableNames 表名集合
     */
    void deletePlatformTableInfo(List<String> tableNames);

    /**
     * 删除数据
     * @param tableNames 表名集合
     */
    void deletePlatformRelateTableInfo(List<String> tableNames);

    /**
     * 更新数据
     * @param platformTableInfo 更新数据信息
     */
    void updatePlatformTableInfo(PlatformTableInfo platformTableInfo);

    /**
     * 更新关联表数据
     * @param relateTableInfo 关联表信息
     */
    void updatePlatformRelateTableInfo(RelateTableInfo relateTableInfo);
}
