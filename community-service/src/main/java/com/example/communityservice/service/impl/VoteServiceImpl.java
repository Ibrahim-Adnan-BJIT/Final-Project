package com.example.communityservice.service.impl;

import com.example.communityservice.entity.Post;
import com.example.communityservice.entity.Vote;
import com.example.communityservice.exception.InvalidRequestException;
import com.example.communityservice.repository.PostRepo;
import com.example.communityservice.repository.VoteRepo;
import com.example.communityservice.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {
    private VoteRepo voteRepo;
    private PostRepo postRepo;
    private final WebClient webClient;
    @Override
    public void upVotes(long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        Post post=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid PostId"));
        Optional<Vote> vote1= Optional.ofNullable(voteRepo.findByPatientId(patientId));
        if(vote1.isEmpty()){
        Vote vote=new Vote();
       vote.setUpVote(true);
       vote.setDownVote(false);
        vote.setPatientId(patientId);
        vote.setPost(post);
        voteRepo.save(vote);}
        else
        {
            vote1.get().setUpVote(true);
            vote1.get().setDownVote(false);
            voteRepo.save(vote1.get());
        }

    }

    @Override
    public void downVote(long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        Post post=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid PostId"));
        Optional<Vote> vote1= Optional.ofNullable(voteRepo.findByPatientId(patientId));
        if(vote1.isEmpty()){
            Vote vote=new Vote();
            vote.setUpVote(false);
            vote.setDownVote(true);
            vote.setPatientId(patientId);
            vote.setPost(post);
            voteRepo.save(vote);}
        else
        {
            vote1.get().setUpVote(false);
            vote1.get().setDownVote(true);
            voteRepo.save(vote1.get());
        }
    }

    @Override
    public long getAllUpVotes(long postId) {
        Post post=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid PostId"));
        List<Vote>votes=voteRepo.findAll();
        long cnt=0;
        for(Vote vote:votes)
        {
            if(vote.getPost().getPostId()==postId)
            {
                if(vote.isUpVote())
                {
                    ++cnt;
                }
            }
        }
        return cnt;
    }

    @Override
    public long getAllDownVotes(long postId) {
        Post post=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid PostId"));
        List<Vote>votes=voteRepo.findAll();
        long cnt=0;
        for(Vote vote:votes)
        {
            if(vote.getPost().getPostId()==postId)
            {
                if(vote.isDownVote())
                {
                    ++cnt;
                }
            }
        }
        return cnt;
    }
}
