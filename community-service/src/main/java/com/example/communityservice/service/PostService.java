package com.example.communityservice.service;

import com.example.communityservice.dto.PostDetails;
import com.example.communityservice.entity.Post;

import java.util.List;

public interface PostService {

    void createPosts(Post post,long id);

    void updatePosts(Post post,long postId);

    void deletePosts(long postId);

    PostDetails getSinglePostDetils(long postId);


    List<Post> getAllPostByPatientIdWithPerticularGroupId(long groupId);
    List<Post>getAllPostByGroupId(long groupId);

}
