package com.example.communityservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "votes")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@Getter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long voteId;
    private long patientId;
    private boolean upVote;
    private boolean downVote;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


}
