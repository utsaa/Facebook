package com.utsa.AddUserService.repositories;

import com.utsa.AddUserService.dto.FollowerPk;
import com.utsa.AddUserService.dto.FollowingFollower;
import com.utsa.AddUserService.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<FollowingFollower, FollowerPk> {

    @Query("select f.following from FollowingFollower f where f.follower.id=:userId")
    List<User> getUserFollower(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("delete from FollowingFollower f where f.follower.id=:userId")
    void deleteFollowers1(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("delete from FollowingFollower f where f.following.id=:userId")
    void deleteFollowers2(@Param("userId") int userId);
}
