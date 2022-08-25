package com.example.JWTLogin.repository;

import com.example.JWTLogin.domain.Likes;
import com.example.JWTLogin.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    void deleteLikesByPost(Post post);

    @Modifying
    @Query(value = "INSERT INTO likes(post_id, member_id) VALUES(:postId, :memberId)", nativeQuery = true)
    void likes(long postId, long memberId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE post_id = :postId AND member_id = :memberId", nativeQuery = true)
    void unLikes(long postId, long memberId);
}