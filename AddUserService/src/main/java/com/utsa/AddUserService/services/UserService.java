package com.utsa.AddUserService.services;

import com.utsa.AddUserService.dto.FollowingFollower;
import com.utsa.AddUserService.dto.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    User deleteUser(int userId);


    FollowingFollower addFollower(int userId, int followerId);

    FollowingFollower removeFollower(int userId, int followerId);

    List<User> getUsers();

    List<User> getUserFollowers(int userId);

    List<FollowingFollower> getFollowers();
}
