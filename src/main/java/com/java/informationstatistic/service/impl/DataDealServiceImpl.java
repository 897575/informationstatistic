package com.java.informationstatistic.service.impl;


import com.java.informationstatistic.model.*;
import com.java.informationstatistic.service.*;

import com.java.informationstatistic.tools.DataUtil;
import com.java.informationstatistic.tools.StringInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 数据处理服务
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 20200807
 */
@Service("dataDealService")
public class DataDealServiceImpl implements DataDealService {

    @Resource
    private PostfInformationDealService postfInformationDealService;
    @Resource
    private RepostInformationDealService repostInformationDealService;
    @Resource
    private CarPostService carPostService;
    @Resource
    private CarRepostService carRepostService;
    @Resource
    private TagPostService tagPostService;
    @Resource
    private TagRepostService tagRepostService;
    @Resource
    private ConfigService configService;

    @Override
    public List<String> dataDeal(CarResultService carResultService, String beginTime, String endTime, String platform) {
        List<String> resultList = new ArrayList<>();
        List<PlatformTableInfo> platformTableInfos = configService.findAllInfo();
        List<RelateTableInfo> relateTableInfos = configService.findRelateTable();
        //获取需要处理的表
        List<PlatformTableInfo> tableNames = DataUtil.getNeedDealTableName(platformTableInfos, platform);
        if (tableNames == null || tableNames.isEmpty()) {
            //没有相关配置信息，不处理
            return null;
        }
        for (PlatformTableInfo tableName : tableNames) {
            //获取post信息
            List<Post> posts = carPostService.getPostTimeInfo(beginTime, endTime, platform + "_" + tableName.getCarTableName());
            if (posts != null && !posts.isEmpty()) {
                //获取postid
                Set<Long> ids = DataUtil.getPostIds(posts, null);
                //获取tag信息
                List<PostTag> postTags = tagPostService.getTagPostInfo(new ArrayList<>(ids), platform + "_" + tableName.getTagTableName());
                if (postTags != null && !postTags.isEmpty()) {
                    //处理post数据
                    List<String> postResult = postfInformationDealService.postInfoDeal(posts, postTags, platform);
                    if (postResult != null && !postResult.isEmpty()) {
                        resultList.addAll(postResult);
                    }
                }
            }
            //获取关联表信息
            RelateTableInfo carRelateName = DataUtil.getRelateTableName(relateTableInfos, tableName.getCarTableName());
            if (carRelateName == null || carRelateName.getTableName() == null) {
                //没有管理
                continue;
            }
            //获取reposts信息
            List<Repost> reposts = carRepostService.getRepostTimeInfo(beginTime, endTime, platform + "_" + carRelateName.getRelateCarTableName());
            if (!reposts.isEmpty()) {
                //有repost信息时处理
                Set<Long> reIds = DataUtil.getPostIds(null, reposts);
                //获取postTag信息
                List<PostTag> repostPostTags = tagPostService.getTagPostInfo(new ArrayList<>(reIds), platform + "_" + tableName.getTagTableName());
                //有维护信息才处理
                List<RepostTag> repostTags = tagRepostService.findByRepostIds(reposts, platform + "_" + carRelateName.getRelateTagTableName());
                if (!repostTags.isEmpty()) {
                    //有repostTag信息才处理
                    List<String> repostResult = repostInformationDealService.repostInfoDeal(reposts, repostTags, repostPostTags, platform);
                    if (repostResult != null && !repostResult.isEmpty()) {
                        resultList.addAll(repostResult);
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 获取repostTag信息
     *
     * @param tagRelateNames tag表名
     * @param reposts        repost信息
     * @param platform       平台类别
     * @return repostTag信息
     */
    private List<RepostTag> getRepostTags(List<String> tagRelateNames, List<Repost> reposts, String platform) {
        List<RepostTag> repostTags = new ArrayList<>();
        for (String tagRelateName : tagRelateNames) {
            if ("".equals(tagRelateName)) {
                continue;
            }
            List<RepostTag> repostList = tagRepostService.findByRepostIds(reposts, platform + "_" + tagRelateName);
            if (repostList != null && !repostList.isEmpty()) {
                repostTags.addAll(repostList);
            }
        }
        return repostTags;
    }
}