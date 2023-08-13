package com.masai.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @NotEmpty(message = "Title should not be Empty !!")
    private String title;

    @NotEmpty(message = "Content should not be Empty !!")
    private String content;

    private String imageName;

    private LocalDate addedDate;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "userId")
    private MyUser myUser;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, String imageName, LocalDate addedDate, Category category, MyUser myUser, List<Comment> comments) {
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.addedDate = addedDate;
        this.category = category;
        this.myUser = myUser;
        this.comments = comments;
    }
}