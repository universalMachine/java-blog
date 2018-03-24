package com.wang.blog.DTO;


import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


public class PostDTO extends BaseDomain {

    private Integer postId;

    private Integer userId;

    private Optional<Integer> boardId = Optional.empty();


    private Integer topicId;


    private String postText;

    private String postTitle;

    private Integer postType;

    private LocalDateTime createTime;

    private long replyFloor = 0;

    public PostDTO() {
    }

    public long getReplyFloor() {
        return replyFloor;
    }

    public void setReplyFloor(long replyFloor) {
        this.replyFloor = replyFloor;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Optional<Integer> getBoardId() {
        return boardId;
    }

    public void setBoardId(Optional<Integer> boardId) {
        this.boardId = boardId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
