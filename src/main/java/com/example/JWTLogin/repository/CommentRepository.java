package com.example.JWTLogin.repository;

import com.example.JWTLogin.domain.Comment;
import com.example.JWTLogin.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentsByPost(Post post);
}
