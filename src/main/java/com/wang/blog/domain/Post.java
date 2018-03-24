package com.wang.blog.domain;


import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table()
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type",discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
@Entity
public class Post extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name="topic_id")
    private Topic topic;

    @Lob
    private String postText;

    private String postTitle;

    private long replyFloor;

    private LocalDateTime createTime;

    public Post() {
    }

    public Post(User user, Board board, Topic topic, String postText, String postTitle) {
        this.user = user;
        this.board = board;
        this.topic = topic;
        this.postText = postText;
        this.postTitle = postTitle;
        this.createTime = createTime;
    }

    public long getReplyFloor() {
        return replyFloor;
    }

    public void setReplyFloor(long replyFloor) {
        this.replyFloor = replyFloor;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
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
