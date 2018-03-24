package com.wang.blog.service;

import com.wang.blog.DTO.PostDTO;
import com.wang.blog.Dao.PostDao;
import com.wang.blog.domain.MainPost;
import com.wang.blog.domain.Post;
import com.wang.blog.domain.Topic;
import com.wang.blog.domain.User;
import com.wang.blog.repository.PostRepository;
import com.wang.blog.repository.TopicRepository;
import com.wang.blog.repository.UserRepository;
import com.wang.blog.helper.enums.USER_CREDIT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class PostService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDao postDao;


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
    public Post addPost(Post post){

        User user = userService.getLoginedOrDefaultUser();
        userService.updateCredit(user, USER_CREDIT.ADD_POST.getUpdateValue());
        post.setUser(user);

        Topic topic = post.getTopic();
        topic.setTopicReplies(topic.getTopicReplies()+1);
        topic.setTotalPostNum(topic.getTotalPostNum()+1);
        topic.setLastPostTime(dateTimeService.getNowBeiJingDateTime());

        post.setReplyFloor(topic.getTotalPostNum());
        post.setBoard(topic.getBoard());

        post.setPostTitle(topic.getTopicTitle());

        post.setCreateTime(dateTimeService.getNowBeiJingDateTime());

        postRepository.save(post);
        return post;
        //topic处于Hibernate受管状态，无须显式更新

    }

    @Transactional
    public PostDTO addPostDTO(PostDTO postDTO){
        return dtoService.convertPostToPostDTO(addPost(dtoService.convertPostDtoToPost(postDTO,2)));
    }

    public Page<Post> getPagedReplyPost(Integer pageNo,Integer pageSize,Integer topicId){
        return postDao.getPagedReplyPost(pageNo,pageSize,topicId);
    }

    public Page<Post> getPagedPost(Integer pageNo,Integer pageSize,Integer topicId){
        return postDao.getPagedPost(pageNo,pageSize,topicId);
    }

    @Transactional
    public Page<MainPost> getMainPost(Integer pageNo,Integer pageSize,Integer topicId){
        return postDao.getPagedMainPost(pageNo,pageSize,topicId);
    }

    public Page<PostDTO> getPagedPostDTO(Integer pageNo, Integer pageSize, Integer topicId){
        return postDao.getPagedPost(pageNo,pageSize,topicId).map(post -> dtoService.convertPostToPostDTO(post));
    }
    @Transactional
    public Page<PostDTO> getPagedReplyPostDTO(Integer pageNo,Integer pageSize,Integer topicId){
        return postDao.getPagedReplyPost(pageNo,pageSize,topicId).map(post -> dtoService.convertPostToPostDTO(post));
    }

    public Page<PostDTO> getPagedMainPostDTO(Integer pageNo, Integer pageSize, Integer topicId){
        return getMainPost(pageNo,pageSize,topicId).map(post -> dtoService.convertPostToPostDTO(post));
    }





    @Transactional
    public Post deletePostById(int postId){
        Post post = postRepository.getOne(postId);





        Topic topic = post.getTopic();
        topic.setTopicReplies(topic.getTopicReplies() - 1);

        User user = post.getUser();
        userService.updateCredit(user,-20);

        postRepository.delete(post);
        return post;
    }


}
