package com.example.communityservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;
    private long userId;
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Vote> votes=new ArrayList<>();

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Comment> comments=new ArrayList<>();
}
