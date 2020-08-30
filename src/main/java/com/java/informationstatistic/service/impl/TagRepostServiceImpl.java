package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.dao.tag.TagRepostDao;
import com.java.informationstatistic.model.Repost;
import com.java.informationstatistic.model.RepostTag;
import com.java.informationstatistic.service.TagRepostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * tag中repost信息
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020731
 */
@Service("tagRepostService")
public class TagRepostServiceImpl implements TagRepostService {

    @Resource
    private TagRepostDao tagRepostDao;

    @Override
    public List<RepostTag> findByRepostIds(List<Repost> reposts, String tableName) {
        Map<String,Object> params = new HashMap<>();
        params.put("ids",reposts);
        params.put("tableName",tableName);
        return tagRepostDao.findByRepostIds(params);
    }
}
