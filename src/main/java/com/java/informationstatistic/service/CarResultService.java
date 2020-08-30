package com.java.informationstatistic.service;

import com.java.informationstatistic.model.Result;

import java.util.List;
import java.util.Map;

/**
 * car库中post服务
 *
 * @author luyu
 * @since 2020726
 * @version v1.0
 *
 * copyright 18994139782@163.com
 *
 */
public interface CarResultService {

    /**
     * 获取今天的数据
     * @return 返回结果
     */
    List<Result> findByCreatTime();

    /**
     * 插入数据
     * @param results 参数集合
     */
    void insertResultInfo(List<Result> results,String platform);

    /**
     * 插入2020之前的数据
     * @param results 参数集合
     */
    void insertAllResultInfo(List<Result> results,String platform);

    /**
     * 查询范围内是否有数据
     * @param param 查询条件
     */
    int queryAllResultInfo(Map<String,String> param);

    /**
     * 查询2020年是否初始化
     * @param param 查询条件
     */
    int queryResultInfo(Map<String,String> param);

    /**
     * 分页查询结果数据（2020之前）
     * @param param 查询参数
     * @return 查询结果
     */
    List<Result> queryAllResultLimit(Map<String,String> param);


    /**
     * 分页查询结果数据（2020之后）
     * @param param 查询参数
     * @return 查询结果
     */
    List<Result> queryResultLimit(Map<String,String> param);

}
