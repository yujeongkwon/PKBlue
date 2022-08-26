package com.example.JWTLogin.repository;

import com.example.JWTLogin.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM post WHERE member_id IN (SELECT to_member_id FROM FOLLOW WHERE from_member_id = :fromMemberId) ORDER BY id DESC", nativeQuery = true)
    Page<Post> mainStory(long fromMemberId, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE tag LIKE :tag OR tag LIKE CONCAT('%,',:tag,',%') OR tag LIKE CONCAT('%,',:tag) " + "OR tag LIKE CONCAT(:tag,',%') ORDER BY id DESC", nativeQuery = true)
    Page<Post> searchResult(String tag, Pageable pageable);
}