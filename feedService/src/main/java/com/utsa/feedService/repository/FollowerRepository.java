package com.utsa.feedService.repository;


import com.utsa.feedService.dto.FollowerPk;
import com.utsa.feedService.dto.FollowingFollower;
import com.utsa.feedService.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<FollowingFollower, FollowerPk> {

    @Query("select f.following from FollowingFollower f where f.follower.id=:userId")
    List<User> getUserFollower(@Param("userId") int userId);
}
