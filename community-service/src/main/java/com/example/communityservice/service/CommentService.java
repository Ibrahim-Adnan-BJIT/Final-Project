package com.example.communityservice.service;

import com.example.communityservice.entity.Comment;

import java.util.List;

public interface CommentService {

    void createComment(Comment comment,long postId);

    void updateComment(Comment comment,long commentId);

    void deleteComment(long commentId);

    List<Comment> getCommentByPostId(long postId);

}
