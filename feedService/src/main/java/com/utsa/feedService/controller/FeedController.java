package com.utsa.feedService.controller;

import com.utsa.feedService.dto.Post;
import com.utsa.feedService.service.FeedService;
import com.utsa.feedService.service.FeedServiceImpl;
import com.utsa.feedService.service.WsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedController {
    Logger logger= LoggerFactory.getLogger(FeedController.class);
    @Autowired
    FeedServiceImpl feedService;

    @Autowired
    WsService service;

    @GetMapping("/posts/{page}/{numbers}")
    public Page<Post> getNewPosts(@PathVariable("page") int page, @PathVariable("numbers") int numbers){
        logger.info("Page {} numbers {} feedService {}", page, numbers, feedService);
        return feedService.getNewPosts(page, numbers);
    }

    @GetMapping("/userPosts/{userId}/{page}/{numbers}")
    public List<Post> getUserPost(@PathVariable("userId") int userId,@PathVariable("page") int page, @PathVariable("numbers") int numbers){
        return feedService.getUserPosts(userId,page,numbers);
    }

    @PostMapping("/feedFollowers/{userId}")
    public boolean feedFollowers(@RequestBody Post post, @PathVariable("userId") int userId){

        boolean res=service.notifyUser(post, userId);
        logger.info( "sending post {} to id {}", post, userId);
        return true;
    }

}
