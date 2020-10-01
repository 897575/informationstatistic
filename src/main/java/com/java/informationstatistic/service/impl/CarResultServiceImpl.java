package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.dao.car.CarResultDao;
import com.java.informationstatistic.model.Repost;
import com.java.informationstatistic.model.Result;
import com.java.informationstatistic.service.CarResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * car中结果信息服务层
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020729
 */
@Service("CarResultService")
public class CarResultServiceImpl implements CarResultService {

    @Resource
    private CarResultDao carResultDao;

    @Override
    public void insertAllResultInfo(List<Result> results,String platform) {
        Map<String,Object> params = new HashMap<>();
        params.put("results",results);
        params.put("platform",platform);
        carResultDao.insertAllResultInfo(params);
    }

    @Override
    public List<Result> findByCreatTime() {
        return carResultDao.findByCreatTime();
    }

    @Override
    public List<Result> queryAllResultLimit(Map<String, String> param) {
        return carResultDao.queryAllResultLimit(param);
    }

    @Override
    public List<Result> queryResultLimit(Map<String, String> param) {
        return carResultDao.queryResultLimit(param);
    }

    @Override
    public void insertResultInfo(List<Result> results,String platform) {
        Map<String,Object> params = new HashMap<>();
        params.put("results",results);
        params.put("platform",platform);
        carResultDao.insertResultInfo(params);
    }

    @Override
    public int queryResultInfo(Map<String, String> param) {
        return carResultDao.queryResultInfo(param);
    }

    @Override
    public int queryAllResultInfo(Map<String, String> param) {
        return carResultDao.queryAllResultInfo(param);
    }

    @Override
    public void insertBrandInfo(List<Result> resultList) {
        carResultDao.insertBrandInfo(resultList);
    }
    @Override
    public int countBrandInfo(Map<String, String> params) {
        return carResultDao.countBrandInfo(params);
    }
}
