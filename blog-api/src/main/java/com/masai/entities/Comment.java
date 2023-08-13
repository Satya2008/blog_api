package com.masai.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private MyUser myUser;

    public Comment(String content, Post post, MyUser myUser) {
        this.content = content;
        this.post = post;
        this.myUser = myUser;
    }
}