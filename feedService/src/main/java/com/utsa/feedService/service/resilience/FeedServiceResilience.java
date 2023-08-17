package com.utsa.feedService.service.resilience;

import com.utsa.feedService.dto.Post;
import com.utsa.feedService.dto.User;
import com.utsa.feedService.repository.FollowerRepository;
import com.utsa.feedService.repository.PostRepository;
import com.utsa.feedService.repository.UserRepository;
import com.utsa.feedService.service.FeedService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedServiceResilience implements FeedService {
    Logger logger= LoggerFactory.getLogger(FeedService.class);
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
FollowerRepository followerRepository;

    @CircuitBreaker(name = "service1", fallbackMethod = "pageCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "pageCircuitBreakerFallback")
    @Override
    public Page<Post> getNewPosts(int page, int numbers) {
        logger.info("page {} numbers {}", page, numbers);
        Page<Post> page1= postRepository.findAll(PageRequest.of(page,numbers, Sort.by(Sort.Direction.ASC,"timeStamp")));
        return page1;
    }

    public Page<Post> pageCircuitBreakerFallback(int page, int numbers, Throwable t){
        logger.error("Inside pageCircuitBreakerFallback page {} numbers {} t {}", page, numbers, t);
        return new PageImpl<>(new ArrayList<>());
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "listCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "listCircuitBreakerFallback")
    @Override
    public List<Post> getUserPosts(int userId,int page, int numbers) {
       User user= userRepository.findById(userId).orElse(null);
       if (user == null) throw new RuntimeException("User id is invalid");
       List<Post> posts=user.getPosts().stream().toList();
       logger.info("userId {} page {} numbers {}", user, page, numbers);
       logger.info("posts {} by user {}", posts, user);
       int minLength=Math.min((page)*numbers,posts.size());
       int maxLength=Math.min((page+1)*numbers, posts.size());
       logger.info("Minlength {} max length {}", minLength, maxLength);
       return new ArrayList<>(posts.subList(minLength, maxLength));
           }

           public List<Post> listCircuitBreakerFallback(int userId,int page, int numbers, Throwable t){
        logger.info("Inside listCircuitBreakerFallback userId {} page {} numbers {} err {}", userId, page, numbers, t);
        return new ArrayList<>();
           }

    @CircuitBreaker(name = "service1", fallbackMethod = "boolCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "boolCircuitBreakerFallback")
    @Override
    public boolean feedFollowers(Post post, int userId) {
        List<User> followers =followerRepository.getUserFollower(userId);
        logger.info("The followers of user {} are {}", userId, followers);
        return true;
    }

    public boolean boolCircuitBreakerFallback(Post post, int userId, Throwable t){
        logger.info(" In boolCircuitBreakerFallback post {} userId {} err {}", post, userId, t);
        return false;
    }
}
