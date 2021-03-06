package com.wang.blog.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.INTEGER)
@Polymorphism(type = PolymorphismType.EXPLICIT)
@DiscriminatorValue("2")
public class MainPost extends Post{
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="topic_id")
    private Topic topic;

    public MainPost() {

    }

    public MainPost(User user, Board board, Topic topic, String postText, String postTitle) {
        super(user, board, topic, postText, postTitle);
        this.topic = topic;
    }

    @Override
    public Topic getTopic() {
        return topic;
    }

    @Override
    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
