package com.java.informationstatistic.dao.car;

import com.java.informationstatistic.model.Result;

import java.util.List;
import java.util.Map;

/**
 * 结果表数据层
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyroght 18994139782@163.com
 * @since 2020729
 */
public interface CarResultDao {

    /**
     * 获取今天的数据
     *
     * @return 今天的数据
     */
    List<Result> findByCreatTime();

    /**
     * 插入数据
     *
     * @param params 插入的信息
     */
    void insertResultInfo(Map<String,Object> params);

    /**
     * 插入2020之前的数据
     *
     * @param params 参数集合
     */
    void insertAllResultInfo(Map<String,Object> params);

    /**
     * 查询范围内是否有数据
     * @param params 查询条件
     */
    int queryAllResultInfo(Map<String,String> params);

    /**
     * 查询2020的数据是否存在
     * @param params 查询条件
     * @return 条数
     */
    int queryResultInfo(Map<String,String> params);

    /**
     * 分页查询结果数据（2020之前）
     * @param params 查询参数
     * @return 查询结果
     */
    List<Result> queryAllResultLimit(Map<String,String> params);


    /**
     * 分页查询结果数据（2020之后）
     * @param params 查询参数
     * @return 查询结果
     */
    List<Result> queryResultLimit(Map<String,String> params);

    /**
     * 插入品牌信息
     * @param resultList 插入数据
     *
     */
    void insertBrandInfo(List<Result> resultList);

    /**
     * 查询数据是否生成
     * @param params 查询信息
     * @return 条数
     */
    int countBrandInfo(Map<String,String> params);
}
