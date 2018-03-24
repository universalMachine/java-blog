package com.wang.blog.service;

import com.wang.blog.DTO.BoardDTO;
import com.wang.blog.DTO.PostDTO;
import com.wang.blog.DTO.TopicDTO;
import com.wang.blog.DTO.UserDTO;
import com.wang.blog.Dao.PostDao;
import com.wang.blog.domain.*;
import com.wang.blog.repository.BoardRepository;
import com.wang.blog.repository.PostRepository;
import com.wang.blog.repository.TopicRepository;
import com.wang.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DTOService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserService userService;



 /*   @Transactional
    protected Post convertPostDtoToPost(PostDTO postDTO){
        User user = userRepository.getOne(postDTO.getUserId());
        Board board = boardRepository.getOne(postDTO.getBoardId().orElse(1));
        Topic topic = topicRepository.getOne(postDTO.getTopicId());
        Assert.notNull(postDTO.getPostType(),"postType should not null");
        switch(postDTO.getPostType()){
            case 1:
                return new Post(user,board,topic,postDTO.getPostText(),postDTO.getPostTitle());
            case 2:
                return new MainPost(user,board,topic,postDTO.getPostText(),postDTO.getPostTitle());
        }

        return null;

    }*/

    protected Board convertBoardDtoToPost(BoardDTO boardDTO){

        User user = userService.getLoginedOrDefaultUser();

        Board board  =  new Board();
        List<User> list = new ArrayList();
        list.add(user);
        board.setUsers(list);
        board.setTopicNum(boardDTO.getTopicNum());
        board.setBoardDesc(boardDTO.getBoardDesc());
        board.setBoardName(boardDTO.getBoardName());
        return  board;
    }

    protected User convertUserDtoToUser(UserDTO userDTO){
        User user = new User();

        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
      //  System.out.println(userDTO.getUserName());
        return  user;
    }

    protected Topic convertTopicDtoToToic(TopicDTO topicDTO){
        Topic topic = new Topic();
        topic.setTopicTitle(topicDTO.getTopicTitle());
        topic.setMainPost(convertPostDtoToMainPost(topicDTO.getMainPost()));
        return topic;
    }

    protected TopicDTO fillTopicDTO(Topic topic,TopicDTO topicDto){
        topicDto.setTopicTitle(topic.getTopicTitle());
        topicDto.setTopicDesc(topic.getTopicDesc());

        topicDto.setTopicReplies(topic.getTopicReplies());
        topicDto.setDigest(topic.getDigest());

        topicDto.setTopicId(topic.getTopicId());
        topicDto.setAuthorName(topic.getAuthor().getUserName());

        topicDto.setCreateTime(topic.getCreateTime());
        topicDto.setLastPostTime(topic.getLastPostTime());
        return topicDto;
    }

    protected PostDTO convertPostToPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setTopicId(post.getTopic().getTopicId());
        postDTO.setPostId(post.getPostId());
        postDTO.setPostText(post.getPostText());
        postDTO.setPostTitle(post.getPostTitle());
        postDTO.setCreateTime(post.getCreateTime());
        postDTO.setReplyFloor(post.getReplyFloor());
        return postDTO;

    }
    protected Post convertPostDtoToPost(PostDTO postDto,Integer postType){
        Post post;
        if(postType == 1){
            post= new MainPost();
        }else {
            post = new Post();
        }

        post.setPostTitle(postDto.getPostTitle());
        post.setPostText(postDto.getPostText());
        if(postDto.getTopicId() != null)
        post.setTopic(topicRepository.getOne(postDto.getTopicId()));
        return post;
    }

    protected MainPost convertPostDtoToMainPost(PostDTO postDto) {
        return (MainPost)convertPostDtoToPost(postDto,1);
    }

    protected TopicDTO convertTopicToTopicDto(Topic topic){
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setTopicId(topic.getTopicId());
        topicDTO.setBoardId(topic.getBoard().getBoardId());

        topicDTO.setTopicTitle(topic.getTopicTitle());
        topicDTO.setTopicDesc(topic.getTopicDesc());
        return topicDTO;
    }
}
