package com.chat.repository;

import com.chat.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    
    Optional<RoomMember> findByRoomIdAndUserId(Long roomId, Long userId);
    
    List<RoomMember> findByRoomIdAndActiveTrue(Long roomId);
    
    List<RoomMember> findByUserIdAndActiveTrue(Long userId);
    
    Long countByRoomIdAndActiveTrue(Long roomId);
    
    Boolean existsByRoomIdAndUserId(Long roomId, Long userId);
    
    @Query("SELECT rm FROM RoomMember rm WHERE rm.room.id = :roomId AND rm.active = true")
    List<RoomMember> findActiveMembers(@Param("roomId") Long roomId);
}

