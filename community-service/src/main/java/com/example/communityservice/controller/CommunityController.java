package com.example.communityservice.controller;

import com.example.communityservice.dto.PostDetails;
import com.example.communityservice.entity.Group;
import com.example.communityservice.entity.Post;
import com.example.communityservice.service.GroupService;
import com.example.communityservice.service.PostService;
import com.example.communityservice.service.VoteService;
import com.example.communityservice.utill.Constants;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
public class CommunityController {
    private GroupService groupService;
    private PostService postService;
    private VoteService voteService;


    @PostMapping("/create/group")
    public ResponseEntity<String> createGroup(@RequestBody Group group) {
        groupService.createGroup(group);
        return new ResponseEntity<>("Group Created Successfully", HttpStatus.CREATED);
    }


    @PutMapping("/update/group/{groupId}")
    public ResponseEntity<String> updateGroup(@RequestBody Group group, @PathVariable long groupId) {
        groupService.updateGroup(group, groupId);
        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
    }


    @DeleteMapping("/delete/group/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable long groupId) {
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


    @GetMapping("/get/AllGroups")
    public ResponseEntity<Object> getAllGroups() {
        List<Group> groups = groupService.seeAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }


    @PostMapping("/create/post/{id}")
    public ResponseEntity<String> createPosts(@RequestBody Post post, @PathVariable long id) {
        postService.createPosts(post, id);
        return new ResponseEntity<>("Post Created Successfully", HttpStatus.CREATED);
    }


    @PutMapping("/update/post/{postId}")
    public ResponseEntity<String> updatePosts(@RequestBody Post post, @PathVariable long postId) {
        postService.updatePosts(post, postId);
        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
    }


    @DeleteMapping("/delete/post/{id}")
    public ResponseEntity<String> deletePosts(@PathVariable long id) {
        postService.deletePosts(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


    @GetMapping("/getByPatientIdAndGroupId/{groupId}")
    public ResponseEntity<List<Post>> getPatientAndGroupId(@PathVariable long groupId) {
        List<Post> posts = postService.getAllPostByPatientIdWithPerticularGroupId(groupId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping("/getByGroupId/{groupId}")
    public ResponseEntity<List<Post>> getByGroupId(@PathVariable long groupId) {
        List<Post> posts = postService.getAllPostByGroupId(groupId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @PutMapping("/upVotes/{id}")
    public ResponseEntity<String> upVote(@PathVariable long id) {
        voteService.upVotes(id);
        return new ResponseEntity<>("UpVoted", HttpStatus.OK);
    }


    @PutMapping("/downVotes/{id}")
    public ResponseEntity<String> downVote(@PathVariable long id) {
        voteService.downVote(id);
        return new ResponseEntity<>("DownVoted", HttpStatus.OK);
    }


    @GetMapping("/getAllUpVotes/{id}")
    public ResponseEntity<Long> getAllUpVotes(@PathVariable long id) {
        long cnt = voteService.getAllUpVotes(id);
        return new ResponseEntity<>(cnt, HttpStatus.OK);
    }


    @GetMapping("/getAllDownVotes/{id}")
    public ResponseEntity<Long> getAllDownVotes(@PathVariable long id) {
        long cnt = voteService.getAllDownVotes(id);
        return new ResponseEntity<>(cnt, HttpStatus.OK);
    }

    @GetMapping("/getSinglePost/{id}")
    public ResponseEntity<PostDetails> getSinglePost(@PathVariable long id)
    {
        PostDetails postDetails=postService.getSinglePostDetils(id);
        return new ResponseEntity<>(postDetails,HttpStatus.OK);
    }
}
