package com.wang.blog.Dao;

import com.wang.blog.domain.Post;
import com.wang.blog.repository.PostRepository;
import com.wang.blog.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public class PostDao extends BaseDao {
    private final String onlyFromPost = " from Post as p ";
    private final String GET_PAGED_POST = onlyFromPost + "where p.topic.topicId = ? order by p.createTime desc";


    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TopicRepository topicRepository;

    public Page<Post> getPagedPost(int pageNo,int pageSize,Integer topicId){
        return pagedQuery(GET_PAGED_POST,pageNo,pageSize,Post.class,topicId);
    }

    @Transactional
    public void deleteTopicPosts(int topicId){

        postRepository.delete(getPostsByTopicId(topicId));


    }


    @Transactional
    public Set<Post> getPostsByTopicId(Integer topicId){
        return topicRepository.findOne(topicId).getPosts();
    }

}
