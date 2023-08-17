package com.utsa.PostAddService.services;

import com.utsa.PostAddService.dto.Comment;
import com.utsa.PostAddService.dto.Post;

public interface PostService {
    Post addPost(Post post, int userId);

    Post removePost(int userId, int postId);

    Comment addComment(int userId, int postId, Comment comment);

    Comment removeComment(int commentId, int postId, int userId);

    Post likePost(int postId, int userId);

    Comment likeComment(int commentId, int userId);

    Post getPost(Integer postId);

    Comment getComment(int commentId);
}
