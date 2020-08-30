package com.java.informationstatistic.model;

/**
 * 回帖实体
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020726
 */
public class Repost {
    /**
     * 回帖id
     */
    private long repostId;
    /**
     * 帖子id
     */
    private long postId;
    /**
     * 回帖时间
     */
    private String repostTime;
    /**
     * 回帖情感指数
     */
    private double positiveProb;

    public long getRepostId() {
        return repostId;
    }

    public void setRepostId(long repostId) {
        this.repostId = repostId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getRepostTime() {
        return repostTime;
    }

    public void setRepostTime(String repostTime) {
        this.repostTime = repostTime;
    }

    public double getPositiveProb() {
        return positiveProb;
    }

    public void setPositiveProb(double positiveProb) {
        this.positiveProb = positiveProb;
    }
}
