package com.java.informationstatistic.model;

/**
 * 表关系信息
 *
 * @author luyu
 * @since 202004814
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public class RelateTableInfo {

    private String tableName;

    private String relateCarTableName;

    private String relateTagTableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRelateCarTableName() {
        return relateCarTableName;
    }

    public void setRelateCarTableName(String relateCarTableName) {
        this.relateCarTableName = relateCarTableName;
    }

    public String getRelateTagTableName() {
        return relateTagTableName;
    }

    public void setRelateTagTableName(String relateTagTableName) {
        this.relateTagTableName = relateTagTableName;
    }
}
