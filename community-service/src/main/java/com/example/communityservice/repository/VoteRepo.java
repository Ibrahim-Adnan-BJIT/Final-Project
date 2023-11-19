package com.example.communityservice.repository;

import com.example.communityservice.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepo extends JpaRepository<Vote,Long> {

    Vote findByPatientId(long id);
}
