package com.utsa.AddUserService.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "followingFollower")
@IdClass(FollowerPk.class)
public class FollowingFollower implements Serializable {

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }


    @Id
    private Integer followerId;

    @Override
    public String toString() {
        return "FollowingFollower{" +
                "followerId=" + followerId +
                ", followingId=" + followingId +
                ", follower=" + follower +
                ", following=" + following +
                '}';
    }

    public Integer getFollowerId() {
        return followerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowingFollower that)) return false;
        return getFollowerId() == that.getFollowerId() && getFollowingId() == that.getFollowingId() && Objects.equals(getFollower(), that.getFollower()) && Objects.equals(getFollowing(), that.getFollowing());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFollowerId(), getFollowingId(), getFollower(), getFollowing());
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }

    @Id
    private Integer followingId;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User follower;


    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User following;
}
