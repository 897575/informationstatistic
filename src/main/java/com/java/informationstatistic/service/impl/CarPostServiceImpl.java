package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.dao.car.CarPostDao;
import com.java.informationstatistic.model.Post;
import com.java.informationstatistic.service.CarPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * car中post信息服务层
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020729
 */
@Service("carPostService")
public class CarPostServiceImpl implements CarPostService {

    @Resource
    private CarPostDao carPostDao;

    @Override
    public List<Post> getPostTimeInfo(String beginTime, String endTime,String tableName) {
        Map<String, String> params = new HashMap();
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        params.put("tableName",tableName);
        List<Post> postList = carPostDao.findByPostTime(params);
        return postList;
    }

    @Override
    public String testTable(String tableName) {
        return carPostDao.testTable(tableName);
    }
}
