package com.utsa.PostAddService.dto;

import java.io.Serializable;
import java.util.Objects;

public class FollowerPk implements Serializable {
    private Integer followerId;

    public int getFollowerId() {
        return followerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowerPk that)) return false;
        return getFollowerId() == that.getFollowerId() && getFollowingId() == that.getFollowingId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFollowerId(), getFollowingId());
    }

    @Override
    public String toString() {
        return "FollowerPk{" +
                "followerId=" + followerId +
                ", followingId=" + followingId +
                '}';
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }


    private Integer followingId;
}
