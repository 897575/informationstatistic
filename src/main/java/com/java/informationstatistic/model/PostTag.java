package com.java.informationstatistic.model;

/**
 * 爱卡发帖标签实体
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020726
 */
public class PostTag {
    /**
     * 标签id
     */
    private long tagId;
    /**
     * 帖子id
     */
    private long postId;
    /**
     * 标签名
     */
    private String name;

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
