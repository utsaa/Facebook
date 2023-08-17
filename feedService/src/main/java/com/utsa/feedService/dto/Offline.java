package com.utsa.feedService.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "offline")
public class Offline {
    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Offline{" +
                "userId=" + userId +
                '}';
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offline offline)) return false;
        return Objects.equals(getUserId(), offline.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }

    @Id
    private Integer userId;
}
