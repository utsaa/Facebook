package com.utsa.feedService.Queue.handlers;

import com.utsa.feedService.dto.Post;
import com.utsa.feedService.dto.UserWs;
import com.utsa.feedService.repository.OfflineRepository;
import com.utsa.feedService.repository.UsernameWsId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.LinkedList;
import java.util.Queue;

public class Subscriber {
    Logger logger = LoggerFactory.getLogger(Subscriber.class);
    private final Queue<Post> posts;
    private final Integer toSend;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public Queue<Post> getMessages() {
        return posts;
    }

    public Integer getToSend() {
        return toSend;
    }

    public UsernameWsId getUsernameWsId() {
        return usernameWsId;
    }




    private  final UsernameWsId usernameWsId;

    private  final OfflineRepository offlineRepository;
    public Subscriber(Integer toSend, final SimpMessagingTemplate simpMessagingTemplate, final UsernameWsId usernameWsId, OfflineRepository offlineRepository) {
        this.toSend=toSend;

        this.simpMessagingTemplate = simpMessagingTemplate;
        this.usernameWsId = usernameWsId;
        this.offlineRepository = offlineRepository;
        posts=new LinkedList<>();
    }

    public synchronized void publish(Post post) {
        posts.add(post);
        logger.info(" User {} post Added {}",toSend, post);

    }


    public synchronized void publishPosts() {
        UserWs userWs=usernameWsId.findById(toSend).orElse(null);
        if (userWs==null || userWs.getWsId()==null){
            logger.info("The id is null for user {}", userWs);
            throw new RuntimeException("The id is null");
        }
        String id =userWs.getWsId();
        for (Post post: posts) {
            simpMessagingTemplate.convertAndSendToUser(id, "/topic/private-messages", post);
            logger.info("User {} Message sent {}", toSend, post);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (offlineRepository.findById(toSend).orElse(null)==null) {
            logger.info("Messages send to User {}", toSend);
            posts.clear();
        }
    }

    public synchronized boolean isMessageDeliverable() {
        logger.info("Username offliners {}, message size {} user {}",offlineRepository.findAll(),posts.size(), toSend  );
        if (posts.size()==0 && offlineRepository.findById(toSend).orElse(null)!=null) offlineRepository.deleteById(toSend);
        return (offlineRepository.findById(toSend).orElse(null)!=null) && posts.size()>0;
    }
}
