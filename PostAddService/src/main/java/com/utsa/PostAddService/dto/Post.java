package com.utsa.PostAddService.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    private String content;

    private LocalDateTime timeStamp;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
    private Set<User> likeList;

    private Integer likes=0;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Comment.class, mappedBy = "post")
//    @JsonManagedReference
    private Set<Comment> comments = new HashSet<>();

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", content='" + content + '\'' +
                ", timeStamp=" + timeStamp +
                ", likes=" + likes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return getPostId() == post.getPostId() && getLikes() == post.getLikes() && Objects.equals(getContent(), post.getContent()) && Objects.equals(getTimeStamp(), post.getTimeStamp()) && Objects.equals(getUser(), post.getUser()) && Objects.equals(getLikeList(), post.getLikeList()) && Objects.equals(getComments(), post.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getContent(), getTimeStamp(), getLikes());
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getLikeList() {
        return likeList;
    }

    public void setLikeList(Set<User> likeList) {
        this.likeList = likeList;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
