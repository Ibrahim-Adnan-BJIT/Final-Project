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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collection;
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
        post.setUserId(id1);
        post.setGroup(group);
        postRepo.save(post);
    }

    @Override
    public void updatePosts(Post post, long postId) {
        Post post1=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid Post Id"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        if(post1.getUserId()!=id)
        {
            throw new InvalidRequestException("You are not authorized to update this");
        }
        post1.setDescription(post.getDescription());
        postRepo.save(post1);
    }

    @Override
    public void deletePosts(long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new InvalidRequestException("Invalid Post Id"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (!isAdmin(authorities)) {
            long userId = getUserId(authentication);

            if (post.getUserId()!=userId) {
                throw new InvalidRequestException("It's not your post, so don't try to delete it");
            }
        }

        postRepo.deleteById(postId);
    }

    private boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private long getUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }

    private Long getPatientId(long userId) {
        return webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", userId)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }


    @Override
    public PostDetails getSinglePostDetils(long postId) {
       Post post=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid PostId"));
       PostDetails postDetails=new PostDetails();
       postDetails.setDescription(post.getDescription());
       postDetails.setUserId(post.getUserId());
        String userName=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getUserName/"+post.getUserId())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        postDetails.setAuthor(userName);
        postDetails.setUpVote(voteService.getAllUpVotes(postId));
        postDetails.setDownVote(voteService.getAllDownVotes(postId));
        return postDetails;
    }


    @Override
    public List<Post> getAllPostByPatientIdWithPerticularGroupId(long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Group group=groupRepo.findById(groupId).orElseThrow(()->new InvalidRequestException("Invalid GroupId"));
        List<Post>posts=postRepo.findAll();
        List<Post>postList=new ArrayList<>();
        for(Post post: posts)
        {
            if(post.getUserId()==id1 && post.getGroup().getGroupId()==groupId)
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
