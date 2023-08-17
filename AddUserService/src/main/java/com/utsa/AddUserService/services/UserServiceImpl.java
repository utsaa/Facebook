package com.utsa.AddUserService.services;

import com.utsa.AddUserService.dto.FollowingFollower;
import com.utsa.AddUserService.dto.User;
import com.utsa.AddUserService.services.resilience.UserServiceResilience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    UserService service;

    @Autowired
    public UserServiceImpl(UserServiceResilience userServiceResilience){
        service=userServiceResilience;
    }
    @Override
    public User addUser(User user) {
        return service.addUser(user);
    }

    @Override
    public User deleteUser(int userId) {
        return service.deleteUser(userId);
    }

    @Override
    public FollowingFollower addFollower(int userId, int followerId) {
        return service.addFollower(userId,followerId);
    }

    @Override
    public FollowingFollower removeFollower(int userId, int followerId) {
        return service.removeFollower(userId, followerId);
    }

    @Override
    public List<User> getUsers() {
        return service.getUsers();
    }

    @Override
    public List<User> getUserFollowers(int userId) {
        return service.getUserFollowers(userId);
    }

    @Override
    public List<FollowingFollower> getFollowers() {
        return service.getFollowers();
    }
}
