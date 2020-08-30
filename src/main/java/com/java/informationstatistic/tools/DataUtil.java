package com.java.informationstatistic.tools;

import com.java.informationstatistic.model.*;
import com.java.informationstatistic.service.CarPostService;
import com.java.informationstatistic.service.TagPostService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据处理工具类
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020730
 */
public class DataUtil {


    /**
     * 获取id
     * @param posts post集合
     * @param reposts repost集合
     * @return id集合
     */
    public static Set<Long> getPostIds(List<Post> posts, List<Repost> reposts){
        Set<Long> ids = new HashSet<>();
        if(posts!=null&&!posts.isEmpty()){
            for(Post post:posts){
                if(post==null){
                    continue;
                }
                ids.add(post.getPostId());
            }
        }
        if(reposts !=null&&!reposts.isEmpty()){
            for(Repost repost : reposts){
                if (repost ==null){
                    continue;
                }
                ids.add(repost.getPostId());
            }
        }
        return ids;
    }


    /**
     * 将数据转化并统计
     *
     * @param resultList 结果集合
     * @return 转化结果
     */
    public static List<Result> formatObject(List<String> resultList) {
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        List<Result> results = new ArrayList<>();
        Map<String, Integer> resultMap = new HashMap<>();
        //统计次数
        for (String str : resultList) {
            if (str == null || "".equals(str)) {
                continue;
            }
            if (!resultMap.containsKey(str)) {
                resultMap.put(str, 1);
            } else {
                resultMap.put(str, resultMap.get(str) + 1);
            }
        }
        List<String> keys = new ArrayList<>(resultMap.keySet());
        //将数据转化为akPost对象
        for (int i = 0; i < keys.size(); i++) {
            String[] strs = keys.get(i).split(StringInfo.COMMA);
            if (strs.length < 7) {
                continue;
            }
            Result result = new Result();
            result.setBrand(strs[0]);
            result.setBrandLevel(strs[1]);
            result.setNeed(strs[2]);
            result.setNeedLevel(strs[3]);
            result.setChannel(strs[4]);
            result.setSentiment(strs[5]);
            result.setTime(strs[6]);
            result.setNum(resultMap.get(keys.get(i)));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            result.setCreateTime(sdf.format(new Date()));
            results.add(result);
        }
        return results;
    }

    /**
     * 查看repost和post中的信息是否相同，不同则补充到repost
     *
     * @param postTags   post标签内容
     * @param repostTags repost标签内容
     * @param repost     repost基本信息
     * @return 返回补充信息
     */
    public static List<String> splitPostAndRepostInfo(List<PostTag> postTags, List<RepostTag> repostTags, Repost repost) {
        //repost信息
        List<String> repostString = new ArrayList<>();
        //缺少数据不处理
        if (repost == null) {
            return null;
        }
        if (repostTags != null && !repostTags.isEmpty()) {
            //获取一个repostid里面所有的标签内容
            for (int i = 0; i< repostTags.size(); i++) {
                if(repostTags.get(i)==null){
                    continue;
                }
                if(repost.getRepostId()== repostTags.get(i).getReportId()){
                    repostString.add(repostTags.get(i).getName());
                }
            }
        }
        if (postTags != null && !postTags.isEmpty()) {
            //post信息
            List<String> postString = new ArrayList<>();
            //获取与之对应的postId所对应的所有的标签信息
            for (PostTag postTag : postTags) {
                if (postTag == null || "".equals(postTag.getName())) {
                    continue;
                }
                if(postTag.getPostId()== repost.getPostId()){
                    if (postTag.getName().startsWith(StringInfo.BRAND)) {
                        postString.add(postTag.getName());
                    }
                }
            }
            //对比数据，看是否缺少post的标签内容
            for (String str : postString) {
                //repost中缺少name信息
                if (!repostString.contains(str)) {
                    repostString.add(str);
                }
            }
        }
        return repostString;
    }

    /**
     * 生成年月日
     *
     * @param year  年
     * @param month 月
     * @return 年月日字符串
     */
    public static int generateYearMonthDay(int year, int month) {
        int day = 0;
        switch (month) {
            default:
                day = 1;
                break;
            case 1:
                day = 31;
                break;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 29;
                } else {
                    day = 28;
                }
                break;
            case 3:
                day = 31;
                break;
            case 4:
                day = 30;
                break;
            case 5:
                day = 31;
                break;
            case 6:
                day = 30;
                break;
            case 7:
                day = 31;
                break;
            case 8:
                day = 31;
                break;
            case 9:
                day = 30;
                break;
            case 10:
                day = 31;
                break;
            case 11:
                day = 30;
                break;
            case 12:
                day = 31;
                break;
        }
        return day;
    }

    public static String isTableExist(CarPostService carPostService,TagPostService tagPostService,String[] platforms,
                               String carplatformName,String tagPlatformName,String carRelateTableName,String tagRelateTableName){
        for(int i = 0;i<platforms.length;i++){
            String result1 = carPostService.testTable(platforms[i]+"_"+carplatformName);
            if(result1==null||"".equals(result1)){
                return "表："+platforms[i]+"_"+carplatformName+",不存在";
            }
            String result2 = tagPostService.testTableName(platforms[i]+"_"+tagPlatformName);
            if(result2==null||"".equals(result2)){
                return "表："+platforms[i]+"_"+tagPlatformName+",不存在";
            }
            if(carRelateTableName!=null&&tagRelateTableName!=null&&!"".equals(carRelateTableName)&&!"".equals(tagRelateTableName)) {
                String reult1 = carPostService.testTable(platforms[i]+"_"+carRelateTableName);
                if (reult1 == null && "".equals(reult1)) {
                    return "表："+platforms[i]+"_"+carRelateTableName+",不存在";
                }
                String reult2 = tagPostService.testTableName(platforms[i]+"_"+tagRelateTableName);
                if (reult2 == null && "".equals(reult2)) {
                    return "表："+platforms[i]+"_"+tagRelateTableName+",不存在";
                }
            }
        }
        return "";
    }
}
