package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.model.PostTag;
import com.java.informationstatistic.model.Repost;
import com.java.informationstatistic.model.RepostTag;
import com.java.informationstatistic.service.CarRepostService;
import com.java.informationstatistic.service.RepostInformationDealService;
import com.java.informationstatistic.service.TagPostService;
import com.java.informationstatistic.service.TagRepostService;
import com.java.informationstatistic.tools.DataUtil;
import com.java.informationstatistic.tools.StringInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
            List<String> brandInfos = brandInfoDeal(repostName, repost.getRepostId());
            if (brandInfos == null || brandInfos.isEmpty()) {
                continue;
            }
            //拆分need信息
            List<String> needInfos = needInfoDeal(repostName, repost.getRepostId());
            //合并结果
            List<String> result = combineInfo(brandInfos, needInfos, repost,platform);
            if (result == null || result.isEmpty()) {
                continue;
            }
            repostResult.addAll(result);
        }
        return repostResult;
    }

    /**
     * 品牌拆分
     *
     * @param repostName repost标签信息
     * @return 拆分后的信息
     */
    private List<String> brandInfoDeal(List<String> repostName, long repostId) {
        if (repostName == null || repostName.isEmpty()) {
            return null;
        }
        List<String> brandInfos = new ArrayList<>();
        for (String str : repostName) {
            if (str == null || "".equals(str)) {
                continue;
            }
            //处理品牌
            if (str.startsWith(StringInfo.BRAND)) {
                String[] flags = str.split("\\.");
                if (flags.length == 0) {
                    continue;
                }
                StringBuffer sb = new StringBuffer();
                sb.append(repostId);
                sb.append(StringInfo.MARK_ID);
                for (int i = 0; i < flags.length; i++) {
                    sb.append(flags[i]);
                    if (i == 0) {
                        sb.append(StringInfo.POT);
                        continue;
                    }
                    sb.append(StringInfo.COMMA);
                    sb.append(i + StringInfo.ONE);
                    brandInfos.add(sb.toString());
                    sb.delete(sb.length() - StringInfo.TWO, sb.length());
                    sb.append(StringInfo.POT);
                }
                sb.delete(0, sb.length());
            }
        }
        return brandInfos;
    }

    /**
     * 诉求信息处理
     *
     * @param repostName 诉求信息
     * @return 返回拆分结果
     */
    private List<String> needInfoDeal(List<String> repostName, long repostId) {
        List<String> needInfos = new ArrayList<>();
        if (repostName == null || repostName.isEmpty()) {
            return null;
        }
        for (String needInfo : repostName) {
            if (needInfo == null || "".equals(needInfo)) {
                continue;
            }
            if (!needInfo.startsWith(StringInfo.BRAND)) {
                String[] strs = needInfo.split("\\.");
                if (strs.length < StringInfo.THREE) {
                    continue;
                }
                StringBuffer sb = new StringBuffer();
                sb.append(repostId);
                sb.append(StringInfo.MARK_ID);
                for (int i = 0; i < strs.length; i++) {
                    if ("".equals(strs[i])) {
                        continue;
                    }
                    sb.append(strs[i]);
                    if (i < StringInfo.TWO) {
                        sb.append(StringInfo.POT);
                        continue;
                    }
                    sb.append(StringInfo.COMMA);
                    sb.append(i + StringInfo.ONE);
                    needInfos.add(sb.toString());
                    sb.delete(sb.length() - StringInfo.TWO, sb.length());
                    sb.append(StringInfo.POT);
                }
                sb.delete(0, sb.length());
            }
        }
        return needInfos;
    }

    /**
     * 数据结合
     *
     * @param brandInfos 品牌拆分信息
     * @param needInfos  诉求拆分信息
     * @param repost     repost基础信息
     * @return 结果集合
     */
    private List<String> combineInfo(List<String> brandInfos, List<String> needInfos, Repost repost,String platform) {
        if (brandInfos == null || brandInfos.isEmpty() || repost == null) {
            return null;
        }
        List<String> repostResult = new ArrayList<>();
        Set<String> brandSet = new HashSet<>(brandInfos);
        if (needInfos == null || needInfos.isEmpty()) {
            //此处为没有诉求信息
            for (String str : brandSet) {
                //没有情感
                repostResult.add(str.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + platform+StringInfo.POST + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + repost.getRepostTime());
                //有情感
                repostResult.add(str.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + platform+StringInfo.POST  + StringInfo.COMMA + (repost.getPositiveProb() >= StringInfo.FLAG_dATA ? StringInfo.POSITIVE + StringInfo.COMMA : StringInfo.NEGATIVE + StringInfo.COMMA) + repost.getRepostTime());
            }
            return repostResult;
        }
        Set<String> needSet = new HashSet<>(needInfos);
        for (String brandStr : brandSet) {
            if (!brandStr.contains(StringInfo.MARK_ID)) {
                continue;
            }
            for (String needStr : needSet) {
                if (!brandStr.contains(StringInfo.MARK_ID)) {
                    continue;
                }
                //无情感
                repostResult.add(brandStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + needStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + platform+StringInfo.POST  + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + repost.getRepostTime());
                //有情感
                repostResult.add(brandStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + needStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + platform+StringInfo.POST  + StringInfo.COMMA + (repost.getPositiveProb() >= StringInfo.FLAG_dATA ? StringInfo.POSITIVE + StringInfo.COMMA : StringInfo.NEGATIVE + StringInfo.COMMA) + repost.getRepostTime());
            }
        }
        return repostResult;
    }
}
