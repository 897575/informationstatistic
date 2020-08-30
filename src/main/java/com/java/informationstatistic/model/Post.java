package com.java.informationstatistic.model;

/**
 * 发帖实体
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020726
 */
public class Post {
    /**
     * 帖子id
     */
    private long postId;
    /**
     * 帖子发布时间
     */
    private String postTime;
    /**
     * 帖子发布者情感指数
     */
    private double positiveProb;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public double getPositiveProb() {
        return positiveProb;
    }

    public void setPositiveProb(double positiveProb) {
        this.positiveProb = positiveProb;
    }
}
