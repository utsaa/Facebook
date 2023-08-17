package com.utsa.AddUserService.dto;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

public class FollowerPk implements Serializable {
    private Integer followerId;

    public Integer getFollowerId() {
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

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Integer followingId) {
        this.followingId = followingId;
    }


    private int followingId;
}
