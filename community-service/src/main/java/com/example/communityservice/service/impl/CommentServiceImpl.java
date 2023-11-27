package com.example.communityservice.service.impl;

import com.example.communityservice.entity.Comment;
import com.example.communityservice.entity.Post;
import com.example.communityservice.exception.InvalidRequestException;
import com.example.communityservice.repository.CommentRepo;
import com.example.communityservice.repository.GroupRepo;
import com.example.communityservice.repository.PostRepo;
import com.example.communityservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private PostRepo postRepo;
    private GroupRepo groupRepo;
    private CommentRepo commentRepo;

    @Override
    public void createComment(Comment comment,long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());

        Post post=postRepo.findById(postId).orElseThrow(()->new InvalidRequestException("Invalid PostId"));
       comment.setPosts(post);
       comment.setUserId(id);
       commentRepo.save(comment);
    }

    @Override
    public void updateComment(Comment comment, long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        Comment comment1=commentRepo.findById(commentId).orElseThrow(()->new InvalidRequestException("Invalid Comment Id"));
        if(comment1.getUserId()!=id)
            throw new InvalidRequestException("Sorry you are not authorized to change it");
        comment1.setDescription(comment.getDescription());
        commentRepo.save(comment1);
    }

    @Override
    public void deleteComment(long commentId) {
        Comment comment1=commentRepo.findById(commentId).orElseThrow(()->new InvalidRequestException("Invalid Comment Id"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if(comment1.getUserId()!=id && !isAdmin)
            throw new InvalidRequestException("Sorry you are not authorized to change it");
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<Comment> getCommentByPostId(long postId) {
        List<Comment> comments=commentRepo.findAll();
        List<Comment>commentList=new ArrayList<>();
        for(Comment comment: comments)
        {
            if(comment.getPosts().getPostId()==postId)
            {
                commentList.add(comment);
            }
        }
        return commentList;
    }
}
