package com.utsa.feedService.service;

import com.utsa.feedService.dto.Post;
import com.utsa.feedService.service.resilience.FeedServiceResilience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServiceImpl implements FeedService{
Logger logger=LoggerFactory.getLogger(FeedServiceImpl.class);
    FeedService service;
    @Autowired
    public FeedServiceImpl(FeedServiceResilience feedServiceResilience){
        service=feedServiceResilience;
    }
    @Cacheable(cacheNames = "posts", key = "#page.toString().concat('-').concat(#numbers)")
    @Override
    public Page<Post> getNewPosts(int page, int numbers) {
        logger.info("page {} numbers {} service {}", page, numbers, service);
        return service.getNewPosts(page, numbers);
    }

    @Cacheable(cacheNames = "userPosts", key = "#userId.toString().concat('-').concat(#page).concat('-').concat(#numbers)")
    @Override
    public List<Post> getUserPosts(int userId,int page,int numbers) {
        return service.getUserPosts(userId,page, numbers);
    }

    @Override
    public boolean feedFollowers(Post post, int userId) {
        return service.feedFollowers(post, userId);
    }
}
