package com.example.communityservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PostDetails {
    private String author;
    private long userId;
    private String description;
    private long upVote;
    private long downVote;
}
