package com.wang.blog.service;

import com.wang.blog.DTO.PostDTO;
import com.wang.blog.domain.Post;
import com.wang.blog.domain.Topic;
import com.wang.blog.domain.User;
import com.wang.blog.repository.PostRepository;
import com.wang.blog.repository.TopicRepository;
import com.wang.blog.repository.UserRepository;
import com.wang.blog.helper.enums.USER_CREDIT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;


    @Autowired
    private DTOService dtoService;

    @Autowired
    private DateTimeService dateTimeService;

    /**
     *
     * @param topic
     */
    @Transactional
    public void updateTopicMainPost(Topic topic){
        topicRepository.save(topic);
    }

    @Transactional
    public void updatePost(Post post){
        postRepository.save(post);
    }

    @Transactional
    public void addPost(PostDTO postDTO){


        Post post = dtoService.convertPostDtoToPost(postDTO,2);

        postRepository.save(post);


        User user = post.getUser();
        userService.updateCredit(user, USER_CREDIT.ADD_POST.getUpdateValue());

        Topic topic = post.getTopic();
        topic.setTopicReplies(topic.getTopicReplies()+1);
        topic.setLastPostTime(dateTimeService.getNowBeiJingDateTime());


        //topic处于Hibernate受管状态，无须显式更新

    }

    @Transactional
    public void deletePostById(int postId){
        Post post = postRepository.getOne(postId);


        postRepository.delete(post);


        Topic topic = post.getTopic();
        topic.setTopicReplies(topic.getTopicReplies() - 1);

        User user = post.getUser();
        userService.updateCredit(user,-20);


    }


}
