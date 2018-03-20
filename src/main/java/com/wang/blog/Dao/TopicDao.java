package com.wang.blog.Dao;


import com.wang.blog.domain.Topic;
import com.wang.blog.repository.TopicRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDao extends BaseDao{
    private static final String onlyTopicRelations = " from Topic as t";
    private static final String GET_BOARD_DIGEST_TOPICS = "from Topic as t where t.board.boardId=? and digest > 0 order by t.lastPostTime desc,digest desc";
    private static final String GET_PAGED_TOPICS = "from Topic as t where t.board.boardId=? order by t.lastPostTime desc";
    private static final String QUERY_TOPICS_BY_TITLE = "from Topic as t where t.topicTitle like ? order by t.lastPostTime desc";

    @Autowired
    private TopicRepository topicRepository;

    /**
     * 获取论坛某一页的精华帖子,按最后回复时间及精华级别排序
     * @param boardId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<Topic> getBoardDigestTopics(Integer boardId,int pageNo,int pageSize){
        return pagedQuery(GET_BOARD_DIGEST_TOPICS,pageNo,pageSize,Topic.class,boardId);
    }

    public Page<Topic> getPagedTopics(Integer boardId,int pageNo,int pageSize){
        return pagedQuery(GET_PAGED_TOPICS,pageNo,pageSize,Topic.class,boardId);
    }


    public Page<Topic> queryTopicByTitle(String title,int pageNo,int pageSize){
        return pagedQuery(QUERY_TOPICS_BY_TITLE,pageNo,pageSize,Topic.class,notSpecifQuery(title));
    }


}
