package com.java.informationstatistic.tools;

import com.java.informationstatistic.model.*;
import com.java.informationstatistic.service.CarPostService;
import com.java.informationstatistic.service.CarResultService;
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
     * 合并品牌信息和诉求信息
     *
     * @param brand 品牌信息
     * @param need  诉求信息
     * @param time  时间信息
     * @return
     */
    public static  List<String> mergeInfos(List<String> brand, List<String> need, String time,String platform,double prob) {
        List<String> result = new ArrayList<>();
        //判断信息是否可用
        if (brand == null || brand.isEmpty() || time == null||"".equals(time)) {
            return null;
        }
        //合并品牌重复数据
        Set<String> brandSet = new HashSet<>(brand);
        Set<String> needSet = new HashSet<>(need);
        //没有诉求信息
        if (need.isEmpty()) {
            for (String str : brandSet) {
                //没有情感
                result.add(str.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + platform+StringInfo.POST + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + time);
                //具有情感
                result.add(str.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + platform+StringInfo.POST + StringInfo.COMMA + (prob >= StringInfo.FLAG_dATA ? StringInfo.POSITIVE + StringInfo.COMMA : StringInfo.NEGATIVE + StringInfo.COMMA) + time);
            }
            return result;
        } else {
            //合并诉求重复信息
            for (String brandStr : brandSet) {
                if (!brandStr.contains(StringInfo.MARK_ID)) {
                    continue;
                }
                for (String needStr : needSet) {
                    if (!brandStr.contains(StringInfo.MARK_ID)) {
                        continue;
                    }
                    result.add(brandStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + needStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + platform+StringInfo.POST  + StringInfo.COMMA + StringInfo.NULL + StringInfo.COMMA + time);
                    result.add(brandStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + needStr.split(StringInfo.MARK_ID)[StringInfo.ONE] + StringInfo.COMMA + platform+StringInfo.POST  + StringInfo.COMMA + (prob >= StringInfo.FLAG_dATA ? StringInfo.POSITIVE + StringInfo.COMMA : StringInfo.NEGATIVE + StringInfo.COMMA) + time);
                }
            }
            return result;
        }
    }


    /**
     * 具有诉求信息的处理
     *
     * @param needInfos 诉求信息
     * @return
     */
    public static List<String> needInfoDeal(long postId, List<String> needInfos) {
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
     * 获取配置平台需要处理的表数据
     *
     * @param platformTableInfos 平台配置信息
     * @param platform           平台类别
     * @return 需要处理的表信息
     */
    public static List<PlatformTableInfo> getNeedDealTableName(List<PlatformTableInfo> platformTableInfos, String platform) {
        List<PlatformTableInfo> tableNames = new ArrayList<>();
        for (PlatformTableInfo platformTableInfo : platformTableInfos) {
            if (platformTableInfo == null) {
                continue;
            }
            if (platformTableInfo.getPlatformFlags().contains(platform)) {
                tableNames.add(platformTableInfo);
            }
        }
        return tableNames;
    }

    /**
     * 获取关联信息表
     *
     * @param relateTableInfos 关联表集合
     * @param tableName        表名
     * @return 关联表信息
     */
    public static RelateTableInfo getRelateTableName(List<RelateTableInfo> relateTableInfos, String tableName) {
        for (RelateTableInfo relateTableInfo : relateTableInfos) {
            if (relateTableInfo == null || "".equals(relateTableInfo.getTableName())) {
                continue;
            }
            if (tableName.equals(relateTableInfo.getTableName())) {
                return relateTableInfo;
            }
        }
        return null;
    }


    /**
     * 插入数据
     * @param resultList 插入数据集
     * @param beginTime 开始时间
     * @param carResultService 持久层
     * @param platform 平台
     */
    public static void insertData(List<String> resultList, String beginTime, CarResultService carResultService,String platform,String type){
        //转化为结果
        List<Result> results = DataUtil.formatObject(resultList,platform);
        List<Result> insertList = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < results.size(); i++){
            insertList.add(results.get(i));
            index++;
            if (index == 10000) {
                if("1".equals(type)){
                    if (Integer.valueOf(beginTime.split("-")[0])>=2020) {
                        carResultService.insertResultInfo(insertList, platform);
                    } else {
                        carResultService.insertAllResultInfo(insertList, platform);
                    }
                }else{
                    carResultService.insertBrandInfo(insertList);
                }

                index=0;
                insertList.clear();
            }
        }
        if(!insertList.isEmpty()){
            if("2".equals(type)){
                carResultService.insertBrandInfo(insertList);
            }else{
                if (Integer.valueOf(beginTime.split("-")[0])>=2020){
                    carResultService.insertResultInfo(insertList, platform);
                } else{
                    carResultService.insertAllResultInfo(insertList, platform);
                }
            }
        }
    }

    /**
     * 品牌信息处理
     *
     * @param postId    postid
     * @param brandInfo 品牌信息
     */
    public static List<String> brandDataDeal(long postId, List<String> brandInfo) {
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
     *
     * @param date 日期
     * @param index 下标序号
     * @return 结果id
     */
    public static String generationId(String date,String platform,int index){
        if(date==null||"".equals(date)||platform==null
                ||"".equals(platform)){
            return null;
        }
        date =date.replace("-","");
        String result = platform+date;
        String number = String.valueOf(index);
        String zero = "";
        if(number.length()!=StringInfo.INDEX_MAX){
            for(int i =0;i<StringInfo.INDEX_MAX-number.length();i++){
                zero += "0";
            }
        }
        result += zero+number;
        return result;
    }

    public static void main(String[] args) {
        String result = DataUtil.generationId("2020-9-8","ak",2);
        System.out.println(result);
    }

    /**
     * 将数据转化并统计
     *
     * @param resultList 结果集合
     * @return 转化结果
     */
    public static List<Result> formatObject(List<String> resultList,String platform) {
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
            String id = DataUtil.generationId(strs[6],platform,i+1);
            if(id!=null&&!"".equals(id)){
                result.setId(id);
            }else{
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                result.setId(uuid);
            }
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
