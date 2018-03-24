package com.wang.blog.DTO;

import java.time.LocalDateTime;


public class TopicDTO extends BaseDomain {

    private Integer topicId;

    private String topicTitle;

    private String topicDesc;

    private Integer boardId;

    private String authorName;

    private LocalDateTime CreateTime;

    private LocalDateTime lastPostTime;

    private Long totalPostNum;

    private Integer topicViews;

    private Integer digest;

    private Integer topicReplies;

    private PostDTO mainPost;

    public TopicDTO() {
    }

    public Long getTotalPostNum() {
        return totalPostNum;
    }

    public void setTotalPostNum(Long totalPostNum) {
        this.totalPostNum = totalPostNum;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDateTime getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        CreateTime = createTime;
    }

    public LocalDateTime getLastPostTime() {
        return lastPostTime;
    }

    public void setLastPostTime(LocalDateTime lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    public Integer getTopicViews() {
        return topicViews;
    }

    public void setTopicViews(Integer topicViews) {
        this.topicViews = topicViews;
    }

    public Integer getDigest() {
        return digest;
    }

    public void setDigest(Integer digest) {
        this.digest = digest;
    }

    public Integer getTopicReplies() {
        return topicReplies;
    }

    public void setTopicReplies(Integer topicReplies) {
        this.topicReplies = topicReplies;
    }

    public PostDTO getMainPost() {
        return mainPost;
    }

    public void setMainPost(PostDTO mainPost) {
        this.mainPost = mainPost;
    }
}
