package com.utsa.PostAddService.controller;

import com.utsa.PostAddService.dto.Comment;
import com.utsa.PostAddService.dto.Post;
import com.utsa.PostAddService.services.PostService;
import com.utsa.PostAddService.services.PostServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
Logger logger= LoggerFactory.getLogger(PostController.class);
    @Autowired
    public PostServiceImpl postService;

    @PostMapping("/add/{userId}")
    public Post addPost(@RequestBody Post post,@PathVariable("userId") int userId){
        return postService.addPost(post, userId);

    }

    @DeleteMapping("/remove/{userId}/{postId}")
    public Post removePost(@PathVariable("userId") int userId, @PathVariable("postId") int postId){
        return postService.removePost(userId, postId);
    }

    @PostMapping("/addComment/{userId}/{postId}")
    public Comment addComment(@PathVariable("userId") int userId, @PathVariable("postId") int postId,@RequestBody Comment comment ){
        return postService.addComment(userId, postId, comment);
    }

    @DeleteMapping("/removeComment/{userId}/{postId}/{commentId}")
    public Comment removeComment(@PathVariable("userId") int userId,@PathVariable("postId") int postId, @PathVariable("commentId") int commentId){
        logger.info("user Id {} post Id {} comment Id {}", userId, postId, commentId);
        return postService.removeComment(commentId,postId,userId);
    }
    @GetMapping("/like/{postId}/{userId}")
    public Post likePost(@PathVariable("postId") int postId,@PathVariable("userId") int userId){
        return postService.likePost(postId, userId);
    }

    @GetMapping("/likeComment/{commentId}/{userId}")
    public Comment likeComment(@PathVariable("commentId") int commentId, @PathVariable("userId") int userId){
        return postService.likeComment(commentId, userId);
    }

    @GetMapping("/get/{postId}")
    public Post getPost(@PathVariable("postId") int postId){
        return postService.getPost(postId);
    }

    @GetMapping("/getComment/{commentId}")
    public Comment getComment(@PathVariable("commentId") int commentId){
        return postService.getComment(commentId);
    }
}
