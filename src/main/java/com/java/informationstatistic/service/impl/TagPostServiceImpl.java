package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.dao.tag.TagPostDao;
import com.java.informationstatistic.model.PostTag;
import com.java.informationstatistic.service.TagPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tagpost信息服务实现
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020729
 */
@Service("tagPostService")
public class TagPostServiceImpl implements TagPostService {

    @Resource
    private TagPostDao tagPostDao;

    @Override
    public List<PostTag> getTagPostInfo(List<Long> postIds,String tableName) {
        Map<String,Object> params = new HashMap<>();
        params.put("tableName",tableName);
        params.put("ids",postIds);
        return tagPostDao.findByPostIds(params);
    }

    @Override
    public String testTableName(String tableName) {
        return tagPostDao.testTableName(tableName);
    }
}
