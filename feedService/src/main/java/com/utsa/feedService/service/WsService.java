package com.utsa.feedService.service;

import com.utsa.feedService.Queue.QueueService;
import com.utsa.feedService.dto.Offline;
import com.utsa.feedService.dto.Post;
import com.utsa.feedService.dto.User;
import com.utsa.feedService.dto.UserWs;
import com.utsa.feedService.repository.FollowerRepository;
import com.utsa.feedService.repository.OfflineRepository;
import com.utsa.feedService.repository.UsernameWsId;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WsService implements IWsService{

    Logger logger= LoggerFactory.getLogger(WsService.class);

    @Autowired
    public  SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    public   UsernameWsId usernameWsId;
    @Autowired
    public OfflineRepository offlineRepository;
@Autowired
    public QueueService queueService;


    @Autowired
    public FollowerRepository followerRepository;

    @CircuitBreaker(name = "service1", fallbackMethod = "boolCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "boolCircuitBreakerFallback")
    @Override
    public boolean notifyUser(Post post, int userId) {

        List<User> users=followerRepository.getUserFollower(userId);
        logger.info("The user followers of user Id {} are {}", userId, users);
        for (User u : users) {
            UserWs userWs = usernameWsId.findById(u.getUserId()).orElse(null);
            logger.info("The username user {} WsId repo {} ", u , userWs);
            if (userWs==null || userWs.getWsId()==null){
                logger.info("The id is null for user {}", userWs);
                continue;
            }

                Offline offline=offlineRepository.findById(u.getUserId()).orElse(null);
                if(offline==null) {
                    offline=new Offline();
                    offline.setUserId(u.getUserId());
                    offlineRepository.save(offline);
                }

            queueService.publishMessages(u.getUserId(), post);
        }
        return true;

    }

    public boolean boolCircuitBreakerFallback(Post post, int userId, Throwable t){
        logger.info("Inside boolCircuitBreakerFallback post {} user Id {} err {}", post, userId, t.toString());
        return false;
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "voidCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "voidCircuitBreakerFallback")
    @Override
    public void deleteOfflineUsers(Integer sender) {
        offlineRepository.deleteById(sender);
        logger.info("The sender is online {}", sender);
    }

    public void voidCircuitBreakerFallback(Integer sender, Throwable t){
        logger.info("Inside voidCircuitBreakerFallback sender {} err {}", sender, t.toString());
    }
}
