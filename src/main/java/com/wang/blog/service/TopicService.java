package com.wang.blog.service;

import com.wang.blog.DTO.TopicDTO;
import com.wang.blog.Dao.TopicDao;
import com.wang.blog.domain.Board;
import com.wang.blog.domain.MainPost;
import com.wang.blog.domain.Topic;
import com.wang.blog.domain.User;
import com.wang.blog.repository.BoardRepository;
import com.wang.blog.repository.TopicRepository;
import com.wang.blog.helper.enums.TOPIC_DIGEST;
import com.wang.blog.helper.enums.USER_CREDIT;
import com.wang.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private DTOService dtoService;

    /**
     * 发表一个主题帖,用户积分加十分,板块主题贴加一个
     * @param topicDto
     */
    @Transactional
    public TopicDTO addTopic(TopicDTO topicDto){
        Topic topic = dtoService.convertTopicDtoToToic(topicDto);
        topic.setBoard(boardRepository.getOne(topicDto.getBoardId()));
        topic = addTopic(topic);
        dtoService.fillTopicDTO(topic,topicDto);
        return topicDto;
    }

    public Topic addTopic(Topic topic) {
        boardService.incTopicNum(topic.getBoard());

        topic.setDigest(TOPIC_DIGEST.NORMAL.getValue());

        if(authenticationService.isLogined()){
            topic.setAuthor(userService.getUserByUserName(authenticationService.getUserName()));
            //发布帖子+10积分
            User postTopicUser = topic.getAuthor();
            userService.updateCredit(postTopicUser, USER_CREDIT.ADD_TOPIC.getUpdateValue());
        }else{
            //默认有一个匿名用户，方便处理
            topic.setAuthor(userService.getDefault());
        }

        topic.setTopicReplies(0);
        topic.setTopicViews(0);
        topic.setLastPostTime(dateTimeService.getNowBeiJingDateTime());
        topic.setCreateTime(dateTimeService.getNowBeiJingDateTime());

        MainPost post = topic.getMainPost();
        if(post == null){
            post = new MainPost();
        }

        topic.setTopicTitle(topic.getMainPost().getPostTitle());
        post.setCreateTime(topic.getCreateTime());
        post.setUser(topic.getAuthor());
        post.setTopic(topic);
        topic.setTopicDesc(genTopicDesc(post.getPostText()));

        topicRepository.save(topic);
        return topic;
    }


    /**
     * 删除一个主题帖,用户积分减50,版块主题帖数减1,删除与主题帖相关的所有回复贴
     */
    @Transactional
    public void deleteTopic(Topic topic){
        topicRepository.delete(topic);
        User user = topic.getAuthor();
        Board board = topic.getBoard();

        boardService.updateTopicNum(board,-1);
        userService.updateCredit(user, USER_CREDIT.DELETE_MAIN_POST.getUpdateValue());

    }

    @Transactional
    public void deleteTopicById(Integer topicId){
        deleteTopic(topicRepository.getOne(topicId));
    }

    @Transactional
    public  void makeTopicDigest(int topicId){
        Topic topic = topicRepository.findOne(topicId);

        topic.setDigest(TOPIC_DIGEST.DIGEST.getValue());

        User author = topic.getAuthor();
        userService.updateCredit(author, USER_CREDIT.TOPIC_DIGEST.getUpdateValue());
    }

    public Page<Topic>  getPagedTopics(int boardId,int pageNo,int pageSize){
       return topicDao.getPagedTopics(boardId,pageNo,pageSize);
    }

    public Page<TopicDTO> getPagedTopicDTOs(int boardId, int pageNo, int pageSize){
        Page<Topic> pagedTopics =  getPagedTopics(boardId,pageNo,pageSize);
      return  pagedTopics.map((Converter<Topic,TopicDTO>)topic->dtoService.convertTopicToTopicDto(topic));

    }

    public Page<Topic> queryTopicByTitle(String title,int pageNo,int pageSize){
        return topicDao.queryTopicByTitle(title,pageNo,pageSize);
    }

    public Page<Topic> getBoardDigestTopics(Integer boardId,int pageNo,int pageSize){
        return topicDao.getBoardDigestTopics(pageNo,pageSize,boardId);
    }


    private String genTopicDesc(String content){
        if(content.length()<100){
            return content;
        }else
        return content.substring(0,100)+"...";
    }


}
