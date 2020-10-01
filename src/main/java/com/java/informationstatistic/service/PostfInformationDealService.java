package com.java.informationstatistic.service;

import com.java.informationstatistic.model.Post;
import com.java.informationstatistic.model.PostTag;

import java.util.List;

/**
 * 信息处理服务层
 *
 * @author luyu
 * @since 2020729
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public interface PostfInformationDealService {

    /**
     * post信息处理
     * @param  posts posts
     * @param platform 平台类型
     * @param postTags  标签
     * @return 处理结果
     */
    List<String> postInfoDeal(List<Post> posts, List<PostTag> postTags,String platform);

    /**
     * post品牌拆分处理
     * @param posts post数据
     * @param postTags post标签信息
     * @param platform 平台
     * @return 处理结果
     */
    List<String> postInfoBrandDeal(List<Post> posts,List<PostTag> postTags,String platform);
}
