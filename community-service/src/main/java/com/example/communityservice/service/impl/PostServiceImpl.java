package com.example.communityservice.service.impl;

import com.example.communityservice.dto.PostDetails;
import com.example.communityservice.entity.Group;
import com.example.communityservice.entity.Post;
import com.example.communityservice.exception.InvalidRequestException;
import com.example.communityservice.repository.GroupRepo;
import com.example.communityservice.repository.PostRepo;
import com.example.communityservice.repository.VoteRepo;
import com.example.communityservice.service.PostService;
import com.example.communityservice.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final WebClient webClient;
    private PostRepo postRepo;
    private GroupRepo groupRepo;
    private VoteService voteService;
    @Override
    public void createPosts(Post post, long id) {
        Group group=groupRepo.findById(id).orElseThrow(()->new InvalidRequestException("Invalid GroupId"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        post.setPatientId(patientId);
        post.setGroup(group);
        postRepo.save(post);
    }

    @Override
    public void updatePosts(Post post, long postId) {
        Post post1=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid Post Id"));

        post1.setDescription(post.getDescription());
        postRepo.save(post1);
    }

    @Override
    public void deletePosts(long postId) {

        Post post1=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid Post Id"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        if(post1.getPatientId()!=patientId)
            throw new InvalidRequestException("Its Not Your Post so dont try it again");
        postRepo.deleteById(postId);
    }

    @Override
    public PostDetails getSinglePostDetils(long postId) {
       Post post=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid PostId"));
       PostDetails postDetails=new PostDetails();
       postDetails.setDescription(post.getDescription());
       postDetails.setPatientId(post.getPatientId());
        String patientName=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatientName/{id}", post.getPatientId())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        postDetails.setAuthor(patientName);
        postDetails.setUpVote(voteService.getAllUpVotes(postId));
        postDetails.setDownVote(voteService.getAllDownVotes(postId));
        return postDetails;
    }


    @Override
    public List<Post> getAllPostByPatientIdWithPerticularGroupId(long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        Group group=groupRepo.findById(groupId).orElseThrow(()->new InvalidRequestException("Invalid GroupId"));
        List<Post>posts=postRepo.findAll();
        List<Post>postList=new ArrayList<>();
        for(Post post: posts)
        {
            if(post.getPatientId()==patientId && post.getGroup().getGroupId()==groupId)
            {
                postList.add(post);
            }
        }
        return postList;
    }

    @Override
    public List<Post> getAllPostByGroupId(long groupId) {
        Group group=groupRepo.findById(groupId).orElseThrow(()->new InvalidRequestException("Invalid GroupId"));
        List<Post>posts=postRepo.findAll();
        List<Post>postList=new ArrayList<>();
        for(Post post: posts)
        {
            if( post.getGroup().getGroupId()==groupId)
            {
                postList.add(post);
            }
        }
        return postList;

    }
}
