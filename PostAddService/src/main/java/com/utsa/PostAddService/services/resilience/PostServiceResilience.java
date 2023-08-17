package com.utsa.PostAddService.services.resilience;

import com.utsa.PostAddService.dto.Comment;
import com.utsa.PostAddService.dto.Post;
import com.utsa.PostAddService.dto.User;
import com.utsa.PostAddService.repositories.CommentRepository;
import com.utsa.PostAddService.repositories.PostRepository;
import com.utsa.PostAddService.repositories.UserRepository;
import com.utsa.PostAddService.services.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Service
public class PostServiceResilience implements PostService {

    Logger logger= LoggerFactory.getLogger(PostServiceResilience.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
@Autowired
    UserRepository userRepository;

@CircuitBreaker(name = "service1", fallbackMethod = "postCircuitBreakerFallback")
@RateLimiter(name = "service1", fallbackMethod = "postRateLimiterFallback")
    @Override
    public Post addPost(Post post, int userId) {
        User user=userRepository.findById(userId).orElse(null);
        if (user==null) throw new RuntimeException("The User is null");
        Set<Post> posts=user.getPosts();
        post.setTimeStamp(LocalDateTime.now());
post.setUser(user);
    postRepository.save(post);
    posts.add(post);
    user.setPosts(posts);
    userRepository.save(user);
        logger.info("post saved {}", post);
        String baseUrl="http://feed-service/feed";
        String leafPath= "/feedFollowers/{userId}";
        boolean res=false;
        for (int i=0; i<3;i+=1) {
            try {
                res=restTemplate.postForObject(baseUrl + leafPath, post, Boolean.class, Map.of("userId", userId));
            }catch (Exception e){
logger.warn("Exception occurred {}", e.toString());
            }
            if (res) break;
        }
        return post;
    }

    public Post postCircuitBreakerFallback(Post post, int userId, Throwable t){
    logger.info("In postCircuitBreakerFallback post {} userId {}, err {}", post, userId,t);
    return new Post();
    }
    public Post postRateLimiterFallback(Post post, int userId, Throwable t){
    logger.info("In postRateLimiterFallback post {} userId {}, err {}", post, userId,t);
    return new Post();
    }
    @CircuitBreaker(name = "service1", fallbackMethod = "postCircuitBreakerFallback1")
    @RateLimiter(name = "service1", fallbackMethod = "postRateLimiterFallback1")
    @Override
    public Post removePost(int userId, int postId) {
        User user=userRepository.findById(userId).orElse(null);
        Post post=postRepository.findById(postId).orElse(null);
        if (user==null || post==null) throw new RuntimeException("The User or post is null");
        if (user.getPosts().contains(post)) postRepository.delete(post);
        else throw new RuntimeException("User doesnot contains the post");
        return post;
    }

    public Post postCircuitBreakerFallback1(int userId, int postId, Throwable t){
    logger.info("Inside postCircuitBreakerFallback1 userId {} postId {}, {}", userId, postId, t);
    return new Post();
    }
    public Post postRateLimiterFallback1(int userId, int postId, Throwable t){
    logger.info("Inside postCircuitBreakerFallback1 userId {} postId {}, {}", userId, postId, t);
    return new Post();
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "commentCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "commentRateLimiterFallback")
    @Override
    public Comment addComment(int userId, int postId, Comment comment) {
        User user=userRepository.findById(userId).orElse(null);
        Post post=postRepository.findById(postId).orElse(null);
        if (user==null || post==null) throw new RuntimeException("The User or post is null");
        comment.setUser(user);
        comment.setTimeStamp(LocalDateTime.now());
        comment.setPost(post);
        commentRepository.save(comment);
        Set<Comment> comments=post.getComments();
        comments.add(comment);
post.setComments(comments);
        postRepository.save(post);
        post.getComments().add(comment);
        return comment;
    }

    public Comment commentCircuitBreakerFallback(int userId, int postId, Comment comment, Throwable t){
    logger.info("Inside commentCircuitBreakerFallback userId {} postId {} comment {} {}", userId,postId, comment, t);
    return new Comment();
    }
 public Comment commentRateLimiterFallback(int userId, int postId, Comment comment, Throwable t){
    logger.info("Inside commentRateLimiterFallback userId {} postId {} comment {}, {}", userId,postId, comment, t);
    return new Comment();
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "commentCircuitBreakerFallback1")
    @RateLimiter(name = "service1", fallbackMethod = "commentRateLimiterFallback1")
    @Override
    public Comment removeComment(int commentId, int postId, int userId) {
        User user=userRepository.findById(postId).orElse(null);
        Comment comment=commentRepository.findById(commentId).orElse(null);
        Post post=postRepository.findById(postId).orElse(null);
        if (comment==null || user==null || post==null) throw new RuntimeException("Comment user or post is null");
        if (user.getComments().contains(comment) || (user.getPosts().contains(post) && post.getComments().contains(comment))){
            commentRepository.delete(comment);
        }else {
            throw new RuntimeException("Comment is not by User and Comment is not on user's post");
        }
        return comment;
    }

    public Comment commentCircuitBreakerFallback1(int commentId, int postId, int userId, Throwable t){
        logger.info("Inside commentCircuitBreakerFallback1 userId {} postId {} comment {}, {}", userId,postId, commentId, t);
        return new Comment();
    }
    public Comment commentRateLimiterFallback1(int commentId, int postId, int userId, Throwable t){
        logger.info("Inside commentRateLimiterFallback1 userId {} postId {} comment {}, {}", userId,postId, commentId, t);
        return new Comment();
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "postCircuitBreakerFallback1")
    @RateLimiter(name = "service1", fallbackMethod = "postRateLimiterFallback1")
    @Override
    public Post likePost(int postId, int userId) {
        Post post=postRepository.findById(postId).orElse(null);
        User user=userRepository.findById(userId).orElse(null);
        if (user==null || post==null) throw new RuntimeException("The User or post is null");
        if (user.getPosts().contains(post)) throw new RuntimeException("Owner cannot like his post");
        Set<User> likeList=post.getLikeList();
        if (likeList.contains(user)) likeList.remove(user);
        else likeList.add(user);
        post.setLikeList(likeList);
        post.setLikes(likeList.size());
        postRepository.save(post);
        return post;
    }


    @CircuitBreaker(name = "service1", fallbackMethod = "commentCircuitBreakerFallback2")
    @RateLimiter(name = "service1", fallbackMethod = "commentRateLimiterFallback2")
    @Override
    public Comment likeComment(int commentId, int userId) {
        Comment comment=commentRepository.findById(commentId).orElse(null);
        User user=userRepository.findById(userId).orElse(null);
        if (user==null || comment==null) throw new RuntimeException("The User or comment is null");
        if (user.getComments().contains(comment)) throw new RuntimeException("Owner cannot like his comment");
        Set<User> likeList=comment.getLikeList();
        if (likeList.contains(user)) likeList.remove(user);
        else likeList.add(user);
        comment.setLikeList(likeList);
        comment.setLikes(likeList.size());
        commentRepository.save(comment);
        return comment;
    }

    public Comment commentCircuitBreakerFallback2(int commentId, int userId, Throwable t){
        logger.info("Inside commentCircuitBreakerFallback2 userId {}  comment {}, {}", userId, commentId, t);
        return new Comment();
    }
    public Comment commentRateLimiterFallback2(int commentId, int userId, Throwable t){
        logger.info("Inside commentRateLimiterFallback2 userId {} comment {}, {}", userId, commentId, t);
        return new Comment();
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "postCircuitBreakerFallback2")
    @RateLimiter(name = "service1", fallbackMethod = "postRateLimiterFallback2")
    @Override
    public Post getPost(Integer postId) {
    logger.info("post id got is {}", postId);
        Post post= postRepository.findById(postId).orElse(null);
        if (post==null) throw new RuntimeException("Post is not found");
        return post;
    }

    @Override
    public Comment getComment(int commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Post postCircuitBreakerFallback2(Throwable t){
        logger.info("Inside postCircuitBreakerFallback2 {}",t.toString() );
        return new Post();
    }
    public Post postRateLimiterFallback2(Throwable t){
        logger.info("Inside postCircuitBreakerFallback2 {}",t.toString() );
        return new Post();
    }


}
