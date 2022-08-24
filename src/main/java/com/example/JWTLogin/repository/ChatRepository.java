package com.example.JWTLogin.repository;

import com.example.JWTLogin.domain.Chat;
import com.example.JWTLogin.web.dto.ChatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


public interface ChatRepository extends JpaRepository<Chat,Long> {
    //OR 양쪽 form to 자리바꾼 이유 = 내가봤던간 보이든 간
    @Query(value = "SELECT * FROM chat WHERE ((from_Id = :fromId AND to_Id = :toId) OR (from_Id = :toId AND to_Id = :fromId)) AND chat_Id > :chatid ORDER BY chat_Time", nativeQuery = true)
    ArrayList<Chat> findChatById(@Param("fromId") long fromId,@Param("toId") long toId,@Param("chatid") int chatid);

    @Query(value = "SELECT * FROM chat WHERE ((from_Id = :fromId AND to_Id = :toId) OR (from_Id = :toId AND to_Id = :fromId)) AND chat_Id > (SELECT MAX(chat_Id) - :number FROM chat) ORDER BY chat_Time", nativeQuery = true)
    ArrayList<Chat> findChatByRecent(@Param("fromId") long fromId,@Param("toId") long toId,@Param("number") int number);

    @Modifying
    @Query(value = "INSERT INTO chat VALUES(NULL, :chatContent, NOW(),:fromId, :toId)", nativeQuery = true)
    int submit(@Param("fromId") long fromId, @Param("toId") long toId,@Param("chatContent") String chatContent);
}
