package com.java.informationstatistic.model;

/**
 * 回帖内容
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020726
 */
public class RepostTag {

    /**
     * 标签id
     */
    private long tagId;
    /**
     * 报告id
     */
    private long reportId;
    /**
     * 报告内容
     */
    private String name;

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
