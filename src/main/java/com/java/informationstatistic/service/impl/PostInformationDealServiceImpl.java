package com.java.informationstatistic.service.impl;

import com.java.informationstatistic.model.Post;
import com.java.informationstatistic.model.PostTag;
import com.java.informationstatistic.service.CarPostService;
import com.java.informationstatistic.service.PostfInformationDealService;
import com.java.informationstatistic.service.TagPostService;
import com.java.informationstatistic.tools.DataUtil;
import com.java.informationstatistic.tools.StringInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 信息处理服务层实现
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020729
 */
@Service("carfInformationDealService")
public class PostInformationDealServiceImpl implements PostfInformationDealService {

    @Override
    public List<String> postInfoDeal(List<Post> posts,List<PostTag> postTags,String platform) {
        if (posts == null || posts.isEmpty()) {
            return null;
        }
        if(postTags==null||postTags.isEmpty()){
            return null;
        }
        List<String> resultCount = new ArrayList<>();
        //根据postId来获取数据
        for (Post post : posts) {
            if (post == null) {
                continue;
            }
            //获取postId下的name标签
            List<String> nameInfos = getByPostId( postTags,post.getPostId());
            if (nameInfos == null || nameInfos.isEmpty()) {
                //没有标签信息
                continue;
            }
            //处理品牌信息
            List<String> brandInfos = brandDataDeal(post.getPostId(), nameInfos);
            //没有品牌数据结束
            if (brandInfos == null || brandInfos.isEmpty()) {
                continue;
            }
            //处理诉求信息
            List<String> needInfos = needInfoDeal(post.getPostId(), nameInfos);
            //信息合并
            List<String> result = mergeInfos(brandInfos, needInfos, post,platform);
            if (result == null || result.isEmpty()) {
                continue;
            }
            resultCount.addAll(result);
        }
        return resultCount;
    }

    /**
     * 品牌信息处理
     *
     * @param postId    postid
     * @param brandInfo 品牌信息
     */
    private List<String> brandDataDeal(long postId, List<String> brandInfo) {
        List<String> brandInfos = new ArrayList<>();
        if (brandInfo == null || brandInfo.isEmpty()) {
            return null;
        }
        StringBuffer stb = new StringBuffer();
        for (int i = 0; i < brandInfo.size(); i++) {
            if (brandInfo.get(i) == null || "".equals(brandInfo.get(i))) {
                continue;
            }
            //只需要品牌信息
            if (!brandInfo.get(i).startsWith(StringInfo.BRAND)) {
                continue;
            }
            //切割品牌信息
            String[] brands = brandInfo.get(i).split("\\.");
            stb.append(postId + StringInfo.MARK_ID);
            for (int j = 0; j < brands.length; j++) {
                if ("".equals(brands[j])) {
                    continue;
                }
                stb.append(brands[j]);
                if (j == 0) {
                    stb.append(StringInfo.POT);
                    continue;
                }
                stb.append(StringInfo.COMMA + (j + StringInfo.ONE));
                brandInfos.add(stb.toString());
                stb.delete(stb.length() - StringInfo.TWO, stb.length());
                stb.append(StringInfo.POT);
            }
            stb.delete(0, stb.length());
        }
        return brandInfos;
    }

    /**
     * 具有诉求信息的处理
     *
     * @param needInfos 诉求信息
     * @return
     */
    private List<String> needInfoDeal(long postId, List<String> needInfos) {
        if (needInfos == null || needInfos.isEmpty()) {
            return null;
        }
        StringBuffer sbt = new StringBuffer();
        List<String> resultInfos = new ArrayList<>();

        for (String info : needInfos) {
            if (info == null || "".equals(info)) {
                continue;
            }
            if (info.startsWith(StringInfo.BRAND)) {
                continue;
            }
            String[] needs = info.split("\\.");
            //等级不足三级
            if (needs.length < StringInfo.THREE) {
                continue;
            }
            sbt.append(postId + StringInfo.MARK_ID);
            for (int i = 0; i < needs.length; i++) {
                if (!"".equals(needs[i])) {
                    sbt.append(needs[i]);
                    if (i < StringInfo.TWO) {
                        sbt.append(StringInfo.POT);
                        continue;
                    }
                    sbt.append(StringInfo.COMMA + (i + StringInfo.ONE));
                    resultInfos.add(sbt.toString());
                    sbt.delete(sbt.length() - StringInfo.TWO, sbt.length());
                    sbt.append(StringInfo.POT);
                }
            }
            sbt.delete(0, sbt.length());
        }
        return resultInfos;
    }

    /**
     * 根据postId获取tag信息
     *
     * @param postTags  tag信息
     * @return  返回tag信息
     */
    private List<String> getByPostId(List<PostTag> postTags, long postId) {
        List<String> tagPostInfo = new ArrayList<>();
        for(int i = 0;i<postTags.size();i++){
            if(postTags.get(i)==null){
                return null;
            }
            if(postId==postTags.get(i).getPostId()){
                //将数据放入集合中
                tagPostInfo.add(postTags.get(i).getName());
                //删除原来的数据
                postTags.remove(i);
                i--;
            }
        }
        return tagPostInfo;
    }


    /**
     * 合并品牌信息和诉求信息
     *
     * @param brand 品牌信息
     * @param need  诉求信息
     * @param post  post信息
     * @return
     */
    private List<String> mergeInfos(List<String> brand, List<String> need, Post post,String platform) {
        List<String> result = new ArrayList<>();
        //判断信息是否可用
        if (brand == null || brand.isEmpty() || post == null) {
            return null;
        }
        //合并品牌重复数据
        Set<String> brandSet = new HashSet<>(brand);
        //没有诉求信息
        if (need == null || need.isEmpty()) {
            for (String str : brandSet) {
                //没有情感
                result.add(str.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + platform+StringInfo.POST + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + post.getPostTime());
                //具有情感
                result.add(str.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + platform+StringInfo.POST + StringInfo.COMMA + (post.getPositiveProb() >= StringInfo.FLAG_dATA ? StringInfo.POSITIVE + StringInfo.COMMA : StringInfo.NEGATIVE + StringInfo.COMMA) + post.getPostTime());
            }
            return result;
        } else {
            //合并诉求重复信息
            Set<String> needSet = new HashSet<>(need);
            for (String brandStr : brandSet) {
                for (String needStr : needSet) {
                    result.add(brandStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + needStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + platform+StringInfo.POST  + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + post.getPostTime());
                    result.add(brandStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + needStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + platform+StringInfo.POST  + StringInfo.COMMA + (post.getPositiveProb() >= StringInfo.FLAG_dATA ? StringInfo.POSITIVE + StringInfo.COMMA : StringInfo.NEGATIVE + StringInfo.COMMA) + post.getPostTime());
                }
            }
            return result;
        }
    }
}
