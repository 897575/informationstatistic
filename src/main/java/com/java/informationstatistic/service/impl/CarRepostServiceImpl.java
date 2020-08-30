package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.dao.car.CarRepostDao;
import com.java.informationstatistic.model.Repost;
import com.java.informationstatistic.service.CarRepostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * car中repost信息服务层
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020729
 */
@Service("carRepostService")
public class CarRepostServiceImpl implements CarRepostService {



    @Resource
    private CarRepostDao carRepostDao;

    @Override
    public List<Repost> getRepostTimeInfo(String beginTime, String endTime, String tableName) {
        Map<String, String> params = new HashMap<>();
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        params.put("tableName",tableName);
        return carRepostDao.findByRepostTime(params);
    }
}
