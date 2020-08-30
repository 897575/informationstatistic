package com.java.informationstatistic.model;

/**
 * 平台信息
 * @author luyu
 * @since 20200822
 * @version v1.0
 *
 */
public class PlatformInfo {

    private int id;

    private String platformName;

    private String platformFlag;

    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformFlag() {
        return platformFlag;
    }

    public void setPlatformFlag(String platformFlag) {
        this.platformFlag = platformFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
