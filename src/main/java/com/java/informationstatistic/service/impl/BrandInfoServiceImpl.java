package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.model.*;
import com.java.informationstatistic.service.*;
import com.java.informationstatistic.tools.DataUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("brandInfoService")
public class BrandInfoServiceImpl implements BrandInfoService {

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
    @Resource
    private PostfInformationDealService postfInformationDealService;
    @Resource
    private RepostInformationDealService repostInformationDealService;


    @Override
    public List<String> brandSplit(CarResultService carResultService,String platform, String beginTime, String endTime) {

        //创建结果集
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
            List<Post> posts = carPostService.getPostTimeInfo(beginTime, endTime, platform + "_" + tableName.getCarTableName());
            if (posts != null && !posts.isEmpty()) {
                //获取postid
                Set<Long> ids = DataUtil.getPostIds(posts, null);
                //获取tag信息
                List<PostTag> postTags = tagPostService.getTagPostInfo(new ArrayList<>(ids), platform + "_" + tableName.getTagTableName());
                if (postTags != null && !postTags.isEmpty()) {
                    //处理post数据
                    List<String> postResult = postfInformationDealService.postInfoBrandDeal(posts, postTags,platform);
                    if(postResult!=null&&!postResult.isEmpty()){
                        resultList.addAll(postResult);
                    }
                }
            }

            RelateTableInfo carRelateName = DataUtil.getRelateTableName(relateTableInfos, tableName.getCarTableName());
            if (carRelateName == null||carRelateName.getTableName()==null) {
                //没有管理
                continue;
            }
            List<Repost> reposts = carRepostService.getRepostTimeInfo(beginTime, endTime, platform + "_" + carRelateName.getRelateCarTableName());
            if (reposts!=null&&!reposts.isEmpty()) {
                //有repost信息时处理
                Set<Long> reIds = DataUtil.getPostIds(null, reposts);
                //获取postTag信息
                List<PostTag> repostPostTags = tagPostService.getTagPostInfo(new ArrayList<>(reIds), platform + "_" + tableName.getTagTableName());
                //有维护信息才处理
                List<RepostTag> repostTags = tagRepostService.findByRepostIds(reposts, platform + "_" + carRelateName.getRelateTagTableName());
                if (repostTags!=null&&!repostTags.isEmpty()) {
                    List<String> repostResult = repostInformationDealService.repostInfoBrand(reposts, repostTags, repostPostTags,platform);
                    if (repostResult != null && !repostResult.isEmpty()) {
                        resultList.addAll(repostResult);
                    }
                }
            }
        }
        return resultList;
    }
}
