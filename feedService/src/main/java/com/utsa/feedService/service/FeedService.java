package com.utsa.feedService.service;

import com.utsa.feedService.dto.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FeedService {
    Page<Post> getNewPosts(int page, int numbers);

    List<Post> getUserPosts(int userId,int page, int numbers);

    boolean feedFollowers(Post post, int userId);
}
