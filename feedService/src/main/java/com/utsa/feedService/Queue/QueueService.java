package com.utsa.feedService.Queue;

import com.utsa.feedService.Queue.handlers.Subscriber;
import com.utsa.feedService.Queue.handlers.SubscriberWorker;
import com.utsa.feedService.dto.Post;
import com.utsa.feedService.repository.OfflineRepository;
import com.utsa.feedService.repository.UsernameWsId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueueService {
    Logger logger= LoggerFactory.getLogger(QueueService.class);
    private final Map<Integer, Subscriber> subscriberMap;
    private final Map<Integer, SubscriberWorker> subscriberWorkerMap;


    private final UsernameWsId usernameWsId;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final OfflineRepository offlineRepository;
@Autowired
    public QueueService(final UsernameWsId usernameWsId, SimpMessagingTemplate simpMessagingTemplate, OfflineRepository offlineRepository) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.offlineRepository = offlineRepository;
    this.subscriberMap = new HashMap<>();
        this.subscriberWorkerMap=new HashMap<>();
        this.usernameWsId=usernameWsId;
    }

    public void publishMessages(Integer toSend, Post post) {
    logger.info("Inside publishMessages toSend {} post {}",toSend, post );
        if (!subscriberMap.containsKey(toSend)){
            subscriberMap.put(toSend, new Subscriber(toSend, simpMessagingTemplate, usernameWsId, offlineRepository));
        }
            Subscriber subscriberHandler= subscriberMap.get(toSend);
            subscriberHandler.publish(post);
            publish(toSend);

    }

    public void publish(Integer toSend){
        if (!subscriberMap.containsKey(toSend)){
            subscriberMap.put(toSend, new Subscriber(toSend, simpMessagingTemplate, usernameWsId, offlineRepository));
        }
        logger.info("SubscriberWorkerMap {}", subscriberWorkerMap);
        if (!subscriberWorkerMap.containsKey(toSend)){
            Subscriber subscriberHandler= subscriberMap.get(toSend);
            logger.info("Subscriber map {}",subscriberMap);
            subscriberWorkerMap.put(toSend, new SubscriberWorker(subscriberHandler));
            new Thread(subscriberWorkerMap.get(toSend)).start();

        }
        subscriberWorkerMap.get(toSend).wakeUpIfNeeded();

    }

    public void publishAll(){
    for (Map.Entry<Integer, Subscriber> e: subscriberMap.entrySet()){
        publish(e.getKey());
    }
    }
}
