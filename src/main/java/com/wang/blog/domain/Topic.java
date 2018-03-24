package com.wang.blog.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(indexes = {

})

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Topic extends BaseDomain{
    @Id
    @GeneratedValue
    private Integer topicId;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "topic",cascade = { CascadeType.DETACH,CascadeType.REMOVE})
    private Set<Post> posts;

    @Column(nullable = false)
    private String topicTitle;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private  Board board;

    @ManyToOne(cascade = {CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User author;

    @Column(nullable = false)
    private LocalDateTime CreateTime;

    @Column(nullable = false)
    private LocalDateTime lastPostTime;

    @Column(nullable = false)
    private Integer topicViews;

    @Column(nullable = false)
    private Integer digest;

    @Column(nullable = false)
    private Integer topicReplies;

    private long totalPostNum;

    @Lob
    private String topicDesc;

    @org.springframework.data.annotation.Transient
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "topic")
    @JoinColumn(name = "post_id")
    private MainPost mainPost = new MainPost();

    public MainPost getMainPost() {
        return mainPost;
    }

    public void setMainPost(MainPost mainPost) {
        this.mainPost = mainPost;
    }

    public Topic() {
    }

    public long getTotalPostNum() {
        return totalPostNum;
    }

    public void setTotalPostNum(long totalPostNum) {
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

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
}
