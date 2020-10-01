package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.model.PostTag;
import com.java.informationstatistic.model.Repost;
import com.java.informationstatistic.model.RepostTag;
import com.java.informationstatistic.service.RepostInformationDealService;
import com.java.informationstatistic.tools.DataUtil;
import com.java.informationstatistic.tools.StringInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * repost信息处理服务层
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020731
 */
@Service("repostInformationDealService")
public class RepostInformationDealServiceImpl implements RepostInformationDealService {

    @Override
    public List<String> repostInfoDeal(List<Repost> reposts, List<RepostTag> repostTags, List<PostTag> postTags,String platform) {
        //最后结果输出
        List<String> repostResult = new ArrayList<>();
        //获取repost信息
        if (reposts == null || reposts.isEmpty()) {
            //没有信息可以处理
            return null;
        }
        //处理信息
        for (Repost repost : reposts) {
            //补充标签信息
            List<String> repostName = DataUtil.splitPostAndRepostInfo(postTags, repostTags, repost);
            if (repostName == null || repostName.isEmpty()) {
                continue;
            }
            //拆分品牌信息
            List<String> brandInfos = DataUtil.brandDataDeal(repost.getRepostId(),repostName);
            if (brandInfos == null || brandInfos.isEmpty()) {
                continue;
            }
            //拆分need信息
            List<String> needInfos = DataUtil.needInfoDeal(repost.getRepostId(),repostName);
            //合并结果
            List<String> result = DataUtil.mergeInfos(brandInfos, needInfos, repost.getRepostTime(),platform,repost.getPositiveProb());
            if (result == null || result.isEmpty()) {
                continue;
            }
            repostResult.addAll(result);
        }
        return repostResult;
    }

    @Override
    public List<String> repostInfoBrand(List<Repost> reposts, List<RepostTag> repostTags, List<PostTag> postTags, String platform) {
        //最后结果输出
        List<String> repostResult = new ArrayList<>();
        //获取repost信息
        if (reposts == null || reposts.isEmpty()) {
            //没有信息可以处理
            return null;
        }
        //处理信息
        for (Repost repost : reposts) {
            //补充标签信息
            List<String> repostName = DataUtil.splitPostAndRepostInfo(postTags, repostTags, repost);
            if (repostName == null || repostName.isEmpty()) {
                continue;
            }
            //拆分品牌信息
            List<String> brandInfos = DataUtil.brandDataDeal(repost.getRepostId(),repostName);
            if (brandInfos != null && !brandInfos.isEmpty()) {
                Set<String> brandSet = new HashSet<>(brandInfos);
                for(String brandName:brandSet){
                    //有情感
                    repostResult.add(brandName.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + StringInfo.NULL+StringInfo.COMMA+StringInfo.NULL + StringInfo.COMMA + platform + StringInfo.COMMA + (repost.getPositiveProb() >= StringInfo.FLAG_dATA ? StringInfo.POSITIVE + StringInfo.COMMA : StringInfo.NEGATIVE + StringInfo.COMMA) + repost.getRepostTime());
                }
            }
        }
        return repostResult;
    }
}