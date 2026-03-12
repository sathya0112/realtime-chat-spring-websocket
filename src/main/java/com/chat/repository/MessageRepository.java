package com.chat.repository;

import com.chat.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    @Query("SELECT m FROM Message m WHERE m.room.id = :roomId AND m.deleted = false ORDER BY m.createdAt DESC")
    Page<Message> findByRoomIdOrderByCreatedAtDesc(@Param("roomId") Long roomId, Pageable pageable);
    
    @Query("SELECT m FROM Message m WHERE m.room.id = :roomId AND m.deleted = false ORDER BY m.createdAt ASC")
    List<Message> findByRoomIdOrderByCreatedAtAsc(@Param("roomId") Long roomId);
    
    Long countByRoomIdAndDeletedFalse(Long roomId);
}

