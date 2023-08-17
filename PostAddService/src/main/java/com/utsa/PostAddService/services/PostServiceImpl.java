package com.utsa.PostAddService.services;

import com.utsa.PostAddService.dto.Comment;
import com.utsa.PostAddService.dto.Post;
import com.utsa.PostAddService.services.resilience.PostServiceResilience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

    PostService service;

    @Autowired
    public PostServiceImpl(PostServiceResilience postServiceResilience){
        this.service=postServiceResilience;
    }
    @Override
    public Post addPost(Post post, int userId) {
        return service.addPost(post, userId);
    }

    @Override
    public Post removePost(int userId, int postId) {
        return service.removePost(userId, postId);
    }

    @Override
    public Comment addComment(int userId, int postId, Comment comment) {
        return service.addComment(userId, postId, comment);
    }

    @Override
    public Comment removeComment(int commentId, int postId, int userId) {
        return service.removeComment(commentId, commentId, commentId);
    }

    @Override
    public Post likePost(int postId, int userId) {
        return service.likePost(postId, userId);
    }

    @Override
    public Comment likeComment(int commentId, int userId) {
        return service.likeComment(commentId, userId);
    }

    @Override
    public Post getPost(Integer postId) {
        return service.getPost(postId);
    }

    @Override
    public Comment getComment(int commentId) {
        return service.getComment(commentId);
    }
}
