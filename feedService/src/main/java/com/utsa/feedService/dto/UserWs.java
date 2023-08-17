package com.utsa.feedService.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "userWs")
public class UserWs {
    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserWs{" +
                "userId=" + userId +
                ", wsId='" + wsId + '\'' +
                '}';
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserWs userWs)) return false;
        return Objects.equals(getUserId(), userWs.getUserId()) && Objects.equals(getWsId(), userWs.getWsId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getWsId());
    }

    public String getWsId() {
        return wsId;
    }

    public void setWsId(String wsId) {
        this.wsId = wsId;
    }

    @Id
    private Integer userId;

    private String wsId;
}
