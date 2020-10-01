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
            List<String> brandInfos = DataUtil.brandDataDeal(post.getPostId(), nameInfos);
            //没有品牌数据结束
            if (brandInfos == null || brandInfos.isEmpty()) {
                continue;
            }
            //处理诉求信息
            List<String> needInfos = DataUtil.needInfoDeal(post.getPostId(), nameInfos);
            //信息合并
            List<String> result = DataUtil.mergeInfos(brandInfos, needInfos, post.getPostTime(),platform,post.getPositiveProb());
            if (result == null || result.isEmpty()) {
                continue;
            }
            resultCount.addAll(result);
        }
        return resultCount;
    }


    @Override
    public List<String> postInfoBrandDeal(List<Post> posts, List<PostTag> postTags, String platform) {
        if (posts == null || posts.isEmpty()) {
            return null;
        }
        if(postTags==null||postTags.isEmpty()){
            return null;
        }
        List<String> resultCount = new ArrayList<>();
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
            List<String> brandInfos = DataUtil.brandDataDeal(post.getPostId(), nameInfos);
            if(brandInfos!=null&&!brandInfos.isEmpty()){
                //合并品牌重复数据
                Set<String> brandSet = new HashSet<>(brandInfos);
                for(String brandName:brandSet){
                    //有情感
                    resultCount.add(brandName.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + StringInfo.NULL+StringInfo.COMMA+StringInfo.NULL + StringInfo.COMMA + platform + StringInfo.COMMA + (post.getPositiveProb() >= StringInfo.FLAG_dATA ? StringInfo.POSITIVE + StringInfo.COMMA : StringInfo.NEGATIVE + StringInfo.COMMA) + post.getPostTime());
                }
            }
        }
        return resultCount;
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

}
