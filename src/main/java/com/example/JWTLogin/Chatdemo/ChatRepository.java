package com.example.JWTLogin.Chatdemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface ChatRepository extends JpaRepository<Chat,Long> {
    //OR 양쪽 form to 자리바꾼 이유 = 내가봤던간 보이든 간
    @Query(value = "SELECT * FROM chat WHERE ((from_Id = :fromId AND to_Id = :toId) OR (from_Id = :toId AND to_Id = :fromId)) AND chat_Id > :chatId ORDER BY chat_Time", nativeQuery = true)
    ArrayList<Chat> findChatById(@Param("fromId") long fromId,@Param("toId") long toId,@Param("chatId") long chatId);

    @Query(value = "SELECT * FROM chat WHERE ((from_Id = :fromId AND to_Id = :toId) OR (from_Id = :toId AND to_Id = :fromId)) AND chat_Id > (SELECT MAX(chat_Id) - :number FROM chat WHERE (from_Id = :fromId AND to_Id = :toId) OR (from_Id = :toId AND to_Id = :fromId)) ORDER BY chat_Time", nativeQuery = true)
    ArrayList<Chat> findChatByRecent(@Param("fromId") long fromId,@Param("toId") long toId,@Param("number") int number);

    @Modifying
    @Query(value = "INSERT INTO chat VALUES(NULL, :chatContent,0, NOW(),:fromId, :toId)", nativeQuery = true)
    int submit(@Param("fromId") long fromId, @Param("toId") long toId,@Param("chatContent") String chatContent);

    @Modifying
    @Query(value = "UPDATE chat SET chat_read = 1 WHERE (from_Id = :fromId AND to_Id = :toId)",nativeQuery = true)
    int readChat(@Param("fromId") long fromId, @Param("toId") long toId);

    @Query(value = "SELECT COUNT(chat_Id) FROM CHAT WHERE to_Id = :userId AND chat_read = 0 ", nativeQuery = true )
    int getAllUnreadChat(@Param("userId") long userId);

}
