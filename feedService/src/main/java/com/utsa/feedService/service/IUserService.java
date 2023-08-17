package com.utsa.feedService.service;

import com.utsa.feedService.dto.User;

public interface IUserService {
    Integer getUserId(User user);

    void addUserWs(Integer userId, String wsId);
}
