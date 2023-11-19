package com.example.communityservice.service;

public interface VoteService {

    void upVotes(long postId);
    void downVote(long postId);

    long getAllUpVotes(long postId);
    long getAllDownVotes(long postId);
}
