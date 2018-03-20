package com.wang.blog.web;

import com.wang.blog.DTO.TopicDTO;
import com.wang.blog.helper.config.ParamDataBInd.JsonArg;
import com.wang.blog.helper.enums.ReturnCode;
import com.wang.blog.result.Data;
import com.wang.blog.result.Result;
import com.wang.blog.service.TopicService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
public class TopicController  {
    @Autowired
    private TopicService topicService;

    @Autowired
    private Logger logger;


//    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/topic/add")
    public Result addTopic(@RequestBody TopicDTO topicDto, @JsonArg("boardId") String boardId){
        Result result = new Result();
        try{
            Integer thisBoardId = Integer.parseInt(boardId);
            TopicDTO savedTopicDto = topicService.addTopic(topicDto);
            result.setData(new Data(savedTopicDto));
            result.setReturnCode(ReturnCode.success.getValue());
        }catch (Exception e){
            result.setReturnCode(300);
        }finally {
            return  result;
        }

    }

    @GetMapping("/topic/{boardId}")
    public Result getPagedTopic(@PathVariable("boardId") Integer boardId,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNo){
        Result result = new Result();
        try{
            Page<TopicDTO> pagedTopicDTOs = topicService.getPagedTopicDTOs(boardId,pageNo,pageSize);
            Data<Page<TopicDTO>> pagedTopicsData = new Data<>(pagedTopicDTOs);
            result.setReturnCode(ReturnCode.success.getValue());
            result.setData(pagedTopicsData);
        }catch (Exception e){
            logger.debug(e.getMessage());
        }finally {
            return result;
        }

    }

    @DeleteMapping("/topic/delete/{topicId}")
    public Result deleteTopic(@PathVariable("topicId") Integer topicId){
        Result result = new Result();
        try{
            topicService.deleteTopicById(topicId);
            result.setData(new Data<Integer>(topicId));
            result.setReturnCode(ReturnCode.success.getValue());
        }catch(Exception e){
            logger.debug(e.getMessage());
            result.setReturnCode(300);
        }finally {
            return result;
        }

    }
}
