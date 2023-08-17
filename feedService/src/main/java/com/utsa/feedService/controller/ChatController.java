package com.utsa.feedService.controller;


import com.utsa.feedService.Queue.QueueService;
import com.utsa.feedService.dto.User;
import com.utsa.feedService.service.UserService;
import com.utsa.feedService.service.WsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ChatController {
    Logger logger = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    WsService wsService;

    @Autowired
    UserService userService;
    @Autowired
    QueueService queueService;
    @MessageMapping("/chat.register")
    @SendTo("topic/public")
    public User register(@Payload User user, SimpMessageHeaderAccessor simpMessageHeaderAccessor, final Principal principal){
        Integer userId =userService.getUserId(user);
        if (userId==null) throw new RuntimeException("user is not present");
        simpMessageHeaderAccessor.getSessionAttributes().put("username", userId);
        logger.info("Name in register {}", principal.getName());
        userService.addUserWs(userId, principal.getName());
        queueService.publish(userId);

        return user;
    }




    @MessageMapping("/private-message2")

    public User getPrivateMessage2(@Payload User user){
        Integer userId =userService.getUserId(user);
        if (userId==null) throw new RuntimeException("user is not present");
wsService.deleteOfflineUsers(userId);
        return user;
    }



}
