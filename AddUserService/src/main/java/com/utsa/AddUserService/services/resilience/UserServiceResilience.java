package com.utsa.AddUserService.services.resilience;

import com.utsa.AddUserService.dto.FollowerPk;
import com.utsa.AddUserService.dto.FollowingFollower;
import com.utsa.AddUserService.dto.User;
import com.utsa.AddUserService.repositories.FollowerRepository;
import com.utsa.AddUserService.repositories.UserRepository;
import com.utsa.AddUserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceResilience implements UserService {

    Logger logger= LoggerFactory.getLogger(UserServiceResilience.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowerRepository followerRepository;

    @CircuitBreaker(name = "service1", fallbackMethod = "userCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "userRateLimiterFallback")
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "userCircuitBreakerFallback1")
    @RateLimiter(name = "service1", fallbackMethod = "userRateLimiterFallback1")
    @Override
    public User deleteUser(int userId) {
        User user=userRepository.findById(userId).orElse(null);
        if(user==null) throw new RuntimeException("User found is null");
        followerRepository.deleteFollowers1( userId);
        followerRepository.deleteFollowers2( userId);
        userRepository.delete(user);
         return user;

    }

    @CircuitBreaker(name = "service1", fallbackMethod = "followerCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "followerRateLimiterFallback")
    @Override
    public FollowingFollower addFollower(int userId, int followerId) {
        User follower = userRepository.findById(userId).orElse(null);
        User following =userRepository.findById(followerId).orElse(null);
        if (follower==null || following==null) throw  new RuntimeException("Following or follower is null");
        FollowingFollower followingFollower= new FollowingFollower();
        followingFollower.setFollowerId(follower.getUserId());
        followingFollower.setFollowingId(following.getUserId());
        followingFollower.setFollower(follower);
        followingFollower.setFollowing(following);
        followerRepository.save(followingFollower);
        return followingFollower;
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "followerCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "followerRateLimiterFallback")
    @Override
    public FollowingFollower removeFollower(int userId, int followerId) {

        FollowerPk followerPk=new FollowerPk();
        followerPk.setFollowerId(userId);
        followerPk.setFollowingId(followerId);
        FollowingFollower followingFollower=followerRepository.findById(followerPk).orElse(null);
        if (followingFollower == null) throw new RuntimeException("The following follower not found look at pk");
        followerRepository.deleteById(followerPk);
        return followingFollower;
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "listUserCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "listUserRateLimiterFallback")
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "listUserCircuitBreakerFallback1")
    @RateLimiter(name = "service1", fallbackMethod = "listUserRateLimiterFallback1")
    @Override
    public List<User> getUserFollowers(int userId) {
        return followerRepository.getUserFollower(userId);
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "listFollowerCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "listFollowerRateLimiterFallback")
    @Override
    public List<FollowingFollower> getFollowers() {
        return followerRepository.findAll();
    }

    public User userCircuitBreakerFallback(User user, Throwable t){
        logger.info("Inside user {} circuitbreaker falback {}",user, t );
        return new User();
    }
    public User userRateLimiterFallback(User user, Throwable t){
        logger.info("Inside user {} rate limiter falback {}",user, t );
        return new User();
    }
public User userCircuitBreakerFallback1(int userId, Throwable t){
        logger.info("Inside user {} circuitbreaker falback {}",userId, t );
        return new User();
    }
    public User userRateLimiterFallback1(int userId, Throwable t){
        logger.info("Inside user {} rate limiter falback {}",userId, t );
        return new User();
    }

    public FollowingFollower followerCircuitBreakerFallback(int userId, int followerId, Throwable t){
        logger.info("Inside Following {} Follower {} circuit breaker fall back {}", userId, followerId, t);
        return new FollowingFollower();
    }
public FollowingFollower followerRateLimiterFallback(int userId, int followerId, Throwable t){
        logger.info("Inside Following {} Follower {} rate limiter fall back {}", userId, followerId, t);
        return new FollowingFollower();
    }

    public List<User> listUserCircuitBreakerFallback(Throwable t){
        logger.info("Inside list user circuit breaker fallback {}", t);
        return new ArrayList<>();
    }
public List<User> listUserRateLimiterFallback(Throwable t){
        logger.info("Inside list user rate limiter fallback {}", t);
        return new ArrayList<>();
    }
    public List<User> listUserCircuitBreakerFallback1(int userId, Throwable t){
        logger.info("Inside list user circuit breaker fallback1 {}", t);
        return new ArrayList<>();
    }
public List<User> listUserRateLimiterFallback1(int userId, Throwable t){
        logger.info("Inside list user rate limiter fallback1 {}", t);
        return new ArrayList<>();
    }

    public List<FollowingFollower> listFollowerCircuitBreakerFallback(Throwable t){
        logger.info(" List follower circuitbreaker fallback {}",t);
        return new ArrayList<>();
    }
public List<FollowingFollower> listFollowerRateLimiterFallback(Throwable t){
        logger.info(" List follower rate limiter fallback {}",t);
        return new ArrayList<>();
    }
}
