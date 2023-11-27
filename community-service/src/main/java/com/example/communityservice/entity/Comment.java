package com.example.communityservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;
    private String description;
    private long userId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post posts;


}
