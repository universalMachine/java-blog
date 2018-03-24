package com.wang.blog.web;

import com.wang.blog.DTO.PostDTO;
import com.wang.blog.domain.Post;
import com.wang.blog.helper.enums.ReturnCode;
import com.wang.blog.result.Data;
import com.wang.blog.result.Result;
import com.wang.blog.service.DTOService;
import com.wang.blog.service.PostService;
import org.hibernate.loader.custom.Return;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
@Transactional
@RestController
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    DTOService dtoService;

    @Autowired
    Logger logger;
    @Transactional
    @GetMapping("/{topicId}/replyPost")
    protected Result getPagedReplyPost(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @PathVariable("topicId")  Integer topicId){
        Result result = new Result();
        try{
            Page<PostDTO> replyPostDto = postService.getPagedReplyPostDTO(pageNum,pageSize,topicId);
            result.setData(new Data<Page<PostDTO>>(replyPostDto));
            result.setReturnCode(ReturnCode.success.getValue());
            result.setExtra("{\"topicId\":%d}",topicId);
        }catch (Exception e){
            logger.debug(e.getMessage());
        }finally {
            return  result;
        }

    }
    @Transactional
    @GetMapping("/{topicId}/mainPost")
    protected Result getMainPost(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @PathVariable("topicId")  Integer topicId){
        Result result = new Result();
        try{
            Page<PostDTO> mainPostDto = postService.getPagedMainPostDTO(pageNum,pageSize,topicId);
            result.setData(new Data<Page<PostDTO>>(mainPostDto));
            result.setReturnCode(ReturnCode.success.getValue());
            result.setExtra("{\"topicId\":%d}",topicId);
        }catch (Exception e){
            logger.debug(e.getMessage());
        }finally {
            return  result;
        }

    }

    @PostMapping("/post/add")
    protected Result addPost(@RequestBody PostDTO postDTO){
        Result result = new Result();
        try{
            PostDTO postDTOResult = postService.addPostDTO(postDTO);
            result.setData(new Data<PostDTO>(postDTOResult));
            result.setReturnCode(ReturnCode.success.getValue());
        }catch (Exception e){
            logger.debug(e.getMessage());
        }finally {
            return result;
        }
    }

    @DeleteMapping("post/delete")
    protected Result deletePost(@RequestParam("postId") Integer postId){
        Result result = new Result();
        try{
            Post deletedPost = postService.deletePostById(postId);
            result.setExtra("{\"postId\":%d,\"topicId\":%d}",postId,deletedPost.getTopic().getTopicId());
            result.setReturnCode(ReturnCode.success.getValue());
        }catch (Exception e){
            logger.debug(e.getMessage());
        }finally {
            return result;
        }

    }
}
