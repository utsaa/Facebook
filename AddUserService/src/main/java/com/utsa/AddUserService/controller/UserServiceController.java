package com.utsa.AddUserService.controller;

import com.utsa.AddUserService.dto.FollowingFollower;
import com.utsa.AddUserService.dto.User;
import com.utsa.AddUserService.services.UserService;
import com.utsa.AddUserService.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserServiceController {


    UserService userService;
    @Autowired
    public UserServiceController(UserServiceImpl userService){
        this.userService=userService;
    }
    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }
    @DeleteMapping("/delete/{id}")
    public User deleteUser(@PathVariable("id") int userId){
        return userService.deleteUser(userId);
    }
    @GetMapping("/addFollower/{userId}/{followerId}")
    public FollowingFollower addFollower(@PathVariable("userId") int userId,@PathVariable("followerId") int followerId){
        return userService.addFollower(userId, followerId);
    }

    @DeleteMapping("/removeFollower/{userId}/{followerId}")
    public FollowingFollower removeFollower(@PathVariable("userId") int userId, @PathVariable("followerId") int followerId){
        return userService.removeFollower(userId, followerId);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/userFollowers/{id}")
    public List<User> getUserFollowers(@PathVariable("id") int userId){
        return userService.getUserFollowers(userId);
    }

    @GetMapping("/followers")
    public List<FollowingFollower> getFollowers(){
        return userService.getFollowers();
    }

}
