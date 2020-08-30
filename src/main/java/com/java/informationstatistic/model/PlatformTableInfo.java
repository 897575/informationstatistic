package com.java.informationstatistic.model;

/**
 * 配置信息表
 *
 * @author luyu
 * @since 20200814
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public class PlatformTableInfo {

    private Integer id;

    private String carTableName;

    private String platformFlags;

    private String tagTableName;

    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCarTableName() {
        return carTableName;
    }

    public void setCarTableName(String carTableName) {
        this.carTableName = carTableName;
    }

    public String getPlatformFlags() {
        return platformFlags;
    }

    public void setPlatformFlags(String platformFlags) {
        this.platformFlags = platformFlags;
    }

    public String getTagTableName() {
        return tagTableName;
    }

    public void setTagTableName(String tagTableName) {
        this.tagTableName = tagTableName;
    }
}
