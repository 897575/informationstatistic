package com.java.informationstatistic.model;

/**
 * 统计结果实体
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020729
 */
public class Result {

    /**
     * 主键
     */
    private String id;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 品牌等级
     */
    private String brandLevel;

    /**
     * 诉求
     */
    private String need;

    /**
     * 诉求等级
     */
    private String needLevel;

    /**
     * 来源渠道
     */
    private String channel;

    /**
     * 情感
     */
    private String sentiment;

    /**
     * 时间
     */
    private String time;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 统计次数
     */
    private Integer num;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandLevel() {
        return brandLevel;
    }

    public void setBrandLevel(String brandLevel) {
        this.brandLevel = brandLevel;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getNeedLevel() {
        return needLevel;
    }

    public void setNeedLevel(String needLevel) {
        this.needLevel = needLevel;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
