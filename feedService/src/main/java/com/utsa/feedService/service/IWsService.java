package com.utsa.feedService.service;

import com.utsa.feedService.dto.Post;

public interface IWsService {
    boolean notifyUser(Post post, int userId);

    void deleteOfflineUsers(Integer sender);
}
