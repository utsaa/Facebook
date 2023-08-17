package com.utsa.AddUserService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "comment")
public class Comment {
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public Set<User> getLikeList() {
        return likeList;
    }

    public void setLikeList(Set<User> likeList) {
        this.likeList = likeList;
    }

    public Integer getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", timeStamp=" + timeStamp +
                ", likes=" + likes +
                ", user="+ user+
                '}';
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return getCommentId() == comment.getCommentId() && getLikes() == comment.getLikes() && Objects.equals(getPost(), comment.getPost()) && Objects.equals(getContent(), comment.getContent()) && Objects.equals(getTimeStamp(), comment.getTimeStamp()) && Objects.equals(getLikeList(), comment.getLikeList()) && Objects.equals(getUser(), comment.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommentId(),  getContent(), getTimeStamp(),  getLikes(), getUser());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @JsonIgnore
    @ManyToOne(targetEntity = Post.class)
    private Post post;
    private String content;

    private LocalDateTime timeStamp;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
    private Set<User> likeList;

    private Integer likes;

    @JsonIgnore
    @ManyToOne( targetEntity = User.class)
    private User user;

}
