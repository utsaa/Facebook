package com.utsa.feedService.service;

import com.utsa.feedService.dto.User;
import com.utsa.feedService.dto.UserWs;
import com.utsa.feedService.repository.UserRepository;
import com.utsa.feedService.repository.UsernameWsId;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    Logger logger= LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;
@Autowired
    UsernameWsId usernameWsId;

@CircuitBreaker(name = "service1", fallbackMethod = "IntegerCircuitBreakerFallback")
@RateLimiter(name = "service1", fallbackMethod = "IntegerCircuitBreakerFallback")
    @Override
    public Integer getUserId(User user) {
        List<Integer> integers=userRepository.getUserByNameAndPasswd(user.getName(), user.getPassword());
        if (integers.size()>0) return integers.get(0);
        throw new RuntimeException("User is not found");
    }

    public Integer IntegerCircuitBreakerFallback(User user, Throwable t){
    logger.info("In IntegerCircuitBreakerFallback user {} err {}", user.toString(), t.toString());
    return 0;
    }

    @CircuitBreaker(name = "service1", fallbackMethod = "voidCircuitBreakerFallback")
    @RateLimiter(name = "service1", fallbackMethod = "voidCircuitBreakerFallback")
    @Override
    public void addUserWs(Integer userId, String wsId) {
        UserWs userWs=usernameWsId.findById(userId).orElse(null);
        if (userWs==null){
            userWs=new UserWs();
            userWs.setUserId(userId);
        }
        userWs.setWsId(wsId);
        usernameWsId.save(userWs);


    }

    public void voidCircuitBreakerFallback(Throwable t){
    logger.info("Inside voidCircuitBreakerFallback {}", t.toString());
    }
}
